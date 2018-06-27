

import java.util.Vector;


public class ArpCache {
	
	protected Vector<ArpCacheEntry> arpCache;
	protected  String arpTimeout="1440";
	private ArpCacheCleaner arpCacheCleaner;
	
	
	
	public ArpCache() {
		arpCache=new Vector<ArpCacheEntry>();
		arpCacheCleaner=new ArpCacheCleaner(this);
		arpCacheCleaner.start();
	}
	
	
	public String getArpTimeout() {
		return arpTimeout;
	}
	
	
	public void setArpTimeout(String timeout) {
		this.arpTimeout = timeout;
	}
	
	
	public Vector<ArpCacheEntry> getArpCache() {
		return arpCache;
	}
	
	
/**************************************** la methode getDestinationMacAddressFromIpAddress**********************************************/
	public MacAddress getDestinationMacAddressFromIpAddress(IpAddress ipAddress){
		MacAddress destinationMacAddress=null;
		for(int i=0;i<arpCache.size();i++){
			if(arpCache.elementAt(i).getIpAddress().toString().equals(ipAddress.toString())){
				destinationMacAddress=arpCache.elementAt(i).getMacAddress();
			}
		}
		return destinationMacAddress;	
	}	
	
	
	/**************************************** la methode isEntryInTheCache**********************************************/
	public boolean isEntryInTheCache(IpAddress ipAddress){
	for(int i=0;i<arpCache.size();i++){
			if(arpCache.elementAt(i).getIpAddress().toString().equals(ipAddress.toString()))
				return true;
		}
		   return false;
	 }
	public boolean isEntryInTheCache(MacAddress macAddress){
		for(int i=0;i<arpCache.size();i++){
				if(arpCache.elementAt(i).getIpAddress().toString().equals(macAddress.toString()))
					return true;
			}
			   return false;
		 }
		
	
	/**************************************** la methode addEntry**********************************************/
	
	protected void addEntry(IpAddress ipAddress,MacAddress macAddress,Ethernet myEthernet){
		if (!this.isEntryInTheCache(ipAddress)){	
			ArpCacheEntry arpCacheEntry=new ArpCacheEntry(Constante.PROTOCOL,Constante.ENCAPSULATION_TYPE,0,ipAddress,macAddress,myEthernet);
				arpCache.add(arpCacheEntry);}
			}

	
	
	/**************************************** la methode addStaticEntry**********************************************/

	public void addStaticEntry(IpAddress ipAddress,MacAddress macAddress){
		if (!this.isEntryInTheCache(ipAddress)){	
			ArpCacheEntry arpCacheEntry=new ArpCacheEntry(Constante.PROTOCOL,Constante.ENCAPSULATION_TYPE,-1,ipAddress,macAddress,null);
				arpCache.add(arpCacheEntry);}
			}
	
	/**************************************** la methode removeEntry**********************************************/

	
   public void removeEntry(IpAddress ipAddress){
	for(int i=0;i<=arpCache.size();i++){
		if(isEntryInTheCache(ipAddress))
			if(arpCache.elementAt(i).getIpAddress().toString().equals(ipAddress.toString())){
				arpCache.removeElementAt(i);
		}}
		}
   
   
	/**************************************** la methode update**********************************************/

	protected void update(IpAddress ipAddress,MacAddress macAddress,Ethernet myEthernet){
		for(int i=0;i<arpCache.size();i++){
			if(arpCache.elementAt(i).getIpAddress().toString().equals(ipAddress.toString())){
				arpCache.setElementAt(new ArpCacheEntry(Constante.PROTOCOL,Constante.ENCAPSULATION_TYPE,0,ipAddress,macAddress,myEthernet), i) ;
			
			}
				
			}
	}
	/**************************************** la methode clean**********************************************/

	public void clean(){
		for(int i=0;i<arpCache.size();i++){
			if(!arpCache.elementAt(i).isStaticEntry())
				arpCache.elementAt(i).setAge(arpCache.elementAt(i).getAge()+1);
			if(arpCache.elementAt(i).getAge()>=Integer.parseInt(getArpTimeout()))
				arpCache.removeElementAt(i);
			
			}
			}
	
	
	/******************************************la methode clear arp cache**************************************/

  public void clearArpCache(){  
	for (int i=0;i<arpCache.size();i++){
		if(!arpCache.elementAt(i).isStaticEntry()){
				arpCache.removeElementAt(i);
			
			}
		}
  	}




	}


