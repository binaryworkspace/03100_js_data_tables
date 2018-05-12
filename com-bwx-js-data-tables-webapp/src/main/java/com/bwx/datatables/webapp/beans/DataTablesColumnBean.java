package com.bwx.datatables.webapp.beans;

/**
 * A bean that contains the column definition and data. This bean has been built
 * to specifically support the DataTables server-side sent parameters contract.
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 * 
 * @author Chris Ludka
 * 
 */
public class DataTablesColumnBean {

	private String data;

	private String name;

	private boolean searchable;

	private boolean orderable;

	private boolean searchRegex;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public boolean isOrderable() {
		return orderable;
	}

	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}

	public boolean isSearchRegex() {
		return searchRegex;
	}

	public void setSearchRegex(boolean searchRegex) {
		this.searchRegex = searchRegex;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	private String searchValue;

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (orderable ? 1231 : 1237);
		result = prime * result + (searchRegex ? 1231 : 1237);
		result = prime * result + ((searchValue == null) ? 0 : searchValue.hashCode());
		result = prime * result + (searchable ? 1231 : 1237);
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
		DataTablesColumnBean other = (DataTablesColumnBean) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderable != other.orderable)
			return false;
		if (searchRegex != other.searchRegex)
			return false;
		if (searchValue == null) {
			if (other.searchValue != null)
				return false;
		} else if (!searchValue.equals(other.searchValue))
			return false;
		if (searchable != other.searchable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DataTablesColumnBean [data=" + data + ", name=" + name + ", searchable=" + searchable + ", orderable=" + orderable + ", searchRegex=" + searchRegex + ", searchValue=" + searchValue
				+ "]";
	}

}
