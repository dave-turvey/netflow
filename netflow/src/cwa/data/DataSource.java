package cwa.data;

public class DataSource {
	static private DataSource m_ds;
	private String m_sourcename;
	
	private DataSource(){}
	
	static public DataSource getDataSource()
	{
		if(m_ds == null)
			m_ds = new DataSource();
		return m_ds;
	}

	public String getSourceName() {
		return m_sourcename;
	}

	public void setSourceName(String m_sourcename) {
		this.m_sourcename = m_sourcename;
	}
}
