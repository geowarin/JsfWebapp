package com.aziphael.webapp.config;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import com.sun.faces.util.MessageFactory;

/**
 * Copie de {@link MessageFactory} pour laisser l'utilisateur définir le bundle souhaité.<br>
 * Veuillez utiliser {@link MessageFactory} si vous souhaitez interroger le bundle par défaut de JSF (au sens de {@link Application#getMessageBundle()}).<br>
 * 
 * Le principe de cette classe est de fournir un contexte par défaut pour localiser les bundles et évaluer les ValueExpressions quelle que soit la phase en cours.
 * 
 * @author aziphael
 *
 */
@SuppressWarnings("deprecation")
public class BundledMessageFactory {

	private BundledMessageFactory() {
	}

	/**
	 * @see #getMessage(String, Object...)
	 * @param FacesMessage.Serverity set a custom severity
	 */
	public static FacesMessage getMessageFromBundle(String bundleName,
													String messageId,
													FacesMessage.Severity severity,
													Object... params) {
		final FacesMessage message = getMessageFromBundle(bundleName, messageId, params);
		message.setSeverity(severity);
		return message;
	}


	/**
	 * @param bundleName Le nom du bundle
	 * @see #getMessageFromBundle(Locale, String, String, Object...)
	 * @param FacesMessage.Serverity set a custom severity
	 */
	public static FacesMessage getMessageFromBundle(Locale locale,
													String bundleName, 
													String messageId,
													FacesMessage.Severity severity,
													Object... params) {
		final FacesMessage message = getMessageFromBundle(locale, bundleName, messageId, params);
		message.setSeverity(severity);
		return message;
	}


	/**
	 * @see #getMessage(FacesContext, String, Object...)
	 * @param FacesMessage.Serverity set a custom severity
	 */
	public static FacesMessage getMessageFromBundle(FacesContext context,
													String bundleName,
													String messageId,
													FacesMessage.Severity severity,
													Object... params) {
		final FacesMessage message = getMessageFromBundle(context, bundleName, messageId, params);
		message.setSeverity(severity);
		return message;
	}


	/**
	 * <p>This version of getMessage() is used for localizing implementation
	 * specific messages.</p>
	 *
	 * @param messageId - the key of the message in the resource bundle
	 * @param params    - substittion parameters
	 *
	 * @return a localized <code>FacesMessage</code> with the severity
	 *  of FacesMessage.SEVERITY_ERROR
	 */
	public static FacesMessage getMessageFromBundle(String bundleName, String messageId, Object... params) {

		Locale locale = null;
		FacesContext context = FacesContext.getCurrentInstance();
		
		// context.getViewRoot() may not have been initialized at this point.
		if (context != null && context.getViewRoot() != null) {
			locale = context.getViewRoot().getLocale();
			if (locale == null)
				locale = Locale.getDefault();
			
		} else {
			locale = Locale.getDefault();
		}

		return getMessageFromBundle(locale, bundleName, messageId, params);
	}

	/**
	 * <p>Creates and returns a FacesMessage for the specified Locale.</p>
	 *
	 * @param locale    - the target <code>Locale</code>
	 * @param bundleNameLol Le nom du bundle
	 * @param messageId - the key of the message in the resource bundle
	 * @param params    - substittion parameters
	 * @return a localized <code>FacesMessage</code> with the severity
	 *  of FacesMessage.SEVERITY_ERROR
	 */
	public static FacesMessage getMessageFromBundle(Locale locale,
													String bundleName,
													String messageId, 
													Object... params) {       

		ResourceBundle bundle = null;
		String summary = null, detail = null;
		try {
			bundle = ResourceBundle.getBundle(bundleName, locale,
					getCurrentLoader(bundleName));
			summary = bundle.getString(messageId);
			detail = bundle.getString(messageId + "_detail");
		} catch (MissingResourceException ignore) {
		}
		
		if (bundle == null) throw new NullPointerException("Bundle '" + bundleName + "' not found");
		if (summary == null) throw new NullPointerException("Message '" + messageId + "' not found");
		
		final FacesMessage ret = new BindingFacesMessage(locale, summary, detail != null ? detail : summary, params);
		ret.setSeverity(FacesMessage.SEVERITY_ERROR);
		return (ret);
	}


