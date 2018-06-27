

public class MesageIcmp extends Message {
	public MesageIcmp(String msg) {
		super(msg);
		
	}

	protected byte type,code;
	protected short checksum;
	protected String message;
	public byte getType() {
		return type;
	}
	public byte getCode() {
		return code;
	}
	public short getChecksum() {
		return checksum;
	}
	
	public String getMessage() {
		return message;
	}
	public boolean checksum(){
		return true;
		
	}

	

}
