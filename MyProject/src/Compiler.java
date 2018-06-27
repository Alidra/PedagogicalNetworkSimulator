import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
// A la place des system.out.println, mettez le code qui cree la commande avec les differents champs fournie par le fichier xml
// Prenez soin de mettre chaque champ dans une variable locale pour plus de lisibilite

public class Compiler {

	public Compiler() {
		// TODO Auto-generated constructor stub
	}
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
		  List Commands = rootelement.getChildren();
		  for(int i = 0;i<Commands.size();i++){
		  System.out.println(((Element) Commands.get(i)).getChild("name").getValue());
		  System.out.println(((Element) Commands.get(i)).getChild("imageAccessPath").getValue());
		  System.out.println((((Element) Commands.get(i)).getChild("shortcutKey").getValue()).charAt(0));
		  System.out.println(Integer.parseInt(((Element) Commands.get(i)).getChild("ShortCutCombinationKey").getValue()));
		  System.out.println(Integer.parseInt(((Element) Commands.get(i)).getChild("ShortcutControlKey").getValue()));
		  
		  }
	return rootelement;
	}

	public static void main(String[] args) {
		Compiler fer= new Compiler();
		fer.Compile("image/commandsDescriptorXML.xml");

	}

}
