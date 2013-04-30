package cwa.netflow.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingWorker;

import cwa.netflow.models.ChartModel;
import cwa.netflow.models.ViewType;
import cwa.netflow.services.NetflowServer;

// Also need a method of registering the model and the view with this controller
// http://www.irit.fr/~Remi.Bastide/Teaching/Java/MVC/


public class ApplicationController implements ActionListener {

	private ApplicationView m_main_window; // Application view object in the MVC
	private RangeFilterController m_range_filter_controller;
	private ChartController m_chart_controller;
	private NetflowServer m_nfserver;
	
	public ApplicationController(final String[] args) {
		try {
			// Create a chart Controller which will give us a chart view
			// This is an integral component of the application view so pass ourselves 
			m_chart_controller = new ChartController(args);
			
			// Create the application window view
			m_main_window = new ApplicationView(m_chart_controller);
			
			// Register this to handle the user input i.e. Get the control
			m_main_window.addMenuItemListener(this);
			
			// Create the other controllers on the fly to reduce startup time
			
			m_range_filter_controller = null;
			m_nfserver = null;
			
			
			//Run the Application Window swing interface in its own thread
			EventQueue.invokeLater(m_main_window);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(arg0.getActionCommand());
		
		if(arg0.getActionCommand().matches("Start"))
		{
			if(m_nfserver==null)
			{
				String args[]=new String[4];
				args[0] = "-f";
				args[1] = "l:/java/data.csv";
				args[2] = "-p";
				args[3] = "2055";
				try {
					m_nfserver = new NetflowServer(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Thread t = new Thread(m_nfserver);
		    t.start();
		}
		
		if(arg0.getActionCommand().matches("Stop"))
		{
			if(m_nfserver!=null && m_nfserver.isRunning())
				m_nfserver.stopServer();
		}
		
		if(arg0.getActionCommand().matches("Properties"))
		{
			if(m_range_filter_controller==null)
			{
				m_range_filter_controller = new RangeFilterController(m_chart_controller);
			}
			m_range_filter_controller.execute();
			m_range_filter_controller.showView(true);
		}
		
		if(arg0.getActionCommand().matches("Time Series"))
		{
			System.out.println("IP address");
			m_chart_controller.setType(ViewType.TIMESERIES);
			m_main_window.fireChartViewChange();
		}
		
		if(arg0.getActionCommand().matches("IP Address"))
		{
			System.out.println("IP address");
			m_chart_controller.setType(ViewType.IPADDRESS);
			m_main_window.fireChartViewChange();
		}
		
		if(arg0.getActionCommand().matches("Protocol"))
		{
			System.out.println("Protocol");
			m_chart_controller.setType(ViewType.PROTOCOL);
			m_main_window.fireChartViewChange();
		}
	}
}
