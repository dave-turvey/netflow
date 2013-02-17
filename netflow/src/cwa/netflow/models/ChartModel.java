package cwa.netflow.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


public class ChartModel {
	TimeSeriesProperties m_time_series_properties;
	// TotalsProperties m_totals_properties; TODO:Implement totals
	
	public ChartModel()
	{
		// TODO: Load the file info from the application properties singleton
		// today    
		Calendar today = new GregorianCalendar();	// Midnight today
		// reset hour, minutes, seconds and millis
		today.set(Calendar.YEAR, 2012);
		today.set(Calendar.MONTH, 0);
		today.set(Calendar.DAY_OF_MONTH, 1);
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		// Midnight tomorrow
		Calendar tomorrow = (Calendar) today.clone();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		m_time_series_properties = new TimeSeriesProperties("C:\\Users\\Dave\\Documents\\java\\workspace\\flowdata.csv", today.getTime(), tomorrow.getTime());
		
	}
	
	public HashMap<String,TimeSeries> getTimeSeriesDataItems()
	{
		return m_time_series_properties.getTimeSeriesData();
	}
	
	public TimeSeriesProperties getTimSeriesProperties() {
		return m_time_series_properties;
	}

	public void setTimeSeriesProperties(
			TimeSeriesProperties m_time_series_properties) {
		this.m_time_series_properties = m_time_series_properties;
	}


}
