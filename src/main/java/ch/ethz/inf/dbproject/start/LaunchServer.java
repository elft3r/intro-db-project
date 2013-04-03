package ch.ethz.inf.dbproject.start;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class LaunchServer {
	/**
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// the location of the WebContent
		String webappDirLocation = "WebContent/";

		// the port we want to run on
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		// We start a Tomcat instance on the specified port
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(Integer.valueOf(webPort));

		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}