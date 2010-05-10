package ospf.simulate.db;

import ospf.simulate.router.IP;

public class LinkStateDBItem {

	public IP getRID() {
		
		return RID;
	}

	public IP getNeighbor() {
		
		return neighbor;
	}

	public int getCost() {
		
		return cost;
	}

	public void setRID(IP rID) {
		
		RID = rID;
	}

	public void setNeighbor(IP neighbor) {
		
		this.neighbor = neighbor;
	}

	public void setCost(int cost) {
		
		this.cost = cost;
	}

	private IP RID;
	private IP neighbor;
	private int cost;
}