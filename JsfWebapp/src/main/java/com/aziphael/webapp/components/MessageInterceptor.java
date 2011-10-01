package com.aziphael.webapp.components;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageInterceptor extends UIComponentBase {

	private static final Logger log = LoggerFactory.getLogger(MessageInterceptor.class);
	private static final String FAMILY = "com.aziphael.comp";

	private String forComponents;

	private void printMessagesAssociatedWithComponents(FacesContext context) {

		final Iterator<String> clientsWithMessages = context.getClientIdsWithMessages();
		while (clientsWithMessages.hasNext()) {

			String clientId = clientsWithMessages.next();

			final Iterator<FacesMessage> messages = context.getMessages(clientId);
			while (messages.hasNext()) {
				final FacesMessage facesMessage = messages.next();
				logMessage(clientId, facesMessage);
				
				facesMessage.setSummary(facesMessage.getSummary() + "  -- tagged by " + this.getClass().getSimpleName());
			}
		}
	}
	
	private void printMessages(FacesContext context) {

		final Iterator<FacesMessage> messages = context.getMessages();
		while (messages.hasNext()) {
			final FacesMessage facesMessage = messages.next();
			logMessage(facesMessage);
		}
	}

	private void logMessage(String clientId, FacesMessage message) {

		final StringBuilder msgBuilder = new StringBuilder("Message for component '");
		msgBuilder.append(clientId);
		msgBuilder.append("' = '");
		msgBuilder.append(message.getSummary());
		msgBuilder.append("'");

		log4j(message, msgBuilder);
	}
	
	private void logMessage(FacesMessage message) {
		
		final StringBuilder msgBuilder = new StringBuilder("Message='");
		msgBuilder.append(message.getSummary());
		msgBuilder.append("'");
		
		log4j(message, msgBuilder); 
	}

	private void log4j(FacesMessage message, final StringBuilder msgBuilder) {
		
		final Severity severity = message.getSeverity();
		if (FacesMessage.SEVERITY_INFO.equals(severity)) {
			log.info(msgBuilder.toString());
		} else if (FacesMessage.SEVERITY_WARN.equals(severity)) {
			log.warn(msgBuilder.toString());
		} else if (FacesMessage.SEVERITY_ERROR.equals(severity)) {
			log.error(msgBuilder.toString());
		} else if (FacesMessage.SEVERITY_FATAL.equals(severity)) {
			//			log.fatal(message.getSummary());
			log.error(msgBuilder.toString());
		}
	}

	/**
	 * 6 - Render response {@inheritDoc}
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		log.info("***********");
		log.info("encodeBegin :");
		printMessagesAssociatedWithComponents(context);
		printMessages(context);
		log.info("***********");
		super.encodeBegin(context);
	}

	/**
	 * 6 - Render response {@inheritDoc}
	 */
	@Override
	public Object saveState(FacesContext context) {

		if (values == null) {
			values = new Object[2];
		}

		values[0] = super.saveState(context);
		values[1] = forComponents;
		return (values);
	}
	
	/**
	 * 1 - Restore View {@inheritDoc}
	 */
	@Override
	public void restoreState(FacesContext context, Object state) {

		values = (Object[]) state;
		log.info("UIOtp.restoreState() " + ArrayUtils.toString(ArrayUtils.subarray(values, 1, values.length)));

		super.restoreState(context, values[0]);
		forComponents = (String) values[1];
	}

	@Override
	public String getFamily() {
		return FAMILY;
	}

	private Object[] values;

	public String getForComponents() {
		return forComponents;
	}

	public void setForComponents(String forComponents) {
		this.forComponents = forComponents;
	}
}
