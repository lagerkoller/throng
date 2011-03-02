package manualView;

import javax.swing.JMenuBar;

public class MacJMenuBar extends JMenuBar{
  public MacJMenuBar(ThrongManualGUI app){
    add(new FileMenu(app)); // instead of add(new JMenu("File"));
  }
}
