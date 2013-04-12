package ch.ethz.inf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesContextUtils {
	/**
	 * Adds an information message, which will be displayed on the next site.
	 */
	public static void showMessage(String text) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(text));
	}

}
