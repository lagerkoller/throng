package manualView;

import javax.swing.JMenu;

public class FileMenu extends JMenu{

	public FileMenu(ThrongManualGUI app){
	    super("File");
	    
	    OpenMenuItem openItem = new OpenMenuItem(app); 
	    openItem.addActionListener(openItem);
	    add(openItem);
  }
}
