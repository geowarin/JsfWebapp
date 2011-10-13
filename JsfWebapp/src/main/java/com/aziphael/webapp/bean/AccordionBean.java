package com.aziphael.webapp.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccordionBean {
	
	private String nom;
	private String prenom;
	
	private int currentTab = 4;

	private static final Logger log = LoggerFactory.getLogger(AccordionBean.class);
	
	public void action() {
		log.info("AccordionBean.action()");
//		currentTab = new Random().nextInt(4) + 1;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}
}
