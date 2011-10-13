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
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class AccordionTabHandler extends TagHandler {

	private static final Logger log = LoggerFactory.getLogger(AccordionTabHandler.class);

	public AccordionTabHandler(TagConfig config) {
		super(config);
	}

	@Override
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {

		log.info("AccordionTabHandler.apply()- " + FacesUtil.debugComponent(parent));
		log.info("this= " + this + " - nextHandler= " + nextHandler);

//		this.nextHandler.apply(ctx, parent);
//		ctx.includeFacelet(parent, source);
	}

}
