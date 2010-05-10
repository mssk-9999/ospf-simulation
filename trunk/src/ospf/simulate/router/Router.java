package ospf.simulate.router;

import java.util.Vector;

import ospf.simulate.Simulator;
import ospf.simulate.db.ForwardDatabase;
import ospf.simulate.db.LinkStateDBItem;
import ospf.simulate.db.LinkStateDatabase;
import ospf.simulate.db.NeighborDBItem;
import ospf.simulate.db.NeighborDatabase;
import ospf.simulate.util.InterfaceType;
import ospf.simulate.util.OSPFState;

public class Router {

	public Router(String name) {

		this.name = name;
		initInterfaces();
	}

	private void initInterfaces() {

		this.addInterface(InterfaceType.F, 0);
		this.addInterface(InterfaceType.F, 1);
		this.addInterface(InterfaceType.F, 2);
		this.addInterface(InterfaceType.F, 3);
		this.addInterface(InterfaceType.S, 0);
		this.addInterface(InterfaceType.S, 1);
	}

	public void calculateRID() {

		// 存储该路由器的所有IP的集合
		Vector<IP> ips = new Vector<IP>();
		// 获取所有的IP
		for (int i = 0; i < interfaces.size(); i++) {
			IP tempIp = interfaces.get(i).getIp();
			if (tempIp == null)
				continue;
			else
				ips.add(tempIp);
		}
		// 如果所有的接口都没有IP，返回
		if (ips.isEmpty())
			return;
		// 否则选取RID
		RID = ips.firstElement();
		for (int i = 1; i < ips.size(); i++) {
			if (ips.get(i).compare(RID) == 1) {
				RID = ips.get(i);
			}
		}
	}

	public boolean addInterface(InterfaceType type, int number) {

		Interface temp = new Interface(this, type, number);
		if (interfaces.contains(temp)) {
			return false;
		} else {
			interfaces.add(temp);
			return true;
		}
	}

	public String getName() {

		return name;
	}

	public IP getRID() {

		return RID;
	}

	public Vector<Interface> getInterfaces() {

		return interfaces;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String toString() {

		return this.name;
	}

	public String info() {

		StringBuilder interfaceInfo = new StringBuilder();
		for (int i = 0; i < interfaces.size(); i++) {

			interfaceInfo.append(interfaces.get(i).info() + "\n");
		}

		return "**************\n" + name + "\n" + interfaceInfo.toString()
				+ "**************";
	}

	public OSPFState getState() {
		return state;
	}

	public void setState(OSPFState state) {
		this.state = state;
	}

	public void findNeighbor() {

		// clear the items in the NeighborDatabase
		clearNeighborDatabase();
		for (int i = 0; i < this.getInterfaces().size(); i++) {
			Interface interface1 = this.getInterfaces().get(i);
			if (interface1.getLink() != null) {
				// create a neighbor item
				NeighborDBItem item = new NeighborDBItem();
				// set the neighbor
				item.setNeighborIp(interface1.getLink().getOtherSide().getIp());
				item.setMineInterface(interface1);
				item.setNeighborRID(interface1.getLink().getOtherSide()
						.getRouter().getRID());
				item.setState(OSPFState.Full);

				// add the neighbor to the database
				this.getNeighborDatabase().addNeighbor(item);
			} else {
				System.out.println(interface1.getRouter().toString()
						+ interface1.getType()
						+ interface1.getInterfaceNumber() + " link is null");
			}
		}
	}

	public NeighborDatabase getNeighborDatabase() {

		if (neighborDatabase == null) {
			neighborDatabase = new NeighborDatabase();
		}
		return neighborDatabase;
	}

	private void clearNeighborDatabase() {

		getNeighborDatabase().clearAll();
	}

	public String showNeighbors() {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Neighbor ID\tState\tAddress\tInterface\n");
		for (int i = 0; i < getNeighborDatabase().getNeighbors().size(); i++) {
			stringBuilder.append(getNeighborDatabase().getNeighbors().get(i)
					.toString());
		}
		return stringBuilder.toString();
	}

	public void exchangeInfo() {

		// clear all the before existing items
		clearLinkStateDatabase();
		
		// exchange data with other all routers
		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router = Simulator.getRouters().get(i);
			NeighborDatabase nDB =  router.getNeighborDatabase();
			Vector<NeighborDBItem> neighbors = nDB.getNeighbors();
			for (int j = 0; j < neighbors.size(); j++) {
				// initialize the lsdb item
				LinkStateDBItem item = new LinkStateDBItem();
				item.setFromRID(router.getRID());
				item.setToRID(neighbors.get(j).getNeighborRID());
				item.setInterface(neighbors.get(j).getMineInterface());
				item.setCost(neighbors.get(j).getMineInterface().getLink()
						.getCost());

				// add the item to the lsdb
				this.getLinkStateDatabase().addItem(item);
			}
		}
		
	}

	public LinkStateDatabase getLinkStateDatabase() {

		if (linkStateDatabase == null) {
			linkStateDatabase = new LinkStateDatabase();
		}
		return linkStateDatabase;
	}

	private void clearLinkStateDatabase() {

		getLinkStateDatabase().clearAll();
	}

	public String showLinkState() {
		// TODO 打印所有的该区域链路状态信息
		StringBuilder linkStateInfo = new StringBuilder();
		linkStateInfo.append("OSPF Router with ID (" + this.getRID().toString()
				+ ") (Process ID 1)" + "\n");
		linkStateInfo.append("FromRID\tToRID\tVia\tCost\n");
		for (int i = 0; i < getLinkStateDatabase().getItems().size(); i++) {
			linkStateInfo.append(getLinkStateDatabase().getItems().get(i) + "\n");
		}
		return linkStateInfo.toString();
	}

	public ForwardDatabase getForwardDatabase() {

		if (forwardDatabase == null) {
			forwardDatabase = new ForwardDatabase();
		}
		return forwardDatabase;
	}

	private String name;
	private IP RID = null;
	private Vector<Interface> interfaces = new Vector<Interface>();
	private OSPFState state = OSPFState.Down;

	private NeighborDatabase neighborDatabase = null;
	private LinkStateDatabase linkStateDatabase = null;
	private ForwardDatabase forwardDatabase = null;
}
