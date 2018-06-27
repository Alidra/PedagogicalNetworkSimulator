
public class ArpCacheCleaner extends Thread{
	
	ArpCache myArpCache;
	
	public ArpCacheCleaner(ArpCache myArpCache){
		this.myArpCache=myArpCache;	
		}
	
    public void  run(){
	       while(!Thread.interrupted()){
		      try {
			     Thread.sleep(1000);
			     
		     }catch (InterruptedException e) {
			   e.printStackTrace();
		     } 
		      
		  myArpCache.clean();
	    }
		
	   }

      }