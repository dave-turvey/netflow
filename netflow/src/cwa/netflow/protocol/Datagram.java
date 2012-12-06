/* Copyright (c) 2012, David Turvey
*  All rights reserved.

*  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
*  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
*  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation 
*  and/or other materials provided with the distribution.

*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
*  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
*  BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
*  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
*  IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
*  THE POSSIBILITY OF SUCH DAMAGE.
*/

package cwa.netflow.protocol;

/** Datagram - Parses and stores a single datagram header and flow records  
*
* @author David Turvey
* @version 1.0 6-10-12
*/

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Datagram {
	private DatagramHeader head;
	private ArrayList<DatagramFlowRecord> records;
	
	/** Description of Datagram(byte[] data)
	 * 
	 * Constructor to build a datagram header and set of flow records from a byte array
	 * 
	 * @param data		data to be parsed
	 * 
	 *
	 */
	public Datagram(byte[] data)
	{
		head = new DatagramHeader(data);
		int offset = 24; // 24 bytes per header record
		records = new ArrayList<DatagramFlowRecord>();
		for(int i=0;i < head.getFlowCount();i++)
		{
			records.add(new DatagramFlowRecord(data,offset));
			offset+=48; // 48 bytes per flow record
		}
	}
	
	/** Description of writeCSV(string fname)
	 * 
	 * writes the comma delimited data based on the internal representation of the datagram
	 * 
	 * @param fname		full path to the file to be written to 
	 *
	 */
	public void writeCSV(String fname)
	{
		try
		{
		    FileWriter writer = new FileWriter(fname,true);  
		    Iterator<DatagramFlowRecord> itr = records.iterator();        
		    DatagramFlowRecord d;
		    while(itr.hasNext())
		    {
		    	d = itr.next();
		   
		    	writer.append(d.getCSVData(head.getSequence(),head.getSysBaseTime()));
		   
		    	writer.append('\n');
		    }
		    writer.flush();
		    writer.close(); 
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 

	}
}
