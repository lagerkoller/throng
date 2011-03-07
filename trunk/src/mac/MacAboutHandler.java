package mac;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.muchsoft.util.mac.Java14Handler;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.EventObject;

import javax.swing.JTextPane;

import util.Constants;

public class MacAboutHandler extends JDialog implements Java14Handler {
	private JFrame owner;
	
	public MacAboutHandler(JFrame owner) {
		super(owner, true);
		setResizable(false);
		setBounds(100, 100, 260, 165);
		getContentPane().setLayout(null);
		
		JLabel aboutTextPane = new JLabel();
		aboutTextPane.setBounds(6, 6, 255, 100);
		aboutTextPane.setText("<html><center><strong>Throng</strong> "+Constants.VERSION+"</center><br/>"+Constants.FULL_NAME+"<br/><br/>For help look in<html>");
		getContentPane().add(aboutTextPane);
		
		JLabel lbljohannesLuderschmidt = new JLabel("<html><a href=''>"+Constants.HOMEPAGE_TEXT+"</a></html>");
		lbljohannesLuderschmidt.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lbljohannesLuderschmidt.addMouseListener(new MouseListener(){
		      public void mouseClicked(MouseEvent e) {
		        java.net.URI uri = null;
				try {
					uri = new java.net.URI(Constants.HOMEPAGE_LINK);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        if(java.awt.Desktop.isDesktopSupported()){
		        	try {
		        		if(uri != null){
		        			java.awt.Desktop.getDesktop().browse( uri );
	        			}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
		      }
		      //other MouseListener methods 

			public void mouseEntered(MouseEvent arg0) {
			}

			public void mouseExited(MouseEvent arg0) {
			}

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}

		    });
		lbljohannesLuderschmidt.setBounds(6, 93, 248, 40);
		getContentPane().add(lbljohannesLuderschmidt);
		
	}

	public void handleAbout(EventObject arg0) {
		this.setVisible(true);
	}

	public void handleOpenApplication(EventObject arg0) {
	}

	public void handleOpenFile(EventObject arg0, String arg1) {
	}

	public void handlePrefs(EventObject arg0) {
	}

	public void handlePrintFile(EventObject arg0, String arg1) {
	}

	public void handleQuit(EventObject arg0) {
	}

	public void handleReOpenApplication(EventObject arg0) {
	}
}
