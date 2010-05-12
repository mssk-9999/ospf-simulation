package ospf.simulate.db;

import java.util.Vector;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;
import ospf.simulate.util.LinkType;

public class ForwardDatabase {

	public void addItem(ForwardDBItem item) {

		if (items.contains(item))
			return;
		items.add(item);
	}

	public void deleteItem(ForwardDBItem item) {

		items.remove(item);
	}

	public Vector<ForwardDBItem> getItems() {

		return items;
	}

	public void clearAll() {

		items.removeAllElements();
	}

	private Vector<ForwardDBItem> items = new Vector<ForwardDBItem>();
}
