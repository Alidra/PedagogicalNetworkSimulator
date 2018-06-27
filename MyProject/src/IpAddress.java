


public class IpAddress {
	
	private short[] ipadress = new short[4];
	
//---------------------------------------------------------la methode getter-------------------------------------------------
	 public short[] getIpadress() { //getter

		 return ipadress;
	}
	 
	 public String toString() { 
		 return ipadress[0]+"."+ipadress[1]+"."+ipadress[2]+"."+ipadress[3];
		 
	}

//--------------------------------------------------------------la methode Stter-------------------------------------------
	public void setIpadress(short[] ipadress) {//setter
		this.ipadress = ipadress;
	}
	
//------------------------------------------------------------------le constructeur ---------------------------------
	
	public IpAddress(String adress)throws InvalidIPAdressException{
		if(isValidIpAdress(adress)==true){
			String[] tab = adress.split( "\\." ); 
			
				   for (int i = 0; i < tab.length; i++) {  
				  ipadress[i] = Short.parseShort(tab[i]);
				 
				   }    
				   
				   adress=""+ipadress[0]+"."+ipadress[1]+"."+ipadress[2]+"."+ipadress[3];
			}else{
				 throw new InvalidIPAdressException("Error in  the IpAddress");
	}
}
//---------------------------------------------------------------------Vlaidation ----------------------------------------------
	
	private boolean isValidIpAdress(String adress){
		String[] parts = adress.split( "\\." ); 
		if ( parts.length != 4 )    {  
			return false;         }
			
		for ( String s : parts )    {   
			short i = Short.parseShort( s );
				
				if ( (i < 0) || (i > 255) )  {   
				return false;        
					}    } 
			return true;
			}
	/////////////---------------------------------
	private IpAddress getBroadcastAdress(NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{
		short[] reseau = this.getNetworkAdress(netmask).getIpadress();
		short[] mask = netmask.getMask();
		
		short[] broad = new short[4];
		String[] binair= new String[4];
		String chiffreBinaire;
		String[] chainChar = new String[4];
		char[] charcter = new char[32];
		
		for(int i=0;i<4;i++){
		    chiffreBinaire=Integer.toBinaryString(mask[i]);
		    binair[i]= String.format("%08d", Integer.parseInt(chiffreBinaire));

		}	
		int j =0;
			for( int i=0;i<4;i++){
				for (int c=0;c<binair[i].length();c++){
					charcter[j] =binair[i].charAt(c);
					j++;
				}
			}
		for(int i=0;i<32;i++){
				if(charcter[i]=='1') charcter[i]='0';
					else charcter[i]='1';
			}
		
			int k=-1;
				for (int i=0;i<charcter.length;i++){
					if((i % 8) ==0){ k++;}
						chainChar[k]+=charcter[i];
					}
				for (int i=0;i<chainChar.length;i++){
						mask[i]=Short.parseShort(chainChar[i].substring(4),2);}
	 			for(short i=0; i<4;i++){
	 		broad[i]=(short) (reseau[i] | mask[i]);}
	 			
	 			String ipbroad =broad[0]+".";
	 			 for(int i=1;i<4;i++){
	 				 ipbroad +=broad[i]+".";
	 			 }

		return new IpAddress(ipbroad);
		
	}
//------------------------------------------------------Valide @ interface--------------------------------------------------------------
	
	public boolean isValidHostIpAdress(NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{//v�rifie la validit� de l'adress pour une interface r�seau (hote)
		String ip = this.toString();
		String reseau = this.getNetworkAdress(netmask).toString();
		String broad = this.getBroadcastAdress(netmask).toString();
		
		
			
		System.out.println(ip);
		System.out.println(broad);
		System.out.println(reseau);
		

		if((ip.equals(reseau))||(ip.equals(broad) ))
return false;
		else		return true;

		    
	}
	//-----------------------------------------------------la CLass-------------------------------------------------------------------
	
	public char getIpClass(){
		if(ipadress[0]>=1 && ipadress[0]<127) {
			return 'a' ;
		}
		if(ipadress[0]>=127 && ipadress[0]<192) {
			return 'b';
		}
		if(ipadress[0]>=192 && ipadress[0]<226) {
			return 'c' ;
		}
		return 0;
	}


//------------------------------------------------------le masque------------------------------------------------------------------------ 	
	public NetMask  getDefaultNetMask() throws NoNetMaskException {
		if(this.isValidIpAdress(this.toString())) {
			  
			  if(this.getIpClass()=='a')
				return new NetMask("255.255.255.0"); 
		  
		  
		       if(this.getIpClass()=='b')
				return new NetMask("255.255.0.0"); 
		  
		       if(this.getIpClass()=='c')
				return new NetMask("255.0.0.0"); 
		  }
		return null; 
		  
		
	}
	
	//-----------------------------------------@Reseau -----------------------------------------------------------------
	
	public IpAddress getNetworkAdress(NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{// retourne l'adresse ip du rÃ©seau par rapport au masque passÃ© en paramÃ¨tre
		short ip[] = this.getIpadress();
		 short mask[] =netmask.getMask();
		
		short reseau[]=new short[4];
		
		 
		 for(int i=0;i<4;i++){
		
			reseau[i]=(short) ((ip[i])&(mask[i]));
			
		}
		String ipreseau =reseau[0]+".";
		 for(int i=1;i<4;i++){
			 ipreseau +=reseau[i]+".";
		 }
		return new IpAddress(ipreseau);
		
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	public boolean isIpAdressInTheSameNetwork(IpAddress ipdestination, NetMask netmask) throws InvalidIPAdressException, NoNetMaskException{
		if((this.getNetworkAdress( netmask)).toString().equals(ipdestination.getNetworkAdress( netmask).toString()))
		return true;
		else return false;
		 
	}
	
	
	public boolean equals(IpAddress ipAddress){
		return true;
	}}

