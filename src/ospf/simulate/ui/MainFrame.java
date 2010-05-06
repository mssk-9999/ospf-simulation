package ospf.simulate.ui;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private MainFrame() {
		
		super();
		initialize();
	}
	
	private void initialize() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("OSPF路由协议模拟");
		this.setLocation(100, 100);
		this.setSize(800, 600);
		
		// TODO Add other components and containers
		this.setContentPane(getMainPane());
	}
	
	
	public JSplitPane getMainPane() {
		
		if (mainSplitPane == null) {
			mainSplitPane = new JSplitPane();
			mainSplitPane.setLeftComponent(getLeftSplitPane());
			mainSplitPane.setRightComponent(getRightSplitPane());
		}
		return mainSplitPane;
	}
	
	public JSplitPane getLeftSplitPane() {
		
		if (leftSplitPane == null) {
			leftSplitPane = new JSplitPane();
			leftSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			leftSplitPane.setTopComponent(getRouterListPanel());
			leftSplitPane.setBottomComponent(getInterfaceListPanel());
		}
		return leftSplitPane;
	}
	
	public JSplitPane getRightSplitPane() {
		
		if (rightSplitPane == null) {
			rightSplitPane = new JSplitPane();
			rightSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return rightSplitPane;
	}
	
	public RouterListPanel getRouterListPanel() {
		
		if (routerListPanel == null) {
			routerListPanel = new RouterListPanel();
		}
		return routerListPanel;
	}
	
	public InterfaceListPanel getInterfaceListPanel() {
		
		if (interfaceListPanel == null) {
			interfaceListPanel = new InterfaceListPanel();
		}
		return interfaceListPanel;
	}

	public TopologyPanel getTopologyPanel() {
		
		if (topologyPanel == null) {
			topologyPanel = new TopologyPanel();
		}
		return topologyPanel;
	}

//	public RouterCLIPanel getRouterCLIPanel() {
//		
//		if (routerCLIPanel == null) {
//			routerCLIPanel = new RouterCLIPanel();
//		}
//		return routerCLIPanel;
//	}

	public static MainFrame getMainFrame() {
		
		if (mainFrame == null) {
			mainFrame = new MainFrame();
		}
		return mainFrame;
	}
	
	private JSplitPane mainSplitPane = null;
	private JSplitPane leftSplitPane = null;
	private JSplitPane rightSplitPane = null;
	private RouterListPanel routerListPanel = null;
	private InterfaceListPanel interfaceListPanel = null;
	private TopologyPanel topologyPanel = null;
	private RouterCLIPanel routerCLIPanel = null;
	private static MainFrame mainFrame = null;
}
