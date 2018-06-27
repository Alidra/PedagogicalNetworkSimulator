import java.util.Vector;


public class AddressResolutionProtocol {
	protected Ethernet myEthernet;
	protected ArpCache arpCache;
	protected Vector<WaitingPacketVactorEntry> waitingPacketVector;
	protected Timer delay;
	
	public AddressResolutionProtocol(Ethernet myEthernet) throws InvalidIPAdressException, NoNetMaskException {
		this.myEthernet = myEthernet;
		arpCache = myEthernet.getMyrouter().getMyArpCache();
		waitingPacketVector=new Vector<WaitingPacketVactorEntry>();
		delay=new Timer(this);
		delay.start();
	}
	



	/**************************************** la methode requestDestinationMacAddress
	 * @throws NoNetMaskException 
	 * @throws InvalidIPAdressException **********************************************/

	public void  requestDestinationMacAddress(IpAddress destinationIpAddress) throws InvalidIPAdressException, NoNetMaskException{

		this.sendArpRequest(destinationIpAddress);
	}
	
	
	/**************************************** la methode sendArpPacket
	 * @throws NoNetMaskException 
	 * @throws InvalidIPAdressException **********************************************/

	protected void sendArpRequest(IpAddress destinationIpAddress) throws InvalidIPAdressException, NoNetMaskException{
		myEthernet.getMyrouter().display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: L'envoi de la requête ARP");
		Packet requestArpPacket=new Packet(Constante.MATERIAL_TYPE,Constante.ADDRESS_RESOLUTION_PROTOCOL,Constante.MAC_ADDRESS_LENGTH,Constante.IP_ADDRESS_LENGTH,(byte)1,myEthernet.getMymacAdress(),myEthernet.getInterfaceAddress(),null,destinationIpAddress);
		this.activateTimer(destinationIpAddress);
		myEthernet.send(requestArpPacket,new MacAddress("FF:FF:FF:FF:FF:FF"), (short) 0x806);
		}
	
	/**************************************** la methode receiveArpPacket
	 * @throws NoNetMaskException 
	 * @throws InvalidIPAdressException **********************************************/

	 public void receiveArpPacket(Packet arpPacket) throws InvalidIPAdressException, NoNetMaskException{
		    myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: Réception d'un paquet ARP"); 
	    	if(arpCache.isEntryInTheCache(arpPacket.getSourceIpAddress()))
	    	arpCache.update(arpPacket.getSourceIpAddress(),arpPacket.getSourceMacAddress(),myEthernet);
	    	
	    	if(arpPacket.getTargetIpAddress().toString().equals(myEthernet.getInterfaceAddress().toString())){
	    	
	    		if(!arpCache.isEntryInTheCache(arpPacket.getSourceIpAddress()))
	    		arpCache.addEntry(arpPacket.getSourceIpAddress(), arpPacket.getSourceMacAddress(),myEthernet);
	    		    		
	    			if(arpPacket.getOperationCode()==1){
	    			sendArpReply(arpPacket);
	    			}
	    			else{
	    			
	    				if(arpPacket.getOperationCode()==2){
	    					this.deactivateTimer(arpPacket.getSourceIpAddress());
	    		  
	    				}
	    	
	    		
	    			}
	    
	    		}
	    	else
	    		throwPacket();	
			}
	 
	 
	 protected void sendArpReply(Packet arpPacket) throws InvalidIPAdressException, NoNetMaskException{
		 	MacAddress macAddressSource=arpPacket.getSourceMacAddress();
 			IpAddress ipAddressTarget=arpPacket.getTargetIpAddress();
 			IpAddress ipAddressSource=arpPacket.getSourceIpAddress();
 			arpPacket.setSourceMacAddress(myEthernet.getMymacAdress());
 			arpPacket.setTargetMacAddress(macAddressSource);
 			arpPacket.setSourceIpAddress(ipAddressTarget);
 			arpPacket.setTargetIpAddress(ipAddressSource);
 			arpPacket.setOperationCode((byte)2);
 	
 			myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: l'envoi de la réponse ARP");
 			myEthernet.send(arpPacket,arpPacket.getTargetMacAddress(),(short) 0x806);
 		
	 }
    /**********************************************la methode timer 
     * @throws NoNetMaskException 
     * @throws InvalidIPAdressException ***********************************************************/
    public synchronized void timer() throws InvalidIPAdressException, NoNetMaskException{
    	
    int k=0;
    	for(int i=0;i<waitingPacketVector.size();i++){

    		 waitingPacketVector.elementAt(i).setAge(waitingPacketVector.elementAt(i).getAge()+1); 
    		while( k<20){
    			if(waitingPacketVector.elementAt(i).getAge()>=1){
    				   myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: Timer expiré");
    	   			waitingPacketVector.elementAt(i).setAge(0);
    	   			 this.sendArpRequest(waitingPacketVector.elementAt(i).getIpAddress());
    	   		
    	   			 k++;
    	   		} 
    			
    		}
    
    	
   		 }
		}	
   protected void throwPacket(){
	   myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: Paquet ARP rejeté");
   }
    	public boolean entryExist (IpAddress ipAddress){
    		
    		for(int i=0;i<waitingPacketVector.size();i++){
    			if(waitingPacketVector.elementAt(i).getIpAddress().toString().equals(ipAddress.toString()))
    				return true;
    		}
    		 
				return false;
    	}
    
    
    /*************************************************la methode activate timer**********************************************/
    protected void activateTimer(IpAddress ipAddress){
    	
    	WaitingPacketVactorEntry waitingPacketVactorEntry=new WaitingPacketVactorEntry(ipAddress,0);
         if(!this.entryExist(ipAddress))
    			waitingPacketVector.add(waitingPacketVactorEntry);
    		}
    	/**************************************************************** la metohde deactivate timer****************************/
    protected void deactivateTimer(IpAddress ipAddress){
    	
    	  for(int i=0;i<this.getWaitingPacketVector().size();i++){
  	    	if((waitingPacketVector.elementAt(i).getIpAddress()).toString().equals(ipAddress.toString())){
  	    		
  	    		waitingPacketVector.removeElementAt(i);
  	    	}
  	    	
  	    }
   	   myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: Timer désactivé");


    }
    	
   
   class WaitingPacketVactorEntry{
	   IpAddress ipAddress;
	   int age;
	   public WaitingPacketVactorEntry(IpAddress ipAddress, int age) {
		this.ipAddress = ipAddress; 
		this.age = age;
	}
	
	public IpAddress getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(IpAddress ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	   
   }
   public Vector<WaitingPacketVactorEntry> getWaitingPacketVector() {
		return waitingPacketVector;
	}
    


}
