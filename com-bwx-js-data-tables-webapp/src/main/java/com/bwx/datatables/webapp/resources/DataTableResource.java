package com.bwx.datatables.webapp.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.bwx.datatables.webapp.MvcServlet;
import com.bwx.datatables.webapp.beans.CellBean;
import com.bwx.datatables.webapp.beans.ColumnBean;
import com.bwx.datatables.webapp.beans.DataTablesColumnBean;
import com.bwx.datatables.webapp.beans.DataTablesSearchBean;
import com.bwx.datatables.webapp.beans.DataTablesSortOrderBean;
import com.bwx.datatables.webapp.beans.RequestDataTablesBodyBean;
import com.bwx.datatables.webapp.beans.ResponseDataTablesBodyBean;
import com.bwx.datatables.webapp.beans.ResponseDataTablesStructrueBean;
import com.bwx.datatables.webapp.beans.RowBean;
import com.bwx.datatables.webapp.beans.RowKeyBean;
import com.bwx.datatables.webapp.beans.SortOrderBean;
import com.bwx.datatables.webapp.beans.TableBean;
import com.bwx.datatables.webapp.beans.TableTransactionBean;
import com.bwx.datatables.webapp.comparators.RowBeanComparator;
import com.bwx.datatables.webapp.utils.EndPointUtil;
import com.bwx.datatables.webapp.utils.TableBeanUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A resource that provides the current state for an example data table.
 *
 * This resource has been build to specifically support the DataTables
 * server-side sent parameters contract.
 * 
 * @see https://datatables.net/manual/server-side
 * @see http://stackoverflow.com/questions/29889909/handle-data-table-request-
 *      parameters-in-spring-request
 *
 * @author Chris Ludka
 *
 */
@Path(EndPointUtil.DATA_TABLE_RESOURCE)
public class DataTableResource {

	private final static Map<Long, TableBean> transactionToTableMap = new LinkedHashMap<Long, TableBean>();

	private static String jsonTableTransactions;

