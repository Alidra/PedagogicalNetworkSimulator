
public class Packet {
	
    protected byte	operationCode;
	protected MacAddress sourceMacAddress,targetMacAddress;
	protected IpAddress sourceIpAddress,targetIpAddress;
	
	
	public byte getOperationCode() {
		return operationCode;
	}
	
	
	public void setOperationCode(byte operationCode) {
		this.operationCode = operationCode;
	}

	
	public MacAddress getSourceMacAddress() {
		return sourceMacAddress;
	}

	
	public void setSourceMacAddress(MacAddress sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}

	
	public MacAddress getTargetMacAddress() {
		return targetMacAddress;
	}

	
	public void setTargetMacAddress(MacAddress targetMacAddress) {
		this.targetMacAddress = targetMacAddress;
	}

	
	public IpAddress getSourceIpAddress() {
		return sourceIpAddress;
	}

	
	public void setSourceIpAddress(IpAddress sourceIpAddress) {
		this.sourceIpAddress = sourceIpAddress;
	}

	
	public IpAddress getTargetIpAddress() {
		return targetIpAddress;
	}

	
	public void setTargetIpAddress(IpAddress targetIpAddress) {
		this.targetIpAddress = targetIpAddress;
	}

	
	public Packet(byte materialType, short protocolType,byte macAddressLength, byte ipAddressLength,byte operationCode, MacAddress sourceMacAddress,	IpAddress sourceIpAddress, MacAddress targetMacAddress
		, IpAddress targetIpAddress) {
		
		this.operationCode = operationCode;
		this.sourceMacAddress = sourceMacAddress;
		this.targetMacAddress = targetMacAddress;
		this.sourceIpAddress = sourceIpAddress;
		this.targetIpAddress = targetIpAddress;
	}

}
