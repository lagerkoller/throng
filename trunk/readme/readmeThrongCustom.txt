Throng Custom allows to adjust Tuio messages that come from different Tuio message providers. This can for instance be quite handy if you try to build a multi-touch table that is based on two cameras that work with two tracking systems: Usually, each Tuio set message is based between 0 and 1. However, if you use two cameras you might want the left camera to yield messages between 0 and 0.5 and the right camera between 0.5 and 1.

Throng Custom can be configured via XML in the file client.xml. The Ips and ports for each Tuio message producer must be set in order to distinguish which settings should be applied to which message. See attached client.xml for further reference.

Actually, only one dynamic GUI element allows to interactively control the x offset. If you need more elements: Build them into Throng custom yourself ;)

Throng Custom looks for the file client.xml in its folder.

Throng uses other libraries. Beware the licenses.

Version history

v007
Initial version