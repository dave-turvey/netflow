package cwa.netflow.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import cwa.netflow.models.IPRangeFilter;

public class RangeFilterController extends SwingWorker<Integer, String> implements ActionListener{

	private IPRangeFilterView m_IPRFV;
	private IPRangeFilter m_IPRF;
	private ChartController m_chart_controller;
	
	public RangeFilterController(ChartController controller)
	{
		super();
		
		m_IPRF = new IPRangeFilter("0.0.0.0", "0.0.0.0");
		m_IPRFV = new IPRangeFilterView(this, m_IPRF);
		m_chart_controller = controller;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		System.out.println("Range filter controller - do in background ");
		m_IPRFV.setVisible(true);
		return null;
	}
	
	synchronized public void showView(boolean show)
	{
		m_IPRFV.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().compareTo("Add")==0)
		{
			// Extract the IP range to add and insert it into the model
			this.addIPRange(m_IPRFV.getStartIPAddr(),m_IPRFV.getEndIPAddr());
		}
		else if(e.getActionCommand().compareTo("Del")==0)
		{
			// Extract the start IP address and remove it from the model
			this.delIPRange(m_IPRFV.getStartIPAddr());
			
		}else if(e.getActionCommand().compareTo("Close")==0){
			m_IPRFV.setVisible(false);
			m_chart_controller.updateIPRange(m_IPRF);
		}else{
			System.out.println("Properties controller: Unknown action performed:"+e.getActionCommand());
		}
	}

	private void delIPRange(String startIPAddr) {
		if(validateIPAddress(startIPAddr))
			m_IPRF.removeIPRange(startIPAddr);
		else
			JOptionPane.showMessageDialog(m_IPRFV,"Invalid IP Address Format",
				    					  "Format error", JOptionPane.ERROR_MESSAGE);
		
	}

	public void addIPRange(String startIPAddr, String endIPAddr) {
		if(!validateIPAddress(startIPAddr))
		{
			JOptionPane.showMessageDialog(m_IPRFV,"Invalid start IP Address Format",
					  "Format error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!validateIPAddress(endIPAddr))
		{
			JOptionPane.showMessageDialog(m_IPRFV,"Invalid end IP Address Format",
					  "Format error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		m_IPRF.addIPRange(startIPAddr, endIPAddr);
	}
	
	private static final String PATTERN = 
	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private static boolean validateIPAddress(final String ip){          

	      Pattern pattern = Pattern.compile(PATTERN);
	      Matcher matcher = pattern.matcher(ip);
	      return matcher.matches();             
	}

}
