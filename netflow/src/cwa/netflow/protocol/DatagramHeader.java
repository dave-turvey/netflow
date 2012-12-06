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

import java.util.Date;

/** DatagramHeader - Parses and stores a single datagram header  
*
* @author David Turvey
* @version 1.0 6-10-12
*/

public class DatagramHeader {
	private int version;
	private int flowcount;
	private long sysuptime;
	private long unixtime;
	private int sampleinterval;
	private int sequencenum;
	
	/** Description of DatagramFlowRecord(byte[] data)
	 * 
	 * Converts the byte array into an internal datagram header
	 * 
	 * @param data 		byte array of data for conversion
	 *
	 */	
	public DatagramHeader(byte[] data) {
		setVersion(ConvertByte.byteToInt(data[0]));
		setFlowCount(ConvertByte.byteToInt(data[3]));
		setSysupTime(data);
		setUnixTime(data);
		setSequenceNum(data);	
		setSampleInterval(data[22],data[23]);
	}

	private void setSequenceNum(byte[] data) {
		sequencenum = 0;
		sequencenum = sequencenum | (ConvertByte.byteToInt(data[16]));
		sequencenum = sequencenum << 8;
		sequencenum = sequencenum | (ConvertByte.byteToInt(data[17]));
		sequencenum = sequencenum << 8;
		sequencenum = sequencenum | (ConvertByte.byteToInt(data[18]));
		sequencenum = sequencenum << 8;
		sequencenum = sequencenum | (ConvertByte.byteToInt(data[19]));
		sequencenum = sequencenum << 8;
	}

	private void setSampleInterval(byte b, byte c) {
		sampleinterval = 0;
		sampleinterval = sampleinterval | (ConvertByte.byteToInt(b));
		sampleinterval = sampleinterval << 8;
		sampleinterval = sampleinterval | (ConvertByte.byteToInt(c));
		
	}
	
	private void setSysupTime(byte[] data) {
		byte sutime[] = new byte[4];
		for(int i=0;i<4;i++)
		{
			sutime[i] = data[4+i];
		}
		sysuptime = ConvertByte.byteToULong(sutime);
	}

	/** Description of setUnixTime()
	 * 
	 * Parser for Unix time in seconds since 1970
	 * Will need to subtract sys uptime as a base time for all time measurements 
	 *    
	 *
	 */
	private void setUnixTime(byte[] data) {
		byte utime[] = new byte[4];
		for(int i=0;i<4;i++)
		{
			utime[i] = data[8+i];
		}
		unixtime = ConvertByte.byteToULong(utime);
		Date d = new Date(unixtime*1000);
	}
	
	private void setFlowCount(int i) {
		this.flowcount = i;
	}
	
	private void setVersion(int i) {
		this.version = i;
	}

	/** Description of getSysBaseTime()
	 * 
	 * Note returns the base time to the nearest second
	 * All other readings are in milliseconds
	 * 
	 * @return long System start time in seconds since 1970   
	 *
	 */
	public long getSysBaseTime() {
		return unixtime - (sysuptime/1000);
	}
	
	/** Description of getFlowCount()
	 * 
	 * getter for flow count value 
	 * 
	 * @return int	flow index for this datagram   
	 *
	 */
	public int getFlowCount() {
		return flowcount;
	}

	/** Description of getVersion()
	 * 
	 * getter for datagram version number 
	 * 
	 * @return int	datagram version   
	 *
	 */
	public int getVersion() {
		return version;
	}
	

	/** Description of getSequence()
	 * 
	 * getter for the datagram sequence number in this flow 
	 * 
	 * @return int	datagram sequence number
	 *
	 */
	public int getSequence() {
		return sequencenum;
	}
	
	
}
