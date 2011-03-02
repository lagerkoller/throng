package manualView;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RemoveHostJButton extends JButton {
	private JLabel oscHostLabel;
	private JLabel oscHostValueLabel;
	private HostRow hostRow;
	
	public HostRow getHostRow() {
		return hostRow;
	}

	public void setHostRow(HostRow hostRow) {
		this.hostRow = hostRow;
	}

	public RemoveHostJButton(String label){
		super(label);
	}
	
	public JLabel getOscHostLabel() {
		return oscHostLabel;
	}
	public void setOscHostLabel(JLabel oscHostLabel) {
		this.oscHostLabel = oscHostLabel;
	}
	public JLabel getOscHostValueLabel() {
		return oscHostValueLabel;
	}
	public void setOscHostValueLabel(JLabel oscHostValueLabel) {
		this.oscHostValueLabel = oscHostValueLabel;
	}
	
}
