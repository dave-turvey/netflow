package cwa.netflow.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import cwa.netflow.models.IPRangeFilter;

public class PropertiesController extends SwingWorker<Integer, String> implements ActionListener{

	private IPRangeFilterView m_IPRFV;
	private IPRangeFilter m_IPRF;
	
	public PropertiesController()
	{
		super();
		
		m_IPRF = new IPRangeFilter("0.0.0.0", "0.0.0.0");
		m_IPRFV = new IPRangeFilterView(this, m_IPRF);
		m_IPRFV.setVisible(true);
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		return null;
	}
	
	@Override
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
			m_IPRFV.dispose();
		}else{
			System.out.println("Properties controller: Unknown action performed:"+e.getActionCommand());
		}
	}

	private void delIPRange(String startIPAddr) {
		// TODO: Validate the ip address and show an error if it is invalid
		m_IPRF.removeIPRange(startIPAddr);
		
	}

	public void addIPRange(String startIPAddr, String endIPAddr) {
		// TODO: Validate the ip addresses and show an error if it is invalid
		m_IPRF.addIPRange(startIPAddr, endIPAddr);
	}

}
