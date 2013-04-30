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

package cwa.netflow.services;
import java.io.IOException;
import java.net.*;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import cwa.netflow.protocol.Datagram;

/** NetflowServer - Captures netflow v5 data from devices. Implements observable interface
* to allow controllers and interfaces to register for changes  
*
* @author David Turvey
* @version 1.1 10-12-12
* @version 1.0 6-10-12
*/

public class NetflowServer extends Observable implements Runnable{
	private String fname;
	private int port;
	private boolean stopping;
	private boolean stopped;
	
	
	public NetflowServer(String args[]) throws Exception
	{
		// Initialise this as observable
		super();
		// Set the default values
		port = -1;
		fname = "";
		// Parse the arguments for the filename and socket number

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
		if(port == -1 || fname == "")
		{
			LogManager lm = LogManager.getLogManager();
			Logger l = lm.getLogger(lm.getLoggerNames().nextElement());
			l.log(Level.ALL, "Usage -f [ouput filename] -p [port number]");
			System.out.println("Usage -f [ouput filename] -p [port number]");
			throw new Exception("Invalid server input parameters");
		}
		stopping = false;
		return;
	}
	
	
	/** Description of stopServer()
	 *  
	 *  Flags (but does not guarantee) the server to stop
	 *
	 */
	public void stopServer()
	{
		stopping = true;
		System.out.println("Server stopping");
	}
	
	/** Description of isRunning()
	 *  
	 *  @return True if the server is running, false otherwise
	 *
	 */
	public boolean isRunning()
	{
		return stopped;
	}

	@Override
	/** Description of run()
	 *  
	 *  Starts the server listening on the pot and outputting to the given CSV file
	 *
	 */
	public void run() {
		LogManager lm = LogManager.getLogManager();
		Logger l = lm.getLogger(lm.getLoggerNames().nextElement());
		System.out.println("Server listening on port"+port);
		// Open the socket and write each datagram received to the CSV file
		DatagramSocket serverSocket=null;
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			l.log(Level.SEVERE,"Unable to start server on port number"+port);
		}

		l.log(Level.INFO, "Listening on port"+port);
		// The packet has a maximum of 16 byte header and 24 records of 48 bytes per record (v5)
		byte[] receiveData = new byte[2048];
		stopped = false;
		while(!stopping)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO: Handle exception
				e.printStackTrace();
			}
			Datagram d = new Datagram(receiveData);
			d.writeCSV(fname);
		}
		stopped = true;
		
	}

} 
