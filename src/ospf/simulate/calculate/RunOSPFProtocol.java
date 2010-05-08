package ospf.simulate.calculate;

import ospf.simulate.Simulator;
import ospf.simulate.router.Router;

public class RunOSPFProtocol {

	public static void runProtocol() {
		
		// calculate the router's RID
		calRID();
		// show all the routers info
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router tempRouter = Simulator.getRouters().get(i);
			System.err.println(tempRouter.info());
			System.err.println(tempRouter.getRID());
		}
	}
	
	private static void calRID() {
		
		//TODO calculator each Router's RID
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Simulator.getRouters().get(i).calculateRID();
		}
	}
	
	private static void exchangeInfo() {
		
	}
}
