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

package cwa;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import cwa.netflow.*;
import cwa.netflow.protocol.Datagram;

/** NetflowServer - Captures netflow v5 data from devices on UDP port 9876  
*
* @author David Turvey
* @version 1.0 6-10-12
*/

public class NetflowServer {
   public static void main(String args[]) throws Exception
   {
	   // Parse the arguments for the filename and socket number
	   String fname="";
	   Integer port = null;
	   
	   for (int i = 0; i < args.length; i++) {
		   switch (args[i].charAt(0)) {
		   case '-':
			   if(args[i].compareToIgnoreCase("-f")==0)
			   {
				   fname=args[i+1];
				   i++;
			   }
			   if(args[i].compareToIgnoreCase("-p")==0)
			   {
				   port=Integer.parseInt(args[i+1]);
				   i++;
			   }
			   break;
		   default:
			   fname = args[i];
			   break;
		   }
	   }
	   
	   // Ensure that we have been passed the required parameters
	   if(port==null || fname=="")
	   {
		   LogManager lm = LogManager.getLogManager();
		   Logger l = lm.getLogger(lm.getLoggerNames().nextElement());
		   l.log(Level.ALL, "Usage -f [ouput filename] -p [port number]");
		   System.out.println("Usage -f [ouput filename] -p [port number]");
		   System.exit(-1);
	   }
	   
	   // Open the socket and write each datagram received to the CSV file
	   DatagramSocket serverSocket = new DatagramSocket(port.intValue());
	   System.out.println("Listening on UDP port "+port.intValue());
	   
	   // The packet has a maximum of 16 byte header and 24 records of 48 bytes per record (v5)
	   byte[] receiveData = new byte[2048];

	   while(true)
	   {
		   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		   serverSocket.receive(receivePacket);
		   Datagram d = new Datagram(receiveData);
		   d.writeCSV(fname);
	   }
   }
} 
