
public class ReverseAddressResolutionProtocol {
	protected Ethernet myEthernet;
	protected ArpCache arpCache;
	
	public  ReverseAddressResolutionProtocol(Ethernet myEthernet){
		this.myEthernet=myEthernet;
		arpCache = myEthernet.getMyrouter().getMyArpCache();

		
	}

	public void  getMyIpAddressFromMyMacddress(MacAddress myMacAddress) throws InvalidIPAdressException, NoNetMaskException {

		this.sendRarpRequest(myMacAddress);
	}

	private void sendRarpRequest(MacAddress myMacAddress) throws InvalidIPAdressException, NoNetMaskException {
		myEthernet.getMyrouter().display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: RARP PROTOCOL:: L'envoi de la requête RARP");

		Packet requestArpPacket=new Packet(Constante.MATERIAL_TYPE,Constante.ADDRESS_RESOLUTION_PROTOCOL,Constante.MAC_ADDRESS_LENGTH,Constante.IP_ADDRESS_LENGTH,(byte)3,myMacAddress,null,null,null);
		myEthernet.send(requestArpPacket,new MacAddress("FF:FF:FF:FF:FF:FF"), (short) 0x8035);	
		System.out.println("le paquet"+requestArpPacket);
		System.out.println("l'interface"+myEthernet);
		System.out.println("le routeur"+myEthernet.myrouter);


		

		
		myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: RARP PROTOCOL:: l'envoi de la réponse RARP");

	}
	
	 public void receiveRarpPacket(Packet rarpPacket) throws InvalidIPAdressException, NoNetMaskException{
	    	
	    	if(arpCache.isEntryInTheCache(rarpPacket.getSourceMacAddress())){
	    			if(rarpPacket.getOperationCode()==3){
	    				for (int i=0;i<arpCache.getArpCache().size();i++){
	    					if(rarpPacket.getSourceMacAddress().toString().equals(arpCache.getArpCache().elementAt(i).getMacAddress().toString())){
	    						rarpPacket.setSourceMacAddress(myEthernet.getMymacAdress());
	    		    			rarpPacket.setTargetMacAddress(arpCache.getArpCache().elementAt(i).getMacAddress());
	    		    			rarpPacket.setSourceIpAddress(myEthernet.interfaceAddress);
	    		    			rarpPacket.setTargetIpAddress(arpCache.getArpCache().elementAt(i).getIpAddress());
	    						rarpPacket.setOperationCode((byte)4);

	    		    			myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: ARP PROTOCOL:: l'envoi de la réponse RARP");
	    		    			myEthernet.send(rarpPacket,rarpPacket.getTargetMacAddress(),(short) 0x8035);
	    		    		
	    		    			

	    					}
	    				}
	    			
	    				
	    		
	    			}
	    			
	    					
	    			
	    			else
	    				if(rarpPacket.getOperationCode()==4)
	    					myEthernet.interfaceAddress=rarpPacket.getTargetIpAddress();
	    			}
	    	else
	    		throwPacket();
	    		}
	 protected void throwPacket(){
		   myEthernet.myrouter.display.appendJTextArea("\n Router"+myEthernet.myrouter.getId()+":: RARP PROTOCOL:: Paquet RARP rejeté");
	   }
	 
	
/*public static void main (String[]args) throws InvalidIPAdressException, NoNetMaskException{
	ReverseAddressResolutionProtocol rarp=new ReverseAddressResolutionProtocol(new Ethernet(new Router("hvhvhj", 0, 0)));
	System.out.println(" "+rarp.myEthernet);
}*/
}
