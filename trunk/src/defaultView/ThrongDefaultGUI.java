package defaultView;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import mac.MacAboutHandler;
import mac.MacAppEventListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.apple.eawt.Application;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;
import com.muchsoft.util.Sys;

import flosc.Debug;
import flosc.Gateway;

import java.awt.EventQueue;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JCheckBox;

import util.Constants;

public class ThrongDefaultGUI extends JFrame{
	private JTextField outgoingPortTextField;
	private JTextField incomingPortTextField;
	private JButton startStopButton;
	private JCheckBox chckbxAliveMessages;
	private JCheckBox chckbxIndividualizeIds; 
	private JCheckBox chckbxManageSourceMsg;
	
	private boolean proxyStarted = false;
	private Gateway tuioProxyGateway;
	
	public ThrongDefaultGUI() throws HeadlessException {
		super();
		
		setCorrectLookAndFeel();
		
		setResizable(false);
		setBounds(100, 100, 290, 275);
		
		setTitle("Throng "+Constants.VERSION);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("118px"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("150px"),},
			new RowSpec[] {
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("28px"),
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.UNRELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel myIpAddressLabel = new JLabel("My IP Address:");
		getContentPane().add(myIpAddressLabel, "1, 1, right, center");
		
		JLabel ipText = new JLabel("");
		try {
			ipText.setText(InetAddress.getLocalHost().getHostAddress());
	    }catch (UnknownHostException e) {
	    	e.printStackTrace();
	    }
		getContentPane().add(ipText, "3, 1, left, center");
		
		JLabel incomingPortLabel = new JLabel("Incoming port:");
		getContentPane().add(incomingPortLabel, "1, 3, right, center");
		
		incomingPortTextField = new JTextField();
		incomingPortTextField.setText("3332");
		incomingPortTextField.setColumns(10);
		getContentPane().add(incomingPortTextField, "3, 3, left, center");
		
		JLabel outgoingPortLabel = new JLabel("Outgoing port:");
		getContentPane().add(outgoingPortLabel, "1, 5, right, center");
		
		outgoingPortTextField = new JTextField();
		outgoingPortTextField.setText("3333");
		outgoingPortTextField.setColumns(10);
		getContentPane().add(outgoingPortTextField, "3, 5, left, center");
		
		startStopButton = new JButton("Start Throng");
		startStopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startOrStopProxy();
			}
		});
		getContentPane().add(startStopButton, "3, 7, right, top");

		//sets initial focus on startStopButton
		this.addWindowFocusListener(new WindowAdapter() {
		    public void windowGainedFocus(WindowEvent e) {
		    	startStopButton.requestFocusInWindow();
		    }
		});
		
		chckbxAliveMessages = new JCheckBox("Multiplex Alive Ids");
		chckbxAliveMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxAliveMessages.isSelected()){
					chckbxIndividualizeIds.setEnabled(true);
					chckbxManageSourceMsg.setEnabled(true);
				}else{
					chckbxIndividualizeIds.setEnabled(false);
					chckbxManageSourceMsg.setEnabled(false);
				}
				if(tuioProxyGateway != null){
					tuioProxyGateway.setManageAliveIds(chckbxAliveMessages.isSelected());
				}
			}
		});
		chckbxAliveMessages.setSelected(true);
		chckbxAliveMessages.setToolTipText("<html>If checked, Throng will multiplex Tuio alive ids in a way that all ids "+
				"of alive touches and fiducials will be included in each alive message.</html>");
		getContentPane().add(chckbxAliveMessages, "3, 9");
		
		chckbxIndividualizeIds = new JCheckBox("Use Alive Id Range");
		chckbxIndividualizeIds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tuioProxyGateway != null){
					tuioProxyGateway.setIndividualize(chckbxIndividualizeIds.isSelected());
				}
			}
		});
		chckbxIndividualizeIds.setSelected(true);
		chckbxIndividualizeIds.setToolTipText("If checked, Throng will put alive ids of each client into a distinct range of ids in order to prevent overlapping of alive ids of different Tuio producers.");
		getContentPane().add(chckbxIndividualizeIds, "3, 11");
		
		chckbxManageSourceMsg = new JCheckBox("Manage Src. Msg.");
		chckbxManageSourceMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tuioProxyGateway != null){
					tuioProxyGateway.setManageSrcMsg(chckbxManageSourceMsg.isSelected());
				}
			}
		});
		chckbxManageSourceMsg.setToolTipText("If \"Adapt Src. Msg.\" is selected, the source Ip address and the source port of the Tuio packet will be added after the @ to allow for discerning in the Tuio client application. If no source message is included, it will be created.");
		chckbxManageSourceMsg.setSelected(true);
		getContentPane().add(chckbxManageSourceMsg, "3, 13");
		
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void startOrStopProxy(){
		if(!proxyStarted){
			try{
				int[] oscInputPorts = new int[1];
				oscInputPorts[0] = Integer.parseInt(incomingPortTextField.getText());
				tuioProxyGateway = new Gateway(oscInputPorts,
						null,
					    "127.0.0.1",
					    Integer.parseInt(outgoingPortTextField.getText())
					    );
				tuioProxyGateway.setIndividualize(chckbxIndividualizeIds.isSelected());
				tuioProxyGateway.setManageAliveIds(chckbxAliveMessages.isSelected());
				tuioProxyGateway.setManageSrcMsg(chckbxManageSourceMsg.isSelected());
				tuioProxyGateway.startGateway();
				startStopButton.setText("Stop Throng");
				proxyStarted = true;
			}catch(Exception ex){
				Debug.writeException("Error when trying to start TuioProxy: "+ex.getMessage(),this, ex);
			}
		}else{
			try{
				tuioProxyGateway.stopGateway();
				startStopButton.setText("Start Throng");
				proxyStarted = false;
			}catch(Exception ex){
				Debug.writeException("Error when stopping gateway from GUI: "+ex.getMessage(),this, ex);
			}
		}
	}
	/**
	    * Mac Os X and Windows systems get different menubars to use systemwide
	    * preferences like mnemonics and labels for default functions like open 
	    * 
	    * @return
	    */
	   private void setCorrectLookAndFeel(){
		   if(Sys.isMacOSX()){
			   Application.getApplication().addAppEventListener(new MacAppEventListener());
			   Application.getApplication().setAboutHandler(new MacAboutHandler(this));
		   }else{
			   //TODO set Windows/Linux look and feel
			   
		   }
		}
	   
}
