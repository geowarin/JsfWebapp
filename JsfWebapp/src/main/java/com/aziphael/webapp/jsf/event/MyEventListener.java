package com.aziphael.webapp.jsf.event;

import javax.faces.event.FacesListener;

public interface MyEventListener extends FacesListener {

	public void listenToMyEvent(MyEvent event);
}
