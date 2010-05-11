package ospf.simulate.router;

public class Link {

	public Link(Interface one, Interface two) {
		
		this.oneSide = one;
		this.otherSide = two;
	}
	
	public Link(Interface one, Interface two, int cost) {
		
		this(one, two);
		this.cost = cost;
	}
	
	public int getCost() {
		
		return cost;
	}

	public Interface getOneSide() {
		
		return oneSide;
	}

	public Interface getOtherSide() {
		
		return otherSide;
	}

	public void setCost(int cost) {
		
		this.cost = cost;
	}

	public void setOneSide(Interface oneSide) {
		
		this.oneSide = oneSide;
	}

	public void setOtherSide(Interface otherSide) {
		
		this.otherSide = otherSide;
	}

	public boolean equals(Link link) {
		
		if (this.oneSide == link.oneSide && this.otherSide == link.otherSide
				|| this.oneSide == link.otherSide && this.otherSide == link.oneSide)
			return true;
		return false;
	}
	
	public String toString() {
		
		return "Link: cost-" + cost + " Dire: " + oneSide + "->" + otherSide;
	}
	
	private int cost = 0;
	private Interface oneSide = null;
	private Interface otherSide = null;
}
