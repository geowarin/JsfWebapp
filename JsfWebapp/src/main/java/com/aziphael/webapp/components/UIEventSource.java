package com.aziphael.webapp.components;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UIEventSource extends UIComponentBase {

	private static final Logger log = LoggerFactory.getLogger(UIEventSource.class);
	
	@Override
	public String getFamily() {
		return "com.aziphael.comp";
	}


	@Override
	public void processDecodes(FacesContext context) {
		log.info("UIEventSource.processDecodes()");
		super.processDecodes(context);
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		log.info("UIEventSource.encodeBegin()");
		super.encodeBegin(context);
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
//		super.encodeChildren(context);
		for (UIComponent child : getChildren()) {
			child.encodeAll(context);
		}
	}
	
	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
