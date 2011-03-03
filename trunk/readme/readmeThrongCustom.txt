Throng Custom allows to adjust Tuio messages that come from different Tuio message providers. This can for instance be quite handy if you try to build a multi-touch table that is based on two cameras that work with two tracking systems: Usually, each Tuio set message is based between 0 and 1. However, if you use two cameras you might want the left camera to yield messages between 0 and 0.5 and the right camera between 0.5 and 1.

Throng Custom can be configured via XML in the file client.xml. The Ips and ports for each Tuio message producer must be set in order to distinguish which settings should be applied to which message. See attached client.xml for further reference.

Actually, only one dynamic GUI element allows to interactively control the x offset. If you need more elements: Build them into Throng custom yourself ;)

Throng Custom looks for the file client.xml in its folder.

Throng uses other libraries. Beware the licenses.

Version history
v012
added support for Windows (before Throng would crash under Windows because of some Mac specific Java classes)
removed obsolete XML library dependencies

v011
Improved range handling of different Tuio producers. Now, each producer has 100,000 ids that it can use. If a session id from a producer outranges this amount, it will start from the beginning of the range. If, e.g., 3 Tuio producers send packets, the ids of the first producer range from 0 to 99,999, the second from 100,000 to 199,999 and the third from 200,000 to 299,000.

v010
added support for Tuio blob messages /tuio/2dblb.
improved exception handling.

v008
Initial version