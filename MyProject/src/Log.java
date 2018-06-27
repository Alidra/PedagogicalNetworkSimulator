
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTextArea;

public class Log {
	
	
	String ligne ;
	String chaine="";
	String file="strapTheLineConnector:/log/log.txt";
	String str ;
	JTextArea area;
	public Log(){
		
	}

	public void WriteinFile(String s,JTextArea a) {
		try {
		FileWriter fw = new FileWriter (file,true);
		BufferedWriter bw = new BufferedWriter (fw);
		PrintWriter fichierSortie = new PrintWriter (bw); 
			fichierSortie.println (s); 
			
			fichierSortie.close();
			ReadInnfile( a);
			}
	
	
	catch (Exception e){
		System.out.println(e.toString());}}
	
	public void ReadInnfile(JTextArea a){
		String chaine="";
		
		
		
		try{
		InputStream ips=new FileInputStream(file); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			
			
			while ((ligne=br.readLine())!=null){
			 str =ligne;
			 }
				
			
String strk=str;

a.append(strk+"\n");

	br.close(); 
		}		
		catch (Exception ex){
		}
	}
	   Vector listeners = new Vector(); 
	   public synchronized void addLoggEvent(LogEvent  lo) {
		     Vector v = (Vector)listeners.clone();
		        v.addElement(lo);
		        listeners = v; // Atomic assignmen
	   }
	   public synchronized void removeLoggEvent(LogEvent  lo) {
	        Vector v = (Vector)listeners.clone();
	        v.removeElement(lo);
	        listeners = v; // Atomic assignment
	    }
	     protected void notifyloggEvent() {
	        Vector l;
	        EventObject e = new EventObject(this);
	
	        l = listeners; // Atomic assignment
	        for (int i = 0; i < l.size(); i++) { // deliver it!
	         ((LogEvent )l.elementAt(i)).loggListener(e);
	        }
	    }	
}
