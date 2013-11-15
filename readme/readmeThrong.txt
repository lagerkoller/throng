Messages arrive as Tuio over UDP and they will be resent via Tuio over UDP.

Per default incoming messages arrive at port 3332. As the common port for Tuio messages is port 3333, you have to adapt the port of your Tuio message producer (like CCV, Touchlib, Tuio Simulator and so on) to port 3332 (or whatever port you choose). If the Tuio message producer is not running on the same computer, Tuio messages must be sent to the IP shown behind "My IP Address". If you use the default settings, the Tuio application must listen on (the common) port 3333.

If the checkbox "Manage Alive Ids" is checked, the alive ids of all the Tuio message producers will be managed. This means that if Tuio message producer A sends a list of alive Ids, Throng will remember these alive ids. If Tuio message producer B sends another list of alive ids, these will be joined with the alive ids from Tuio message producer A. Uncheck the checkbox to see what happens: E.g., touches will start to flicker as everytime a Tuio packet arrives from A the touches of B will be removed in your application. (However, you can teach your application not to do so and manage alive id lists for each producer. Anyway, as most Tuio libraris does not do this for you, "Manage Alive Ids" is checked by default.)

If the checkbox "Individualize Ids" is checked (which is only possible if "Manage Alive Ids" is checked), Throng "tries" to individualize the Tuio session ids for you. "Tries" means following simple algorithm: A certain threshold will be added to the session id of each producer in order to prevent session ids to overlap. E.g., no threshold will be added to the ids of producer A, 10,000 will be added to the ids of B, 20,000 to the messages of C and so on. However, after a certain time, session ids could overlap nonetheless. Thus, this approach should be improved.

To start from the command line, do the following:
- cd to the folder of the jar file
- java jar jarname.jar
- optional arguments: -autoStart true|false -inboundPort port -outboundPort port -debug true|false 

Throng uses other libraries. Beware of the licenses.

Version history

v015
added options for the start from the command line (see explanations above)

v014
fixed a bug where saving took ages.

v013
major code redesign. added GPLv3 license. lots of bugfixing.

v012
added support for Windows (before Throng would crash under Windows because of some Mac specific Java classes)
removed obsolete XML library dependencies

v011
Improved range handling of different Tuio producers. Now, each producer has 100,000 ids that it can use. If a session id from a producer outranges this amount, it will start from the beginning of the range. If, e.g., 3 Tuio producers send packets, the ids of the first producer range from 0 to 99,999, the second from 100,000 to 199,999 and the third from 200,000 to 299,000.

Renamed checkbox labels.

Added application name under Mac OS X.

Set the focus on the start button after the application's start.

v010
added support for Tuio blob messages /tuio/2dblb.
improved exception handling.

v009
added checkbox "Manage Source Message": If this checkbox is checked, Tuio source messages will be modified after the "@" to contain source_ip:source_port. The original source ip after the "@" will be removed. Uncheck it to prevent that.

v008
Initial version