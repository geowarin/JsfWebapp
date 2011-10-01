package com.aziphael.webapp.listener;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatePhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger("ValidatePhaseListener");

	@Override
	public void beforePhase(PhaseEvent event) {

		final FacesContext facesContext = event.getFacesContext();
		String focus = null;

		final StringBuilder highlight = new StringBuilder();
        for (final Iterator<String> clientIdsWithMessages = facesContext.getClientIdsWithMessages(); clientIdsWithMessages.hasNext();) {
            
        	final String clientIdWithMessages = clientIdsWithMessages.next();
            if (focus == null)
                focus = clientIdWithMessages;
            
            highlight.append(clientIdWithMessages);
            if (clientIdsWithMessages.hasNext())
                highlight.append(",");
        }

        // Set ${focus} and ${highlight} in JSP.
        facesContext.getExternalContext().getRequestMap().put("focus", focus);
        facesContext.getExternalContext().getRequestMap().put("highlight", highlight.toString());
        log.info("focus = " + focus);
        log.info("highlight = " + highlight);
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
