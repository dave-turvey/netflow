package cwa;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBox;

public class MainWindow {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenu mnCollector = new JMenu("Collect");
		menuBar.add(mnCollector);
		
		JMenuItem mntmNewstart = new JMenuItem("Start");
		mnCollector.add(mntmNewstart);
		
		JMenuItem mntmStop = new JMenuItem("Stop");
		mnCollector.add(mntmStop);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences");
		mnCollector.add(mntmPreferences);
		
		JMenu mnAnalyse = new JMenu("Analyse");
		menuBar.add(mnAnalyse);
		
		JMenuItem mntmByProtocol = new JMenuItem("By Protocol");
		mnAnalyse.add(mntmByProtocol);
		
		JMenuItem mntmByAddress = new JMenuItem("By Address");
		mnAnalyse.add(mntmByAddress);
		
		JMenuItem mntmFilters = new JMenuItem("Filters");
		mnAnalyse.add(mntmFilters);
	}

}
