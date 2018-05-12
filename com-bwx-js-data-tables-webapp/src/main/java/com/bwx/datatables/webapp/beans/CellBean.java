package com.bwx.datatables.webapp.beans;

import java.io.Serializable;

/**
 * Table cell.
 * 
 * @author Chris Ludka
 *
 */
public class CellBean implements Serializable{

	private static final long serialVersionUID = 6914396180288684567L;

	private int index;
	
	private String value;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		CellBean other = (CellBean) obj;
		if (index != other.index)
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
		return "CellBean [index=" + index + ", value=" + value + "]";
	}
	
}
