package ospf.simulate.ui;

import javax.swing.JPanel;

import ospf.simulate.router.Router;

/**
 * 路由器的CLI命令行操控平台
 * @author hutushen222
 *
 */
@SuppressWarnings("serial")
public class RouterCLIPanel extends JPanel {

	public RouterCLIPanel(Router router) {
		
		this.router = router;
	}
	
	private Router router;
}
