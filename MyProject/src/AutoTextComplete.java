
import java.io.IOException;
import java.util.Vector;

import org.jdom.Element;
import org.jdom.JDOMException;

public class AutoTextComplete {
	private Cli Mycli;
	Interpreter interpreter = new Interpreter(Mycli);

	public AutoTextComplete(Cli Mycli) {
		super();
		this.Mycli = Mycli;
	}

	public String autoComplete(String[] CMD, Element element)
			throws JDOMException, IOException {
		Vector<Element> vector = new Vector<Element>();
		vector = this.interpreter.Parsing(CMD, element, 0);

		if (vector.size() == 1) {
			return complete(vector.get(0), CMD);
		} else {
			String completeCMD = "";
			for (int i = 0; i < CMD.length; i++)

				completeCMD = completeCMD + CMD[i];
			return completeCMD;
		}
	}


	private String complete(Element element, String[] CMD2) {
		Vector<String> CMD = new Vector<String>();
		String completeCMD = "";

		int i = CMD2.length - 1;
		do {
			if (element.getName().startsWith("adr")) {
				CMD.addElement(CMD2[i]);
			} else
				CMD.addElement(element.getName());

			element = element.getParentElement();
			i--;
		} while (!element.getName().equalsIgnoreCase("command"));

		for (int j = CMD.size() - 1; j >= 0; j--) {
			completeCMD = completeCMD + CMD.elementAt(j) + " ";
		}
		return completeCMD;

	}
}
