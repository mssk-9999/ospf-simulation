package ospf.simulate.ui.dialog;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ospf.simulate.Simulator;
import ospf.simulate.router.Interface;
import ospf.simulate.router.Link;
import ospf.simulate.router.Router;
import ospf.simulate.ui.MainFrame;
import ospf.simulate.util.Constant;

@SuppressWarnings("serial")
public class LinkDialog extends JDialog {

	public LinkDialog(Frame owner) {

		super(owner, true);
		initialize();
	}

	private void initialize() {

		this.setLayout(new BorderLayout());
		this.setTitle("Link");
		this.setSize(500, 200);
		this.setLocation(MainFrame.getMainFrame().getX() + Constant.X_OFFSET,
				MainFrame.getMainFrame().getY() + Constant.Y_OFFSET);
		JLabel label = new JLabel("The Router Link");
		this.add(label, BorderLayout.NORTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);
	}

	public JPanel getCenterPanel() {

		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(new GridLayout(3, 4));
			JLabel router1 = new JLabel("Router 1");
			centerPanel.add(router1);
			centerPanel.add(getRoutercombo1());

			JLabel interface1 = new JLabel("Interface 1");
			centerPanel.add(interface1);
			centerPanel.add(getInterfaceCombo1());

			JLabel router2 = new JLabel("Router 2");
			centerPanel.add(router2);
			centerPanel.add(getRoutercombo2());

			JLabel interface2 = new JLabel("Interface 2");
			centerPanel.add(interface2);
			centerPanel.add(getInterfaceCombo2());

			JLabel costLabel = new JLabel("Cost");
			centerPanel.add(costLabel);
			centerPanel.add(getCosTextField());

			centerPanel.add(getLinkButton());
//			centerPanel.add(getUnLinkButton());
		}
		return centerPanel;
	}

	public JComboBox getRoutercombo1() {

		if (routercombo1 == null) {
			if (Simulator.getRouters().isEmpty()) {
				routercombo1 = new JComboBox();
				return routercombo1;
			}
			routercombo1 = new JComboBox(Simulator.getRouters());
			routercombo1.setSelectedIndex(0);
			routercombo1.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

					JComboBox temp = (JComboBox) e.getSource();
					Router router = (Router) temp.getSelectedItem();
					// update interface combobox 1
					DefaultComboBoxModel model = new DefaultComboBoxModel(
							router.getInterfaces());
					interfaceCombo1.setModel(model);
					interfaceCombo1.setSelectedIndex(0);
					interfaceCombo1.validate();
				}
			});
		}
		return routercombo1;
	}

	public JComboBox getInterfaceCombo1() {

		if (interfaceCombo1 == null) {
			if (Simulator.getRouters().isEmpty()) {
				interfaceCombo1 = new JComboBox();
				return interfaceCombo1;
			}
			interfaceCombo1 = new JComboBox(Simulator.getRouters()
					.firstElement().getInterfaces());
			interfaceCombo1.setSelectedIndex(0);
		}
		return interfaceCombo1;
	}

	public JComboBox getRoutercombo2() {

		if (routercombo2 == null) {
			if (Simulator.getRouters().isEmpty()) {
				routercombo2 = new JComboBox();
				return routercombo2;
			}
			routercombo2 = new JComboBox(Simulator.getRouters());
			routercombo2.setSelectedIndex(0);
			routercombo2.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {

					JComboBox temp = (JComboBox) e.getSource();
					Router router = (Router) temp.getSelectedItem();
					// update interface combobox 2
					DefaultComboBoxModel model = new DefaultComboBoxModel(
							router.getInterfaces());
					interfaceCombo2.setModel(model);
					interfaceCombo2.setSelectedIndex(0);
					interfaceCombo2.validate();
				}
			});
		}
		return routercombo2;
	}

	public JComboBox getInterfaceCombo2() {

		if (interfaceCombo2 == null) {
			if (Simulator.getRouters().isEmpty()) {
				interfaceCombo2 = new JComboBox();
				return interfaceCombo2;
			}
			interfaceCombo2 = new JComboBox(Simulator.getRouters()
					.firstElement().getInterfaces());
			interfaceCombo2.setSelectedIndex(0);
		}
		return interfaceCombo2;
	}

	public JTextField getCosTextField() {

		if (costTextField == null) {
			costTextField = new JTextField();
		}
		return costTextField;
	}

	public JButton getLinkButton() {

		if (linkButton == null) {
			linkButton = new JButton("Link");
			linkButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					// TODO get cost
					try {
						int cost = Integer
								.parseInt(getCosTextField().getText());
						Interface interface1 = (Interface) interfaceCombo1
								.getSelectedItem();

						Interface interface2 = (Interface) interfaceCombo2
								.getSelectedItem();

						// initialize interface1
						Link link1 = new Link(interface1, interface2, cost);
						interface1.setLink(link1);
						System.err.println(interface1.getRouter().info());
//						MainFrame.getMainFrame().getRouterCLIPanel().getRouterCLIArea().append(interface1.getRouter().info() + "\n");
						
						// initialize interface2
						Link link2 = new Link(interface2, interface1, cost);
						interface2.setLink(link2);
						System.err.println(interface2.getRouter().info());
//						MainFrame.getMainFrame().getRouterCLIPanel().getRouterCLIArea().append(interface2.getRouter().info() + "\n");
					} catch (NumberFormatException nfe) {
						JOptionPane
								.showMessageDialog(null,
										"You must input a number \n in the cost text field!");
					}

				}
			});
		}
		return linkButton;
	}

	public JButton getUnLinkButton() {

		if (unLinkButton == null) {
			unLinkButton = new JButton("Unlink");
		}
		return unLinkButton;
	}

	private JPanel centerPanel = null;
	private JComboBox routercombo1 = null;
	private JComboBox interfaceCombo1 = null;
	private JComboBox routercombo2 = null;
	private JComboBox interfaceCombo2 = null;
	private JTextField costTextField = null;
	private JButton linkButton = null;
	private JButton unLinkButton = null;
}
