package ospf.simulate.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ospf.simulate.router.Router;
import ospf.simulate.ui.dialog.AddRouterDialog;

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
		this.add(getAddButton(), BorderLayout.SOUTH);
	}

	public JList getRouterList() {

		if (routerList == null) {
			DefaultListModel model = new DefaultListModel();
			routerList = new JList(model);
			routerList.setSize(100, 200);
			routerList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					
					JList list = (JList)e.getSource();
					Router router = (Router)list.getSelectedValue();
					System.out.println(router.toString());
					// TODO 设置InterfaceListPane的显示
					// TODO 设置路由器的CLI显示
				}
			});
		}
		return routerList;
	}

	public JButton getAddButton() {

		if (addButton == null) {
			addButton = new JButton("添加路由");
			addButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO 打开添加路由器的窗口，添加路由，并刷新RouterListPanel的界面
					AddRouterDialog dialog = new AddRouterDialog(MainFrame
							.getMainFrame());
					dialog.setVisible(true);
				}
			});
		}
		return addButton;
	}

	private JList routerList = null;
	private JButton addButton = null;
}
