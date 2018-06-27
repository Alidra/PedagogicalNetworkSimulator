


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

import org.jdom.Element;


public class Interpreter {
	private Cli Mycli;

	public Interpreter(Cli Mycli) {
		super();
		  this.Mycli = Mycli; 

	}
	/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

	public Vector<Element> Parsing(String []CMD,Element element ,int index){
	     Vector<Element> v = new Vector<Element>();//////////////////////////////////////////////////////////////////
	     
		     List<Element> child= element.getChildren(); 
	            int i=0;
	     while (i<child.size()) {
		     if (comparTo  (CMD[index] , ((child.get(i))))){
				  if(index==CMD.length-1){
					 v.addElement(child.get(i));
		          }else{
			         v.addAll(Parsing( CMD, child.get(i) , index+1));
			   	 }
			  }
		    i++;
				}
	 return v;
	 }


	//*/*//**/*//*//*/*/*/*/*//*/*/*/*//*/*/*/**/*//*/*/*/**//*/*//*//*/*//*/*/*/*/*//*/*/*/*//*/*/*/**


	private  boolean comparTo(String Child, Element ChildElement) {
	if(isGeneric(ChildElement.getName())){
		Class<Binaries> classe=Binaries.class;
	    Method Type=null;
	   try{
		   Type=classe.getMethod((ChildElement.getAttributeValue("type")),String.class);
	 } catch (SecurityException e){
	 } catch (NoSuchMethodException e){ }
	   try{ 
		Binaries obj = new Binaries(Mycli);
	  return (Boolean)Type.invoke(obj,Child);
	   
	} catch (IllegalArgumentException e){
	} catch (IllegalAccessException e){
	} catch (InvocationTargetException e){ }

	}
	 
	   return CompareCaracterByCaracter(Child,ChildElement.getName());
	  
	}   

	/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

	private boolean isGeneric(String name) {
		
	return name.startsWith("adr");
	   }

	/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/


	private boolean CompareCaracterByCaracter(String Child, String ChildElement) {
		char[] child = Child.toCharArray();
		char[] childElement = ChildElement.toCharArray();
		    	for(int i=0; i<child.length;i++) {
		    		if(child[i] !=  childElement[i])
		    			
		    			return false;
		    	}
		    	return true;
	}
}
