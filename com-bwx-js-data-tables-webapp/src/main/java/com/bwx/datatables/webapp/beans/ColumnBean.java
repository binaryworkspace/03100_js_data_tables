package com.bwx.datatables.webapp.beans;

/**
 * Table column.
 * 
 * @author Chris Ludka
 *
 */
public class ColumnBean {

		private String header;
		
		private int index;
		
		private String key;

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((header == null) ? 0 : header.hashCode());
			result = prime * result + index;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
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
			ColumnBean other = (ColumnBean) obj;
			if (header == null) {
				if (other.header != null)
					return false;
			} else if (!header.equals(other.header))
				return false;
			if (index != other.index)
				return false;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ColumnBean [header=" + header + ", index=" + index + ", key=" + key + "]";
		}
		
}