	public DataTableResource() {
		// Create Tables
		long transactionId = 0L;
		int replications = MvcServlet.getReplicaitons();
		List<TableTransactionBean> tableTransactionBeanList = new ArrayList<TableTransactionBean>();
		for (int colCount = MvcServlet.getMinColumns(); colCount <= MvcServlet.getMaxColumns(); colCount++) {
			for (int replicationCount = 0; replicationCount < replications; replicationCount++) {
				// Table Bean
				TableBean tableBean = TableBeanUtil.newInstance(transactionId, colCount);
				transactionToTableMap.put(Long.valueOf(transactionId), tableBean);

				// Table Transaction
				TableTransactionBean tableTransactionBean = new TableTransactionBean();
				tableTransactionBean.setId(transactionId);
				tableTransactionBean.setDescription(TableBeanUtil.getTitle(transactionId, colCount));
				tableTransactionBeanList.add(tableTransactionBean);

				// Transaction Id
				transactionId++;
			}
		}

		// JSON
		ObjectMapper responseMapper = new ObjectMapper();
		jsonTableTransactions = "";
		try {
			jsonTableTransactions = responseMapper.writeValueAsString(tableTransactionBeanList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Path(EndPointUtil.DATA_TABLE_STRUCTURE)
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public String getTableStructure(@PathParam("transactionId") String strTransactionId) {
		// Key
		Long transactionId = Long.parseLong(strTransactionId);

		// Validation
		if (!transactionToTableMap.containsKey(transactionId)) {
			throw new RuntimeException("Could not find TableBean for transactionId: " + transactionId.toString());
		}

		// Table
		TableBean tableBean = transactionToTableMap.get(transactionId);

		// Response
		ResponseDataTablesStructrueBean responseBean = new ResponseDataTablesStructrueBean();
		responseBean.setTitle(tableBean.getTitle());

		// Columns
		List<DataTablesColumnBean> dtColBeanList = new ArrayList<DataTablesColumnBean>();
		for (ColumnBean colBean : tableBean.getColumns()) {
			DataTablesColumnBean dtColBean = new DataTablesColumnBean();
			dtColBean.setData(colBean.getHeader());
			dtColBean.setName(colBean.getHeader());
			dtColBean.setOrderable(true);
			dtColBeanList.add(dtColBean);
		}
		responseBean.setColumns(dtColBeanList);

		// JSON
		ObjectMapper responseMapper = new ObjectMapper();
		String resonseJson = "";
		try {
			resonseJson = responseMapper.writeValueAsString(responseBean);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Result
		return resonseJson;
	}

	@GET
	@Path(EndPointUtil.DATA_TABLE_TRANSACTIONS)
	@Produces("application/json; charset=UTF-8")
	public String getTableTransactions() {
		return jsonTableTransactions;
	}

	@POST
	@Path(EndPointUtil.DATA_TABLE_BODY)
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public String postTableBody(String requestJson) {
		// Get request
		ObjectMapper requestMapper = new ObjectMapper();
		RequestDataTablesBodyBean requestBean = null;
		try {
			requestBean = requestMapper.readValue(requestJson, RequestDataTablesBodyBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(requestBean);
		
		// TransactionId
		Long transactionId = requestBean.getTransactionId();

		// Validation
		if (!transactionToTableMap.containsKey(transactionId)) {
			throw new RuntimeException("Could not find TableBean for transactionId: " + transactionId.toString());
		}

		// Table
		TableBean tableBean = transactionToTableMap.get(transactionId);

		// Search
		DataTablesSearchBean dtSearchBean = requestBean.getSearch();
		String searchString = dtSearchBean.getValue();
		TableBean filterTableBean = tableBean;
		if (!searchString.isEmpty()) {
			filterTableBean = TableBeanUtil.search(tableBean, searchString, true);
		}

		// Sort Order
		int sortOrderIndex = 0;
		List<SortOrderBean> sortOrderList = new ArrayList<SortOrderBean>();
		for (DataTablesSortOrderBean dtSortOrderBean : requestBean.getOrder()) {
			SortOrderBean sortOrderBean = new SortOrderBean();
			sortOrderBean.setColumnIndex(dtSortOrderBean.getColumn());
			sortOrderBean.setAsc(dtSortOrderBean.getDir().equals("asc"));
			sortOrderBean.setSortOrderIndex(sortOrderIndex);
			sortOrderList.add(sortOrderBean);
			sortOrderIndex++;
		}
		Collections.sort(filterTableBean.getRows(), new RowBeanComparator(sortOrderList));

		// Pagination
		TableBean pageTableBean = TableBeanUtil.paginate(filterTableBean, requestBean.getStart(), requestBean.getLength());

		// Response Bean
		ResponseDataTablesBodyBean responseBean = new ResponseDataTablesBodyBean();
		responseBean.setDraw(requestBean.getDraw());
		responseBean.setRecordsFiltered(filterTableBean.getRows().size());
		responseBean.setRecordsTotal(transactionToTableMap.get(transactionId).getRows().size());

		// Rows
		ColumnBean columnBean;
		String columnHeader;
		List<Map<String, String>> dtRowList = new ArrayList<Map<String, String>>();
		for (RowBean rowBean : pageTableBean.getRows()) {
			// Cells
			Map<String, String> dtCellMap = new LinkedHashMap<String, String>();

			/**
			 * This next portion of the code is here to demonstrate how to id
			 * this row on the HTML client side to ID the row on click. Often
			 * times this will simply be a unique identify, but it could also be
			 * more complex data. To demonstrate, this example will serialize a
			 * JavaBean representing a row key.
			 * 
			 * Note, serializing the POJO that represents a key value is not a
			 * typical use case. More typical use case would be to use something
			 * akin to a numeric ID or (non-hyphenated) string UUID.
			 * 
			 * Serialize a string to be used as the id. Conform to HTML4
			 * standard where ID and NAME tokens must begin with a letter
			 * ([A-Za-z]) and may be followed by any number of letters, digits
			 * ([0-9]), hyphens ("-"), underscores ("_"), colons (":"), and
			 * periods (".").
			 * 
			 * @see https://www.w3.org/TR/html4/types.html#type-id
			 * @see https://datatables.net/forums/discussion/30268/has-something
			 *      -fundamental-changed-with-the-new-version-of-editor-i-cant-
			 *      use-complex-keys-anymore
			 * @see http://www.bmchild.com/2012/06/serializing-and-deserializing
			 *      -object-to.html
			 * @see http://www.tutorialspoint.com/java8/java8_base64.htm
			 */
			String base64encodedString = "";
			try {
				// Encode
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
				objectOutputStream.writeObject(rowBean.getRowKeyBean());
				objectOutputStream.close();
				base64encodedString = Base64.getUrlEncoder().encodeToString(byteArrayOutputStream.toByteArray());

				// Now replace any padding ('=') with a random string
				String strUuid = UUID.randomUUID().toString();
				base64encodedString = strUuid + base64encodedString;
				base64encodedString = base64encodedString.replaceAll("=", strUuid);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			/**
			 * Row ID must be specified as "DT_RowId" per the contract of the
			 * DataTables server-side contract.
			 * 
			 * @see https://datatables.net/examples/server_side/ids.html
			 */
			dtCellMap.put("DT_RowId", base64encodedString);

			// Cells
			for (CellBean cellBean : rowBean.getCells()) {
				columnBean = pageTableBean.getColumns().get(cellBean.getIndex());
				columnHeader = columnBean.getHeader();
				dtCellMap.put(columnHeader, cellBean.getValue());
			}
			dtRowList.add(dtCellMap);
		}
		responseBean.setData(dtRowList);

		// JSON
		ObjectMapper responseMapper = new ObjectMapper();
		String responseJson = "";
		try {
			responseJson = responseMapper.writeValueAsString(responseBean);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Result
		return responseJson;
	}

	@PUT
	@Path(EndPointUtil.DATA_TABLE_ON_ROW_CLICK)
	@Consumes("application/json; charset=UTF-8")
	public String putOnRowClick(@PathParam("rowId") String rowId) {
		/**
		 * Recall in the EndPointUtil.EXAMPLE_DATA_TABLE_BODY end point, when
		 * the body was being built, the intent was to demonstrate how an row
		 * key could be sent to the HTML client side and then returned server
		 * side on click. Often times this will simply be a unique identifier, but
		 * it could also be more complex data. To demonstrate, this example a
		 * JavaBean was serialized representing a row key.
		 * 
		 * Note, serializing the POJO that represents a key value is not a
		 * typical use case. More typical use case would be to use something
		 * akin to a Long ID or (non-hyphenated) UUID.
		 * 
		 * Also recall, during serialization, the resulting string had to
		 * conform to HTML4 standard where ID and NAME tokens must begin with a
		 * letter ([A-Za-z]) and may be followed by any number of letters,
		 * digits ([0-9]), hyphens ("-"), underscores ("_"), colons (":"), and
		 * periods (".").
		 * 
		 * During this process, we replaced any padding ('=') with a random
		 * string. Here we remove padding, recall at UUID is 36 characters long
		 * including the hyphens.
		 * 
		 * @see https://www.w3.org/TR/html4/types.html#type-id
		 * @see https://datatables.net/forums/discussion/30268/has-something
		 *      -fundamental-changed-with-the-new-version-of-editor-i-cant-
		 *      use-complex-keys-anymore
		 * @see http://www.bmchild.com/2012/06/serializing-and-deserializing
		 *      -object-to.html
		 * @see http://www.tutorialspoint.com/java8/java8_base64.htm
		 */

		/*
		 * Remove padding, recall at UUID is 36 characters long including the
		 * hyphens.
		 */
		String paddingKey = rowId.substring(0, 36);
		rowId = rowId.substring(36);
		rowId = rowId.replaceAll(paddingKey, "=");
		byte[] base64decodedBytes = Base64.getUrlDecoder().decode(rowId);
		RowKeyBean rowKeyBean = null;
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(base64decodedBytes));
			rowKeyBean = (RowKeyBean) objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		System.out.println("Click Heard For: " + rowKeyBean.toString());

		// JSON
		ObjectMapper responseMapper = new ObjectMapper();
		String responseJson = "";
		try {
			responseJson = responseMapper.writeValueAsString(rowKeyBean);
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Send the key back as JSON to demonstrate that the server could
		 * deserialize the event data.
		 * 
		 */
		return responseJson;
	}
}
