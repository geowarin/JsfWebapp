package com.aziphael.webapp.jsf.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

public class MyEvent extends FacesEvent {

	public MyEvent(UIComponent component) {
		super(component);
	}

	@Override
	public boolean isAppropriateListener(FacesListener listener) {
		return listener instanceof MyEventListener;
	}

	@Override
	public void processListener(FacesListener listener) {
		((MyEventListener) listener).listenToMyEvent(this);
	}

}
