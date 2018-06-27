import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.management.Attribute;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
 
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Project {	 
	protected GraphicalUserInterface  myGUI;
	 private String CurrentFile = "";	 
	 boolean isSaved=true;
	public Project(GraphicalUserInterface myGUI ) {
		this.myGUI=myGUI;	  		 
	} 

	public void Load() {
		  
		JFileChooser OpenIt = new JFileChooser();
		OpenIt.setFileFilter(new FilterXML());
		boolean canOpen = NewProject () ;
		if (canOpen) {
			int reponse = OpenIt.showOpenDialog(OpenIt);
			if (reponse == 0) {

				CurrentFile = OpenIt.getCurrentDirectory() + "\\"
						+ OpenIt.getSelectedFile().getName();
				 

				// declaration
				org.jdom.Document document = null;
				Element racine;
				SAXBuilder sxb = new SAXBuilder();

				try {
					document = sxb.build(new File(CurrentFile));
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				racine = document.getRootElement();

				java.util.List listPc = racine.getChildren("pc");

				Iterator i = listPc.iterator();
				while (i.hasNext()) {
					Element courant = (Element) i.next();
					String s =this.myGUI. sendInformation.getText();
					this.myGUI.sendInformation.setBackground(new Color(225, 248, 253));
					this.myGUI.sendInformation.setText(s);
					this.myGUI.totalNumberOfRouter = this.myGUI.totalNumberOfRouter + 1;
					this.myGUI.listofRouter[this.myGUI.totalNumberOfRouter] = new DragOption("image/pc1.jpg",this.myGUI. totalNumberOfRouter, myGUI);

					this.myGUI.listofRouter[this.myGUI.totalNumberOfRouter].addMouseListener();
					this.myGUI.listofRouter[this.myGUI.totalNumberOfRouter].addMouseMotionListener();
					this.myGUI.workSpace .add(this.myGUI.listofRouter[this.myGUI.totalNumberOfRouter] );

					int xx = Integer.parseInt(courant.getAttributeValue("x"));

					int yy = Integer.parseInt(courant.getAttributeValue("y"));

					this.myGUI.listofRouter[this.myGUI.totalNumberOfRouter].setBounds(xx, yy, 55, 40);
					java.util.List listMat = courant.getChildren("linkBetweenRouters");
					Iterator j = listMat.iterator();
					while (j.hasNext()) {
						Element currentElm = (Element) j.next();
						int J = Integer.parseInt(currentElm
								.getAttributeValue("id"));
						this.myGUI.linkBetweenRouters[this.myGUI.totalNumberOfRouter][J] = Float
								.parseFloat(currentElm.getText());
						this.myGUI.linkBetweenRouters[J][this.myGUI.totalNumberOfRouter] = Float
								.parseFloat(currentElm.getText());
					}

				}

				
				this.myGUI.workSpace .repaint();

			}
		}
	}
	public void exit (){
		this.myGUI .setVisible(false) ;
			
		}
	public void help(){
		JOptionPane.showMessageDialog(this.myGUI , "lire le fichier readme.text", "About",
	            JOptionPane.INFORMATION_MESSAGE);	
	}

	public boolean NewProject(   ) {
		 int reponse;
         
        boolean isSaved=false ;
		if(isSaved==false){
         	reponse=JOptionPane.showConfirmDialog(this.myGUI,"Voulez vous enregistrer le projet ? ",
                                 "Enregistrer le projet",1);
         	if(reponse==0){
           	Save();
           	effacer();
         	}else if(reponse==1){
				effacer();
   	    }else{
   	    	return false;
   	    }
         
        }
        else{
        	effacer();
        }
        return true;
	}

	public boolean Reboot() { 

		effacer();
		return true;
	}
	public void Save() {
		JFileChooser SaveIt = new JFileChooser();
		SaveIt.setFileFilter(new FilterXML());

		if (CurrentFile == "") {
			int reponse = SaveIt.showSaveDialog(SaveIt);
			if (reponse == 0) {
				CurrentFile = SaveIt.getCurrentDirectory() + "\\"
						+ SaveIt.getSelectedFile().getName() + ".xml";
				enregistrer();
			}
		} else {
			enregistrer();
		}
	}
	public void ecranView(){
		JFrame miniFrameToAfficher=new JFrame();				 
		JTextArea area=new JTextArea();
		area.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        area.setPreferredSize(new Dimension(300,100));
			JPanel PACTIV_RES = new JPanel();
			PACTIV_RES.setBorder(new TitledBorder("OBJETS ACTIFS"));
			PACTIV_RES.setBackground(Color.LIGHT_GRAY);
			PACTIV_RES.add(area);				
			miniFrameToAfficher.add(PACTIV_RES);
			miniFrameToAfficher.setSize(350, 200);
			miniFrameToAfficher.setResizable(false);
			miniFrameToAfficher.setVisible(true);
	}

	 public void enregistrer(){
       	isSaved=true;
       	Element racine=new Element("network");
         	Document document=new Document(racine);
         	for(int i=0;i<=this.myGUI.totalNumberOfRouter;i++){
         		Element pcE=new Element("pc");
         		Attribute id=new Attribute("id",i+"");
         		Attribute x=new Attribute("x",this.myGUI.listofRouter [i].getX()+"");
         		Attribute y=new Attribute("y",this.myGUI.listofRouter[i].getY()+"");
         		
         		 
         		for(int j=i+1;j<=this.myGUI.totalNumberOfRouter;j++){
	          		Element Mat=new Element("linkBetweenRouters");
         			Attribute idd=new Attribute("id",j+"");
         		 
         			Mat.setText(this.myGUI.linkBetweenRouters[i][j]+"");
         			pcE.addContent(Mat);
         		}
         	}
         
         	try{
         		XMLOutputter sortie=new XMLOutputter(Format.getPrettyFormat());
         		sortie.output(document,new FileOutputStream(CurrentFile));
         	}catch(IOException e){
         	}
       }
       
	public void effacer() {
		 
		 
		CurrentFile = "";
		this.myGUI.listofRouter = new DragOption[100];
		  
		this.myGUI.linkBetweenRouters  = new float[100][100];
		for (int i = 0; i < 99; i++)
			for (int j = 0; j < 99; j++)
				this.myGUI.linkBetweenRouters[i][j] = -1;
		 this.myGUI.aPreviousAction = 0;
		 this.myGUI. aRouterSelected = false;
		 this.myGUI.totalNumberOfRouter = -1;
		this.myGUI.workSpace.removeAll();
		myGUI.sendInformation.setText(null);
		this.myGUI.workSpace  .repaint();

	}
	

		
	}


 class FilterXML extends FileFilter {

	@Override
	public boolean accept(File arg0) {
		
		return false;
	}

	@Override
	public String getDescription() {
		
		return null;
	}

}



