public class ArpCacheEntry {
	protected static final String protocol ="Internet",encapsulationType="ARPA";
	protected int age;
	protected IpAddress ipAddress;
	protected MacAddress macAddress;
	protected Ethernet myEthernet;
	
	
	
	public ArpCacheEntry(String protocol, String encapsulationType,int age, IpAddress ipAddress,
			MacAddress macAddress, Ethernet myEthernet) {
		this.age = age;
		this.ipAddress = ipAddress;
		this.macAddress = macAddress;
		this.myEthernet = myEthernet;
		
	    }
	
	
	
	public int getAge() {
		return age;
	     }
		
		
	public void setAge(int age) {
		    this.age=age;
				
			}
		
		
	public IpAddress getIpAddress() {
		return ipAddress;
	}
	
	
	public MacAddress getMacAddress() {
		return macAddress;
	}
	
	public Ethernet getMyEthernet() {
		return myEthernet;
	}
	
	
	public boolean isStaticEntry(){
	
	if(this.getAge()==-1)
		return true;
	else
			return false;
		
	}
	
	
	
}
