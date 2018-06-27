
public class Timer extends Thread{
	AddressResolutionProtocol arp;
	
	public Timer(AddressResolutionProtocol arp){
		this.arp=arp;
	}
	
    public void  run(){
    	while(!Thread.interrupted()){
		try {
		
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
			try {
				arp.timer();
			} catch (InvalidIPAdressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoNetMaskException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	   }
		
	   }
 
     }
