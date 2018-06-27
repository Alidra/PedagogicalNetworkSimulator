

public class NetMask  {
		private  short[]  mask  = new short[4];
		
		
		
///////////////////***********************************
public short[] getMask() {
return mask;
}
/////*******************************////////
public void setMask(short[] mask) {
this.mask = mask;
}
	//--------------------------------------------

		 public String toString() { 
			 return mask[0]+"."+mask[1]+"."+mask[2]+"."+mask[3];
			 
		}
		 //----------------------------------------------------------------
	public NetMask(String netmask)throws NoNetMaskException{
		if(isValidNetMask(netmask)==true){
			String[] tab = netmask.split( "\\." ); 
			
				   for (int i = 0; i < tab.length; i++) {  
				  mask[i] = Short.parseShort(tab[i]);
				 
				   }    
				   
				   netmask=""+mask[0]+"."+mask[1]+"."+mask[2]+"."+mask[3];
			}else{
				
				 throw new NoNetMaskException("Error in the NetMask");
	}
	}
	///////////////////////////////////////////************************
		
	private boolean isValidNetMask(String netmask){
		String chiffreBinaire;
		String[] binair= new String[4];
		char[] charcter = new char[32];
		short tab[] = new short[4];
		String[] parts = netmask.split( "\\." ); 
		if ( parts.length != 4 )    {  
			return false;         }
		
		for (int i = 0; i < parts.length; i++) {  
			  tab[i] = Short.parseShort(parts[i]);
			   }
		for(int i=0;i<4;i++){
		    chiffreBinaire=Integer.toBinaryString(tab[i]);
		    binair[i]= String.format("%08d", Integer.parseInt(chiffreBinaire));

		}	
		int j =0;
		for( int i=0;i<4;i++){
			for (int c=0;c<binair[i].length();c++){
				charcter[j] =binair[i].charAt(c);
				j++;
			}
		}
		int index = 0 ;
		for(index=0;index<32;index++){
			if(charcter[index]=='0') {  
				break;
			}
		} 
		for(int i=index;i<charcter.length;i++){
			if((charcter[i]=='1')){
				return false; 
			}	
		}
		return true;
			}
	
	/*******************************************longerThen*****************************************************/
public boolean longerThen(NetMask netmask){
		
		for(int i=0;i<4;i++){
			if(this.mask[i]>netmask.mask[i])
				return true;
			else 
				if(this.mask[i]<netmask.mask[i])
				return false;
		}
		return false;
	}

	public static void main(String[] args) throws NoNetMaskException  {
		NetMask netmask1 = new NetMask("255.129.0.0");		
		System.out.println(netmask1);

		
		}
	
}
