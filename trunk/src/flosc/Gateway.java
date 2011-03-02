package flosc;import throngModel.Host;import throngModel.ThrongCropper;import throngModel.ThrongMultiplexer;import java.util.Vector;/** * Gateway <BR><BR> OpenSoundControl / Flash Gateway Server. Usage: java Gateway [oscPort] [flashPort] * @author    Ben Chun          		ben@benchun.net * @author	  Ignacio Delgado   		idelgado@h-umus.it * @author	  Johannes Luderschmidt 	luderschmidt@yahoo.de * @version   2.0 */public class Gateway{	private static final long serialVersionUID = 1L;	private OscServer broadcastOscServer;        private Vector<OscServer> oscServers;    private String outputIp;    private int outputPort;        private boolean useCropper;    private boolean listenOnBroadcastAddress;	private boolean manageAliveIds;    private boolean individualize;    private boolean manageSrcMsg;        public Gateway(int[] oscInputPorts, Vector<Host> hosts, String outputIp, int outputPort){    	this.outputIp = outputIp;    	this.outputPort = outputPort;    	    	oscServers = new Vector<OscServer>();        for(int i=0; i < oscInputPorts.length; i++){	    	OscServer oscServer = new OscServer(oscInputPorts[i], this, outputIp, outputPort);	    	oscServers.add(oscServer);	    }        	    if(hosts != null){	    	ThrongCropper.getInstance().setHosts(hosts);	    	useCropper = true;	    }    }    public void multiplexPacket(OscPacket packet, OscServer server) throws Exception{    	packet = ThrongMultiplexer.getInstance().multiplexPacket(packet, manageAliveIds, individualize, manageSrcMsg);    	Debug.writeActivity("multiplexed packet "+packet,this);    	if(useCropper){    		packet = ThrongCropper.getInstance().globalizeTUIO(packet, server.getPort());    		Debug.writeActivity("customized packet "+packet,this);    	}    	if(packet != null){    		server.sendPacket(packet);    	}else{    		throw new Exception("Modified OSCPacket == null. Maybe received Tuio packet has an unknown type or is formatted badly and could not be treated by multiplexer.");    	}    }    public void startGateway(){    	for (OscServer oscServer : oscServers){    		oscServer.start();    	}    }        public void stopGateway(){    	for (OscServer oscServer : oscServers){    		oscServer.killServer();    	}    }        public boolean isManageAliveIds() {		return manageAliveIds;	}	public void setManageAliveIds(boolean manageAliveIds) {		this.manageAliveIds = manageAliveIds;	}	public boolean isIndividualize() {		return individualize;	}	public void setIndividualize(boolean individualize) {		this.individualize = individualize;	}	public boolean isManageSrcMsg() {		return manageSrcMsg;	}	public void setManageSrcMsg(boolean manageSrcMsg) {		this.manageSrcMsg = manageSrcMsg;	}		public boolean listensOnBroadcastAddress() {		return listenOnBroadcastAddress;	}}