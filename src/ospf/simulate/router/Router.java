package ospf.simulate.router;

import java.util.Vector;

import ospf.simulate.db.AdjacencyDatabase;
import ospf.simulate.db.ForwardDatabase;
import ospf.simulate.db.LinkStateDatabase;
import ospf.simulate.util.InterfaceType;

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

	
	
	public AdjacencyDatabase getAdjacencyDatabase() {
		
		if (adjacencyDatabase == null) {
			adjacencyDatabase = new AdjacencyDatabase();
		}
		return adjacencyDatabase;
	}

	public LinkStateDatabase getLinkStateDatabase() {
		
		if (linkStateDatabase == null) {
			linkStateDatabase = new LinkStateDatabase();
		}
		return linkStateDatabase;
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
	
	private AdjacencyDatabase adjacencyDatabase = null;
	private LinkStateDatabase linkStateDatabase = null;
	private ForwardDatabase forwardDatabase = null;
}
