# throng

## Throng (Tuio multiplexeR that crOps and Globalizes)

Throng provides a Tuio multiplexer with added benefits written in Java. Throng's code base can be employed as a sophisticated OSC/TUIO processing framework.

Please download the releases in the sidebar if you do not want to build it from source. You will find three ready-to-use applications:

### 1. Throng

1.) … is a Tuio proxy (or bridge) that is necessary for applications that may only access network sources on the local machine (like Adobe Flash). If other devices (like iPhones) want to access such an application via Tuio over the network, a proxy will be necessary.

2.) … multiplexes Tuio alive messages: If Tuio messages from multiple sources access one client application (or as it is called in OSC: a server application), all sources use alive messages that only contain their alive ids. If the client application does not support different source providers (which is the case for many tuio libraries), each Tuio source will remove the alive objects and cursors of each other Tuio source causing, e.g., touches to flicker.

3.) … individualizes the session ids of each source: if different Tuio message sources use the same session id range, the results can be … awkward. To prevent this Throng individualizes those messages.

4.) … provides one consistent Tuio Fseq message order for all arriving packets.

5.) … adds a Tuio source message to each Tuio package or, if such a message already exists, modifies this message after the “@” to contain source_ip:source_port to allow for discerning of sources in the client application.

### 2. Throng OSCDeck 

Throng OSCDeck provides an OSC recording and playback application. OSC streams can be recorded and saved to file. Vice versa, OSC stream can be loaded from file and played back.

### 3. Throng Custom

Throng Custom provides an application in which the x and y values of Tuio set messages of different sources can be cropped, shrunk and moved in order to allow for, e.g., a two camera – two tracker system on two computers for one interactive surface.


Throng builds on the [http://www.illposed.com/software/javaosc.html illposed OSC Java implementation] and on the [http://www.tuio.org/?software TUIO Java implementation by M. Kaltenbrunner].

*Use Throng applications*

The applications come as runnable jars - doubleclick to start them. Make sure to direct the Tuio input to the inbound IP address and port. If you use Throng's default settings, send Tuio input to port 3332. If the Tuio provider is not running on the same computer, you will also have to send the input to the IP address of Throng's computer (or to your LAN's broadcast address). For convenience, you can find Throng's inbound IP address in its GUI.

With the default settings, it should not be necessary to modify your actual Tuio applications as packages are still sent to port 3333.

If you want to have debug output, you can start Throng (and the other applications) via command line:
```
  cd _/Throng application/path
  java -jar Throngxxx.jar -autoStart true|false -inboundPort port -outboundPort port -debug true|false
  java -jar ThrongOSCDeckxxx.jar -autoStart true|false -inboundPort port -outboundIp ip -outboundPort port -debug true|false
```

In debug mode, Throng will prompt the contents of modified Tuio packages to the Terminal. This may help you in analyzing Tuio communication.
