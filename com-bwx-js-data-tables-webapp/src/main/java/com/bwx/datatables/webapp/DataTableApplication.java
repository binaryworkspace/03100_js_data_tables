package com.bwx.datatables.webapp;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.bwx.datatables.webapp.resources.DataTableResource;
import com.bwx.datatables.webapp.utils.EndPointUtil;

/**
 * Entry point into the application.
 * 
 * Recall, all initialization, web.xml interactions and shutdown logic is
 * handled by the MvcServlet.
 * 
 * @author Chris Ludka
 */
@ApplicationPath(EndPointUtil.APPLICATION)
public class DataTableApplication extends Application {

	private Set<Object> singletons = new LinkedHashSet<Object>();

	public DataTableApplication() {
		// Add resources
		singletons.add(new DataTableResource());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}