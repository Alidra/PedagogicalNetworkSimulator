


public class RoutingEntry {
	private String type ;
	private IpAddress nextHopAddress;
	private IpAddress netWorkAddress;
	private NetMask mask;
	private Ethernet exitInterface;
	private int metric;
	private byte distanceAdm;
	private int age ;
	private boolean hiden ;
	
	public RoutingEntry(String type, IpAddress netWorkAddress, NetMask mask,IpAddress netAddress,
			Ethernet exitInterface, int metric, byte distanceAdm , int age, boolean hiden) {
		this.type = type;
		this.netWorkAddress=netWorkAddress;
		this.mask = mask;
		this.nextHopAddress = netAddress;
		this.exitInterface = exitInterface;
		this.metric = metric;
		this.distanceAdm = distanceAdm;
		this.age = age;
		this.hiden = hiden;
	}
/*	public void setNetAddress(IpAddress netAddress) {
		this.netAddress = netAddress;
	}*/
	public void setNetWorkAddress(IpAddress netWorkAddress) {
		this.netWorkAddress = netWorkAddress;
	}
	public void setMask(NetMask mask) {
		this.mask = mask;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public IpAddress getNetWorkAddress() {
		return  netWorkAddress;
	}
	
	public NetMask getMask() {
		return mask;
	}
	
	public IpAddress getNextHopAddress() {
		return  nextHopAddress;
	}
	
	public Ethernet getExitInterface() {
		return exitInterface;
	}
	public int getMetric() {
		return metric;
	}
	public byte getDistanceAdm() {
		return distanceAdm;
	}
	public int getAge() {
		return age;
	}
	
	public boolean isHiden() {
		return hiden;
	}
	public void setHiden(boolean hiden) {
		this.hiden = hiden;
	}
	
}
