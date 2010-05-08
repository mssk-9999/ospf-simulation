package ospf.simulate.router;

import java.util.StringTokenizer;

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
		StringTokenizer tokenizer1 = new StringTokenizer(this.ipNumber, ".");
		StringTokenizer tokenizer2 = new StringTokenizer(ip.ipNumber, ".");
		for (int i = 0; i < 4; i++) {
			int a = Integer.parseInt(tokenizer1.nextToken());
			int b = Integer.parseInt(tokenizer2.nextToken());
			if (a > b) {
				return 1;
			} if (a == b) {
				if (i != 3) {
					continue;
				}
				return 0;
			} if (a < b) {
				return -1;
			}
		}
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

	public String toString() {
		
		return "" + ipNumber + "-" + maskNumber;
	}

	private String networkNumber = null;
	private String ipNumber = null;
	private String maskNumber = null;
}
