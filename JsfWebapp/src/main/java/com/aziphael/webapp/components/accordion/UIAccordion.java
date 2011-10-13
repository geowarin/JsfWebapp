package com.aziphael.webapp.components.accordion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.ajax4jsf.component.UIAjaxOutputPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.jsf.FacesUtil;

public class UIAccordion extends UIAjaxOutputPanel implements NamingContainer, ActionListener {

	private static final Logger log = LoggerFactory.getLogger(UIAccordion.class);
	
	private UIAccordionTab previousTab;
	
	private ValueExpression currentTabId;
	
	
	@Override
	public void processDecodes(FacesContext context) {
		super.processDecodes(context);
		for (UIAccordionTab tab : getTabs()) {
			tab.getHeaderAction().addActionListener(this);
		}
	}
	
	@Override
	public void encodeBegin(FacesContext context) throws IOException {
		log.info("UIAccordionTab.encodeBegin()");
		super.encodeBegin(context);

		int tabId = 1;
		Integer mappedTab = extractValue(currentTabId, context);
		for (UIAccordionTab tab : getTabs()) {
			tab.tabId = tabId++;
			if (mappedTab != null && mappedTab == tab.tabId)
				tab.selected = true;
			tab.getHeaderAction().removeActionListener(this);
		}
	}
	
	private Integer extractValue(ValueExpression ve, FacesContext context) {
		if (ve == null)
			return null;
		return (Integer) ve.getValue(context.getELContext());
	}
	
	
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		
		final UIComponent headerAction = event.getComponent();
		final UIAccordionTab firingTab = FacesUtil.getParentByType(headerAction, UIAccordionTab.class);
		
		log.info("UIAccordion.processAction() tabId=" + firingTab.tabId + " previousTab=" + (previousTab != null ? previousTab.tabId : "null"));
		
		firingTab.selected = true;
		if (currentTabId != null)
			currentTabId.setValue(FacesContext.getCurrentInstance().getELContext(), firingTab.tabId);
		previousTab = firingTab;
	}
	
	public List<UIAccordionTab> getTabs() {
		final List<UIAccordionTab> tabs = new ArrayList<UIAccordionTab>();
		for (UIComponent child : getChildren()) {
			if (child instanceof UIAccordionTab)
				tabs.add((UIAccordionTab) child);
		}
		return tabs;
	}
	
	@Override
	public void setAjaxRendered(boolean ajaxRendered) {
	}

	@Override
	public boolean isAjaxRendered() {
		return true;
	}

	@Override
	public boolean isKeepTransient() {
		return true;
	}

	@Override
	public void setKeepTransient(boolean ajaxRendered) {
	}

	@Override
	public String getLayout() {
		return "block";
	}

	@Override
	public void setLayout(String layout) {
	}
	
	private Object[] values;

	public Object saveState(FacesContext _context) {
		if (values == null) {
			values = new Object[3];
		}
		values[0] = super.saveState(_context);
		values[1] = previousTab;
		values[2] = currentTabId;
		return values;
	}

	public void restoreState(FacesContext _context, Object _state) {
		values = (Object[]) _state;
		super.restoreState(_context, values[0]);
		this.previousTab = (UIAccordionTab) values[1];
		this.currentTabId = (ValueExpression) values[2];
	}

	public ValueExpression getCurrentTabId() {
		return currentTabId;
	}

	public void setCurrentTabId(ValueExpression currentTabId) {
		this.currentTabId = currentTabId;
	}
}
