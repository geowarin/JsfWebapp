package com.aziphael.webapp.components.tags;

import java.io.IOException;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class EventSourceTagHandler extends TagHandler {

	private static final Logger log = LoggerFactory.getLogger(EventSourceTagHandler.class);
	
	public EventSourceTagHandler(TagConfig config) {
		super(config);
	}

	@Override
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {
		log.info("apply() " + parent.getClass().getSimpleName() + " - id=" + parent.getClientId(ctx.getFacesContext()));
		VariableMapper vars = ctx.getVariableMapper();
		
		ValueExpression randomVe = ctx.getExpressionFactory().createValueExpression(RandomStringUtils.randomAlphabetic(42), String.class);
		vars.setVariable("randomVal", randomVe);
		
		this.nextHandler.apply(ctx, parent);
	}

	
}
