
public class EthernetFrame {
	
	MacAddress targetMacAddress,sourceMacAddress;
	protected short protocolType;
	protected Object data;
	protected int fcs;
	
	
	public EthernetFrame( MacAddress targetMacAddress, MacAddress sourceMacAddress, short protocolType,
			Object data, int fcs) {
		
		this.targetMacAddress = targetMacAddress;
		this.sourceMacAddress = sourceMacAddress;
		this.protocolType = protocolType;
		this.data = data;
		this.fcs = fcs;
	    }
	
	
	public MacAddress getSourceMacAddress() {
		return sourceMacAddress;
	    }
	
	
	public short getProtocolType() {
		return protocolType;
	    }
	
	
	public Object getData() {
		return data;
	    }


	public MacAddress getTargetMacAddress() {
		// TODO Auto-generated method stub
		return targetMacAddress;
	}

    }
