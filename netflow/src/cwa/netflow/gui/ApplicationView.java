package cwa.netflow.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ApplicationView implements Runnable, ActionListener{
	private JFrame frame;
	private JMenuBar menuBar;
	private ArrayList<ActionListener> listeners;
	private JPanel chartPanel;
	
	public ApplicationView(ChartController m_chart_controller) {
		listeners = new ArrayList<ActionListener>();
		chartPanel = m_chart_controller.getChartView();
	}

	@Override
	public void run() {
		// Initialise the interface in the event dispatch thread see article:
		// http://www.javaworld.com/javaworld/jw-08-2007/jw-08-swingthreading.html?page=5
		
		initialize();
		
		// Add this as the main event listener
		addMenuItemCallbacks();
		
		// Display the Application window
		frame.setVisible(true);
	}
	
	/** Description of actionPerformed()
	 *  
	 * Iterate over the set of registered application listeners and execute each one.
	 *
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Iterator<ActionListener> li = listeners.iterator();
	
		while(li.hasNext())
		{
			li.next().actionPerformed(arg0);
		}
	}

	/** Description of addModel()
	 *  
	 *  Add a model to this view so that we can register as an observer of it.
	 *  @param m - model to register as an observer of
	 *
	 */
	public void addModel(Observable m)
	{
		m.addObserver((Observer) this);
	}

	/** Description of addMenuItemCallbacks()
	 *  
	 *  Register this class as the main callback for all of the menus. We need to do this as
	 *  all of the registered action listeners need to be called in the swing worker threads 
	 *  to stop the interface freezing it they take a long time
	 *
	 */
	private void addMenuItemCallbacks()
	{
		JMenu m;
		JMenuItem jm;
		
		for(int i=0;i<menuBar.getMenuCount();i++ )
		{
			m=menuBar.getMenu(i);
			for(int j=0;j<m.getItemCount();j++)
			{
				jm=m.getItem(j);
				jm.addActionListener(this);
			}
		}
	}
	
	public void addMenuItemListener(ActionListener il)
	{
		listeners.add(il);
	}
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add the main chart to the application window
		frame.add(chartPanel);
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnListener = new JMenu("Listener");
		menuBar.add(mnListener);
		
		JMenuItem mntmStart = new JMenuItem("Start");
		mnListener.add(mntmStart);
		
		JMenuItem mntmStop = new JMenuItem("Stop");
		mnListener.add(mntmStop);
		
		JMenu mnCharts = new JMenu("Charts");
		menuBar.add(mnCharts);
		
		JMenuItem mntmTimeSeries = new JMenuItem("IP Address");
		mnCharts.add(mntmTimeSeries);
		
		JMenuItem mntmProtocol = new JMenuItem("Protocol");
		mnCharts.add(mntmProtocol);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Properties");
		mnCharts.add(mntmNewMenuItem);
	}

	
}
