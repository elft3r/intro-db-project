package ch.ethz.inf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.sun.istack.internal.logging.Logger;

public class FacesContextUtils {

	private static final Logger logger = Logger
			.getLogger(FacesContextUtils.class);

	private static final String LOCAL_DB_PATH = System.getProperty("user.home")
			+ "/localDb/";

	/**
	 * Adds an information message, which will be displayed on the next site.
	 */
	public static void showMessage(String text) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(text));
	}

	public static void redirect(String redirectTo) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect(redirectTo);
	}

	public static InputStream getInputStreamToDb(String tableName) {
		String filePath = LOCAL_DB_PATH + tableName + ".txt";
		try {
			if (new File(filePath).exists())
				return new FileInputStream(filePath);
		} catch (FileNotFoundException e) { 
			// should never happen
		}
		logger.warning("table doesn't exist: " + filePath);
		return null;
	}

	public static PrintWriter getOutputStreamToDb(String tableName) {

		String filePath = LOCAL_DB_PATH + tableName + ".txt";
		File file = new File(filePath);
		File parentFile = file.getParentFile();
		try {
			if (!parentFile.exists() && !parentFile.mkdirs()) {
				throw new IOException("Couldn't create dir: " + parentFile);
			}
			if (!file.exists() && !file.createNewFile()) {
				throw new IOException("Couldn't create file: " + filePath);
			}
			logger.info("write to file:" + file.getAbsolutePath());
			return new PrintWriter(new BufferedWriter(
					new FileWriter(file, true)));
		} catch (FileNotFoundException e) {
			logger.warning("file not found: " + filePath, e);
		} catch (IOException e) {
			logger.warning("failed to write to open output stream at: "
					+ filePath, e);
		}
		return null;
	}
}
