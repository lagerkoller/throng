package manualView;

import javax.swing.JMenuBar;

public class WindowsJMenuBar extends JMenuBar{
  public WindowsJMenuBar(ThrongManualGUI app){
    add(new FileMenu(app));
  }
}
