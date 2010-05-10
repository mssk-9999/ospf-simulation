package ospf.simulate.db;

import java.util.Vector;

import ospf.simulate.router.IP;

public class LinkStateDatabase {


	/**
	 * Add one item into the database
	 * @param item
	 */
	public void addItem(LinkStateDBItem item) {
		
		if (items.contains(item))
			return;
		items.add(item);
	}
	
	public void deleteItem(LinkStateDBItem item) {
		
		items.remove(item);
	}
	
	public Vector<LinkStateDBItem> getItems() {
		
		return items;
	}
	
	public void clearAll() {
		
		items.removeAllElements();
	}
	
	private Vector<LinkStateDBItem> items = new Vector<LinkStateDBItem>();	
}