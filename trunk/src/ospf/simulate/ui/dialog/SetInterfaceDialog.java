package ospf.simulate.ui.dialog;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ospf.simulate.Simulator;
import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;
import ospf.simulate.router.Router;
import ospf.simulate.ui.MainFrame;
import ospf.simulate.util.Constant;

@SuppressWarnings("serial")
public class SetInterfaceDialog extends JDialog {

	public SetInterfaceDialog(Frame owner) {

		super(owner, true);
		initialize();
	}

	private void initialize() {

		this.setTitle("Set up the Interfaces");
		this.setLocation(MainFrame.getMainFrame().getX() + Constant.X_OFFSET,
				MainFrame.getMainFrame().getY() + Constant.Y_OFFSET);
		this.setSize(300, 200);
		this.setLayout(new GridLayout(5, 2));
		this.add(new JLabel("Router:"));
		this.add(getRoutersBox());
		this.add(new JLabel("Interface:"));
		this.add(getInterfacesBox());
		this.add(new JLabel("IP Num:"));
		this.add(getIpTextField());
		this.add(new JLabel("Mask Num:"));
		this.add(getMaskTextField());
		this.add(getSetButton());
		this.add(getDelButton());
	}

	public JComboBox getRoutersBox() {

		if (routersBox == null) {
			routersBox = new JComboBox(Simulator.getRouters());
			routersBox.setSelectedIndex(0);
			routersBox.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					// TODO Auto-generated method stub
					JComboBox tempBox =(JComboBox) e.getSource();
					Router router = (Router) tempBox.getSelectedItem();
					DefaultComboBoxModel model = new DefaultComboBoxModel(router.getInterfaces());
					interfacesBox.setModel(model);
					interfacesBox.setSelectedIndex(0);
					interfacesBox.validate();
				}
			});
		}
		return routersBox;
	}

	public JComboBox getInterfacesBox() {

		if (interfacesBox == null) {
			interfacesBox = new JComboBox(Simulator.getRouters().firstElement()
					.getInterfaces());
			interfacesBox.setSelectedIndex(0);
		}
		return interfacesBox;
	}

	public JTextField getIpTextField() {

		if (ipTextField == null) {
			ipTextField = new JTextField();
		}
		return ipTextField;
	}

	public JTextField getMaskTextField() {

		if (maskTextField == null) {
			maskTextField = new JTextField();
		}
		return maskTextField;
	}

	public JButton getSetButton() {

		if (setButton == null) {
			setButton = new JButton("Set");
			setButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO add the ip info to the interface
					Interface interface1 = (Interface) getInterfacesBox()
							.getSelectedItem();
					interface1.setIp(new IP(getIpTextField().getText(),
							getMaskTextField().getText()));
				}
			});
		}
		return setButton;
	}
	
	public JButton getDelButton() {

		if (delButton == null) {
			delButton = new JButton("Delete");
			delButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO add the ip info to the interface
					Interface interface1 = (Interface) getInterfacesBox()
							.getSelectedItem();
					interface1.setIp(null);
				}
			});
		}
		return delButton;
	}

	private JComboBox routersBox = null;
	private JComboBox interfacesBox = null;
	private JTextField ipTextField = null;
	private JTextField maskTextField = null;

	private JButton setButton = null;
	private JButton delButton = null;
}
