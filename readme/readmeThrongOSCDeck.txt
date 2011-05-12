ThrongOSCDeck is an OSC recorder and player. It allows to record an OSC stream, save it to file,
load it from file and play back an OSC stream.

ThrongOSCDeck has been developed to record TUIO streams (http://www.tuio.org) but it can be used
for any kind of OSC stream.

ThrongOSCDeck is embedded into the Throng infrastructure. This means that ThrongOSCDeck works as
an OSC proxy that sits between the OSC message provider and receiver. To record an OSC stream,
tell your OSC provider to direct its OSC datagrams to the inbound Ip and inbound port (default:
3332) of ThrongOSCDeck. The received data will be automatically forwarded to the outbound Ip 
(default: 127.0.0.1) and the outbound port (default: 3333). Throng currently works only via UDP.

If the playback of an OSC stream is started, the OSC bundles will be sent to the outbound Ip address 
and outbound port named in the ThrongOSCDeck's GUI.

If an OSC stream has been recorded, it can be saved to file in a human readable and editable text 
format. The file ending is .osc.

A saved OSC message may look like this:
/tuio/2Dcur sset i19 f0.20625 f0.11666667 f-0.07352941 f0.19607842 f12.27757
The address (in this example '/tuio/2Dcur') remains unchanged. Each attribute afterwards is preceded
with its OSC data type in the first char. E.g., sset encodes the string value 'set', i19 encodes the 
integer value 19 or f0.11666667 encodes the float value 0.11666667.

An OSC bundle consists of several OSC messages. In the employed file format each OSC bundle is 
preceded by three additional lines, each separated by a LF:
2415
127.0.0.1
61845
In this example the OSC message has been received 2415 ms after the start of the recording. The 
origin Ip address is 127.0.0.1 and the origin port is 61845 (this port is automatically assigned
by the operating system and has nothing to do with the target port of the OSC datagram).

Each bundle is separated from the next by 2 LF.

Version history

v014
fixed a bug where saving took ages.

v013
first version.