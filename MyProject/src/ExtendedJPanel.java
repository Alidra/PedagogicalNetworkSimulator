import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


public class ExtendedJPanel extends JPanel{
	protected String anImageIcon;//Should initialize this one
	protected GraphicalUserInterface myGUI;

	public void selectBagkround() {
		BufferedImage image = null;
		try {
			image = ImageIO
					.read(new File( anImageIcon));
		} catch (final IOException e) {
			e.printStackTrace();
		}
 		myGUI.workSpace.setImage(image);
	}
	

	public ExtendedJPanel(GraphicalUserInterface myGUI) {
		super();
		this.myGUI = myGUI;
	}
}
