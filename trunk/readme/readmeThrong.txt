Messages arrive as Tuio over UDP and they will be resent via Tuio over UDP.

Per default incoming messages arrive at port 3332. As the common port for Tuio messages is port 3333, you have to adapt the port of your Tuio message producer (like CCV, Touchlib, Tuio Simulator and so on) to port 3332 (or whatever port you choose). If the Tuio message producer is not running on the same computer, Tuio messages must be sent to the IP shown behind "My IP Address". If you use the default settings, the Tuio application must listen on (the common) port 3333.

If the checkbox "Manage Alive Ids" is checked, the alive ids of all the Tuio message producers will be managed. This means that if Tuio message producer A sends a list of alive Ids, Throng will remember these alive ids. If Tuio message producer B sends another list of alive ids, these will be joined with the alive ids from Tuio message producer A. Uncheck the checkbox to see what happens: E.g., touches will start to flicker as everytime a Tuio packet arrives from A the touches of B will be removed in your application. (However, you can teach your application not to do so and manage alive id lists for each producer. Anyway, as most Tuio libraris does not do this for you, "Manage Alive Ids" is checked by default.)

If the checkbox "Individualize Ids" is checked (which is only possible if "Manage Alive Ids" is checked), Throng "tries" to individualize the Tuio session ids for you. "Tries" means following simple algorithm: A certain threshold will be added to the session id of each producer in order to prevent session ids to overlap. E.g., no threshold will be added to the ids of producer A, 10,000 will be added to the ids of B, 20,000 to the messages of C and so on. However, after a certain time, session ids could overlap nonetheless. Thus, this approach should be improved.

Throng uses other libraries. Beware of the licenses.

Version history

v007
Initial version