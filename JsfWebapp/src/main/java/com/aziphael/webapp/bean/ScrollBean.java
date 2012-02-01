package com.aziphael.webapp.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrollBean {
	
	private static final Logger log = LoggerFactory.getLogger(ScrollBean.class);

	private List<String> urlsToLoad;
	
	public ScrollBean() {
		urlsToLoad = new ArrayList<String>();
	}

	public List<String> getUrlsToLoad() {
		return urlsToLoad;
	}
	
	public void addUrlToLoad() {
		final String url = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("url");
		urlsToLoad.add(url);
	}
	
	public void action() {
		log.info("action !");
	}
}
