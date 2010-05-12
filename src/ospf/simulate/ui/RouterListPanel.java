package ospf.simulate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ospf.simulate.router.Router;

@SuppressWarnings("serial")
public class RouterListPanel extends JPanel {

	public RouterListPanel() {

		super();
		initialize();
	}

	private void initialize() {

		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(150, 200));
		JLabel title = new JLabel("路由器列表");
		this.add(title, BorderLayout.NORTH);
		this.add(getRouterList(), BorderLayout.CENTER);
	}

	public JList getRouterList() {

		if (routerList == null) {
			DefaultListModel model = new DefaultListModel();
			routerList = new JList(model);
			routerList.setSize(100, 200);
			routerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			routerList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {

					JList list = (JList) e.getSource();
					Router router = (Router) list.getSelectedValue();
					
					// TODO 设置InterfaceListPane的显示
					JList interfaceList = MainFrame.getMainFrame()
							.getInterfaceListPanel().getInterfaceList();
					DefaultListModel model2 = new DefaultListModel();
					for (int i = 0; i < router.getInterfaces().size(); i++) {
						model2.add(i, router.getInterfaces().get(i));
					}
					interfaceList.setModel(model2);
					interfaceList.validate();
					// TODO 设置路由器的CLI显示
					RouterCLIPanel cliPanel = new RouterCLIPanel(router);
					MainFrame.getMainFrame().getRightSplitPane().setTopComponent(new JScrollPane(cliPanel));
				}
			});
		}
		return routerList;
	}

	private JList routerList = null;
}
