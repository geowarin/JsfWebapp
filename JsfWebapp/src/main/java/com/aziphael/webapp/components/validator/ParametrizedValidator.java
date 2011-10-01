package com.aziphael.webapp.components.validator;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aziphael.webapp.config.WebappBundle;

public class ParametrizedValidator  implements Validator, Serializable {

	private static final Logger log = LoggerFactory.getLogger(ParametrizedValidator.class);
	private static final long serialVersionUID = 1L;
	private String winningString;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		if (winningString == null) throw new NullPointerException("Winning string is null");
		
		log.info("validate " + value);
		
		final String stringValue = (String) value;

		for (int i = 1; i < stringValue.length() + 1; i++) {
			if (!winningString.regionMatches(0, stringValue, 0, i)) {
				final String messageId = "validator.parametrizedValidator.failedAt" + (i == 1 ? "First" : "");
				throw new ValidatorException(WebappBundle.VALIDATION.getMessage(messageId, i));
			}
		}
		
		if (stringValue.length() < winningString.length())
			throw new ValidatorException(WebappBundle.VALIDATION.getMessage("validator.parametrizedValidator.tooShort"));

		if (stringValue.length() > winningString.length())
			throw new ValidatorException(WebappBundle.VALIDATION.getMessage("validator.parametrizedValidator.tooLong"));
		
		throw new ValidatorException(WebappBundle.VALIDATION.getMessage("validator.parametrizedValidator.win"));
	}

	public String getWinningString() {
		return winningString;
	}

	public void setWinningString(String winningString) {
		this.winningString = winningString;
	}
}
