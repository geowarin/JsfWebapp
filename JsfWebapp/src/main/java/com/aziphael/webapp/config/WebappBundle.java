package com.aziphael.webapp.config;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public enum WebappBundle {
	
	VALIDATION(FacesMessage.SEVERITY_ERROR);
	
	private final Severity defaultSeverity;
	
	public FacesMessage getMessage(String messageId, Object...params) {
		return BundledMessageFactory.getMessageFromBundle(name().toLowerCase(), messageId, defaultSeverity, params);
	}
	
	public FacesMessage getMessage(String messageId, Severity severity, Object...params) {
		return BundledMessageFactory.getMessageFromBundle(name().toLowerCase(), messageId, severity, params);
	}
	
	private WebappBundle() {
		defaultSeverity = FacesMessage.SEVERITY_INFO;
	}
	
	private WebappBundle(Severity defaultSeverity) {
		this.defaultSeverity = defaultSeverity;
	}
}
