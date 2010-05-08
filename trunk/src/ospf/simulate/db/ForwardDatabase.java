package ospf.simulate.db;

import ospf.simulate.router.IP;
import ospf.simulate.router.Interface;

public class ForwardDatabase {

}

class ForwardDBItem {
	
	private LinkType linkType = null;
	private IP ip = null;
	private Interface interface1 = null;
}

enum LinkType {
	C,
	O
}