	/**
	 * <p>Creates and returns a FacesMessage for the specified Locale.</p>
	 *
	 * @param context   - the <code>FacesContext</code> for the current request
	 * @param messageId - the key of the message in the resource bundle
	 * @param params    - substittion parameters
	 *
	 * @return a localized <code>FacesMessage</code> with the severity
	 *  of FacesMessage.SEVERITY_ERROR
	 */
	public static FacesMessage getMessageFromBundle(FacesContext context, String bundleName, String messageId, Object... params) {

		if (context == null || messageId == null ) {
			throw new NullPointerException(" context " + context + " messageId " + messageId);
		}
		Locale locale;
		// viewRoot may not have been initialized at this point.
		if (context.getViewRoot() != null)
			locale = context.getViewRoot().getLocale();
		else
			locale = Locale.getDefault();

		if (locale == null)
			throw new NullPointerException(" locale is null ");

		final FacesMessage message = getMessageFromBundle(locale, bundleName, messageId, params);
		if (message != null) {
			return message;
		}
		locale = Locale.getDefault();
		return getMessageFromBundle(locale, bundleName, messageId, params);
	}

	protected static ClassLoader getCurrentLoader(Object fallbackClass) {
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = fallbackClass.getClass().getClassLoader();
		}
		return loader;
	}

	/**
	 * This class overrides FacesMessage to provide the evaluation
	 * of binding expressions in addition to Strings.
	 * It is often the case, that a binding expression may reference
	 * a localized property value that would be used as a 
	 * substitution parameter in the message.  For example:
	 *  <code>#{bundle.userLabel}</code>
	 * "bundle" may not be available until the page is rendered.
	 * The "late" binding evaluation in <code>getSummary</code> and 
	 * <code>getDetail</code> allow the expression to be evaluated
	 * when that property is available.
	 */
	static class BindingFacesMessage extends FacesMessage {
		private static final long serialVersionUID = 1L;

		/**
		 * @param parameters array of parameters, both Strings and ValueBindings
		 */
		BindingFacesMessage(Locale locale, String messageFormat,
							String detailMessageFormat, Object[] parameters) {

			super(messageFormat, detailMessageFormat);
			this.locale = locale;
			this.parameters = parameters;
			if (parameters != null)
				resolvedParameters = new Object[parameters.length];
		}

		@Override
		public String getSummary() {
			String pattern = super.getSummary();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		@Override
		public String getDetail() {
			String pattern = super.getDetail();
			resolveBindings();
			return getFormattedString(pattern, resolvedParameters);
		}

		private void resolveBindings() {
			FacesContext context = null;
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					Object o = parameters[i];
					if (o instanceof ValueBinding) {
						if (context == null) {
							context = FacesContext.getCurrentInstance();
						}
						o = ((ValueBinding) o).getValue(context);
					}
					if (o instanceof ValueExpression) {
						if (context == null) {
							context = FacesContext.getCurrentInstance();
						}
						o = ((ValueExpression) o).getValue(context.getELContext());
					}
					// to avoid 'null' appearing in message
					if (o == null) {
						o = "";
					}
					resolvedParameters[i] = o;
				}
			}
		}

		private String getFormattedString(String msgtext, Object[] params) {
			String localizedStr = null;

			if (params == null || msgtext == null ) {
				return msgtext;
			}
			StringBuffer b = new StringBuffer(100);
			MessageFormat mf = new MessageFormat(msgtext);
			if (locale != null) {
				mf.setLocale(locale);
				b.append(mf.format(params));
				localizedStr = b.toString();
			}
			return localizedStr;
		}

		private final Locale locale;
		private final Object[] parameters;
		private Object[] resolvedParameters;
	}
}
