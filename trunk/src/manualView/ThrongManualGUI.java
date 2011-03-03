package manualView;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import loader.ThrongLoader;
import mac.MacAboutHandler;
import throngModel.Host;
import throngModel.ThrongClient;
import util.Config;
import util.Constants;

import com.muchsoft.util.Sys;
import com.muchsoft.util.mac.Java14Adapter;

import flosc.Debug;
import flosc.Gateway;

public class ThrongManualGUI extends JFrame implements MouseListener{
	
	public static final int PADDING_TOP = 5;
	public static final int PADDING_LEFT = 5;
	public static final int PADDING_RIGHT = 5;
	public static final int LABEL_HEIGHT = 20;
	public static final int GUI_WIDTH = 400;
	
	private JButton startStopButton;
	private boolean floscStarted = false;
	private Gateway throngGateway;
	
	private Vector<JLabel> oscPorts;
	private JLabel oscOutputPort;
	private JLabel outputIp;

	private Vector<HostRow>hostRows;
	private Vector<Host> hosts;
	
	private boolean singleTUIOMode = true;
	private boolean multipleLocalTUIOMode = false;
	private boolean multipleNetworkTUIOMode = false;
	
	private JPanel variableGUIPanel;
	private JPanel portPanel;
	private JPanel inputPanel;
	
	private JPanel offsetTopPanel;
	private JPanel offsetBottomPanel;
	
	private GridBagLayout gridbag;
	private GridBagConstraints c;
	
	private GridBagLayout gridbagVariable;
	private GridBagConstraints cVariable;
	
	private GridBagLayout gridbagPort;
	private GridBagConstraints cPort;
	
	private GridBagLayout gridbagRest;
	private GridBagConstraints cRest;
	
	private GridBagLayout gridbagInput;
	private GridBagConstraints cInput;
	
	private HashMap<String, ThrongClient> clientsDict;
	
	public ThrongManualGUI(String windowName){
		super(windowName);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setJMenuBar(getCorrectMenuBar());
		
		//initialize layout for input panel
		gridbagInput  = new GridBagLayout();
		cInput = new GridBagConstraints();
		
		//initialize layout for variable panel
		gridbagVariable = new GridBagLayout();
		cVariable = new GridBagConstraints();
		
		//initialize layout for port panel
		gridbagPort= new GridBagLayout();
		cPort = new GridBagConstraints();
		
		//initialize layout for rest panel
		gridbagRest= new GridBagLayout();
		cRest = new GridBagConstraints();
		
		gridbag = new GridBagLayout();
		c = new GridBagConstraints();
		
		hostRows = new Vector<HostRow>();
		
		Container pane = getContentPane();
		pane.setLayout(gridbag);
		
		int tempHeight = PADDING_TOP;
		
		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(0,1));
	    ButtonGroup group = new ButtonGroup();
	    
	    Dimension sizes = getSize();

	    c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbag.setConstraints(radioPanel, c);
		add(radioPanel);
		
		setMultipleNetworkProducerLayout();
		
		setResizable(false);
        setVisible(true);

