------------- UserGuide for the UrlyBird application ------------- 

-------- Content -------- 
1. Overview
2. Starting the client application
3. Starting the server application
4. Starting the stand alone application (no networking)
5. The UrlyBirdView



-------- 1. Overview --------

The UrlyBird applications supports the  customer service representatives (CSRs) by their daily work.
It offers a simple way to list all rooms in the underlying database and allows the CSR to search for specific rooms and 
book rooms for a customer.

The application could be started as an client-server application (s. 2. and 3.) or as a stand-alone application without any networking (s. 4.).



-------- 2. Starting the client application --------

!Important: to work with the client application u need a running instance of the server application (s. 3.)!

There are two ways to start the client application: 

1. Through the command line:
	- open a command line
	- go with the command line to the directory of the runme.jar file.
	- type: java -jar runme.jar
2. By double clicking:
	- go in your explorer to the directory of the runme.jar file
	- double click on it.

Either way starts a dialog in which u could establish a connection to the server.
To connect to the server fill the host field with the server address (for example "localhost")
and the port field with the port number to connect to (for example "1099").
The port text field only accepts digits.	

If you filled both fields you can connect to the server by clicking the "connect" button.
The client connects now to the server and shows you the UrlyBirdView (s. 5.).
If the client can not establish a connection an error window is shown with a description of the problem.



-------- 3. Starting the server application --------

!Important the server application starts only a server, to retrieve or modify any data you need a connected instance of the client application (s. 2.)!

You could start the server application through the command line:
	- open a command line
	- go with the command line to the directory of the runme.jar file.
	- type: java -jar runme.jar server

A dialog starts in which u can configure the path of the database the server should use and the address under which the server should be started.
	- To specify the database click on the button "Select" and choose a file with the now opened file chooser.
	- To specify the address fill the field "Host" and "Port" (the port field only accepts digits).

At the head of the dialog is a label that provides you with status information about the server.
If you have filled all fields you could start the server with the button "Start Server".

If the server is running it could be shut down by clicking the Button "Stop Server".



-------- 4. Starting the stand alone application --------
 
!Important the stand alone application is a local version of the UrlyBirdApplication. There is no networking involved and it is a single user application!
 
You could start the server application through the command line:
	- open a command line
	- go with the command line to the directory of the runme.jar file.
	- type: java -jar runme.jar alone

A dialog starts in which a could specify the database path by clicking on the button "Select". After you had chosen your UrlyBirdDatabase file you could 
connect to with the "Connect" button.
If you have specified a valid data file the UrlyBirdView starts (s. 5.).	  



-------- 4. The UrlyBirdView --------

The UrlyBirdView is the main gui of the UrlyBird application.
In the center of the view is the table that displays the rooms that are stored in the database (by clicking on the button "all").

The table is sortable and you could change the sequence of the columns by drag and drop.
The following columns are displayed for every room:
	 - "ID": an unique identifier for the room.
	 - "Hotel Name": the name of the hotel that offers the room.
	 - "Location": the name of the city where the hotel is located at.
	 - "Room Size": the size of the room in persons.
	 - "Smoking": if smoking is allowed ("Y") or disallowed ("N") in this room.
	 - "Price": the price of the room in US Dollar $.
	 - "Date": the occupancy of this room.
	 - "Customer ID": the id of the customer that has booked the room.

The following buttons are available:
	 - "All": searches for all stored rooms in the database and displays them in the table.
	 - "Find": Opens a find dialog in which u could execute a search for room which city and/or location name begins with a specified string. 
	  			You could also select the checkbox "any" to search for any city and/or location name.
	 - "New": Is disabled in this version. 
	 - "Book": Opens a dialog in which a could insert the customer id (only 8 digits are allowed) of the customer that wants to book the room. 
	 			A click on the "book" button, books the room for the customer and stores this in the database. The table is refreshed.
	 - "Delete": Is disabled in this version. 
	 - "Change": Is disabled in this version. 	
	 - "Exit": Exits the application.

The following menu is available:
	- UrlyBird -> Connection: Opens a dialog that enables u to specify a new database (in the stand alone mode) or server (in the client mode) connection.
							  If you leave the dialog without specifying a new connection the old one is used.	 		   




			Have fun with the UrlyBird application!!!