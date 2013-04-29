package cwa.netflow.unittesting;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import cwa.netflow.models.IPRangeFilter;

public class IPRangeFilterTest {

	private HashMap<String,String> map;
	private IPRangeFilter iprf=null;
	
	@Before
	public void setUp() throws Exception {
		map = new HashMap<String,String>();
	}

	@Test
	public void testIPRangeFilter() {
		iprf = new IPRangeFilter("10.10.10.0", "10.10.10.255");
	}

	@Test
	public void testApplyFilter() {
		if(iprf == null)
			testIPRangeFilter();
		map.clear();
		map.put("10.10.10.0", "Test 1");
		map.put("10.10.10.125", "Test 2");
		map.put("10.10.10.255", "Test 3");
		map.put("192.168.200.1", "Test 4");
		//TODO: Modify to test with <string,timeseries>
		// iprf.applyFilter(map);
		
		if(map.size() != 1)
			fail("Apply filter: returned "+map.size()+ " rather than 1 element after filtering");
	}

	@Test
	public void testAddIPRange() {
		if(iprf == null)
			testIPRangeFilter();
		iprf.addIPRange("192.168.200.1", "192.168.200.255");
		String[] svals= iprf.getStartIPRangeArray();
		String[] evals= iprf.getEndIPRangeArray();
		
		if((svals.length != evals.length) && (svals.length != 2))
			fail("Add range: start vals="+svals.length+" end vals="+evals.length+" should both be 2");
	}

	@Test
	public void testRemoveIPRange() {
		if(iprf == null)
			testIPRangeFilter();
		
		if(!iprf.removeIPRange("10.10.10.0"))		// Remove ranges to zero
			fail("Remove range: Failed to remove original range");
		if(iprf.removeIPRange("10.10.10.0"))		// Remove range that doesn't exist
			fail("Remove range: successfully removed range that didn't exist");
		
		if(!iprf.addIPRange("192.168.200.1", "192.168.200.255"))
			fail("Remove range: Failed to add range to empty filter");
		if(!iprf.removeIPRange("192.168.200.1"))		// Remove range that does exist
			fail("Remove range: successfully removed range that was addd");
		
		String[] svals= iprf.getStartIPRangeArray();
		String[] evals= iprf.getEndIPRangeArray();
		
		if((svals.length != evals.length) && (svals.length != 1))
			fail("Add range: start vals="+svals.length+" end vals="+evals.length+" should both be 1");
	}

	@Test
	public void testGetStartIPRangeArray() {
		if(iprf == null)
			testIPRangeFilter();
		String[] svals= iprf.getStartIPRangeArray();
		if(svals.length != 1)
			fail("Add get start IP range: start vals="+svals.length+" should be 1");
		if(!iprf.addIPRange("192.168.200.1", "192.168.200.255"))
			fail("Add get start IP range: Failed to add new IP range");
		svals= iprf.getStartIPRangeArray();
		if(svals.length != 2)
			fail("Add get start IP range: start vals="+svals.length+" should be 2");
	}

	@Test
	public void testGetEndIPRangeArray() {
		if(iprf == null)
			testIPRangeFilter();
		String[] evals= iprf.getEndIPRangeArray();
		if(evals.length != 1)
			fail("Add get end IP range: start vals="+evals.length+" should be 1");
		if(!iprf.addIPRange("192.168.200.1", "192.168.200.255"))
			fail("Add get start IP range: Failed to add new IP range");
		evals= iprf.getEndIPRangeArray();
		if(evals.length != 2)
			fail("Add get start IP range: start vals="+evals.length+" should be 2");
	}

}
