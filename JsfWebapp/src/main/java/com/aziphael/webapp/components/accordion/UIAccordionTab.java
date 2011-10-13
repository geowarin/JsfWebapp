package com.aziphael.webapp.components.accordion;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.FacesUtil;

public class UIAccordionTab extends HtmlPanelGroup implements NamingContainer {

	private static final Logger log = LoggerFactory.getLogger(UIAccordionTab.class);

	int tabId;
	boolean selected;

	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		super.encodeBegin(context);
	}
	
	@Override
	public void encodeChildren(FacesContext context) throws IOException {
		log.info("UIAccordionTab.encodeChildren() tabId=" + tabId + " - selected=" + selected);
		HtmlPanelGroup header = getHeader();
		HtmlPanelGroup contents = getContents();
		if (selected) {
			header.setStyle("background-color: red;");
			contents.setRendered(true);
		} else {
			header.setStyle("background-color: grey;");
			contents.setRendered(false);
		}
		super.encodeChildren(context);
	}

	public HtmlPanelGroup getHeader() {
		return getChildByName("header", HtmlPanelGroup.class);
	}
	
	public HtmlPanelGroup getContents() {
		return getChildByName("contents", HtmlPanelGroup.class);
	}

	private <T extends UIComponent> T getChildByName(String name, Class<T> componentType) {
		final UIComponent header = findComponent(name);
		if (!componentType.isInstance(header))
			throw new FacesException(name + " doit être un " + componentType.getSimpleName());
		return (T) header;
	}

	public HtmlAjaxCommandLink getHeaderAction() {
		final UIComponent action = FacesUtil.getComponentByType(getHeader(), HtmlAjaxCommandLink.class);
		if (action == null)
			throw new FacesException("Pas de HtmlAjaxCommandLink trouvé dans le header");
		return (HtmlAjaxCommandLink) action;
	}

	private Object[] values;

	public Object saveState(FacesContext _context) {
		if (values == null) {
			values = new Object[2];
		}
		values[0] = super.saveState(_context);
		values[1] = tabId;
		return values;
	}

	public void restoreState(FacesContext _context, Object _state) {
		values = (Object[]) _state;
		super.restoreState(_context, values[0]);
		this.tabId = (Integer) values[1];
	}
}
