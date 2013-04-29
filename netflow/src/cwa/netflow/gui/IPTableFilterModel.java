package cwa.netflow.gui;
import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

import cwa.netflow.models.IPRangeFilter;


public class IPTableFilterModel extends AbstractTableModel implements Observer{

	private IPRangeFilter m_IPRF;
	/**
	 *	Generated serial ID 
	 */
	private static final long serialVersionUID = 3961770757750451471L;

	public IPTableFilterModel(IPRangeFilter IPRF)
	{
		m_IPRF = IPRF;
	}
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return m_IPRF.getStartIPRangeArray().length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		String[] vals;
		if(col==0)
			vals = m_IPRF.getStartIPRangeArray();
		else
			vals = m_IPRF.getEndIPRangeArray();
		
		return vals[row];
	}

	public boolean isCellEditable(int row, int col)
    { return false; }

	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	
	public Class<?> getColumnClass(int c)   
    {      
        return String.class;   
    }
	
	@Override
	public void update(Observable arg0, Object arg1) {
		this.fireTableDataChanged();
	}   


}
