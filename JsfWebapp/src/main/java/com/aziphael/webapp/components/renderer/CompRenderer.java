package com.aziphael.webapp.components.renderer;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompRenderer extends Renderer {

	private static final Logger log = LoggerFactory.getLogger(CompRenderer.class);

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		log.info("CompRenderer.encodeBegin()");
		super.encodeBegin(context, component);
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		log.info("CompRenderer.decode()");
		super.decode(context, component);
	}
}
