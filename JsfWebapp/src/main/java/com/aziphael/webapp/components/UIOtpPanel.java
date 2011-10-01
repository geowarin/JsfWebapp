package com.aziphael.webapp.components;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.ComponentUtils;

public class UIOtpPanel extends UIComponentBase implements ValueChangeListener, ActionListener, Serializable {

	private static final String OTP_SESSION_PARAM = "OTP_VALUE_FOR_";
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(UIOtpPanel.class);
	private static final String FAMILY = "com.aziphael.comp";

	private String forComponent;
	private boolean changed;
	
	public UIOtpPanel() {
		log.info("UIOtpPanel<init> " + changed);
	}

	/**
	 * MAGIQUE! On écoute le changement de valeur du composant linké
	 */
	@Override
	public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
		log.info("UIOtpPanel.processValueChange() oldValue=" + event.getOldValue() + " - newValue=" + event.getNewValue());
		
		UIComponent component = event.getComponent();
		if (component instanceof UIInput) {
			processInputValueChange(event);
		}
	}
	
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		processCancelAction(event);
	}
	
	private void processCancelAction(ActionEvent event) {
		log.info("cancel");
		
		final UIInput linkedComponent = getLinkedComponent(getFacesContext());
		if (linkedComponent instanceof HtmlInputText) {
			((HtmlInputText) linkedComponent).setDisabled(false);
			log.info("Component set to enabled");
		}
		linkedComponent.setValue(getValueFromSession());
		removeValueFromSession();
	}

	private void processInputValueChange(ValueChangeEvent event) {
		
		storeValueInSession(event.getOldValue());
		changed = true;
		
		final UIInput linkedComponent = getLinkedComponent(getFacesContext());
		if (linkedComponent instanceof HtmlInputText) {
			((HtmlInputText) linkedComponent).setDisabled(true);
			log.info("Component set to disabled");
		}
	}


	@Override
	public void decode(FacesContext context) {

		log.info("UIOtpPanel.decode()");
		getLinkedComponent(context).addValueChangeListener(this);
		getCommandFacet("cancel").addActionListener(this);
		super.decode(context);
	}

	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		
		super.encodeEnd(context);
		getLinkedComponent(context).removeValueChangeListener(this);
		getCommandFacet("cancel").removeActionListener(this);
	}
	
	private UIInput getLinkedComponent(FacesContext context) {
		
//		final UIComponent linked = FacesContext.getCurrentInstance().getViewRoot().findComponent(forComponent);
		final UIComponent linked = ComponentUtils.findComponentById(context, context.getViewRoot(), forComponent);
		if (linked instanceof UIInput) 
			return (UIInput) linked;
		
		throw new IllegalStateException(forComponent + " should be UIInput " + linked + " (" + forComponent + ")");
	}
	
	private UICommand getCommandFacet(String name) {
		
		final UIComponent facet = getFacet(name);
		if (facet == null) throw new NullPointerException(name + " facet is null");
		if (facet instanceof UICommand)
			return (UICommand) facet;
		throw new IllegalStateException(name + " facet should be UICommand : " + facet.getClass().getSimpleName());
	}
	
	@Override
	public boolean getRendersChildren() {
		return true;
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		
		super.encodeChildren(context);
		
		if (changed) {
			for (UIComponent child : getChildren()) {
				child.encodeAll(context);
			}
			getCommandFacet("cancel").encodeAll(context);
		}
	}

	public String getForComponent() {
		return forComponent;
	}

	public void setForComponent(String forComponent) {
		this.forComponent = forComponent;
	}
	
	@Override
	public String getFamily() {
		return FAMILY;
	}
	
	private Object[] values;

	@Override
	public Object saveState(FacesContext context) {

		log.info("UIOtpPanel.saveState()");
		if (values == null) {
			values = new Object[2];
		}

		values[0] = super.saveState(context);
		values[1] = forComponent;
		return (values);

	}

	@Override
	public void restoreState(FacesContext context, Object state) {

		values = (Object[]) state;
		log.info("UIOtpPanel.restoreState() " + ArrayUtils.toString(ArrayUtils.subarray(values, 1, values.length)));

		super.restoreState(context, values[0]);
		forComponent = (String) values[1];
	}
	
	private void storeValueInSession(Object value) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(OTP_SESSION_PARAM + forComponent, value);
	}
	private void removeValueFromSession() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(OTP_SESSION_PARAM + forComponent);
	}
	
	private Object getValueFromSession() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(OTP_SESSION_PARAM + forComponent);
	}
}
