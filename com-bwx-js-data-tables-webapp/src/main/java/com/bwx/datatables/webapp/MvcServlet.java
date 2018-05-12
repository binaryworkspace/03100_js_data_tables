package com.bwx.datatables.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * A servlet that controls the initialization and shutdown of the WebApp as well
 * as the lifecycle of the MVC.
 * 
 * This service has a Listener registered in src/main/webapp/WEB-INF/web.xml.
 * 
 * @see http://stackoverflow.com/questions/1814517/get-servletcontext-on-tomcat-
 *      from-jax-rs-jersey/29537231#29537231
 * @see http://www.deadcoderising.com/execute-code-on-webapp-startup-and-
 *      shutdown-using-servletcontextlistener/
 * 
 * @author Chris Ludka
 *
 */
public class MvcServlet implements ServletContextListener {

	private static int minColumns;

	private static int maxColumns;
	
	private static int replications;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Set initialization values
		minColumns = Integer.parseInt(sce.getServletContext().getInitParameter("minColumns"));
		maxColumns = Integer.parseInt(sce.getServletContext().getInitParameter("maxColumns"));
		replications = Integer.parseInt(sce.getServletContext().getInitParameter("replications"));
	}

	/**
	 * Minimum number of columns to be generated.
	 * 
	 * @return
	 */
	public static int getMinColumns() {
		return minColumns;
	}

	/**
	 * Maximum number of columns to be generated.
	 * 
	 * @return
	 */
	public static int getMaxColumns() {
		return maxColumns;
	}

	/**
	 * Number of table replications for each column count to be randomly generated.
	 * 
	 * @return
	 */
	public static int getReplicaitons() {
		return replications;
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing.
	}
}
