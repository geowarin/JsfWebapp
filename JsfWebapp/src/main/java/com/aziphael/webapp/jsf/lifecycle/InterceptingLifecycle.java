package com.aziphael.webapp.jsf.lifecycle;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterceptingLifecycle extends Lifecycle {

	private static final Logger log = LoggerFactory.getLogger(InterceptingLifecycle.class);
	private final Lifecycle wrapped;

	public InterceptingLifecycle(Lifecycle standardLifecycle) {
		wrapped = standardLifecycle;
	}

	Lifecycle getWrapped() {
		return wrapped;
	}

	@Override
	public void addPhaseListener(PhaseListener listener) {
		log.info("addPhaseListener(" + listener + ")");
		getWrapped().addPhaseListener(listener);
	}

	@Override
	public void execute(FacesContext context) throws FacesException {
		try {
			log.info("execute(...)");
			getWrapped().execute(context);
		} catch (FacesException intercepted) {
			log.info("===>>> Intercepted Throwable from execute()");
			intercepted.printStackTrace();
			FacesContext.getCurrentInstance().renderResponse();
			throw intercepted;
		}
	}

	@Override
	public PhaseListener[] getPhaseListeners() {
		return getWrapped().getPhaseListeners();
	}

	@Override
	public void removePhaseListener(PhaseListener listener) {
		getWrapped().removePhaseListener(listener);
	}

	@Override
	public void render(FacesContext context) throws FacesException {
		try {
			log.info("render(...)");
			getWrapped().render(context);
		} catch (FacesException intercepted) {
			log.info("===>>> Intercepted Throwable from render()");
			intercepted.printStackTrace();
			throw intercepted;
		}
	}

}
