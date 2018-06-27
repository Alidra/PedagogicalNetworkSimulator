


import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;







import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;



public   class Cli extends JPanel implements ActionListener,KeyListener {
	protected Router Myrouter;
	Vector<Ethernet> fer ;
	private Ethernet Currentinterface ;
    protected  String mainprompt;
	private String newtext="";
	protected JTextArea textarea;
	protected Element element=Compile("xml/User.xml");
    private Vector<String> _historyCommands = new Vector<String>();
	private int _historyCursor = 0;
	private int historyLength = 20;
	History History = new History(_historyCursor ,historyLength ,_historyCommands,textarea) ;
	Interpreter interpreter=new Interpreter(this);
	Executer executeur = new Executer(this);
    Help help=new Help(this);
	AutoTextComplete autotextcomplele=new AutoTextComplete(this);
	public String getMainprompt() {
		return mainprompt;
	}
   public String setMainprompt(String mainprompt) {
		return this.mainprompt = mainprompt;
	}
public Cli(Router Myrouter) {
	
this.Myrouter=Myrouter;

fer = new Vector<Ethernet>();
		for(int i =0;i<10;i++){
			Ethernet e = null;
			try {
				e = new Ethernet(Myrouter);
			} catch (InvalidIPAdressException e1) {	
				e1.printStackTrace();
			} catch (NoNetMaskException e1) {
				e1.printStackTrace();
				}
			fer.add(e);
			fer.elementAt(i).setName("FastEthernet"+i);
			
		}
		Myrouter.setInterfaceRouter(fer);
		IpAddress ip = null;	NetMask mask = null;
		try {
			ip = new IpAddress("0.0.0.0");
			mask = new NetMask("0.0.0.0");
			for (int j=0;j<Myrouter.getInterfaceRouter().size();j++){	
				Myrouter.getRoutingTable().addRouter("strapTheLineConnector",ip, mask, ip, Myrouter.getInterfaceRouter().elementAt(j), 0,(byte)1,-1,true);

				}
	} catch (InvalidIPAdressException e1) {e1.printStackTrace();}
		catch (NoNetMaskException e) {	e.printStackTrace();}
	
mainprompt = Myrouter.Nom+">";
setLayout(new BorderLayout());
textarea = new JTextArea();
textarea.setForeground(Color.BLACK);
textarea.setBackground(Color.WHITE);
textarea.setCaretColor(Color.BLACK);
textarea.setFont(new Font("Verdana",0,12));
textarea.addKeyListener(this);
textarea.setLineWrap(true);
textarea.setBorder( BorderFactory.createLineBorder(Color.black, 4));
setBounds(400, 400, 300, 300);
appendJTextArea("System Bootstrap"+"\n"+"\n");
appendJTextArea("Press RETURN to get started!"+"\n");
appendJTextArea(mainprompt);
JPanel panb = new JPanel(new FlowLayout(2));
JPanel panb2 = new JPanel(new FlowLayout(1));
panb.setBackground(Color.WHITE);
panb2.setBackground(Color.WHITE);
textarea.setWrapStyleWord(true);
JScrollPane scrollPane = new JScrollPane(textarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
scrollPane.setVisible(true);
add(scrollPane);
JLabel l = new JLabel ();
l.setText("IOS Command Line Interface");

l.setBackground(Color.WHITE);
l.setFont(new Font("Verdana",1,24));
panb2.add(l);
JButton copy = new JButton("copy");
copy.addActionListener(this);
panb.add(copy);
JButton past = new JButton("past");
past.addActionListener(this);
panb.add( past);
add(panb,BorderLayout.SOUTH);	
add(panb2,BorderLayout.NORTH);	

add(textarea);
setVisible(true);

copy.addActionListener(new ActionListener () {
	public void actionPerformed(ActionEvent event) {
	textarea.copy();
	}}); 
	past.addActionListener(new ActionListener () {
	public void actionPerformed(ActionEvent event) {
	textarea.paste();
	
	}});}

	
///////////////////////////////////////////////////////////

public int _promptCursor(){

	int j,i = textarea.getText().lastIndexOf("\n");
	newtext = textarea.getText().substring(i);
	
	if(newtext.contains(">")){
		 j = newtext.lastIndexOf(">");
	}else
	    j = newtext.lastIndexOf("#");
return i+j+1;
}


///////////////////////////////////////////////////////////

public void appendJTextArea(final String text) {
	Runnable doAppendJTextArea = new Runnable() {
		public void run() {
			textarea.append(text);
			textarea.setCaretPosition(textarea.getText().length());
		}
	};
	SwingUtilities.invokeLater(doAppendJTextArea);
}


////////////////////////////////////////////////////////////	

public void replaceRangeJTextArea(final String text, final int start,final int end) {
    Runnable doReplaceRangeJTextArea = new Runnable() {
        public void run() {
        	textarea.replaceRange(text,start,end);
        	
        }
    };

    SwingUtilities.invokeLater(doReplaceRangeJTextArea);
}
 
////////////////////////////////////////////////////////////

public  void clearJTextArea() {
    Runnable doClearJTextArea = new Runnable() {
        public void run() {
            textarea.setText("");
            textarea.setCaretPosition(0);
        }
    };

    SwingUtilities.invokeLater(doClearJTextArea);
}


//////////////////////////////////////////////////////////////////

public void ReadCommand() {
	
	int j=_promptCursor();
	newtext = textarea.getText().substring(j);
	
	String [] CMD=newtext.split(" ");
	Vector<Element> vector = interpreter.Parsing(CMD, element, 0);
	
	try {
		executeur.execute(vector, CMD);
	} catch ( IOException e) {
		e.printStackTrace();
	} catch (JDOMException e) {
		e.printStackTrace();
	}
    appendJTextArea("\n"+mainprompt);
    History._updateHistory(newtext);
}

//////////////////////////////////////////////////////////////////


public  void  keyPressed(KeyEvent keyEvent) {
    switch  (keyEvent.getKeyCode()) {
     case  KeyEvent.VK_ENTER:
        ReadCommand();
        keyEvent.consume();
    break;   
       
    
////////////////////////////////////////////////////////////////
       
    case KeyEvent.VK_BACK_SPACE:
    	int v=_promptCursor();
		newtext = textarea.getText().substring(v);

        if (textarea.getCaretPosition() <=_promptCursor()) {
        	
           keyEvent.consume();
        }
        
    break;
        
////////////////////////////////////////////////////////////	
        
    case KeyEvent.VK_LEFT:
        if (textarea.getCaretPosition() ==_promptCursor()) {
            keyEvent.consume();
        }
        
    break;
    
//////////////////////////////////////////////////////////////
        
    case KeyEvent.VK_UP:
    	 keyEvent.consume();
    	int i= _promptCursor();
		newtext = textarea.getText().substring(i);

    	 replaceRangeJTextArea(History._lastCommand(),_promptCursor(), textarea.getText() .length());
    	 
    break;
        
        
///////////////////////////////////////////////////////////////        

    case KeyEvent.VK_DOWN:
    	keyEvent.consume();
    	int j= _promptCursor();
		newtext = textarea.getText().substring(j);

    	replaceRangeJTextArea(History._nextCommand(newtext ),_promptCursor(), textarea.getText().length());

    break;
      
     
///////////////////////////////////////////////////////////////	       
        
    case KeyEvent.VK_TAB:
           keyEvent.consume(); 
           int t=_promptCursor();
   		
   		newtext = textarea.getText().substring(t);
   		String [] CMD=newtext.split(" ");
           try {
			String CompleteCMD=autotextcomplele.autoComplete(CMD, element);
			appendJTextArea("\n"+mainprompt+CompleteCMD);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
           
    break;
    default:}
    if ((keyEvent.getKeyCode() == KeyEvent.VK_Z) && (keyEvent.isControlDown())) {
    	appendJTextArea("\n"+"% SYS-5-CONFIG_I: Configured from console by console"+"\n"+Myrouter.Nom+"#");
        element=Compile("xml/privileiged.xml");
    
    }}

/////////////////////////////////////////////////////////////////////


public void keyTyped(KeyEvent keyEvent) {
	int j=_promptCursor();

	newtext = textarea.getText().substring(j);

	   if (keyEvent.getKeyChar()=='?'){
	    	 if ((textarea.getCaretPosition()==j)) {
	    	        
	    		try {
	    			
					
					String afficherhelp=help.help1(element);
					appendJTextArea("\n"+"Exec commands:"+"\n"+" "+afficherhelp);
					appendJTextArea(mainprompt);

				} catch (JDOMException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	   
	    	}}
}
 
////////////////////////////////////////////////////////////////

public  Element Compile( String	fichierxml){
	SAXBuilder builder = new SAXBuilder();
	builder.setIgnoringElementContentWhitespace(true);
	Document document = null;
	   
		try {
			 document = builder.build(new File(fichierxml));
			   
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	  Element rootelement = document.getRootElement();
	  
return rootelement;
}

//////////////////////////////////////////////////////////////

  public void actionPerformed(ActionEvent arg0) {
	
  }

/////////////////////////////////////////////////////////////
  
public void keyReleased(KeyEvent arg0) {
	
}
/////////////////////////////////////////////////////////////

public void setCurrentinterface(String nomInterface) throws InvalidIPAdressException, NoNetMaskException {
	Currentinterface =getinterfaceByname(Myrouter.getInterfaceRouter(),nomInterface);
		

}
public  Ethernet getCurrentInterface() {
	
	return Currentinterface ;
}

////////////////////////////////////////////////////////////

private Ethernet getinterfaceByname(Vector<Ethernet> vector, String nomInterface) {/////////////////
	for(int i=0;i<vector.size();i++){
		if(vector.elementAt(i).getName().equals(nomInterface)){
			return vector.elementAt(i);}
		}
	return null;
	}

}

