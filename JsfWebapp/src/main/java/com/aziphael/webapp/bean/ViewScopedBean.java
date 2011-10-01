package com.aziphael.webapp.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewScopedBean {

	private static final Logger log = LoggerFactory.getLogger(ViewScopedBean.class);
	
	public ViewScopedBean() {
		log.info("ViewScopedBean.<init>");
	}
	
	public void action() {
		log.info("ViewScopedBean.action()");
	}
}
