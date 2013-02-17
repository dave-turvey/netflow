package cwa.netflow.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import cwa.netflow.models.ChartModel;

// Also need a method of registering the model and the view with this controller
// http://www.irit.fr/~Remi.Bastide/Teaching/Java/MVC/


public class ApplicationController implements ActionListener {

	private ApplicationView m_main_window; // Application view object in the MVC
	private PropertiesController m_properties_controller;
	private ChartController m_chart_controller;
	
	public ApplicationController(final String[] args) {
		try {
			// Create a chart Controller which will give us a chart view
			// This is an integral component of the application view so pass ourselves 
			m_chart_controller = new ChartController();
			
			// Create the application window view
			m_main_window = new ApplicationView(m_chart_controller);
			
			// Register this to handle the user input i.e. Get the control
			m_main_window.addMenuItemListener(this);
		
			// Now create the Model object. This is the set of properties we will use to filter each of the views 
			// it could be IP, Time series etc, etc, etc
			// We will need to have a properties view and controller (its own dialogue) so that it can be updated
			// we will also need to register the views with it. The views are actually the chart display object 
			// that will need to change when the properties change
			// In a Jfreechart the actual model is the timeseries data and the view (chart) will updated when this model
			// is changed. In this case the model must be capable of reporting/changing its type
	
			
			// Create the other controllers on the fly to reduce startup time
			
			m_properties_controller = null;
			
			//Run the Application Window swing interface in its own thread
			EventQueue.invokeLater(m_main_window);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Action controller action perfomed:"+arg0.getActionCommand());
		if(arg0.getActionCommand().matches("Properties"))
		{
			if(m_properties_controller==null)
			{
				m_properties_controller = new PropertiesController();
				// Run this as a swing worker thread
				m_properties_controller.execute();
			}
		}
	}
}
