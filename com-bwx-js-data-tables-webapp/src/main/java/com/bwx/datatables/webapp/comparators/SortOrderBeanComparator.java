package com.bwx.datatables.webapp.comparators;

import java.util.Comparator;

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
public final class SortOrderBeanComparator {

	/**
	 * Sorts SortOrderBean in ascending order according to the defined sort
	 * order specified on the bean.
	 */
	private static class SortOrderBeanAscComparator implements Comparator<SortOrderBean> {

		@Override
		public int compare(SortOrderBean o1, SortOrderBean o2) {
			if (o1.getSortOrderIndex() > o2.getSortOrderIndex()) {
				return 1;
			} else if (o1.getSortOrderIndex() < o2.getSortOrderIndex()) {
				return -1;
			}
			return 0;
		}
	}

	/**
	 * Sorts SortOrderBean in ascending order according to the defined sort
	 * order specified on the bean.
	 */
	public static final SortOrderBeanAscComparator SORT_ORDER_ITEM_ASC = new SortOrderBeanAscComparator();
	
}
