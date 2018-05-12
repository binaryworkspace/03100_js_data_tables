package com.bwx.datatables.webapp.beans;

/**
 * Specifies the sort order (zero based index) and direction (ascending or
 * descending) for a given column index.
 * 
 * @author Chris Ludka
 *
 */
public class SortOrderBean {

	/**
	 * Provides the order in which this entry should be sorted where the lowest
	 * value is the first (or primary) sorting field. The later values
	 * (secondary, tertiary, and so forth) are sorted accordingly in order.
	 */
	private int sortOrderIndex;

	/**
	 * Provides the column index of the table. This index is intended to be zero
	 * based.
	 * 
	 * Recall, when obtaining SQL index it is necessary to offset this column
	 * index by 1 since the SQL indexing system uses base 1.
	 */
	private int columnIndex;

	/**
	 * Specify if the sort direction is either ascending or descending.
	 */
	private boolean isAsc;

	public int getSortOrderIndex() {
		return sortOrderIndex;
	}

	public void setSortOrderIndex(int sortOrderIndex) {
		this.sortOrderIndex = sortOrderIndex;
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public boolean isAsc() {
		return isAsc;
	}

	public void setAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columnIndex;
		result = prime * result + (isAsc ? 1231 : 1237);
		result = prime * result + sortOrderIndex;
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
		SortOrderBean other = (SortOrderBean) obj;
		if (columnIndex != other.columnIndex)
			return false;
		if (isAsc != other.isAsc)
			return false;
		if (sortOrderIndex != other.sortOrderIndex)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SortOrderBean [sortOrderIndex=" + sortOrderIndex + ", columnIndex=" + columnIndex + ", isAsc=" + isAsc + "]";
	}
	
}
