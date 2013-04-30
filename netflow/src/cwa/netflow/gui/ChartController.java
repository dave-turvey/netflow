package cwa.netflow.gui;

import cwa.netflow.models.ChartModel;
import cwa.netflow.models.IPRangeFilter;
import cwa.netflow.models.ViewType;

public class ChartController {

	private ChartModel m_chart_model;
	private ChartView m_chart_view;
	
	public ChartController(String[] args)
	{
		m_chart_model = new ChartModel();
		m_chart_view=new TimeChartView(m_chart_model);
	}
	
	public void setType(ViewType type)
	{
		m_chart_model.setType(type);
		if(type == ViewType.TIMESERIES)
		{
			m_chart_view=new TimeChartView(m_chart_model);
		}
		if(type == ViewType.IPADDRESS)
		{
			m_chart_view=new IPTotalsView(m_chart_model);
		}
		if(type == ViewType.PROTOCOL)
		{
			m_chart_view=new ProtocolTotalsView(m_chart_model);
		}
		
	}

	public void updateIPRange(IPRangeFilter rf)
	{
		m_chart_view.setIPRangeFilter(rf);
	}
	
	public ChartView getChartView() {
		return m_chart_view;
	}	
}
