package ospf.simulate.db;

import java.util.Vector;

import ospf.simulate.router.IP;

public class LinkStateDatabase {


	/**
	 * Add one item into the database
	 * @param item
	 */
	public void addItem(LinkStateDBItem item) {
		
		items.add(item);
	}
	
	public void deleteItem(LinkStateDBItem item) {
		
		items.remove(item);
	}
	
	private Vector<LinkStateDBItem> items = new Vector<LinkStateDBItem>();	
}

class LinkStateDBItem {
	
	
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