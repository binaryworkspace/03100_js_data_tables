package com.bwx.datatables.webapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Table row.
 * 
 * @author Chris Ludka
 *
 */
public class RowBean implements Serializable {
	
	private static final long serialVersionUID = -8224205129572377968L;

	private List<CellBean> cells;

	private RowKeyBean rowKeyBean;

	public List<CellBean> getCells() {
		return cells;
	}

	public void setCells(List<CellBean> cells) {
		this.cells = cells;
	}

	public RowKeyBean getRowKeyBean() {
		return rowKeyBean;
	}

	public void setRowKeyBean(RowKeyBean rowKeyBean) {
		this.rowKeyBean = rowKeyBean;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cells == null) ? 0 : cells.hashCode());
		result = prime * result + ((rowKeyBean == null) ? 0 : rowKeyBean.hashCode());
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
		RowBean other = (RowBean) obj;
		if (cells == null) {
			if (other.cells != null)
				return false;
		} else if (!cells.equals(other.cells))
			return false;
		if (rowKeyBean == null) {
			if (other.rowKeyBean != null)
				return false;
		} else if (!rowKeyBean.equals(other.rowKeyBean))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RowBean [cells=" + cells + ", rowKeyBean=" + rowKeyBean + "]";
	}
	
}
