package com.bwx.datatables.webapp.beans;

import java.util.List;

/**
 * Table.
 * 
 * @author Chris Ludka
 *
 */
public class TableBean {

	private List<ColumnBean> columns;
	
	private List<RowBean> rows;
	
	private String title;

	public List<ColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnBean> columns) {
		this.columns = columns;
	}

	public List<RowBean> getRows() {
		return rows;
	}

	public void setRows(List<RowBean> rows) {
		this.rows = rows;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((rows == null) ? 0 : rows.hashCode());
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
		TableBean other = (TableBean) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (rows == null) {
			if (other.rows != null)
				return false;
		} else if (!rows.equals(other.rows))
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
		return "TableBean [columns=" + columns + ", rows=" + rows + ", title=" + title + "]";
	}
	
}
