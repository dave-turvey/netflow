package cwa.netflow.models;

import java.util.Date;
import java.util.HashMap;

import org.jfree.data.time.TimeSeries;

public class TimeSeriesProperties extends ChartProperties{
	// These are the properties that can be set by the user 
	private String m_fname;
	private Date m_start_time;
	private Date m_end_time;
	private Boolean m_running_total;
	
	// This is the underlying data model that needs to be re-built each time the properties change
	private TimeSeriesCSVReader reader;
	
	public TimeSeriesProperties(String fname, Date start_time, Date end_time)
	{
		// Don't use the setters internally as these will re-build the model each time
		m_start_time = start_time;
		m_end_time = end_time;
		m_running_total = false;
		reader = new TimeSeriesCSVReader(fname, m_running_total);
	}
	
	/*
	 * @return HashMap<String, Timeseries> Map between IP address and time series for that address
	 */
	public HashMap<String,TimeSeries> getTimeSeriesData() 
	{
		return reader.getTimeSeriesItems();
	}
	
	public Date getStartTime() {
		return m_start_time;
	}

	public void setStartTime(Date m_start_time) {
		this.m_start_time = m_start_time;
		
	}

	public Date getEndTime() {
		return m_end_time;
	}

	public void setEndTime(Date m_end_time) {
		this.m_end_time = m_end_time;
	}

	public String getFileName() {
		return m_fname;
	}

	public void setFileName(String m_fname) {
		this.m_fname = m_fname;
	}
	
}
