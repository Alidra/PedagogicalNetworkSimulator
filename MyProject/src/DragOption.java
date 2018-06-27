
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class DragOption extends JPanel implements MouseListener,MouseMotionListener,LoggListener{
	Log log;
	GraphicalUserInterface myGUI;
 	private BufferedImage image;
	private static final long serialVersionUID = 1L;
   private Border overBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
   private Border outBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
   Image img=null;
   int n;
   LogEvent e;


   public DragOption(String file,int n,GraphicalUserInterface  myGUI) {
	   this.myGUI=myGUI;
	   log=new Log();
   	  img=Toolkit.getDefaultToolkit().getImage(file);
   	  this.setOpaque(false);
   	  this.n=n;
      setBorder(outBorder);
      addMouseListener(this);
      addMouseMotionListener(this);
      
    
	MsgReceived( e);

   }
   
   public void paint(Graphics g){
   	g.drawImage(img,0,0,getSize().width,getSize().height,this);
   	String m= n+"";
   	g.setFont(new Font("Verdana",1,10));
   	int x=23-5*m.length()/2;
   	g.drawString(n+"",x,14);
   	super.paint(g);
  
   }
   
   public void mousePressed(MouseEvent arg0) {}

   public void mouseReleased(MouseEvent arg0) {
      setBorder(outBorder);
   
   }

   public void mouseEntered(MouseEvent arg0) {
    
      setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      setBorder(overBorder);
   }

   public void mouseExited(MouseEvent arg0) {
	   setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      setBorder(outBorder);
   }

   public void mouseClicked(MouseEvent arg0) {
	   
   }

   public void mouseMoved(MouseEvent e){}

   public void mouseDragged(MouseEvent e){
     setLocation(this.getX()-15+e.getX(),this.getY()-15+e.getY());
   }




public void MsgReceived(LogEvent e) {
	 log.WriteinFile("Routeur"+n+"a éte bien crée à :"+new Date().toString()+",il utilise le activeProtocole :"+ this.myGUI.activeProtocole,this.myGUI.sendInformation);
	
	


	}

public void addMouseMotionListener() {
	// TODO Auto-generated method stub
	
}

public void addMouseListener() {
	// TODO Auto-generated method stub
	
}

	 

	
}


