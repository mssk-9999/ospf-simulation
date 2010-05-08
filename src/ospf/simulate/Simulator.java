package ospf.simulate;

import java.util.Vector;

import javax.swing.SwingUtilities;

import ospf.simulate.router.Router;
import ospf.simulate.ui.MainFrame;

public class Simulator {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				MainFrame mainFrame = MainFrame.getMainFrame();
				mainFrame.setVisible(true);
			}
		});
	}
	
	public static Vector<Router> getRouters() {
		
		return routers;
	}
	
	public static boolean addRouter(Router router) {
		
		if (routers.contains(router)) {
			return false;
		} 
		routers.add(router);
		return true;
	}
	
	private static Vector<Router> routers = new Vector<Router>();
}
