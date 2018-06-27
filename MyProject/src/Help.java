


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JTextArea;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/


    public class Help {
	  Cli Mycli;
	
	Interpreter interpreter=new Interpreter(Mycli);
	 
	  public Help(Cli Mycli){
		  this.Mycli = Mycli;
		  
		  }
	  

	
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/
	
	  public   String help1(Element element) throws JDOMException, IOException{
			Vector<Element> cmd =new Vector<Element>();
			String HELP="";
			List Fils=element.getChildren();
			
			int i=0;

		    while (i<Fils.size()) {
               cmd.addElement((Element) Fils.get(i));
				i++;
			}

		    for(int j=0;j<cmd.size();j++){
		    	
		     HELP= HELP+ cmd.elementAt(j).getName()+"                    "+((Element) element.getChildren().get(j)).getTextTrim()+"\n"+" ";
		 
		    }
		return HELP ;
			}

/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/
	  
	 
	public   String help2(String[] CMD,Element element) throws JDOMException, IOException{
		  
			Vector<Element> vector =new Vector<Element>();
				vector= this.interpreter.Parsing(CMD, element, 0);
		         if(vector.size()==1){

		        	return aide(vector.get(0));
		        	 
		         }
		        	 
		         else{
		        	 String CMDhelp = "";
		        	 for(int i=0;i<CMD.length;i++)

		        		 CMDhelp=CMDhelp+CMD[i];
					return CMDhelp;

		        }
		}
	
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

		private String aide(Element element) {
			String HELP="";
			Vector<Element> cmd = new Vector<Element>();
			List Fils=element.getChildren();
			int i=0;

		    while (i<Fils.size()) {
		   
		      cmd.addElement(  (Element) Fils.get(i));
				i++;
			}
		    for(int j=0;j<cmd.size();j++){
		    	
		  HELP=HELP+cmd.elementAt(j).getName()+"       "+((Element) element.getChildren().get(j)).getTextTrim()+"\n"+" ";;

		    }
		   return HELP;
			
		}}
    
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

		