package com.aziphael.webapp.listener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComponentTreePrinterPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger("JSFTree");

	@Override
	public void afterPhase(PhaseEvent arg0) {
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

		log.info("---------------------");
		final long start = System.nanoTime();
		printComponent(0, FacesContext.getCurrentInstance().getViewRoot());
		log.info("took " + (System.nanoTime() - start) + " nanos");
		log.info("---------------------");
		log.info("print2 :");
		print2();
		log.info("---------------------");
	}

	private void printComponent(int depth, UIComponent component) {

		logComponent(depth, component);
		for (UIComponent child : component.getChildren())
			printComponent(depth + 1, child);
	}

	private void print2() {

		final long start = System.nanoTime();

		UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
		final LinkedList<UIComponent> queue = new LinkedList<UIComponent>();
		queue.add(root);
		queue.add(delimiter);

		int depth = 0;
		while (!queue.isEmpty()) {
			UIComponent component = queue.poll();

			if (component == delimiter) {
				depth++;
			} else {
				logComponent(depth, component);

				for (UIComponent uiComponent : component.getChildren()) {
					queue.add(uiComponent);
				}
				if (!component.getChildren().isEmpty())
					queue.add(delimiter);
				printQueue(queue);
			}
		}

		log.info("took " + (System.nanoTime() - start) + " nanos");
	}

	private void printQueue(Queue<UIComponent> queue) {

		final StringBuilder builder = new StringBuilder("{");
		for (Iterator<UIComponent> i = queue.iterator(); i.hasNext();) {
			builder.append(toString(i.next()));
			if (i.hasNext())
				builder.append(", ");
		}
		builder.append("}");
		log.info(builder.toString());
	}

	private void logComponent(int depth, UIComponent component) {

		final StringBuilder builder = new StringBuilder(StringUtils.repeat("\t", depth));
		if (depth > 0)
			builder.append(" -> ");
		builder.append(toString(component));
		log.info(builder.toString());
	}

	private String toString(UIComponent component) {
		return component.getClass().getSimpleName() + " '"
				+ component.getClientId(FacesContext.getCurrentInstance()) + "'";
	}


	private final UIComponent delimiter = new UIComponentBase() {

		public String getFamily() { return null; }
		public String toString() { return "delimiter"; };
		public String getClientId(FacesContext context) { return "delim"; };
	};

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

}
