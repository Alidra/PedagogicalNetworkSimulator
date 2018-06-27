

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



public class Display extends JPanel{
	
	
	protected Router Myrouter ;

	protected JTextArea textarea;
 
public Display(Router Myrouter) {
	
this.Myrouter=Myrouter;

setLayout(new BorderLayout());
textarea = new JTextArea();
textarea.setForeground(Color.BLACK);
textarea.setBackground(Color.WHITE);
textarea.setCaretColor(Color.BLACK);
textarea.setFont(new Font("Verdana",0,14));
textarea.setEditable(false);
textarea.setLineWrap(true);
textarea.setBorder( BorderFactory.createLineBorder(Color.black, 4));
setBounds(400, 400, 300, 300);
JButton Bclear=new JButton("Clear");
Bclear.setBackground(Color.gray);
Bclear.setBounds(520,410,100,30);


JPanel panb = new JPanel(new FlowLayout(2));
JPanel panb2 = new JPanel(new FlowLayout(1));
panb.setBackground(Color.WHITE);
panb2.setBackground(Color.WHITE);
textarea.setWrapStyleWord(true);
JScrollPane scrollPane = new JScrollPane(textarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
scrollPane.setVisible(true);
add(scrollPane);
JLabel l = new JLabel ();
l.setText("  Traitement  ");

l.setBackground(Color.WHITE);
l.setFont(new Font("Verdana",1,24));
panb2.add(l);
panb.add(Bclear);
add(panb,BorderLayout.SOUTH);	
add(panb2,BorderLayout.NORTH);	
Bclear.addActionListener(new ActionListener () {
	 
	public void actionPerformed(ActionEvent event) {
	textarea.setText("");
	
	}});


add(textarea);
textarea.add(Bclear);
setVisible(true);}


public void appendJTextArea(final String text) {
	Runnable doAppendJTextArea = new Runnable() {
		public void run() {
			textarea.append(text);
			
		}
	};
	SwingUtilities.invokeLater(doAppendJTextArea);
}

}