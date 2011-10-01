package com.aziphael.webapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

public abstract class PropertyLoader {

	private static final String SUFFIX = ".properties";

	/**
	 * Looks up a resource named 'name' in the classpath. The resource must map
	 * to a file with .properties extention. The name is assumed to be absolute
	 * and can use either "/" or "." for package segment separation with an
	 * optional leading "/" and optional ".properties" suffix. Thus, the
	 * following names refer to the same resource:
	 * 
	 * <pre>
	 * some.pkg.Resource
	 * some.pkg.Resource.properties
	 * some/pkg/Resource
	 * some/pkg/Resource.properties
	 * /some/pkg/Resource
	 * /some/pkg/Resource.properties
	 * </pre>
	 * 
	 * @param name
	 *            classpath resource name [may not be null]
	 * @param loader
	 *            classloader through which to load the resource [null is
	 *            equivalent to the application loader]
	 * 
	 * @return resource converted to java.util.Properties [may be null if the
	 *         resource was not found and THROW_ON_LOAD_FAILURE is false]
	 * @throws IllegalArgumentException
	 *             if the resource was not found and THROW_ON_LOAD_FAILURE is
	 *             true
	 */
	public static Properties loadPropertiesAsResourceBundle(String name, ClassLoader loader, Locale locale)
			throws MissingResourceException {

		if (name == null)
			throw new IllegalArgumentException("null input: name");

		if (name.startsWith("/"))
			name = name.substring(1);
		if (name.endsWith(SUFFIX))
			name = name.substring(0, name.length() - SUFFIX.length());

		final Properties properties = new Properties();
		name = name.replace('/', '.');

		// Throws MissingResourceException on lookup failures:
		final ResourceBundle rb = ResourceBundle.getBundle(name, locale, loader);

		for (Enumeration<String> keys = rb.getKeys(); keys.hasMoreElements();) {
			final String key = keys.nextElement();
			final String value = rb.getString(key);
			properties.put(key, value);
		}

		return properties;
	}

	public Properties loadAsClassloaderResource(String name, ClassLoader loader, Locale locale) throws IOException {

		name = name.replace('.', '/');
		if (!name.endsWith(SUFFIX))
			name = name.concat(SUFFIX);
		// name.concat(locale.getCountry());

		final Properties properties = new Properties();
		final InputStream in = loader.getResourceAsStream(name);
		if (in == null)
			throw new IOException("Resource bundle not found " + name);
		properties.load(in);
		in.close();

		return properties;
	}
}