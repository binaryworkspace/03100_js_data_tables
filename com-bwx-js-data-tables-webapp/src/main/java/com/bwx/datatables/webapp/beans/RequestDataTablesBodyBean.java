package com.bwx.datatables.webapp.beans;

import java.util.List;

/**
 * A bean that contains the body for a request for a table body. This bean has
 * been built to specifically support the DataTables server-side sent parameters
 * contract.
 * 
 * <pre>
	{"draw":1,
	
	"columns":[
	{
	"data":"Exchange",
	"name":"",
	"searchable":true,
	"orderable":true,
	"searchRegex":false,
	"searchValue":""
	},
	{"data":"Symbol","name":"","searchable":true,"orderable":true,"searchRegex":false,"searchValue":""},
	{"data":"Name","name":"","searchable":true,"orderable":true,"searchRegex":false,"searchValue":""},
	...],
	
	"order":[{"column":0,"dir":"asc"}],
	
	"start":0,
	
	"length":10,
	
	"search":{"value":"","regex":false},
	
	"filterData":"EXTRA DATA"
	}
 * </pre>
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 * 
 * @author Chris Ludka
 *
 */
public class RequestDataTablesBodyBean {
	
	private List<DataTablesColumnBean> columns;

	private int draw;
	
	private long transactionId;
	
	private int length;
	
	private List<DataTablesSortOrderBean> order;
	
	private DataTablesSearchBean search;
	
	private int start;
	
	public List<DataTablesColumnBean> getColumns() {
		return columns;
	}

	public void setColumns(List<DataTablesColumnBean> columns) {
		this.columns = columns;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<DataTablesSortOrderBean> getOrder() {
		return order;
	}

	public void setOrder(List<DataTablesSortOrderBean> order) {
		this.order = order;
	}

	public DataTablesSearchBean getSearch() {
		return search;
	}

	public void setSearch(DataTablesSearchBean search) {
		this.search = search;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + draw;
		result = prime * result + length;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result + start;
		result = prime * result + (int) (transactionId ^ (transactionId >>> 32));
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
		RequestDataTablesBodyBean other = (RequestDataTablesBodyBean) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (draw != other.draw)
			return false;
		if (length != other.length)
			return false;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (start != other.start)
			return false;
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RequestDataTablesBodyBean [columns=" + columns + ", draw=" + draw + ", transactionId=" + transactionId + ", length=" + length + ", order=" + order + ", search=" + search + ", start="
				+ start + "]";
	}
	
}
