package cwa.netflow.gui;

import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JLabel;

import cwa.netflow.models.IPRangeFilter;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;
import java.util.Observable;
import java.util.Observer;

import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.UIManager;

public class IPRangeFilterView  extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3724705632364076274L;

	private RangeFilterController m_properties_controller;
	
	private JTable table;	
	private JTextField txtStartIp;
	private JTextField txtEndIp;
	
	public IPRangeFilterView(RangeFilterController PC, IPRangeFilter IPFilter) {
		// Register the Controller so that call backs can be added by the buttons 
		m_properties_controller = PC;
		// setup the jtable based on the set of values in the filter
		IPTableFilterModel table_filter_model = new IPTableFilterModel(IPFilter);
		table = new JTable(table_filter_model);
		table_filter_model.addTableModelListener(table);
		// No add the table model as the observer so the table view can be updated when something
		// is added or removed
		IPFilter.addObserver(table_filter_model);
		// Kept this as the design view fails on initialisation of the FilterModel
		// table = new JTable();
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		JPanel panel_2 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(table, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(table, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
		);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(m_properties_controller);
		panel_2.add(btnCancel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{119, 85, 105, 0};
		gbl_panel.rowHeights = new int[]{23, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblStartAddress = new JLabel("Start address");
		GridBagConstraints gbc_lblStartAddress = new GridBagConstraints();
		gbc_lblStartAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartAddress.gridx = 0;
		gbc_lblStartAddress.gridy = 0;
		panel.add(lblStartAddress, gbc_lblStartAddress);
		
		txtStartIp = new JTextField();
		GridBagConstraints gbc_txtStartIp = new GridBagConstraints();
		gbc_txtStartIp.insets = new Insets(0, 0, 5, 5);
		gbc_txtStartIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtStartIp.gridx = 1;
		gbc_txtStartIp.gridy = 0;
		panel.add(txtStartIp, gbc_txtStartIp);
		txtStartIp.setColumns(10);
		
		JLabel lblEndAddress = new JLabel("End Address");
		GridBagConstraints gbc_lblEndAddress = new GridBagConstraints();
		gbc_lblEndAddress.insets = new Insets(0, 0, 5, 5);
		gbc_lblEndAddress.gridx = 0;
		gbc_lblEndAddress.gridy = 1;
		panel.add(lblEndAddress, gbc_lblEndAddress);
		
		txtEndIp = new JTextField();
		GridBagConstraints gbc_txtEndIp = new GridBagConstraints();
		gbc_txtEndIp.insets = new Insets(0, 0, 5, 5);
		gbc_txtEndIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEndIp.gridx = 1;
		gbc_txtEndIp.gridy = 1;
		panel.add(txtEndIp, gbc_txtEndIp);
		txtEndIp.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(m_properties_controller);
		panel_1.add(btnAdd);
		
		JButton btnRemove = new JButton("Del");
		btnRemove.addActionListener(m_properties_controller);
		panel_1.add(btnRemove);
		getContentPane().setLayout(groupLayout);
		
		this.pack();
	}

	public String getStartIPAddr() {
		return txtStartIp.getText();
	}
	
	public String getEndIPAddr() {
		return txtEndIp.getText();
	}

}
