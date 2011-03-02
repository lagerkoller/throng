package manualView;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

import util.Constants;

/**
 * 
 */

/**
 * @author johannes
 *
 */
public class OpenMenuItem extends JMenuItem implements ActionListener{
	ThrongManualGUI app;
	
	public OpenMenuItem(ThrongManualGUI app){
		super("Open...");
		setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		this.app = app;
	}
	public void actionPerformed(ActionEvent arg0) {
		
//		JFileChooser chooser = new JFileChooser(Constants.START_PATH);
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		  chooser.addChoosableFileFilter(new FileFilter() {
		    public boolean accept(File f) {
		      if (f.isDirectory()) return true;
		      return f.getName().toLowerCase().endsWith(".xml");
		    }
		    public String getDescription () { return "XMLs"; }  
		  });
		  chooser.setMultiSelectionEnabled(false);
		  if (chooser.showOpenDialog(this) == 
		                                JFileChooser.APPROVE_OPTION){
			  File selectedFile = chooser.getSelectedFile();
			  String filePath = selectedFile.toString(); 
			  app.loadFile(filePath);
		  }
	}
}
