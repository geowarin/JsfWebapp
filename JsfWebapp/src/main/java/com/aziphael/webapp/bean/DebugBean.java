package com.aziphael.webapp.bean;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.xml.bind.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.FacesUtil;

public class DebugBean {

	private String text;
	private static final Logger log = LoggerFactory.getLogger(DebugBean.class);

	public DebugBean() {
		log.info("kikoo");
		log.info("Request Bean <init> "
				+ FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("lol"));
	}

	public void valueChanged(ValueChangeEvent event) {
		log.info("--> valueChanged -- oldVal=" + event.getOldValue() + " - newVal=" + event.getNewValue());
	}

	public void actionLaunched(ActionEvent event) {
		log.info("--> actionLaunched - component=" + event.getComponent() + " - phaseId=" + event.getPhaseId()
				+ " - source=" + event.getSource());
	}

	public void listenerRedirecter(ActionEvent event) throws IOException {
		log.info("--> custom redirect");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("custom redirect"));
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("lol", "kikoo");
		FacesUtil.redirect("/MyWebapp/pages/test2.jsf");
	}

	public String noredirectAction() {
		log.info("noredirectAction");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("noredirect"));
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("lol", "kikoo");
		return "noredirect";
	}

	public String redirectAction() {
		log.info("redirectAction");
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("redirect"));
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("lol", "kikoo");
		return "redirect";
	}

	public void validate(FacesContext facesContext, UIComponent component, Object value) throws ValidationException {
		if ("error".equalsIgnoreCase(value.toString())) {
			log.info("bean validation " + component + " -- error");
			throw new ValidationException("pouet pouet");
		} else {
			log.info("bean validation " + component + " -- ok");
		}
	}
	
	public void emptyAction() {
		log.info("DebugBean.emptyAction()");
	}

	public String getText() {
		log.info("getText =" + text);
		return text;
	}

	public void setText(String text) {
		log.info("setText to " + text);
		this.text = text;
	}

}
