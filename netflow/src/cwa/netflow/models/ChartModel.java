package cwa.netflow.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cwa.data.DataSource;


public class ChartModel {
	private ViewType m_current_type;
	private ChartProperties m_chart_properties;
	private Calendar m_today;
	private Calendar m_tomorrow;
	
	public ChartModel()
	{
		m_today = new GregorianCalendar();	// Midnight today
		// reset hour, minutes, seconds and millis
		m_today.set(Calendar.YEAR, 2012);
		m_today.set(Calendar.MONTH, 0);
		m_today.set(Calendar.DAY_OF_MONTH, 1);
		m_today.set(Calendar.HOUR_OF_DAY, 0);
		m_today.set(Calendar.MINUTE, 0);
		m_today.set(Calendar.SECOND, 0);
		m_today.set(Calendar.MILLISECOND, 0);

		// Midnight tomorrow
		m_tomorrow = (Calendar) m_today.clone();
		m_tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		buildTimeSeriesModel();
		
	}
	
	private void buildTimeSeriesModel()
	{
    
		DataSource ds = DataSource.getDataSource();		
		m_chart_properties = new TimeSeriesProperties(ds.getSourceName(), m_today.getTime(), m_tomorrow.getTime());
		m_current_type = ViewType.TIMESERIES;
	}
	
	/*
	 * @return - The time series data items if this is the current model, otherwise null
	 */
	
	public HashMap<String,TimeSeries> getTimeSeriesDataItems()
	{
		if(m_current_type == ViewType.TIMESERIES)
		{
			TimeSeriesProperties tsp = (TimeSeriesProperties) m_chart_properties;
			return tsp.getTimeSeriesData();
		}else{
			return null;
		}
	}
	
	public HashMap<String, Integer> getIPSeriesDataItems()
	{
		if(m_current_type == ViewType.IPADDRESS)
		{
			IPRangeProperties iprp = (IPRangeProperties) m_chart_properties;
			return iprp.getTotalsData();
		}else{
			return null;
		}
	}

	public HashMap<String, Integer> getProtocolSeriesDataItems() {
		if(m_current_type == ViewType.PROTOCOL)
		{
			ProtocolRangeProperties iprp = (ProtocolRangeProperties) m_chart_properties;
			return iprp.getTotalsData();
		}else{
			return null;
		}
	}

	public void setType(ViewType type) {
		DataSource ds = DataSource.getDataSource();		
		
		if(type == ViewType.TIMESERIES){
			buildTimeSeriesModel();
			m_current_type = type;
		}
		if(type == ViewType.IPADDRESS){
			m_chart_properties = new IPRangeProperties(ds.getSourceName());
			m_current_type = type;
		}
		if(type == ViewType.PROTOCOL){
			m_chart_properties = new ProtocolRangeProperties(ds.getSourceName());
			m_current_type = type;
		}
	}
	
	public ViewType getType()
	{
		return m_current_type;
	}

	
}
