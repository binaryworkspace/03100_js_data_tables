package com.bwx.datatables.webapp.beans;

import java.util.List;

/**
 * A bean that contains the title and column definitions for a TableItem. This
 * bean has been build to specifically support the DataTables server-side sent
 * parameters contract.
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 * 
 * @author Chris Ludka
 *
 */
public class ResponseDataTablesStructrueBean {

	private String title;

	private List<DataTablesColumnBean> columns;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DataTablesColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<DataTablesColumnBean> columns) {
		this.columns = columns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseDataTablesStructrueBean other = (ResponseDataTablesStructrueBean) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseDataTablesStructrueBean [title=" + title + ", columns=" + columns + "]";
	}

	
	
}