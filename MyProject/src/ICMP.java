

public class ICMP implements ProtocolOverIp {
	
	InternetProtocol internetProtocol ;
	
		public ICMP(InternetProtocol internetProtocol) {
		this.internetProtocol=internetProtocol;
			}
		public InternetProtocol getInternetProtocol() {
		return internetProtocol;
			}
		public void send(Message message,IpAddress ipAddress) throws InvalidIPAdressException, NoNetMaskException{
		MesageIcmp msg = new MesageIcmp(message.toString());
		getInternetProtocol().myrouter.display.appendJTextArea("\n Router"+internetProtocol.myrouter.getId()+":: ICMP:: Le message envoyé est "+msg);
		getInternetProtocol().myrouter.display.appendJTextArea("\n Router"+internetProtocol.myrouter.getId()+":: ICMP:: L'adresse de destination est "+ipAddress);
        internetProtocol.send( (short)0, (short)10,(byte) 30,(short) 127,(byte)Constante.ICMP,ipAddress , msg);
		}
	
		public void receive(Message message) {
		getInternetProtocol().myrouter.display.appendJTextArea("\n Router"+internetProtocol.myrouter.getId()+":: ICMP:: Le message est reçu "+message);
		
		}

	}
