package com.bwx.datatables.webapp.beans;

import java.io.Serializable;

/**
 * Table row key.
 * 
 * @author Chris Ludka
 *
 */
public class RowKeyBean implements Serializable {

	private static final long serialVersionUID = -1466491325524308183L;
	
	private int rowIndex;
	
	private int transactionId;
	
	private String uuid;

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + rowIndex;
		result = prime * result + transactionId;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		RowKeyBean other = (RowKeyBean) obj;
		if (rowIndex != other.rowIndex)
			return false;
		if (transactionId != other.transactionId)
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RowKeyBean [rowIndex=" + rowIndex + ", transactionId=" + transactionId + ", uuid=" + uuid + "]";
	}
	
}
