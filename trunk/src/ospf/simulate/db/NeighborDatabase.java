package ospf.simulate.db;

import java.util.Vector;


/**
 * Adjacency Database
 * 
 * @author hutushen222
 *
 */
public class NeighborDatabase {
	
	public NeighborDatabase() {
		
	}
	
	public void addNeighbor(NeighborDBItem item) {
		
		if (neighbors.contains(item)) 
			return;
		neighbors.add(item);
	}
	
	public Vector<NeighborDBItem> getNeighbors() {
		
		return neighbors;
	}
	
	public void clearAll() {
		
		neighbors.removeAllElements();
	}
	
	private Vector<NeighborDBItem> neighbors = new Vector<NeighborDBItem>();
	
	
}


