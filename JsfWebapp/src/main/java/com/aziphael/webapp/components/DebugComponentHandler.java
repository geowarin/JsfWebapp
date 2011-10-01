package com.aziphael.webapp.components;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.tag.MetaRuleset;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;

public class DebugComponentHandler extends ComponentHandler {

	private static final Logger log = LoggerFactory.getLogger(DebugComponentHandler.class);

	public DebugComponentHandler(ComponentConfig config) {
		super(config);
		log.info("Comp.Comp()");
	}

	@Override
	protected void onComponentCreated(FaceletContext ctx, UIComponent c, UIComponent parent) {
		log.info("ComponentHandler.onComponentCreated()");
		super.onComponentCreated(ctx, c, parent);
	}

	@Override
	protected UIComponent createComponent(FaceletContext ctx) {
		log.info("Comp.createComponent()");
		return super.createComponent(ctx);
	}

	@Override
	protected void applyNextHandler(FaceletContext ctx, UIComponent c) throws IOException, FacesException, ELException {
		log.info("Comp.applyNextHandler()");
		super.applyNextHandler(ctx, c);
	}

	@Override
	protected void onComponentPopulated(FaceletContext ctx, UIComponent c, UIComponent parent) {
		log.info("Comp.onComponentPopulated()");
		super.onComponentPopulated(ctx, c, parent);
	}

	@Override
	protected void setAttributes(FaceletContext ctx, Object instance) {
		log.info("Comp.setAttributes() instance = " + instance);
		super.setAttributes(ctx, instance);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MetaRuleset createMetaRuleset(Class type) {
		log.info("Comp.createMetaRuleset() type=" + type);
		return super.createMetaRuleset(type);
	}

}
