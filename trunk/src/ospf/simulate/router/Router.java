package ospf.simulate.router;

import java.util.ArrayList;
import java.util.Vector;

import ospf.simulate.Simulator;
import ospf.simulate.db.ForwardDatabase;
import ospf.simulate.db.LinkStateDBItem;
import ospf.simulate.db.LinkStateDatabase;
import ospf.simulate.db.NeighborDBItem;
import ospf.simulate.db.NeighborDatabase;
import ospf.simulate.util.InterfaceType;
import ospf.simulate.util.OSPFState;
import ospf.simulate.util.SpfA;

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
				// System.out.println(interface1.getRouter().toString()
				// + interface1.getType()
				// + interface1.getInterfaceNumber() + " link is null");
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
			NeighborDatabase nDB = router.getNeighborDatabase();
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
		// linkStateInfo.append("FromRID\tToRID\tVia\tCost\n");
		// for (int i = 0; i < getLinkStateDatabase().getItems().size(); i++) {
		// linkStateInfo.append(getLinkStateDatabase().getItems().get(i) +
		// "\n");
		// }
		linkStateInfo.append("\tRouter Link State (Area 0) + \n\n");
		linkStateInfo.append("Link ID \t ADV Router \t Link Count \n");

		for (int i = 0; i < Simulator.getRouters().size(); i++) {
			Router router = Simulator.getRouters().get(i);
			int linkNum = 0;
			for (int j = 0; j < router.getInterfaces().size(); j++) {
				if (router.getInterfaces().get(j).getLink() != null) {
					linkNum++;
				}
			}
			linkStateInfo.append(router.getRID().getIpNumber() + "\t"
					+ router.getRID().getIpNumber() + "\t" + linkNum + "\n");
		}

		linkStateInfo.append("\tNet Link State (Area 0)\n\n");
		linkStateInfo.append("Link ID \t ADV Router \n");
		// save all the links
		Vector<Link> links = new Vector<Link>();
		for (int i = 0; i < Simulator.getRouters().size(); i++) {

			Router router = Simulator.getRouters().get(i);
			for (int j = 0; j < router.getInterfaces().size(); j++) {

				Link tempLink = router.getInterfaces().get(j).getLink();
				if (tempLink != null) {

					if (links.size() == 0) {
						links.add(tempLink);
						continue;
					}
					for (int k = 0; k < links.size(); k++) {

						if (tempLink.equals(links.get(k))) {
							break;
						}
						links.add(tempLink);
					}
				}
			}
		}
		// print all the links
		for (int i = 0; i < links.size(); i++) {
			Link temp = links.get(i);
			IP linkID = null;
			IP rid = null;
			Interface interface1 = temp.getOneSide();
			Interface interface2 = temp.getOtherSide();
			if (interface1.getIp().compare(interface2.getIp()) == 1) {
				linkID = interface1.getIp();
				rid = interface1.getRouter().getRID();
			} else {
				linkID = interface2.getIp();
				rid = interface2.getRouter().getRID();
			}
			linkStateInfo.append(linkID.getIpNumber() + "\t"
					+ rid.getIpNumber() + "\n");
		}

		return linkStateInfo.toString();
	}

	public void runSPF() {
		// TODO 生成路由表
		// First 生成二维矩阵
		int dimensions = Simulator.getRouters().size();
		int[][] graph = new int[dimensions][dimensions];
		// initialize the graph
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				if (i == j) {
					graph[i][j] = 0;
					continue;
				}
				graph[i][j] = 1;
				graph[i][j] = getCost(Simulator.getRouters().get(i), Simulator
						.getRouters().get(j));
			}
		}
//		// output the graph
		System.err.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		for (int i = 0; i < dimensions; i++) {
			for (int j = 0; j < dimensions; j++) {
				System.err.print(graph[i][j] + " ");
			}
			System.err.println();
		}
		// 获得最短路径以及其间的具体的路由
		int start = Simulator.getRouters().indexOf(this);
		ArrayList<Integer> distance = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
		
		// 
		System.out.println("***************************");
		
		
		System.err.println("start is " + start);
		System.err.println(distance);
		System.out.println(paths);
		System.out.println("***************************");
		
		SpfA.dijkstra(graph, start, distance, paths);
		
		// 输出该最短路径
		System.out.println(distance);
		System.out.println(paths);
	}

	private int getCost(Router router1, Router router2) {

		for (int i = 0; i < router1.getInterfaces().size(); i++) {
			Interface interface1 = router1.getInterfaces().get(i);
			if (interface1.getLink() == null) {
				continue;
			} else {
				if (interface1.getLink().getOtherSide().getRouter().equals(router2)) {
					
					return interface1.getLink().getCost();
				}
			}

		}
		return SpfA.MAXVALUE;
	}

	public boolean equals(Router router) {

		if (this.name == router.getName() && this.getRID() == router.getRID()) {
			return true;
		}
		return false;
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
