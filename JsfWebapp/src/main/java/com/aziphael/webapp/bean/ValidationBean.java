package com.aziphael.webapp.bean;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.config.WebappBundle;
import com.sun.faces.util.MessageFactory;

public class ValidationBean {

	private static final Logger log = LoggerFactory.getLogger(ValidationBean.class);
	private String text = "defaut";

	public ValidationBean() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void onTextChange(ValueChangeEvent event) {
		log.info("ValidationBean.onTextChange() oldVal=" + event.getOldValue() + " - newVal=" + event.getNewValue());
	}

	public void action() {
		log.info("ValidationBean.action()");
		
		final FacesContext context = FacesContext.getCurrentInstance();
		
		// On peut aller chercher un message directement depuis le bundle par défaut
		context.addMessage(null, MessageFactory.getMessage("classe", FacesMessage.SEVERITY_INFO, "Azzedine Alaia", "Yohji Yamamoto"));
		
		// Lui passer une valueExpression..
		final ValueExpression ve = context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{facesContext}", FacesContext.class);
		
		// Ecrit la valeur du facesContext dans un faces message. Totalement useless.
		final FacesMessage message = WebappBundle.VALIDATION.getMessage("maybe", "facesContext", ve);
		
		log.info(message.getSummary());
		context.addMessage(null, message);
	}
}
