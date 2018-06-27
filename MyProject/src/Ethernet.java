
public class Ethernet extends PhysicalPort {
	protected String name ;
	protected IpAddress interfaceAddress;
	protected NetMask maskInterface ;
	protected MacAddress mymacaddress ;
	protected boolean physicalyconnected;
	protected boolean admistrativelyup;
	protected Router myrouter;
	protected Router nextHop;
    protected Ethernet remoteEthernetinterface;
	protected AddressResolutionProtocol arp;
	protected ReverseAddressResolutionProtocol rarp;
	

/////////**********************************ConSTRUCTEUR *********************************************/////////////////////////////////	
	public Ethernet(Router router) throws InvalidIPAdressException, NoNetMaskException{
		this.myrouter = router;
		arp= new AddressResolutionProtocol(this);
		rarp=new ReverseAddressResolutionProtocol(this);
		this.interfaceAddress = null;
		this.maskInterface = new NetMask("0.0.0.0");
		this.mymacaddress=new MacAddress("0C:00:00:8A:7B:2C");
		this.physicalyconnected=false;
		this.admistrativelyup=false;
		myrouter.getInterfaceRouter().addElement(this);


	}
	

	//************************************************methode send***************************************************************
			public void send( Object ipdatagram , MacAddress targetMacAddress, short protocolType) throws InvalidIPAdressException, NoNetMaskException {
			
			this.myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: Une trame envoyée");

			MacAddress sourceMacAddress = mymacaddress;
			EthernetFrame frame = new EthernetFrame(targetMacAddress,sourceMacAddress, protocolType ,ipdatagram , (Integer)25);
			
			remoteEthernetinterface.receive(frame);
			
			}
		//************************************************methode receive***************************************************************
		
			private void receive(EthernetFrame frame) throws InvalidIPAdressException, NoNetMaskException {
			this.myrouter.display.appendJTextArea("\n Router"+myrouter.getId()+":: Une trame reçue");
           	MacAddress targetMacAddress= mymacaddress;
				if(frame.getTargetMacAddress().equals(targetMacAddress)||frame.getTargetMacAddress().equals("FF:FF:FF:FF:FF:FF"))
					if(frame.getProtocolType()==Constante.INTERNET_PROTOCOL)
				myrouter.getInternetProtocol().receive((IPDatagram)frame.getData());
				else if(frame.getProtocolType()==Constante.ADDRESS_RESOLUTION_PROTOCOL)
				getArp().receiveArpPacket((Packet)frame.getData());
			
				
			}
		/*************************************remoteEthernetInterface*********************************************************************/
	
	public AddressResolutionProtocol getArp() {
		return arp;
		
	}
	public ReverseAddressResolutionProtocol getRarp() {
		return rarp;
		
	}
	/*************************************myrouter*********************************************************************/

	public Router getMyrouter() {
		
		return myrouter;
	}
	/*************************************myMacAdress*********************************************************************/
	public MacAddress getMymacAdress() {
		return mymacaddress;
	}

	public MacAddress setMymacAdress() {
		return mymacaddress ;
	}
	/*************************************ipAddress*********************************************************************/
	public IpAddress getInterfaceAddress() {
		
		return  interfaceAddress;
	}
	
	public void setInterfaceAddress(IpAddress ipAddress) {
		
		 this. interfaceAddress = ipAddress;
	}
	/////////////******************************************* NAME **************************************//////////////	
	public void setName(String name) {
	this.name=name;
	}
	public String getName() {
	return name;
	}
	
	/////////////////************************************ Mask *******************************************************//////////	
	public void setMaskInterface(NetMask mask) {
	this.maskInterface = mask;
	}
	
	public NetMask getMaskInterface() {
	return maskInterface;
	}
	
	///////////////////******************************** IsPhysically ****************************************//////////////////////////	
	public boolean isPhysicalyconnected() {
	return physicalyconnected;
	}
	public void setPhysicalyconnected(boolean physicalyconnected) {
	this.physicalyconnected = physicalyconnected;
	}
	/////////*********************************** ADMIN **************///////////////////////////	
	public boolean isAdmistrativelyup() {
	return admistrativelyup;
	}
	public void setAdmistrativelyup(boolean admistrativelyup) {
	this.admistrativelyup = admistrativelyup;
	}
	//////////////////////////***************************************CONNRCT ******************//////////////////////	
	public Ethernet getConnectedEthernet() {
	return remoteEthernetinterface;
	}
	public void setConnectedEthernet(Ethernet connectedEthernet) {
	this.remoteEthernetinterface = connectedEthernet;
	}
	////////////////////////***************************** ROUTER ***********************/////////////	
	public Router getNextHop() {
	return nextHop;
	}
	public void setNextHop(Router nextHop) {
	this.nextHop = nextHop;
	}
	public boolean equals(Ethernet myEthernetAddress){
		return true;
	}
		}
