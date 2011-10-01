package com.aziphael.webapp.components.tags;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;

public class MyTagHandler extends TagHandler {

	private static final Logger log = LoggerFactory.getLogger(MyTagHandler.class);
	private final TagAttribute test;

	public MyTagHandler(TagConfig config) {
		super(config);
		this.test = this.getRequiredAttribute("test");
	}

	@Override
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
			ELException {

		log.info("TagHandler.applyContext test= " + test.getValue());
		// this.nextHandler.
	}

}
