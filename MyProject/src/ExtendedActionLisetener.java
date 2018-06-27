import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ExtendedActionLisetener<Caller> implements ActionListener {
	protected Caller project;
	protected Caller graphicalUserInterface; 
	protected String aMethodName;
	public ExtendedActionLisetener(Caller project , String aMethodName) {
		 
		 this.project=project;
		  this.aMethodName =aMethodName;
	}		 

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final Class classOfTheMethod = project.getClass();			
			 Method aMethod;
			try {
				aMethod = classOfTheMethod.getMethod(aMethodName, null);
				aMethod.invoke(project );
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} 		 
	}
	}	
 	
	 

 
