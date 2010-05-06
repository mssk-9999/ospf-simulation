package ospf.simulate.router;

public class IP {

	public IP(String ip, String mask) {
		
		this.ipNumber = ip;
		this.maskNumber = mask;
		calculateNetworkNumber();
	}

	private void calculateNetworkNumber() {
		// TODO 计算网络号
	}
	
	/**
	 * 比较两个IP地址，即当前IP和参数中的IP
	 * 
	 * @param ip
	 * @return int
	 * 			大于 1
	 * 			等于 0
	 * 			小于 -1
	 */
	public int compare(IP ip) {
		// TODO
		return 1;
	}
	
	
	
	public String getNetworkNumber() {
		
		return networkNumber;
	}

	public String getIpNumber() {
		
		return ipNumber;
	}

	public String getMaskNumber() {
		
		return maskNumber;
	}

	public void setNetworkNumber(String networkNumber) {
		
		this.networkNumber = networkNumber;
	}

	public void setIpNumber(String ipNumber) {
		
		this.ipNumber = ipNumber;
	}

	public void setMaskNumber(String maskNumber) {
		
		this.maskNumber = maskNumber;
	}



	private String networkNumber = null;
	private String ipNumber = null;
	private String maskNumber = null;
}
