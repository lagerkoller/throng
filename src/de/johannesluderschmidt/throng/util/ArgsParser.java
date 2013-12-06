package de.johannesluderschmidt.throng.util;

public class ArgsParser {

	private int inboundPort;
	private int outboundPort;
	private String outboundIp;
	private boolean autoStart;
	private boolean debug;
	private boolean showWarning;
	private boolean minimize;

	public ArgsParser(String[] args) {
		inboundPort = Constants.DEFAULT_INPUT_PORT;
		outboundIp = Constants.DEFAULT_IP;
		outboundPort = Constants.DEFAULT_OUTPUT_PORT;
		autoStart = Constants.AUTOSTART;
		debug = Constants.DEFAULT_DEBUG;
		showWarning = false;
		
		for(int i = 0; i < args.length; i++){
			String arg = args[i];
			
			if(arg.equals("help")){
				showWarning = true;
			}
			
			if(arg.indexOf("-")==0){
				boolean foundCorrectParameter = false;
				
				if(arg.equals("-inboundPort")){
					if(args.length > i+1){
						inboundPort = parsePort(args[i+1], Constants.DEFAULT_INPUT_PORT);
						foundCorrectParameter = true;
					}else{
						inboundPort = Constants.DEFAULT_INPUT_PORT;
					}
				}
				
				if(arg.equals("-outboundIp")){
					if(args.length > i+1){
						outboundIp = args[i+1];
						foundCorrectParameter = true;
					}else{
						outboundIp = Constants.DEFAULT_IP;
					}
				}
				
				if(arg.equals("-outboundPort")){
					if(args.length > i+1){
						outboundPort = parsePort(args[i+1], Constants.DEFAULT_OUTPUT_PORT);
						foundCorrectParameter = true;
					}else{
						outboundPort = Constants.DEFAULT_OUTPUT_PORT;
					}
				}
				
				if(arg.equals("-autoStart")){
					if(args.length > i+1){
						autoStart = Boolean.parseBoolean(args[i+1]);
						foundCorrectParameter = true;
					}else{
						autoStart = Constants.AUTOSTART;
					}
				}
				
				if(arg.equals("-minimize")){
					if(args.length > i+1){
						minimize = Boolean.parseBoolean(args[i+1]);
						foundCorrectParameter = true;
					}else{
						minimize = Constants.MINIMIZE;
					}
				}
				
				if(arg.equals("-debug")){
					if(args.length > i+1){
						debug = Boolean.parseBoolean(args[i+1]);
						foundCorrectParameter = true;
					}else{
						debug = Constants.DEFAULT_DEBUG;
					}
				}
				
				if(!foundCorrectParameter){
					showWarning = true;
				}
			}
		}
	}
	
	private int parsePort(String port, int defaultPort){
		int calcPort;
		try{
			calcPort = Integer.parseInt(port);
			if(calcPort < 1 || calcPort > 65535){
				calcPort = defaultPort;	
			}
		}catch(NumberFormatException ex){
			calcPort = defaultPort;			
		}
		
		return calcPort;
	}

	public int getInboundPort() {
		return inboundPort;
	}

	public int getOutboundPort() {
		return outboundPort;
	}

	public String getOutboundIp() {
		return outboundIp;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public boolean isShowWarning() {
		return showWarning;
	}

	public boolean isDebug() {
		return debug;
	}
	
	public boolean isMinimize() {
		return minimize;
	}

	public void setMinimize(boolean minimize) {
		this.minimize = minimize;
	}
}
