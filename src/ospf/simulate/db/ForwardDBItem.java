package ospf.simulate.db;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;
import ospf.simulate.router.Router;

public class ForwardDBItem {

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public String getNetworkString() {
		return networkString;
	}

	public void setNetworkString(String networkString) {
		this.networkString = networkString;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public IP getNextInterfaceIp() {
		return nextInterfaceIp;
	}

	public void setNextInterfaceIp(IP nextInterfaceIp) {
		this.nextInterfaceIp = nextInterfaceIp;
	}

	public Interface getCurrentInterface() {
		return currentInterface;
	}

	public void setCurrentInterface(Interface currentInterface) {
		this.currentInterface = currentInterface;
	}

	public String toString() {

		return router.getName() + "\t" + cost + "\t"
				+ nextInterfaceIp.getIpNumber() + "\t"
				+ currentInterface.getType() + "/"
				+ currentInterface.getInterfaceNumber();
	}

	private Router router = null;
	private String networkString = null;
	private int cost = 0;
	private IP nextInterfaceIp = null;
	private Interface currentInterface = null;
}
