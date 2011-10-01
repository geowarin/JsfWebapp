package com.aziphael.webapp.jsf;

import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;

/**
 * Class that is able to store the state of itself completely through apache 
 * BeanUtils, using reflection that relies on getters and setters.
 * 
 * @author Benny Bottema
 * @see GenericUtils#cloneBean(Object)
 * @see GenericUtils#projectBean(Object, Object)
 */
public abstract class AbstractStateholder implements StateHolder {

	/**
	 * Necessary for {@link StateHolder}.
	 */
	private boolean isTransient;

	@Override
	public final void setTransient(final boolean isTransient) {
		this.isTransient = isTransient;
	}

	@Override
	public final boolean isTransient() {
		return isTransient;
	}

	/**
	 * Saves the entire state of the current validator as clones bean
	 * 
	 * @see StateHolder#saveState(FacesContext)
	 * @see GenericUtils#cloneBean(Object)
	 */
	@Override
	public final Object saveState(final FacesContext context) {
		return GenericUtils.cloneBean(this); // 'return this' might work as well
	}

	/**
	 * Restores the entire state through the use of project bean (project the previously cloned bean's 
	 * properties on the current validator)
	 * 
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 * @see GenericUtils#projectBean(Object, Object)
	 */
	@Override
	public final void restoreState(final FacesContext context, final Object state) {
		GenericUtils.projectBean(state, this);
	}
}
