package com.aziphael.webapp.listener;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.FacesUtil;

public class DebugPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	private static final String SEPARATOR = "-----------------------------------------------------------------------------------";
	private static final Logger log = LoggerFactory.getLogger("DebugPhaseListener");

	private void logCentered(String info) {
		final String begin = "##" + StringUtils.repeat(" ", (SEPARATOR.length() - 4 - info.length()) / 2);
		log.info(begin + info + StringUtils.reverse(begin));
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		log.info(SEPARATOR);
		logCentered(event.getPhaseId().toString());
		log.info(SEPARATOR);
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId()))
			log.info(SEPARATOR);
	}

	public StringBuilder logPhase(PhaseEvent event) {

		final FacesContext facesContext = FacesContext.getCurrentInstance();

		final StringBuilder builder = new StringBuilder();
		builder.append("Phase=" + event.getPhaseId() + " - ");
		if (facesContext.getViewRoot() != null) {
			final String actionUrl = facesContext.getApplication().getViewHandler()
					.getActionURL(facesContext, facesContext.getViewRoot().getViewId());
			builder.append("actionUrl=" + actionUrl + " - ");
		} else {
			builder.append("viewRoot is null" + " - ");
		}
		builder.append("method=" + FacesUtil.getRequest().getMethod() + " - ");
		// builder.append("bean=" + getBean() + " - ");
		// if (getBean() != null)
		// builder.append("text=" + getBean().getText());

		return builder;
	}

//	private DebugBean getBean() {
//		return (DebugBean) FacesUtil.getRequest().getAttribute("test");
//	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
