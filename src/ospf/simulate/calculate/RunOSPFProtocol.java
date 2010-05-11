package ospf.simulate.calculate;

import ospf.simulate.Simulator;
import ospf.simulate.router.Router;
import ospf.simulate.util.OSPFState;

public class RunOSPFProtocol {

	public static void runProtocol() {
		
		// calculate the router's RID
		calRID();
		// show all the routers info
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router  = Simulator.getRouters().get(i);
			router.setState(OSPFState.Init);
			System.err.println(router.info());
//			System.err.println(router.getRID());
		}
		
		// find the neighbor of the routers
		findNeighbor();
		// all the routers exchange the info and save into the link state database
		exchangeInfo();
		// all the routers will run the spf algorithms and output to the Forward Database
		runSPF();
	}
	
	private static void calRID() {
		
		//TODO calculator each Router's RID
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Simulator.getRouters().get(i).calculateRID();
		}
	}
	
	private static void findNeighbor() {
		
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router = Simulator.getRouters().get(i);
			router.findNeighbor();
		}
	}
	
	private static void exchangeInfo() {
		
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router = Simulator.getRouters().get(i);
			router.exchangeInfo();
		}
	}
	
	private static void runSPF() {
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router = Simulator.getRouters().get(i);
			router.runSPF();
		}
	}
}
