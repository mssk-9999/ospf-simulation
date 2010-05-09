package ospf.simulate.db;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;
import ospf.simulate.util.OSPFState;

public class NeighborDBItem {
	
	
	
	public IP getNeighborRID() {
		
		return neighborRID;
	}
	
	public void setNeighborRID(IP neighborRID) {
		
		this.neighborRID = neighborRID;
	}
	
	public OSPFState getState() {
		
		return state;
	}
	
	public void setState(OSPFState state) {
		
		this.state = state;
	}
	
	public IP getNeighborIp() {
		
		return neighborIp;
	}
	
	public void setNeighborIp(IP neighborIp) {
		
		this.neighborIp = neighborIp;
	}
	
	public Interface getMineInterface() {
		
		return mineInterface;
	}
	
	public void setMineInterface(Interface mineInterface) {
		
		this.mineInterface = mineInterface;
	}
	
	@Override
	public String toString() {
		
		return "" + neighborRID.getIpNumber() + "\t" + state + "\t" +
				neighborIp.getIpNumber() + "\t" + mineInterface.getInterfaceInfo() + "\n";
	}
	
	private IP neighborRID = null;
	private OSPFState state = OSPFState.Down;
	private IP neighborIp = null;
	private Interface mineInterface = null;
}