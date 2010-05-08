package ospf.simulate.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class InterfaceListPanel extends JPanel {

	public InterfaceListPanel() {
		
		super();
		initialize();
	}
	
	private void initialize() {
		
		this.setLayout(new BorderLayout());
		JLabel title = new JLabel("路由器接口列表");
		this.add(title, BorderLayout.NORTH);
		this.add(getInterfaceList(), BorderLayout.CENTER);
	}
	
	public JList getInterfaceList() {
		
		if (interfaceList == null) {
			DefaultListModel model = new DefaultListModel();
			interfaceList = new JList(model);
			interfaceList.addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		return interfaceList;
	}
	private JList interfaceList = null;
}
