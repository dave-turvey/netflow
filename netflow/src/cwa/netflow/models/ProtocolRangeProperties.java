package cwa.netflow.models;

import java.util.HashMap;

public class ProtocolRangeProperties extends ChartProperties{
	// These are the properties that can be set by the user
	private String m_fname;

	// This is the underlying data model that needs to be re-built each time the
	// properties change
	private TotalsCSVReader reader;

	public ProtocolRangeProperties(String fname) {
		setFname(fname);
	}

	/*
	 * @return HashMap<String, Integer> Map between IP address and time series
	 * for that address
	 */
	public HashMap<String, Integer> getTotalsData() {
		return reader.getTotalsItems();
	}

	public String getFname() {
		return m_fname;
	}

	public void setFname(String m_fname) {
		this.m_fname = m_fname;
		reader = new TotalsCSVReader(m_fname, TotalsCSVReader.PROTOCOL,
				TotalsCSVReader.OCTETS);
	}
}