        try{
        	loadFile(System.getProperty("user.dir")+System.getProperty("file.separator")+Constants.DEFAULT_FILE_NAME);
        }catch(Exception e){}
	}
	
	/**
	    * Mac Os X and Windows systems get different menubars to use systemwide
	    * preferences like mnemonics and labels for default functions like open 
	    * 
	    * @return
	    */
	   private JMenuBar getCorrectMenuBar(){
		   if(Sys.isMacOSX()){
			   System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
			   System.setProperty("apple.laf.useScreenMenuBar", "true");
			   System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Throng Custom");
			   Java14Adapter.registerJava14Handler(new MacAboutHandler(this));
			   return new MacJMenuBar(this);
		   }else{
			   return new WindowsJMenuBar(this);
		   }
		}
	
	private void initDefaultOSCInputPort(){
		oscPorts = new Vector<JLabel>();
		oscPorts.add(new JLabel( "3333", 4));
	}
	
	private void setMultipleNetworkProducerLayout(){
		try{
			remove(variableGUIPanel);
		}catch(Exception e){}
		variableGUIPanel = new JPanel();
		variableGUIPanel.setLayout(gridbagVariable);

		inputPanel = new JPanel();
		inputPanel.setLayout(gridbagInput);
		
		JLabel inputPanelLabel = new JLabel("Input:");
		cInput.gridwidth = GridBagConstraints.REMAINDER; //end row
		gridbagInput.setConstraints(inputPanelLabel, cInput);
		inputPanel.add(inputPanelLabel);
		
        cVariable.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbagVariable.setConstraints(inputPanel, cVariable);
        variableGUIPanel.add(inputPanel);
		
		portPanel = new JPanel();
		portPanel.setLayout(gridbagPort);
		
		oscPorts = new Vector<JLabel>();
		
		//output ip row
		outputIp = new JLabel( "127.0.0.1");
		
		cPort.fill = GridBagConstraints.BOTH;
		gridbagPort.setConstraints(outputIp, cPort);
		
		portPanel.add(new JLabel("OSC Output    IP: "));
		portPanel.add(outputIp);
		
		//output port row
		oscOutputPort = new JLabel( "3334", 4);
		
		gridbagPort.setConstraints(oscOutputPort, cPort);
		
		portPanel.add(new JLabel("    Port: "));
		portPanel.add(oscOutputPort);
		
        cVariable.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbagVariable.setConstraints(portPanel, cVariable);
        variableGUIPanel.add(portPanel);
		
		JPanel restGUIPanel = new JPanel();
		restGUIPanel.setLayout(gridbagRest);
		
        startStopButton = new JButton("Start Throng");
        
        cRest.gridwidth = GridBagConstraints.REMAINDER;
        gridbagRest.setConstraints(startStopButton, cRest);
        
        startStopButton.addMouseListener(this);
        restGUIPanel.add(startStopButton);
		
		cVariable.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridbagVariable.setConstraints(restGUIPanel, cVariable);
        
        variableGUIPanel.add(restGUIPanel);
        
        getContentPane().add(variableGUIPanel);
		pack();
	}
	
	private void addTopOrBottomPanel(JPanel topOrBottomPanel, String topOrBottom, Float value){
		GridBagLayout gridbagTopOrBottom = new GridBagLayout();
		GridBagConstraints cTopOrBottom = new GridBagConstraints();
		topOrBottomPanel.setLayout(gridbagTopOrBottom);
		
		JLabel topOrBottomLabel = new JLabel("Offset "+ topOrBottom+":");
		topOrBottomPanel.add(topOrBottomLabel);
		
		JTextField topOrBottomTextField = new JTextField(value.toString());
		
		cTopOrBottom.gridwidth = GridBagConstraints.REMAINDER; //end row
		gridbagTopOrBottom.setConstraints(topOrBottomTextField, cTopOrBottom);
		
		topOrBottomPanel.add(topOrBottomTextField);
		
	}
	private void addHostRow(ThrongClient client){
		HostRow hostRow = new HostRow();
		
		//ip
		JLabel oscHostLabel = new JLabel("  OSC Host "+(hostRows.size()+1)+"    IP: ");
		hostRow.setOscHostLabel(oscHostLabel);
		
		JLabel oscHost = new JLabel(client.getIp());
		hostRow.setOscHost(oscHost);
		
		//port
		JLabel oscHostPortLabel = new JLabel("    Port: ");
		hostRow.setOscHostPortLabel(oscHostPortLabel);
		
		JLabel oscHostPort = new JLabel(""+client.getPort());
		hostRow.setOscHostPort(oscHostPort);
		
		
		if(Config.getInstance().getUseOffsets() && client.getWidth() != null){
			ThrongSlider hostWidthSliderPanel = new ThrongWidthSlider(client.getType(), hosts, hostRows.size());
			JLabel hostWidthSliderLabel = new JLabel("    Width: ");
			hostWidthSliderPanel.setSliderLabel(hostWidthSliderLabel);
			
			JSlider hostWidthSlider = new JSlider();
			hostWidthSlider.setValue((int)(client.getWidth()*100));
			hostWidthSliderPanel.setSlider(hostWidthSlider);
			
			JLabel hostWidthSliderLabelValue = new JLabel();
			hostWidthSliderPanel.setSliderValueLabel(hostWidthSliderLabelValue);
			
			hostRow.setWidthSlider(hostWidthSliderPanel);
			
			if(hostRows.size() > 0){
				hostWidthSliderPanel.setPreviousSlider(hostRows.get(hostRows.size()-1).getWidthSlider());
				hostRows.get(hostRows.size()-1).getWidthSlider().setNextSlider(hostWidthSliderPanel);
			}
		}
		
		JLabel typeLabel = new JLabel();
		typeLabel.setText("    type: "+client.getType()+ "  ");
		hostRow.setTypeLabel(typeLabel);
		
		cInput.gridwidth = GridBagConstraints.REMAINDER; //end row
		gridbagInput.setConstraints(hostRow, cVariable);
		inputPanel.add(hostRow);
		
		hostRows.add(hostRow);
		
	}
	private void removeHostRow(RemoveHostJButton removeButton){
		inputPanel.remove(removeButton.getHostRow());
		hostRows.remove(removeButton.getHostRow());
		pack();
	}
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ThrongManualGUI("Throng Custom "+Constants.VERSION);
            }
        });
        
        int i=0;
		boolean correctParameterFound=false;
		for(String arg:args){
			if(arg.equals("-debug")){
				correctParameterFound = true;
				try{
					Debug.enableDebug(Boolean.parseBoolean(args[i+1]));
				}catch(ArrayIndexOutOfBoundsException e){}
			}
			i = i+1;
		}
		if(args.length>0 && !correctParameterFound){
			System.out.println("Usage: -debug true|false.");
		}
	}
	
	public boolean isSingleTUIOMode() {
		return singleTUIOMode;
	}

	public boolean isMultipleLocalTUIOMode() {
		return multipleLocalTUIOMode;
	}

	public boolean isMultipleNetworkTUIOMode() {
		return multipleNetworkTUIOMode;
	}
	
	/**
	 * triggered when start/stop server is clicked.
	 */
	public void mouseClicked(MouseEvent e) {
		if(!floscStarted){
			try{
				try{
					if(oscOutputPort!= null){
						HashSet<Integer> ports = new HashSet<Integer>();
						
						hosts.clear();
						for(HostRow hostRow : hostRows){
							Host host = new Host();
							String key = hostRow.getOscHost().getText()+hostRow.getOscHostPort().getText();
							host.setIp(hostRow.getOscHost().getText());
							host.setPort(clientsDict.get(key).getPort());
							ports.add(clientsDict.get(key).getPort());
							host.setType(clientsDict.get(key).getType());
							if(Config.getInstance().getUseOffsets()){
								host.setWidth(((float)hostRow.getWidthSlider().getSlider().getValue())/100);
								host.setCropStartY(clientsDict.get(key).getCropStartY());
								host.setCropEndY(clientsDict.get(key).getCropEndY());
								host.setFlipRotation(clientsDict.get(key).getFlipRotation());
							}else{
								try{
									host.setFlipRotation(clientsDict.get(key).getFlipRotation());
								}catch(Exception ex){
									host.setFlipRotation(false);
								}
							}
							host.setFlipXAndY(clientsDict.get(key).getFlipXAndY());
							hosts.add(host);
						}
						int[] oscPortsArray = new int[ports.size()];
						int i = 0;
						Iterator<Integer> portsIterator = ports.iterator(); 
						while ( portsIterator.hasNext() ){
							oscPortsArray[i] = portsIterator.next();
							i = i+1;
						}
						throngGateway = new Gateway( oscPortsArray,
						     hosts,
						     outputIp.getText(),
						     Integer.parseInt(oscOutputPort.getText())
						     );
						throngGateway.startGateway();
					}
					startStopButton.setText("Stop Throng");
					floscStarted = true;
				}catch(Exception ex){
					Debug.writeActivity("Error when trying to start Flosc: "+ex.getMessage(),this);
					ex.printStackTrace();
				}
			}catch(Exception ex){Debug.writeActivity("Error when starting gateway from GUI: "+ex.getMessage(),this);}
		}else{
			try{
				startStopButton.setText("Start Throng");
				floscStarted = false;
				throngGateway.stopGateway();
			}catch(Exception ex){Debug.writeActivity("Error when stopping gateway from GUI: "+ex.getMessage(),this);}	
		}
	}
	
	public void loadFile(String fileName){
			if(fileName.toLowerCase().endsWith(".xml")){
				ThrongLoader loader=new ThrongLoader(fileName, this); 
				this.clientsDict = loader.getClientsDict();
					
				//1st switch to network perspective
				setMultipleNetworkProducerLayout();
				
				//2nd remove all current entries
				if(offsetTopPanel != null && offsetBottomPanel != null){
					inputPanel.remove(offsetTopPanel);
					inputPanel.remove(offsetBottomPanel);
				}
					
				int hostRowsSize = hostRows.size(); 
				for(int i = 0; i < hostRowsSize ; i++){
					removeHostRow(hostRows.get(0).getRemoveIPButton());
				}
				
				offsetTopPanel = new JPanel();
				if(Config.getInstance().getUseOffsets()){
					if(!Config.getInstance().isFlipXAndY()){
						addTopOrBottomPanel(offsetTopPanel,"top",Config.getInstance().getOffsetXLeft());
					}else{
						addTopOrBottomPanel(offsetTopPanel,"top",Config.getInstance().getOffsetYTop());
					}
				}
				cInput.gridwidth = GridBagConstraints.REMAINDER; //end row
				gridbagInput.setConstraints(offsetTopPanel, cVariable);
				inputPanel.add(offsetTopPanel);
				
				hosts = new Vector<Host>();
				
				//3rd add new entries
				for(ThrongClient client : loader.getClientsVector()){
					Host host = new Host();
					host.setIp(client.getIp());
					host.setType(client.getType());
					host.setWidth(client.getWidth());
					host.setCropStartY(client.getCropStartY());
					host.setCropEndY(client.getCropEndY());
					host.setFlipRotation(client.getFlipRotation());
					hosts.add(host);
					
					addHostRow(client);
				}
				
				offsetBottomPanel = new JPanel();
				if(Config.getInstance().getUseOffsets()){
					if(!Config.getInstance().isFlipXAndY()){
						addTopOrBottomPanel(offsetBottomPanel,"bottom",Config.getInstance().getOffsetXRight());
					}else{
						addTopOrBottomPanel(offsetBottomPanel,"bottom",Config.getInstance().getOffsetYBottom());
					}
				}
				cInput.gridwidth = GridBagConstraints.REMAINDER; //end row
				gridbagInput.setConstraints(offsetBottomPanel, cVariable);
				inputPanel.add(offsetBottomPanel);
				
				//4th set output ip and port
				outputIp.setText(Config.getInstance().getReceiver());
				oscOutputPort.setText(Config.getInstance().getReceiverPort());
				
				pack();
			}
			
		}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}