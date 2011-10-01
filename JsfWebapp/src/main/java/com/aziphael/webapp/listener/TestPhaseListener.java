package com.aziphael.webapp.listener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class TestPhaseListener implements PhaseListener {

//	private static final Logger log = LoggerFactory.getLogger(TestPhaseListener.class);

	private static final long serialVersionUID = 1L;

	public TestPhaseListener() {
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
