package ospf.simulate.db;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;

public class LinkStateDBItem {

	public IP getFromRID() {

		return fromRID;
	}

	public void setFromRID(IP fromRID) {

		this.fromRID = fromRID;
	}

	public IP getToRID() {

		return toRID;
	}

	public void setToRID(IP toRID) {

		this.toRID = toRID;
	}

	public Interface getInterface() {

		return interface1;
	}

	public void setInterface(Interface interface1) {

		this.interface1 = interface1;
	}

	public int getCost() {

		return cost;
	}

	public void setCost(int cost) {

		this.cost = cost;
	}

	public String toString() {

		return fromRID.getIpNumber() + "\t" + toRID.getIpNumber() + "\t"
				+ interface1.getType() + "/" + interface1.getInterfaceNumber()
				+ "\t" + cost;
	}

	private IP fromRID;
	private IP toRID;
	private Interface interface1 = null;
	private int cost;
}