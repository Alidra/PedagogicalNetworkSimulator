


import java.util.Vector;

import javax.swing.JTextArea;


public class History {
	
	private int _historyCursor;
	private int historyLength;
	private Vector<String> _historyCommands;
	JTextArea textarea;
	
	
	public History(int _historyCursor ,int historyLength ,Vector<String> _historyCommands,JTextArea textarea) {
		
		this._historyCommands=_historyCommands;
		this._historyCursor= _historyCursor;
		this.historyLength=historyLength;
		this.textarea=textarea;
		
		
		}
 
	/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/
	
	
	public void _updateHistory(String command) {
		       _historyCursor = 0;

	    if (_historyCommands.size() == historyLength) {
	        _historyCommands.removeElementAt(0);
	           }
	            _historyCommands.addElement(command);
	     }
	
	
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/*/

public  String _lastCommand(){
	String text;
    if (_historyCursor == _historyCommands.size()) {
        return "";
    } else {
        _historyCursor++;
        text = (String) _historyCommands.elementAt(_historyCommands.size() - _historyCursor);
    }
	return text;
       
}

/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/*/

 public String _nextCommand(String commande ) {
	 if (_historyCursor == 0) {
        	return commande = " ";
        } else {
        	
            _historyCursor--;
            commande =(String)_historyCommands.elementAt(_historyCommands.size()- _historyCursor-1);
        }
	return commande;
           
    }
	
        }
	
/*//*///*///*//*/*//*//*//*//*//*///*//*/*/*/*/*/*//*//*////*///*/*//*/*/*/*/*/*/*/*/*/*/*/
	


