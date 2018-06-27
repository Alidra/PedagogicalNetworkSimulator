

public class Network {
 
	GraphicalUserInterface  myGUI;
	public Network(GraphicalUserInterface myGUI) {
		 this.myGUI=myGUI;
	}

	public  void intialiseTheNetwork(   ){		
	  this.myGUI.listofRouter   = new DragOption[16];
	 this.myGUI.linkBetweenRouters   = new float[16][16];
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
			this.myGUI.	linkBetweenRouters[i][j] = -1;
			}
		}		
}
	}
