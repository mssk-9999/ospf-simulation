package ospf.simulate;

import javax.swing.SwingUtilities;

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
}
