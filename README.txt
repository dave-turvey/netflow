-----------------
1.  INTRODUCTION
-----------------

This application has been developed to allow users to capture and display data from Cisco devices using the netflow V5 over UDP transport.
Initially is was an exercise for displaying network traffic to students in a network management class at the college of west anglia (hence the CWA path)

help an support would always be appreciated!!    

-------------------
2.  LATEST VERSION
-------------------

V0.2 available at https://github.com/dave-turvey/netflow.git

-----------------
3.  DOCUMENTATION
-----------------
The application has a single component:

Netflow Chart Application 	- Provides a service to capture and display netflow data

CONFIGURING THE NETFLOW SERVER: 

You will need to configure your netflow devices to export the netflow data. Details of this can be found at:

http://www.cisco.com/en/US/docs/ios/netflow/configuration/guide/cfg_nflow_data_expt_ps6922_TSD_Products_Configuration_Guide_Chapter.html

 
You start the netflow server using the menu in the application

The netflow server currently supports netflow V5 format only

USING THE NETFLOW CHARTING APPLICATION

You need to run the chart application with a single (optional) parameter 

-f [filename] 

this is the full path to the file created by the NetFlow server.
 
The application allows the display of time series charts and IP filtered charts. The IP filter is accessed in the properties menu and allows a set of subnets to be added for discarding specific ranges.

-----------------
4.  DEPENDENCIES
-----------------

The netflow chart application requires JFreechart which is available at http://www.jfree.org/jfreechart/

-----------
5.  SUPPORT
-----------
Not currently available 

--------------------
6.  ANT BUILD SCRIPT
--------------------

The ant build script used to build the distribution and is included in the top level as build.xml. To run the ant script directly from the command line, edit the buildit.bat file to set the environment variables to ant and jfreechart installations, then just run buildit. The final jar is placed in the dist folder.

---------------
8.  LIMITATIONS
---------------
As V0.2 there are a large number of limitations, not least:

No support for netflow versions other than V5 format 
No file rotation or file management for long term capture of data
No support for database capture
No support for openflow data
 
--------------
9.  WHAT's NEW
--------------

v0.2 - Integrated functionality and added GUI. Considerable re-architecture to MVC
V0.1 - Initial implementation supporting fixed chart types and Netflow V5 data format  

-----------------
10.  Contributors
-----------------

Project lead - David Turvey
