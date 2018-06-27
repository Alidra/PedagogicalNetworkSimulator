

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

import org.jdom.Element;
import org.jdom.JDOMException;

public class Executer {


private Cli Mycli;

public Executer(Cli Mycli){
	this.Mycli = Mycli;
}


/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

public  void execute(Vector<Element> vector, String[] CMD)throws JDOMException, IOException{
	 
         if(vector.size()==1){
        	 if(ismethodfind(vector.get(0))){/////////////////////////////////////
                  String method_name = isChildExecutable(vector.get(0));
                  invoke(method_name,CMD);
        	}else{
      	      Mycli.appendJTextArea("\n"+"% Incomplete command.");
      	      
        	}}
         else{
     	      Mycli.appendJTextArea("\n"+"% Unknown command.");

         }
        }


/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

private boolean ismethodfind(Element element) {
	if(element.getChild("cr") != null){
	return true;
	
	 }else
	 return false;
	
}

/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

private  String isChildExecutable(Element element) {

	if(element.getChild("cr") != null){
		return element.getChild("cr").getAttributeValue("method");
	}else
		return null;}



/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

public 	void invoke(String methode_name,String[] param){
     Class<Binaries> classe=Binaries.class;
          Method method=null;
        try{
    	   method=classe.getMethod(methode_name,String.class);
      } catch (SecurityException e){
      } catch (NoSuchMethodException e){ }
        
       try{
    	   String newparam = concatenat(param);
    	   Binaries obj = new Binaries(Mycli);
          Object routeur=method.invoke(obj,newparam);
     } catch (IllegalArgumentException e){
     } catch (IllegalAccessException e){
     } catch (InvocationTargetException e){ }
   }


/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/

private String concatenat(String[] param) {
	String newparam = param[0];
	for(int i = 1; i<param.length;i++) 
		newparam = newparam +" "+ param[i];
	return newparam;

}}
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/
