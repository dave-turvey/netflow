package cwa.netflow.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;

import org.jfree.data.time.TimeSeries;

public class IPRangeFilter extends ChartProperties{
	Vector<String> m_start_ip;
	Vector<String> m_end_ip;
	
	/*
	 * Description of IPRangeFIlter
	 * This stores and applies ranges of IP addresses to the underlying models
	 * Ranges are given in pairs. Individual IP addresses are added as a pair with the same values
	 * 
	 */
	
	/** Description of IPRangeFilter(String startIP, String endIP)
	 * 
	 * Holds IP filter ranges and applies them to data structures produced by the CSV Reader objects
	 * 
	 * @param startIP	start IP of the address range		
	 * @param endIP		end ip of the address range
	 *
	 */
	public IPRangeFilter(String startIP, String endIP)
	{
		m_start_ip = new Vector<String>();
		m_end_ip = new Vector<String>();
		
		m_start_ip.add(startIP);
		m_end_ip.add(endIP);
	}
	
	/** Description of applyFilter(HashMap<String,Object> map)
	 * 
	 * Applies current IP filter to a set of netflow values. IP address is always the key in the hash 
	 * map to this means that the method supports all of the netflow value types using IP.  
	 * 
	 * @param startIP	start IP of the address range		
	 * @param endIP		end ip of the address range
	 *
	 */
	public void applyFilter(HashMap<String, TimeSeries> mappeddata)
	{
		Iterator<Entry<String, TimeSeries>> iter = mappeddata.entrySet().iterator();
		while (iter.hasNext()) {
			if(keyInFilter(iter.next().getKey()))
		    {
		    	iter.remove();
		    }
		}
		this.setChanged();
		this.notifyObservers();
	}
	
	// Returns true of the value is in the range of the current filters 
	private boolean keyInFilter(String key)
	{
		for(int i=0;i < m_start_ip.size();i++)
		{

			if((IPAddrToNumeric(key) >= IPAddrToNumeric(m_start_ip.elementAt(i)))
					&&(IPAddrToNumeric(key) <= IPAddrToNumeric(m_end_ip.elementAt(i))))
					{
						return true;
					}
		}
		return false;
	}
	
	/** Description of addIPRange(String startIP, String endIP)
	 * 
	 * Adds additional address ranges to the filter object. These are not applied until a data structure is passed
	 * 
	 * @param startIP	start IP of the address range		
	 * @param endIP		end ip of the address range
	 * @return 			True if the range was successfully added
	 *
	 */
	public boolean addIPRange(String startIP, String endIP)
	{
		if(m_start_ip.add(startIP))
		{
			m_end_ip.add(endIP);
			this.setChanged();
			this.notifyObservers();
			return true;
		}
		
		return false;
	}
	
	/** Description of removeIPRange(String startIP)
	 * 
	 * Removes a range of address specified by this starting address
	 * 
	 * @param startIP	start IP of the address range		
	 * @return 			True if the range was successfully removed
	 *
	 */
	public boolean removeIPRange(String startIP)
	{
		// As we cannot guarantee that the same Object will be representing the IP address
		// it is safer to remove the IP ranges using a string comparison
		Long cip = IPAddrToNumeric(startIP);
		Long aip = (long) 0;
		
		for(int i = 0;i<m_start_ip.size();i++)
		{
			aip = IPAddrToNumeric(m_start_ip.elementAt(i));
			if(aip.equals(cip))
			{
				m_start_ip.remove(i);
				m_end_ip.remove(i);
				this.setChanged();
				this.notifyObservers();
				return true;
			}
		}
		return false;
	}
	
	/** Description of getStartIPRangeArray()
	 * 
	 * Provides the set of start IP address ranges as a string array
	 *
	 */
	public String[] getStartIPRangeArray()
	{
		String[] rv = new String[m_start_ip.size()];
		for(int i=0;i<m_start_ip.size();i++)
		{
			rv[i] = m_start_ip.elementAt(i);
		}
		return rv;
	}
	
	/** Description of getEndIPRangeArray()
	 * 
	 * Provides the set of end IP address ranges as a string array
	 *
	 */
	public String[] getEndIPRangeArray()
	{
		String[] rv = new String[m_end_ip.size()];
		for(int i=0;i<m_end_ip.size();i++)
		{
			rv[i] = m_end_ip.elementAt(i);
		}
		return rv;
	}
	
	/*
	 * Utility function that allows IP address comparison as longs rather than lexical comparison
	 */
	private Long IPAddrToNumeric(String ip) {
        Scanner sc = new Scanner(ip).useDelimiter("\\.");
        return 
            (sc.nextLong() << 24) + 
            (sc.nextLong() << 16) + 
            (sc.nextLong() << 8) + 
            (sc.nextLong()); 
    }
}
