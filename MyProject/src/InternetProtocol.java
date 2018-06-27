
 
import java.util.Vector;

		public class InternetProtocol {
	//------------------------------------------les attributs----------------------------------------------------------------
	
	protected ICMP icmp;
	protected ProtocolOverIp protocolOverIp[] = new ProtocolOverIp[30];
	protected RoutingTable routingTable;
	protected  Router myrouter;
	
	//----------------------------------constructeur avec parametres-----------------------------------------------------------
		public InternetProtocol(Router myrouter) {
	super();
	this.icmp = new ICMP(this);
	this.routingTable = new RoutingTable(this);
	this.myrouter = myrouter;
	protocolOverIp[Constante.ICMP]=icmp;

		}			
	//-----------------------------------la méthode send----------------------------------------------------------------------------
	
		public void send( short typeOfService, short identification, byte flag,short ttl,
				byte protocol,IpAddress destinationAddress,Message data) throws InvalidIPAdressException, NoNetMaskException   {
		   Vector<RoutingEntry>  exitInterfaces = routingTable.lookUp(destinationAddress);
		   Ethernet  exitInterface = selectRoute(exitInterfaces);
		  
			if(exitInterface != null){
		   IpAddress sourceAddress = exitInterface.getInterfaceAddress();
		   
		 
			   
			   
     	   IPDatagram  ipdatagram=new IPDatagram(typeOfService, identification, flag,ttl,protocol,sourceAddress,destinationAddress, data);
     	   MacAddress targetMacAddress=this.myrouter.getMyArpCache().getDestinationMacAddressFromIpAddress( destinationAddress);
     	  if(sourceAddress==null)
			   exitInterface.getRarp().getMyIpAddressFromMyMacddress(exitInterface.getMymacAdress());
			  myrouter.display.appendJTextArea("\n  Router"+myrouter.getId()+":: INTERNET PROTOCOL:: L'adresse mac récuperée "+targetMacAddress);
			  int j=0;
	     	   while(j<4){
	 		 if(targetMacAddress==null){
		 		exitInterface.getArp().requestDestinationMacAddress(destinationAddress);	
				 try {
				Thread.sleep(1000);
			
				} catch (InterruptedException e) {
				
				e.printStackTrace();
				}
			 targetMacAddress=this.myrouter.getMyArpCache().getDestinationMacAddressFromIpAddress( destinationAddress);
			  myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: INTERNET PROTOCOL:: L'adresse mac récuperée "+targetMacAddress);

			 if(!targetMacAddress.equals(null))
			 	  myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: INTERNET PROTOCOL:: Le datagramme est envoyé ");
			}
 		       j++;
 		     
	     	   }
	 	  myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: INTERNET PROTOCOL:: Le datagramme est envoyé ");
         IPDatagram[] ipdatagrams= datagramFragment(ipdatagram);
			 for (int i=0;i<ipdatagrams.length;i++){
				 if(this.isaLocalAddress(destinationAddress))
				 receive(ipdatagram);
				 else
           exitInterface.send(  ipdatagram, targetMacAddress,Constante.INTERNET_PROTOCOL);
           
							}
			}else
		   throwDatagram("interface de sortie introuvable");
		}
	//----------------------------la methode selectRoute()--------------------------------------------------------------------
		public Ethernet selectRoute(Vector<RoutingEntry> exitInterfaces) {
		
			if (exitInterfaces.size()>0)
			return  exitInterfaces.elementAt(0).getExitInterface();
			else
			return null;
				
		}
	//------------------------------------------la methde throwDatagram()-----------------------------------------------------
		public void throwDatagram(String msg){
		myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: INTERNET PROTOCOL:: Datagramme jeté "+ msg);
		}
	//-----------------------------------------la methde receive---------------------------------------------------------------
		public void receive(IPDatagram ipdatagram) throws InvalidIPAdressException, NoNetMaskException {
		myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: INTERNET PROTOCOL:: Datagramme reçu "+ipdatagram.getData());
			if(!ipdatagram.checksum())
			throwDatagram("checksum erroné");
			else{
			
			  if( isaLocalAddress(ipdatagram.getDestinationAdress())){
			  ipdatagram =datagramDefragment(ipdatagram);
				 if(ipdatagram.calculeTimeToLive()<=0)
				throwDatagram("timeout");
				 else
				getProtocolByNumber(ipdatagram).receive(ipdatagram.getData());
			}
			  else
			send(ipdatagram.getTypeOfService(),  ipdatagram.getIdentification(), ipdatagram.getFlag(),ipdatagram.getTimeToLive(),
					ipdatagram.getProtocol(), ipdatagram.getDestinationAdress(), ipdatagram.getData());
		
			}
		
		}
	//-------------------------------------------la methode getProtocolByNumber()---------------------------------------------
		 public ProtocolOverIp getProtocolByNumber( IPDatagram ipdatagram) {
    		return protocolOverIp[ipdatagram.getProtocol()];
		
			}
		
	//--------------------------------------la methde isaLocalAdress----------------------------------------------------------
		 public boolean isaLocalAddress(IpAddress destinationAddress){
		Vector<IpAddress> ipaddresses= routingTable.getLocalIpAddresses();
			for(int i=0;i<ipaddresses.size();i++){
		      if(destinationAddress.toString().equals(ipaddresses.elementAt(i).toString()))
		  return true;
		}
	      return false;
	
		}
		
		//-------------------------------la methode datagramfragment()-----------------------------------------------------
			
		 public IPDatagram[] datagramFragment(IPDatagram ipdatagram ){
	   IPDatagram tab[] = new IPDatagram[1];
	   tab[0]= ipdatagram;
	   return tab;
			 
				  }
		//-------------------------------la methode datagramDefragment()-----------------------------------------------------
		 public IPDatagram datagramDefragment(IPDatagram ipdatagram){
		return ipdatagram;
		
		 }
	 /******************************************ICMP****************************************************************/
		 public ICMP getIcmp() {
		return icmp;
			}
		 public void setIcmp(ICMP icmp) {
		this.icmp = icmp;
			}

	}		
		
		
		
		

	


