package com.aziphael.webapp.jsf;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class (reduced version) with generic functionality neutral to any application.
 *  
 * @author Benny Bottema
 */
public final class GenericUtils {
	
	private static final Logger log = LoggerFactory.getLogger(GenericUtils.class);

	/**
	 * Private constructor for this <code>final</code> class; this class cannot 
	 * be instantiated; it is a utility class.
	 */
	private GenericUtils() {
	};

	/**
	 * Clones bean using Apache's {@link BeanUtils#cloneBean}
	 */
	public static Object cloneBean(final Object bean) {
		try {
			return BeanUtils.cloneBean(bean);
		} catch (final IllegalAccessException e) {
			log.error("error cloning bean", e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (final InstantiationException e) {
			log.error("error cloning bean", e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (final InvocationTargetException e) {
			log.error("error cloning bean", e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (final NoSuchMethodException e) {
			log.error("error cloning bean", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * Takes over all properties from the source bean through getters, 
	 * and projects them on the target bean through setters, using Apache's 
	 * {@link BeanUtils#copyProperties(Object, Object)}.
	 */
	public static void projectBean(final Object source, final Object target) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			log.error("error while copying bean properties", e);
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.error("error while copying bean properties", e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
