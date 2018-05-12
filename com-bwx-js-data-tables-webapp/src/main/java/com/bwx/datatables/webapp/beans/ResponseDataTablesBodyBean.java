package com.bwx.datatables.webapp.beans;

import java.util.List;
import java.util.Map;

/**
 * A bean that contains the body for a response for a table body. This bean has
 * been built to specifically support the DataTables server-side sent parameters
 * contract.
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 * 
 * @author Chris Ludka
 *
 */
public class ResponseDataTablesBodyBean {

	private List<Map<String, String>> data;

	private int draw;

	private int recordsFiltered;

	private int recordsTotal;

	public List<Map<String, String>> getData() {
		return data;
	}

	public int getDraw() {
		return draw;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + draw;
		result = prime * result + recordsFiltered;
		result = prime * result + recordsTotal;
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
		ResponseDataTablesBodyBean other = (ResponseDataTablesBodyBean) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (draw != other.draw)
			return false;
		if (recordsFiltered != other.recordsFiltered)
			return false;
		if (recordsTotal != other.recordsTotal)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseDataTablesBodyBean [data=" + data + ", draw=" + draw + ", recordsFiltered=" + recordsFiltered + ", recordsTotal=" + recordsTotal + "]";
	}

}
