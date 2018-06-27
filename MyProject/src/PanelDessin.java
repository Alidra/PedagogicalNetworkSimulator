import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelDessin extends JPanel {
	GraphicalUserInterface myGUI;
	Log log;
	private BufferedImage image;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public PanelDessin(GraphicalUserInterface myGUI) {
  super ();
   this.myGUI= myGUI;
		try {
			image = ImageIO.read(new File("image/gGeoViewCity.png"));
		} catch (IOException ex) {
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);

		for (int i = 0; i <= this.myGUI.totalNumberOfRouter - 1; i++) {
			for (int j = i + 1; j <= this.myGUI.totalNumberOfRouter; j++) {
				if (this.myGUI.linkBetweenRouters [i][j] >= 0) {
					int x1 = this.myGUI.listofRouter[i].getX() + 16;
					int y1 = this.myGUI.listofRouter[i].getY() + 16;
					int x2 = this.myGUI.listofRouter[j].getX() + 16;
					int y2 = this.myGUI.listofRouter[j].getY() + 16;
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}

	public void ColorerWay(int[] N) {
		Graphics2D g = (Graphics2D) this.getGraphics();
		g.setColor(Color.RED);
		for (int i = 0; i < N.length - 1; i++) {
			int x1 = this.myGUI.listofRouter[N[i]].getX() + 16;
			int y1 = this.myGUI.listofRouter[N[i]].getY() + 16;
			int x2 = this.myGUI.listofRouter[N[i + 1]].getX() + 16;
			int y2 = this.myGUI.listofRouter[N[i + 1]].getY() + 16;
			g.drawLine(x2, y2, x1, y1);
			this.myGUI.listofRouter[N[i]].repaint();
			this.myGUI.listofRouter[N[i + 1]].repaint();
			g.drawImage(image, 0, 0, null);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
	}
}
