package com.aziphael.webapp.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
