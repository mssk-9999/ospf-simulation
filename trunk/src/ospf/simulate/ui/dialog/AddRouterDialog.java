package ospf.simulate.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import ospf.simulate.Simulator;
import ospf.simulate.router.Router;
import ospf.simulate.ui.MainFrame;
import ospf.simulate.util.Constant;

@SuppressWarnings("serial")
public class AddRouterDialog extends JDialog {

	public AddRouterDialog(Frame owner) {

		super(owner, true);
		initialize();
	}

	private void initialize() {

		this.setLayout(new BorderLayout());
		this.setTitle("添加路由");
		this.setLocation(MainFrame.getMainFrame().getX() + Constant.X_OFFSET,
				MainFrame.getMainFrame().getY() + Constant.Y_OFFSET);
		this.setSize(200, 100);
		JLabel label = new JLabel("路由标识");
		this.add(label, BorderLayout.NORTH);
		this.add(getRouterNameField(), BorderLayout.CENTER);
		this.add(getAddButton(), BorderLayout.EAST);
	}

	public JTextField getRouterNameField() {

		if (routerNameField == null) {
			routerNameField = new JTextField("Router");
		}
		return routerNameField;
	}

	private JButton getAddButton() {

		if (addButton == null) {
			addButton = new JButton("添加");
			addButton.setToolTipText("添加路由器到模拟器当中");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// 实现添加路由器的功能
					Router router = new Router(getRouterNameField().getText());
					// add the router to the simulator
					Simulator.addRouter(router);
					// add the router to the router list
					JList list = MainFrame.getMainFrame().getRouterListPanel()
							.getRouterList();
					DefaultListModel model = (DefaultListModel) list.getModel();
					model.add(model.getSize(), router);
					list.setSelectedIndex(model.getSize() - 1);
					list.validate();
					System.err.println(router.info());
//					MainFrame.getMainFrame().getRouterCLIPanel().getRouterCLIArea().append(router.info() + "\n");
					// 路由器接口列表的更新
					JList interfaceList = MainFrame.getMainFrame()
							.getInterfaceListPanel().getInterfaceList();

					DefaultListModel model2 = new DefaultListModel(); 
					for (int i = 0; i < router.getInterfaces().size(); i++) {
						model2.add(i, router.getInterfaces().get(i));
					}
					interfaceList.setModel(model2);
					interfaceList.setSelectedIndex(0);
					interfaceList.validate();
					// TODO 路由拓扑图的更新
					
					// exit current dialog
					setVisible(false);
					dispose();
				}
			});
		}
		return addButton;
	}

	private JTextField routerNameField = null;
	private JButton addButton = null;
}
