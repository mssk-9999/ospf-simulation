package ospf.simulate.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ospf.simulate.router.Router;

/**
 * 路由器的CLI命令行操控平台
 * @author hutushen222
 *
 */
@SuppressWarnings("serial")
public class RouterCLIPanel extends JPanel {

	public RouterCLIPanel() {
		
		super();
		initialize();
	}
	
	private void initialize() {
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(getRouterCLIArea()), BorderLayout.CENTER);
	}
	
	public JTextArea getRouterCLIArea() {
	
		if (routerCLIArea == null) {
			routerCLIArea = new JTextArea("Router CLI\n");
		}
		return routerCLIArea;
	}
	
	private JTextArea routerCLIArea = null;
}
