

public class IPDatagram {
	//----------------------------------les champs du paquet-----------------------------------------------------
	
	protected byte version;
	protected byte internetHeaderLength;
	protected short typeOfService;
	protected short totalLength;
	protected short identification;
	protected byte flag;
	protected short fragmentOffset;
	protected short timeToLive;
	protected byte protocol;
	protected int checksum;
	protected int options;
	protected IpAddress sourceAddress;
	protected IpAddress destinationAddress;
	protected Message data;
		 
//------------------------------------------constructeur avec parametres---------------------------------------------------------
	public IPDatagram(short typeOfService, short identification, byte flag,short calculeTimeToLive,
			byte protocol, IpAddress sourceAddress, IpAddress destinationAdress,
			Message data) {
		super();
		this.typeOfService = typeOfService;
		this.identification = identification;
		this.flag = flag;
		this.protocol = protocol;
		this.sourceAddress = sourceAddress;
		this.destinationAddress = destinationAdress;
		this.data = data;
		this.checksum = 0;
		this.version =  4;
		this.internetHeaderLength=(byte) 200;
		this.totalLength=15;
		this.fragmentOffset=45;
		this.options=85;
		this.timeToLive=(short) 254;
		
		}
	
	//-----------------------------------méthode qui calcule le checksum----------------------------------------------------
		public boolean  checksum (){
			
			return true;
			
		}
		
	
		public short calculeTimeToLive(){
			
			return timeToLive=  (short) (timeToLive-1);
		
	 }
	
	//----------------------------------------Getters-----------------------------------------------------------------------------
	
	public byte getVersion() {
		return version;
	}

    public byte getInternetHeaderLength() {
		return internetHeaderLength;
	}
    
    public short getTypeOfService() {
		return typeOfService;
	}

	public short getTotalLength() {
		return totalLength;
	}

	public short getIdentification() {
		return identification;
	}

	public byte getFlag() {
		return flag;
	}
 
    public short getFragmentOffset() {
		return fragmentOffset;
	}

    public short getTimeToLive() {
		return timeToLive;
	}

	public  byte getProtocol() {
		return protocol;
	}

	public int getChecksum() {
		return checksum;
	}

	public int getOptions() {
		return options;
	}

	public IpAddress getSourceAdress() {
		return sourceAddress;
	}

	public IpAddress getDestinationAdress() {
		return destinationAddress;
	}

	public Message getData() {
		return data;
	}

}
