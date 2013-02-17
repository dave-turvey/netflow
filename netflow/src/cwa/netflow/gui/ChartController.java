package cwa.netflow.gui;

import cwa.netflow.models.ChartModel;

public class ChartController {

	private ChartModel m_chart_model;
	private ChartView m_chart_view;
	
	public ChartController()
	{
		m_chart_model = new ChartModel();
		m_chart_view=new ChartView(m_chart_model);
	}

	public ChartView getChartView() {
		return m_chart_view;
	}	
}
