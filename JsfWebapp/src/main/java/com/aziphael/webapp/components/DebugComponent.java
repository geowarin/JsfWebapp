package com.aziphael.webapp.components;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebugComponent extends UIComponentBase {

	private static final Logger log = LoggerFactory.getLogger(DebugComponent.class);
	private static final String FAMILY = "com.aziphael.comp";

	private String forComponents;

	/**
	 * 1 - Restore View {@inheritDoc}
	 */
	@Override
	public void restoreState(FacesContext context, Object state) {

		values = (Object[]) state;
		log.info("DebugComponent.restoreState() " + ArrayUtils.toString(ArrayUtils.subarray(values, 1, values.length)));

		super.restoreState(context, values[0]);
		forComponents = (String) values[1];
	}
	
	/**
	 * 2 - Apply Request Values {@inheritDoc}
	 */
	@Override
	public void processDecodes(FacesContext context) {
		log.info("DebugComponent.processDecodes()");
		super.processDecodes(context);
	}

	/**
	 * 3 - Process Validation {@inheritDoc}
	 */
	@Override
	public void processValidators(FacesContext context) {
		log.info("DebugComponent.processValidators()");
		super.processValidators(context);
	}
	
	/**
	 * 4 - Update Model Values {@inheritDoc}
	 */
	@Override
	public void processUpdates(FacesContext context) {
		log.info("DebugComponent.processUpdates()");
		super.processUpdates(context);
	}
	
	/**
	 * 6 - Render response {@inheritDoc}
	 */
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		log.info("DebugComponent.encodeBegin()");
		super.encodeBegin(context);
	}
	
	@Override
	public void encodeEnd(FacesContext context) throws IOException {
		super.encodeEnd(context);
	}
	
	/**
	 * 6 - Render response {@inheritDoc}
	 */
	@Override
	public Object saveState(FacesContext context) {

		log.info("DebugComponent.saveState()");
		if (values == null) {
			values = new Object[2];
		}

		values[0] = super.saveState(context);
		values[1] = forComponents;
		return (values);
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
		log.info("DebugComponent.setForComponents()");
		this.forComponents = forComponents;
	}
}
