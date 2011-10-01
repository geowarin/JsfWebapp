package com.aziphael.webapp.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PhaseListener de la RenderResponse.<br/>
 * Associé un fichier de config dont le chemin est précisé via un paramètre de web.xml,
 * ce PhaseListener permet de gérer un ensemble de 'viewScoped' beans, ie, des beans session-scope
 * associés explicitement à des vues.<br/>
 * 
 * Si le phase listener trouve ces beans en dehors de leur(s) vue(s), il les enlève automatiquement
 * de la session.
 * 
 * @author waring
 *
 */
public class ViewScopedPhaseListener implements PhaseListener {

	private static final String VIEWSCOPE_CONFIG_INIT_PARAM = "viewscope-config";
	private static final String FACES_DEFAULT_SUFFIX = "javax.faces.DEFAULT_SUFFIX";
	
	private static final Logger log = LoggerFactory.getLogger(ViewScopedPhaseListener.class);
	private static final long serialVersionUID = 1L;

	private final ConfigLoader config = new ConfigLoader();

	@Override
	public void beforePhase(PhaseEvent event) {

		if (!config.loaded) {
			config.loadConfig();
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		final String viewId = facesContext.getViewRoot().getViewId();
		final String defaultSuffix = facesContext.getExternalContext().getInitParameter(FACES_DEFAULT_SUFFIX);
		// Au cas on arrive sur un script ou une feuille de style
		if (!viewId.endsWith(defaultSuffix))
			return;
		
		final List<String> viewScopedBeansToKeep = config.viewScopedBeansByViewId.get(viewId);
		log.info("currentView id = " + viewId + " - beansToKeep= " + viewScopedBeansToKeep);

		for (String viewScopedBeanName : config.allViewScopedBeanNames) {
			if (viewScopedBeansToKeep == null || !viewScopedBeansToKeep.contains(viewScopedBeanName))
				removeBeanFromSession(viewScopedBeanName, viewId);
		}
	}

	private void removeBeanFromSession(String viewScopedBeanName, String viewId) {
		Object removed = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(viewScopedBeanName);
		if (removed != null)
			log.info("viewScopedBean " + viewScopedBeanName + " removed from view " + viewId);
	}
	
	private class ConfigLoader {
		
		private boolean loaded;
		private final Map<String, List<String>> viewScopedBeansByViewId = new HashMap<String, List<String>>();
		private final List<String> allViewScopedBeanNames = new ArrayList<String>();
		
		private void loadConfig() {

			final FacesContext facesContext = FacesContext.getCurrentInstance();
			final String configFileUrl = facesContext.getExternalContext().getInitParameter(VIEWSCOPE_CONFIG_INIT_PARAM);
			final InputStream inputStream = facesContext.getExternalContext().getResourceAsStream(configFileUrl);
			
			final Builder builder = new Builder();
			try {
				final Document doc = builder.build(inputStream);
				final Element root = doc.getRootElement();

				final Elements viewScopedBeans = root.getChildElements("viewScopedBean");
				for (int i = 0; i < viewScopedBeans.size(); i++) {
					
					final Element viewScopedBean = viewScopedBeans.get(i);
					handleBean(viewScopedBean);
				}

			} catch (ParsingException ex) {
				log.error(configFileUrl + " is malformed.");
			} catch (IOException ex) {
				log.error("Could not read " + configFileUrl); 
			}
			loaded = true;
		}
		
		private void handleBean(final Element viewScopedBean) {
			
			final String viewScopedBeanName = viewScopedBean.getAttributeValue("name");
			allViewScopedBeanNames.add(viewScopedBeanName);

			final Elements views = viewScopedBean.getChildElements("view");
			for (int j = 0; j < views.size(); j++) {
				final Element view = views.get(j);
				final String viewId = view.getAttributeValue("id");

				addBeanToView(viewId, viewScopedBeanName);
			}
		}

		private void addBeanToView(final String viewId, final String viewScopedBeanName) {

			List<String> previousBeans = viewScopedBeansByViewId.get(viewId);
			if (previousBeans == null) {
				previousBeans = new ArrayList<String>(3);
				viewScopedBeansByViewId.put(viewId, previousBeans);
			}
			previousBeans.add(viewScopedBeanName);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

}
