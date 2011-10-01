package com.aziphael.webapp.jsf;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FacesUtil {


	/**
	 * Other Servlets than the FacesServlet and all Filters cannot directly access the FacesContext in the same web container, 
	 * because they are sitting in the ServletContext outside the FacesContext.<br>
	 * When you want to request the FacesContext instance outside the FacesContext, you'll get FacesContext.getCurrentInstance() == null.
	 * In this case you need to precreate the FacesContext yourself.
	 * 
	 * @author http://balusc.blogspot.com
	 */
	public static FacesContext getFacesContext(
			HttpServletRequest request, HttpServletResponse response)
	{
		// Get current FacesContext.
		FacesContext facesContext = FacesContext.getCurrentInstance();

		// Check current FacesContext.
		if (facesContext == null) {

			// Create new Lifecycle.
			LifecycleFactory lifecycleFactory = (LifecycleFactory)
					FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY); 
			Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

			// Create new FacesContext.
			FacesContextFactory contextFactory  = (FacesContextFactory)
					FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
			facesContext = contextFactory.getFacesContext(
					request.getSession().getServletContext(), request, response, lifecycle);

			// Create new View.
			UIViewRoot view = facesContext.getApplication().getViewHandler().createView(
					facesContext, "");
			facesContext.setViewRoot(view);                

			// Set current FacesContext.
			FacesContextWrapper.setCurrentInstance(facesContext);
		}

		return facesContext;
	}
	
	// Wrap the protected FacesContext.setCurrentInstance() in a inner class.
	private static abstract class FacesContextWrapper extends FacesContext {
		protected static void setCurrentInstance(FacesContext facesContext) {
			FacesContext.setCurrentInstance(facesContext);
		}
	}  

//	public static ValueExpression createValueExpression(String expression) {
//		
//		final FacesContext context = FacesContext.getCurrentInstance();
//		return context.getApplication().getExpressionFactory()
//        .createValueExpression(context.getELContext(), "#{"+expression+"}", MyBean2.class)
//	}

	public static <T extends UIComponent> T getComponentByType(UIComponent root, Class<T> componentType) {
		
		final Stack<UIComponent> stack = new Stack<UIComponent>();
		stack.add(root);

		while (!stack.isEmpty()) {
			UIComponent component = stack.pop();
			if (componentType.isInstance(component))
				return (T) component;
			stack.addAll(component.getChildren());
		}
		
		return null;
	}

	public static StringBuilder printAttributes(UIComponent component) {

		final StringBuilder builder = new StringBuilder(component.toString() + " - Attributes= {");
		for (final Iterator<Entry<String, Object>> i = component.getAttributes().entrySet().iterator(); i.hasNext();) {
			final Entry<String, Object> entry = i.next();
			builder.append(entry.getKey() + "=" + entry.getValue());
			if (i.hasNext())
				builder.append(", ");
		}
		builder.append("}");
		return builder;
	}


	// public static FacesMessage getFacesMessage(String name) {
	// final FacesContext facesContext = FacesContext.getCurrentInstance();
	// final ResourceBundle bundle =
	// facesContext.getApplication().getResourceBundle(facesContext, name);
	// final String summary = bundle.getString(key);
	// }
	//

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public static void redirect(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	public static List<PhaseListener> getAllPhaseListeners() {

		final Lifecycle applicationLifecycle = getDefaultLifecycle();
		return Arrays.asList(applicationLifecycle.getPhaseListeners());
	}

	public static Lifecycle getDefaultLifecycle() {

		final LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		return lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
	}

	public static void addPhaseListener(PhaseListener phaseListener) {
		getDefaultLifecycle().addPhaseListener(phaseListener);
	}
}
