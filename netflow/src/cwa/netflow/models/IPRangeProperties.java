package cwa.netflow.models;


import java.util.HashMap;

public class IPRangeProperties extends ChartProperties {
		// These are the properties that can be set by the user 
		private String m_fname;
		
		// This is the underlying data model that needs to be re-built each time the properties change
		private TotalsCSVReader reader;
		
		public IPRangeProperties(String fname)
		{
			setFname(fname);
		}
		
		/*
		 * @return HashMap<String, Timeseries> Map between IP address and time series for that address
		 */
		public HashMap<String,Integer> getTotalsData() 
		{
			return reader.getTotalsItems();
		}

		public String getFname() {
			return m_fname;
		}

		public void setFname(String m_fname) {
			this.m_fname = m_fname;
			reader = new TotalsCSVReader(m_fname, TotalsCSVReader.IP, TotalsCSVReader.OCTETS);
		}

}
