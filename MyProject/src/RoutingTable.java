


import java.util.Vector;




public class RoutingTable {
	InternetProtocol myip;

	protected Vector<RoutingEntry> tabRouting = new Vector <RoutingEntry>() ;
	public RoutingTable(InternetProtocol myip) {
		super();
		this.myip = myip;
		myip.routingTable=this;
	}
	
	//------------------------------------------AddRoute-------------------------------------------------
	public void addRouter (String type, IpAddress netWorkAddress, NetMask mask,IpAddress netAddress,
			Ethernet exitInterface, int metric, byte distanceAdm , int age, boolean hiden){
		
			RoutingEntry routingentry = new RoutingEntry( type, netWorkAddress,mask , netAddress,
				 exitInterface, metric, distanceAdm ,  age,  hiden);
			
			tabRouting.add(routingentry);		
		}	
	//----------------------------------search------------------------------------------------------
	
	public int search(IpAddress ip,IpAddress ipreseau,NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{
		int indice=0;
			String ipEnter = ip.toString();
			for(int i=0;i<tabRouting.size();i++)
			{	
				String ipSearch =tabRouting.elementAt(i).getNextHopAddress().toString();
				if(ipEnter.equals(ipSearch)){
				   indice = i; 		   
				   break ;
			   }else indice =-1;				

			   
		}
		return indice;

	}
	
	
//----------------------------------------------RemoveRoute---------------------------------------------------
	public void removeRouter(IpAddress ip,IpAddress ipdestination,NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{
		int indice =this.search(ip, ipdestination, netmask);
		if(indice!= -1){
			tabRouting.removeElementAt(indice);
			
		}else System.out.println("flase"+indice);
	}
	public void removeInterface(String ether,IpAddress netWork,NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{
for(int i=0;i<tabRouting.size();i++){
	if(tabRouting.elementAt(i).getExitInterface().getName().equals(ether)
			&&(tabRouting.elementAt(i).getType().equals("s"))&&(tabRouting.elementAt(i).getNetWorkAddress().toString().equals(netWork.toString()))
			&&(tabRouting.elementAt(i).getMask().toString().equals(netmask.toString()))){
		tabRouting.removeElementAt(i);

			}
			
		}
	}
	//--------------------------------------SHow Ip Route-----------------------------------------------------
	
	public void showIpRoute(Cli Mycli) throws InvalidIPAdressException, NoNetMaskException{
		String chiffreBinaire;
		String[] binair= new String[4];
		char[] charcter = new char[32];
		short tab[] = new short[4];

		for(int i=0;i<tabRouting.size();i++){	
			update();
			int subnet = 0;
			String netmask = tabRouting.elementAt(i).getMask().toString();
			String[] parts = netmask.split( "\\." ); 
			for (int j = 0; j < parts.length; j++) {  
				  tab[j] = Short.parseShort(parts[j]);
				   }
			for(int k=0;k<4;k++){
			    chiffreBinaire=Integer.toBinaryString(tab[k]);
			    binair[k]= String.format("%08d", Integer.parseInt(chiffreBinaire));	}	
			int j1 =0;
			for( int j2=0;j2<4;j2++){
				for (int c=0;c<binair[j2].length();c++){
					charcter[j1] =binair[j2].charAt(c);
					j1++;
				}
			}
			for(int index=0;index<32;index++){
				if(charcter[index]=='1') {  
					subnet =subnet +1;
				}
			} 
	if((tabRouting.elementAt(i).getType()=="strapTheLineConnector")
			&&(this.updateEntry(tabRouting.elementAt(i))==false)){	
		
			Mycli.appendJTextArea("\n"+tabRouting.elementAt(i).getType()+"    "+tabRouting.elementAt(i).getNetWorkAddress()+"/"+
			 subnet +" is directly connected   "+tabRouting.elementAt(i).getExitInterface().getName() );
			
		}
			
	if((tabRouting.elementAt(i).getType()=="s")/*&&(tabRouting.elementAt(i).getExitInterface().getName().equals("null"))*/
			&&(this.updateRoute(tabRouting.elementAt(i))==false)){
			
		
			Mycli.appendJTextArea("\n"+tabRouting.elementAt(i).getType()+"   "+tabRouting.elementAt(i).getNetWorkAddress()+"/"+subnet 
						 +"  ["+tabRouting.elementAt(i).getDistanceAdm()+"/"+ tabRouting.elementAt(i).getMetric()+"]  via   "+
							tabRouting.elementAt(i).getNextHopAddress() );
			
					}
 if((tabRouting.elementAt(i).getType()=="s") &&(!tabRouting.elementAt(i).getExitInterface().getName().equals("null"))
		 &&(this.updateEntry(tabRouting.elementAt(i))==false)
		){
	 	Mycli.appendJTextArea("\n"+tabRouting.elementAt(i).getType()+"    "+tabRouting.elementAt(i).getNetWorkAddress()+"/"+
				 subnet +" is directly connected   "+tabRouting.elementAt(i).getExitInterface().getName() );
			}
		}
				
	}
	
	/***************************************lookUp********************************************************/
	
		public Vector<RoutingEntry> lookUp(IpAddress destinationAddress) throws InvalidIPAdressException, NoNetMaskException{	
		NetMask longerMask = new NetMask("0.0.0.0");
		Vector<RoutingEntry> matchingRoute = new Vector<RoutingEntry>() ;
			for(int i=0;i<tabRouting.size();i++){
				if(tabRouting.elementAt(i).getMask().longerThen(longerMask))
				longerMask =tabRouting.elementAt(i).getMask();
			}
			for(int i=0;i<tabRouting.size();i++){//deuxième passe
				if(!tabRouting.elementAt(i).isHiden() &&
				tabRouting.elementAt(i).getMask()== longerMask&&
				destinationAddress.isIpAdressInTheSameNetwork(tabRouting.elementAt(i).getNetWorkAddress(), longerMask)){
					if(tabRouting.elementAt(i).getExitInterface()==null)
				    matchingRoute.addAll(lookUp(tabRouting.elementAt(i).getNextHopAddress()));
					else{
					matchingRoute.add(tabRouting.elementAt(i));
				
					}
				}
			
			
			}
		return matchingRoute;
		}
		
		
		
	
								
			
		
	/////////////////////////////////////////////////////UpDate ////////////////////////////////////////////////////////
	public void update() throws InvalidIPAdressException, NoNetMaskException {

for(int i=0;i<tabRouting.size();i++){
	if(!tabRouting.elementAt(i).getExitInterface().getName().equals("null")){
			updateEntry(tabRouting.elementAt(i));
			//return hasRtbeanModified;
		
	}
	if(tabRouting.elementAt(i).getExitInterface().getName()=="null") {
		updateRoute(tabRouting.elementAt(i));
			//return hasRtbeanModified;
			//System.out.println("Erreur1");
			}
	
	}

}
////////////////////////////////////////////////////UpDate Route ////////////////////////////////////////////////////
 boolean updateRoute(RoutingEntry routestatic) throws InvalidIPAdressException, NoNetMaskException {
boolean newValue = true  ; 
	for(int j =0;j<tabRouting.size();j++){
		IpAddress ipsearch = routestatic.getNextHopAddress().getNetworkAdress(tabRouting.elementAt(j).getMask());

				if((tabRouting.elementAt(j).getNetWorkAddress().toString().equals(ipsearch.toString()))&&(tabRouting.elementAt(j).isHiden()==false)){
						routestatic.setHiden(false);
						newValue = false ;
						return newValue;
						
					}else{
						routestatic.setHiden(true);
						newValue = true ;
						}
				
	}
	return newValue;

}
 /////////////////////////////////////////UpDate Entry //////////////////////////////////////////////////////////////////////
private boolean updateEntry(RoutingEntry entry) {
	boolean newValue ;

if((entry.getExitInterface().isAdmistrativelyup()==true)
		&&(entry.getExitInterface().isPhysicalyconnected()==true)
		&&(!entry.getExitInterface().getInterfaceAddress().toString().equals("0.0.0.0"))){
	//if(entry.getType().equals("strapTheLineConnector")) {
		entry.setHiden(false);
		newValue = false ;
		return newValue;
}else{
	entry.setHiden(true);
	newValue = true ;
	return newValue;}
		
}
	
	//------------------------------------Taille de vectreur --------------------------------------------------
	public int getRoutingTableLength(){
		int i = tabRouting.size();
		return i;
	}
	
	public Ethernet getEntry(int i){
		return tabRouting.elementAt(i).getExitInterface();
		
	}
	public String getType(int i){
		return tabRouting.elementAt(i).getType();
		
	}
	public void setType(String type,int i){
		tabRouting.elementAt(i).setType(type);
		
	}
	public void setNetWorkAddress(IpAddress address,int i){
		tabRouting.elementAt(i).setNetWorkAddress(address);
		
	}
	public void setMask(NetMask mask,int i){
		tabRouting.elementAt(i).setMask(mask);
		
	}
	public Ethernet getEntryByName(String nom){
		for (int i=0; i<tabRouting.size();i++){
			if(tabRouting.elementAt(i).getExitInterface().getName().equals(nom)) 
				return tabRouting.elementAt(i).getExitInterface();
		}
		return null;
	}
	public boolean isHiden(int i){
		return tabRouting.elementAt(i).isHiden();
	}
	public void setHiden(boolean hiden , int i){
		tabRouting.elementAt(i).setHiden(hiden);
	}
	/**********************************************getLocalAddress******************************************************/
		public Vector<IpAddress> getLocalIpAddresses(){
        Vector<IpAddress> ipaddresses = new Vector<IpAddress> () ;
			for(int i = 0;i<tabRouting.size();i++){
				if(tabRouting.elementAt(i).getType().equals("strapTheLineConnector")){
				ipaddresses.addElement(tabRouting.elementAt(i).getExitInterface().getInterfaceAddress());
				
				}
			}
		return ipaddresses;
					 
			}

		}
