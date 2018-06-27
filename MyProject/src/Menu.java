import javax.swing.JMenu;


public class Menu {
	 String commandName ;
	    String anImage ;
	    char accessCharacter ;
	    int aShortcutKey ;
	    int ctrlMask ;
	    String  aMethodName;
	    JMenu aJmenu ;
	    
	    public Menu( String commandName , String anImage , char accessCharacter, int aShortcutKey ,int ctrlMask,
	    		String  aMethodName,
	    JMenu aJmenu  ){
	    	this.commandName=commandName;
	    	this.anImage=anImage;
	    	this.accessCharacter=accessCharacter;
	    	this.aShortcutKey=aShortcutKey;
	    	this.ctrlMask=ctrlMask;
	    	this.aMethodName=aMethodName;
	     }
	    
}
 