 import java.awt.Color; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;
public class CreateProtocolRadioButton extends JRadioButton implements MouseListener{
	protected GraphicalUserInterface myGUI;
	protected String nameRadio;
	protected	boolean slectRadio;
	protected JToolBar aToolBarLeft;
	protected ButtonGroup aButtonGroupExclusif;
JRadioButton rip,icmp,ospf,nat;
	public CreateProtocolRadioButton(GraphicalUserInterface myGUI   ) {	
		super ();
		this.myGUI=myGUI;
	    
	}	 
 
public	JRadioButton createProtocolRadioButton(GraphicalUserInterface myGUI, String nameRadio, boolean slectRadio, JToolBar aToolBarLeft, ButtonGroup aButtonGroupExclusif)  {        
		final JRadioButton aRadioProtocol = new JRadioButton(nameRadio,	slectRadio);
		aRadioProtocol.setBackground(new Color(225, 248, 253));		
		aRadioProtocol.addMouseListener(this);
		aToolBarLeft.add(aRadioProtocol);		
		aButtonGroupExclusif.add(aRadioProtocol);		 		  
		return aRadioProtocol;
	}  
@Override
public void mouseClicked(MouseEvent arg0) {
	 
		  
		if(rip.isSelected()){
   		this.myGUI. activeProtocole="RIP" ;}
		else if(ospf.isSelected())
			this.myGUI. activeProtocole="OSPF";
		else if(nat.isSelected())
			this.myGUI. activeProtocole="NAT";
		else if(icmp.isSelected())
		
			this.myGUI. activeProtocole="icmp";
			
			
		
		this.myGUI.workSpace.repaint();  
		  
	
}
@Override
public void mouseEntered(MouseEvent arg0) {
	 
	
}
@Override
public void mouseExited(MouseEvent arg0) {
	 
}
@Override
public void mousePressed(MouseEvent arg0) {
	 
	
}
@Override
public void mouseReleased(MouseEvent arg0) {
	 
}

}
