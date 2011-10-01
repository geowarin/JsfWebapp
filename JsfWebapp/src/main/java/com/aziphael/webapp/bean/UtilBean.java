package com.aziphael.webapp.bean;

import javax.faces.context.FacesContext;

/**
 * Bean application pour faire mumuse.
 * 
 * @author aziphael
 *
 */
public class UtilBean {

	public boolean isFacesMessagePresent() {
		return FacesContext.getCurrentInstance().getMessages(null).hasNext();
	}
}
