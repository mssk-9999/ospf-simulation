package ospf.simulate.db;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;

public class ForwardDBItem {
	
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
	
	private String networkString = null;
	int cost = 0;
	private IP nextInterfaceIp = null;
	private Interface currentInterface = null;
}
