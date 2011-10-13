package com.aziphael.webapp.components.accordion;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.FacesUtil;
import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class AccordionHandler extends TagHandler {

	private static final Logger log = LoggerFactory.getLogger(AccordionHandler.class);
	
	private TagAttribute currentTab;

	public AccordionHandler(TagConfig config) {
		super(config);
		currentTab = getAttribute("currentTab");
	}

	@Override
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {

		log.info("AccordionHandler.apply()  - " + FacesUtil.debugComponent(parent));
		
		if (currentTab != null && parent instanceof UIAccordion) {
			((UIAccordion) parent).setCurrentTabId(currentTab.getValueExpression(ctx, Integer.class));
		}
		
		this.nextHandler.apply(ctx, parent);
	}

}
