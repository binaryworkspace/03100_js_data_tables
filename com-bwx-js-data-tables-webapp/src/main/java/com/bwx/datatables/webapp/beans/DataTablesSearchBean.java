package com.bwx.datatables.webapp.beans;

/**
 * A bean that contains the search term for a Data Table. This bean has been
 * built to specifically support the DataTables server-side sent parameters
 * contract.
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 * 
 * @author Chris Ludka
 *
 */
public class DataTablesSearchBean {

	private String value;

	private boolean regex;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRegex() {
		return regex;
	}

	public void setRegex(boolean regex) {
		this.regex = regex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (regex ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		DataTablesSearchBean other = (DataTablesSearchBean) obj;
		if (regex != other.regex)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataTablesSearchBean [value=" + value + ", regex=" + regex + "]";
	}

}
