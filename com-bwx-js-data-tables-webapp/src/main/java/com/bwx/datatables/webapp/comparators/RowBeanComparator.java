package com.bwx.datatables.webapp.comparators;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.math.NumberUtils;

import com.bwx.datatables.webapp.beans.RowBean;
import com.bwx.datatables.webapp.beans.SortOrderBean;

/**
 * A helper class that holds comparators.
 * 
 * @see http://stackoverflow.com/questions/11176227/simple-way-to-sort-strings-
 *      in-the-alphabetical-order
 * 
 * @author Chris Ludka
 * 
 */
public class RowBeanComparator implements Comparator<RowBean> {

	private List<SortOrderBean> sortOrderBeanList;

	public RowBeanComparator(List<SortOrderBean> sortOrderBeanList) {
		this.sortOrderBeanList = sortOrderBeanList;
	}

	@Override
	public int compare(RowBean o1, RowBean o2) {
		// Ensure list has been sorted according to the SortOrderBean
		Collections.sort(sortOrderBeanList, SortOrderBeanComparator.SORT_ORDER_ITEM_ASC);

		// Compare
		CompareToBuilder compareToBuilder = new CompareToBuilder();
		for (SortOrderBean sortOrderBean : sortOrderBeanList) {
			int colIndex = sortOrderBean.getColumnIndex();
			boolean isNumber = (NumberUtils.isNumber(o1.getCells().get(colIndex).getValue())) && (NumberUtils.isNumber(o2.getCells().get(colIndex).getValue()));
			if (sortOrderBean.isAsc()) {
				if (isNumber) {
					compareToBuilder.append(Double.parseDouble(o1.getCells().get(colIndex).getValue()), Double.parseDouble(o2.getCells().get(colIndex).getValue()));
				} else {
					compareToBuilder.append(o1.getCells().get(colIndex).getValue(), o2.getCells().get(colIndex).getValue());
				}
			} else {
				if (NumberUtils.isNumber(o1.getCells().get(colIndex).getValue())) {
					compareToBuilder.append(Double.parseDouble(o2.getCells().get(colIndex).getValue()), Double.parseDouble(o1.getCells().get(colIndex).getValue()));
				} else {
					compareToBuilder.append(o2.getCells().get(colIndex).getValue(), o1.getCells().get(colIndex).getValue());
				}
			}
		}

		// Result
		return compareToBuilder.toComparison();
	}

}
