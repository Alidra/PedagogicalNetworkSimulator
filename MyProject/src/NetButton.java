import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NetButton implements ActionListener {
protected GraphicalUserInterface myGUI ;
	public NetButton(String aMethodName, GraphicalUserInterface myGUI ) {
    this.myGUI=myGUI;
     
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 
		myGUI.activeButton=this;
	}
	public void insert ( ){
		
	}

}
