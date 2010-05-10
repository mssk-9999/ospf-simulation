package ospf.simulate.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import ospf.simulate.Simulator;
import ospf.simulate.router.Router;
import ospf.simulate.ui.MainFrame;
import ospf.simulate.util.Constant;

@SuppressWarnings("serial")
public class ShowRouterInfoDialog extends JDialog {

	public ShowRouterInfoDialog(Frame owner) {
		
		super(owner, true);
		initialize();
	}
	
	private void initialize() {
		
		this.setTitle("Show the Database");
		this.setLocation(MainFrame.getMainFrame().getX() + Constant.X_OFFSET,
				MainFrame.getMainFrame().getY() + Constant.Y_OFFSET);
		this.setSize(400, 400);
		this.setLayout(new BorderLayout());
		this.add(getRouterComboBox(), BorderLayout.NORTH);
		this.add(getButtonBox(), BorderLayout.WEST);
		this.add(getInfoTextArea(), BorderLayout.CENTER);
	}
	
	
	
	public JComboBox getRouterComboBox() {
		
		if (routerComboBox == null) {
			if (Simulator.getRouters().isEmpty()) {
				routerComboBox = new JComboBox();
				return routerComboBox;
			}
			routerComboBox = new JComboBox(Simulator.getRouters());
			routerComboBox.setSelectedIndex(0);
		}
		return routerComboBox;
	}

	public JTextArea getInfoTextArea() {
		
		if (infoTextArea == null) {
			infoTextArea = new JTextArea();
		}
		return infoTextArea;
	}

	public Box getButtonBox() {
		
		if (buttonBox == null) {
			buttonBox = new Box(BoxLayout.Y_AXIS);
			buttonBox.add(getAdjacencyButton());
			buttonBox.add(getLinkStateButton());
			buttonBox.add(getForwardButton());
		}
		return buttonBox;
	}

	public JButton getAdjacencyButton() {
		
		if (neighborButton == null) {
			neighborButton = new JButton("Neighbor");
			neighborButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Router router = (Router) getRouterComboBox().getSelectedItem();
					String neighborInfo = router.showNeighbors();
					getInfoTextArea().setText(neighborInfo);
				}
			});
		}
		return neighborButton;
	}

	public JButton getForwardButton() {
		
		if (forwardButton == null) {
			forwardButton = new JButton("Forward");
			forwardButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Router router = (Router) getRouterComboBox().getSelectedItem();
					String linkStateInfo = router.showLinkState();
					getInfoTextArea().setText(linkStateInfo);
				}
			});
		}
		return forwardButton;
	}

	public JButton getLinkStateButton() {
		
		if (linkStateButton == null) {
			linkStateButton = new JButton("LinkState");
			linkStateButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return linkStateButton;
	}



	private JComboBox routerComboBox = null;
	private JTextArea infoTextArea = null;
	
	private Box buttonBox = null;
	private JButton neighborButton = null;
	private JButton forwardButton = null;
	private JButton linkStateButton = null;
}
