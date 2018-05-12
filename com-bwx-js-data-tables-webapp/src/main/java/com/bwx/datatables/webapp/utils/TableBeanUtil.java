package com.bwx.datatables.webapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import com.bwx.datatables.webapp.beans.CellBean;
import com.bwx.datatables.webapp.beans.ColumnBean;
import com.bwx.datatables.webapp.beans.RowBean;
import com.bwx.datatables.webapp.beans.RowKeyBean;
import com.bwx.datatables.webapp.beans.TableBean;

/**
 * TableBean Utils.
 * 
 * Note, this is an over simplified example of search to demonstrates the
 * mechanics of the Data Tables API in REST. Here TableBean represents the model
 * data present in a back-end system. In production, the colCount would likely
 * be a Transaction ID that returns a snapshot of data present in the table.
 * Also in a production environment, would want to use immutable structures
 * instead of modifiable lists with beans.
 * 
 * @author Chris Ludka
 *
 */
public class TableBeanUtil {

	/**
	 * Produces an example table given a table count.
	 * 
	 * If a non-positive column count is provided, will return a table with one
	 * column.
	 * 
	 * Note, this is an over simplified example of search to demonstrates the
	 * mechanics of the Data Tables API in REST. In production, the colCount
	 * would likely be a Transaction ID that returns a snapshot of data present
	 * in the table. Also in a production environment, would want to use
	 * immutable structures instead of modifiable lists with beans.
	 * 
	 * @param colCount
	 * @return
	 */
	public static final TableBean newInstance(long transactionId, int colCount) {
		// Validation
		if (colCount < 1) {
			colCount = 1;
		}

		// Columns
		List<ColumnBean> colList = new ArrayList<ColumnBean>();
		for (int colIndex = 0; colIndex < colCount; colIndex++) {
			// Provide an HTML5 key using a random non-hyphenated string.
			String colKey = UUID.randomUUID().toString();
			colKey = colKey.replaceAll("-", "");

			// Build Column
			ColumnBean col = new ColumnBean();
			col.setHeader("col(" + colIndex + ")");
			col.setIndex(colIndex);
			col.setKey(colKey);

			// Add Column
			colList.add(col);
		}

		// Add 105 Rows
		List<RowBean> rowList = new ArrayList<RowBean>();
		Random randomInteger = new Random();
		String oddCellValue = "";
		for (int rowIndex = 0; rowIndex < 105; rowIndex++) {
			// Cells
			List<CellBean> cellList = new ArrayList<CellBean>();
			for (int colIndex = 0; colIndex < colCount; colIndex++) {
				String cellValue = "";
				if (colIndex == 0) {
					// Row ID
					cellValue = Integer.toString(rowIndex + 1);
				} else if (colIndex == 1) {
					// Only create a random number every other row to test multi-sorts
					if(rowIndex % 2 == 0){
						cellValue = Integer.toString(randomInteger.nextInt(100));
						oddCellValue = cellValue;
					} else {
						cellValue = oddCellValue;
					}
				} else {
					// Random cell value
					if (Math.random() < 0.33) {
						// Random Double
						cellValue = Double.toString(Math.random());
					} else if (Math.random() < 0.66) {
						// Random Lower Case String
						cellValue = UUID.randomUUID().toString().toLowerCase();
					} else {
						// Random Upper Case String
						cellValue = UUID.randomUUID().toString().toUpperCase();
					}
				}

				// Build Cell
				CellBean cell = new CellBean();
				cell.setIndex(colIndex);
				cell.setValue(cellValue);

				// Add Cell
				cellList.add(cell);
			}

			// Row Key
			RowKeyBean rowKeyBean = new RowKeyBean();
			// Generate an HTML5 key using a random non-hyphenated string.
			String rowUUID = UUID.randomUUID().toString();
			rowUUID = rowUUID.replaceAll("-", "");
			rowKeyBean.setUuid(rowUUID);
			rowKeyBean.setTransactionId(Math.toIntExact(transactionId));
			rowKeyBean.setRowIndex(rowIndex);
			
			// Row
			RowBean row = new RowBean();
			row.setRowKeyBean(rowKeyBean);
			row.setCells(cellList);
			rowList.add(row);
		}

		// Table
		TableBean tableBean = new TableBean();
		tableBean.setTitle(getTitle(transactionId, colCount));
		tableBean.setColumns(colList);
		tableBean.setRows(rowList);

		// Result
		return tableBean;
	}

	/**
	 * Provides the table's title.
	 * 
	 * @param colCount
	 * @param transactionId
	 * @return
	 */
	public static final String getTitle(long transactionId, int colCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("(Transaction ");
		sb.append(Long.toString(transactionId));
		sb.append(") Table with ");
		sb.append(Integer.toString(colCount));
		sb.append(" Columns.");
		return sb.toString();
	}
	
	/**
	 * Returns a TableBean containing search results.
	 * 
	 * Note, this is an over simplified example of search to demonstrates the
	 * mechanics of the Data Tables API in REST. In production, would want to
	 * use immutable structures instead of modifiable lists with beans. Also
	 * would want to consider more advanced hashing of search strings to avoid
	 * linear walks.
	 * 
	 * @param tableBean
	 * @param searchString
	 * @return
	 */
	public static final TableBean search(TableBean tableBean, String searchString, boolean isCaseSensitive) {
		// Init
		TableBean result = new TableBean();
		result.setTitle(tableBean.getTitle());
		result.setColumns(tableBean.getColumns());

		// Search pattern
		Pattern pattern;
		if (isCaseSensitive) {
			pattern = Pattern.compile(Pattern.quote(searchString));
		} else {
			pattern = Pattern.compile(Pattern.quote(searchString), Pattern.CASE_INSENSITIVE);
		}

		// Search
		List<RowBean> rowBeanList = new ArrayList<RowBean>();
		for (RowBean rowBean : tableBean.getRows()) {
			for (CellBean cellBean : rowBean.getCells()) {
				/*
				 * If a cell contains the search string, add entire row and then
				 * break to begin searching the next row.
				 */
				if (pattern.matcher(cellBean.getValue()).find()) {
					rowBeanList.add(rowBean);
					break;
				}
			}
		}

		// Add rows
		result.setRows(rowBeanList);

		// Result
		return result;
	}

	/**
	 * Returns a TableBean containing search results.
	 * 
	 * Note, this is an over simplified example of to demonstrate pagination
	 * mechanics of the Data Tables API in REST. In production, would want to
	 * use immutable structures instead of modifiable lists with beans. Also
	 * would want to consider more advanced hashing and combine table filter
	 * operations to improve efficiency.
	 * 
	 * @param tableBean
	 * @param offset
	 * @param limit
	 * @return
	 */
	public static final TableBean paginate(TableBean tableBean, int offset, int limit) {
		// Init
		TableBean result = new TableBean();
		result.setTitle(tableBean.getTitle());
		result.setColumns(tableBean.getColumns());

		// Paginate
		List<RowBean> rowBeanList = new ArrayList<RowBean>();
		int startIndex = Math.min(offset, tableBean.getRows().size());
		startIndex = Math.max(startIndex, 0);
		int endIndex = Math.min(startIndex + limit, tableBean.getRows().size());
		for (int i = startIndex; i < endIndex; i++) {
			RowBean row = tableBean.getRows().get(i);
			rowBeanList.add(row);
		}

		// Add rows
		result.setRows(rowBeanList);

		// Result
		return result;
	}

}
