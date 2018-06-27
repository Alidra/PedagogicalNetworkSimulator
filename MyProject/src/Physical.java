import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

public class Physical extends JPanel {
	static ImageIcon routeurci = new ImageIcon("image/cic.jpg");
	JTextArea area;

	public Physical() {
		this.setLayout(new FlowLayout(1, 1, 1));
		this.setPreferredSize(new Dimension(600, 600));
		this.setBackground(Color.WHITE);
			area = new JTextArea();
		area.setLocation(300, 300);
		area.setPreferredSize(new Dimension(570, 200));
		area.setLocation(350, 340);
		area.setForeground(Color.black);
		area.setEditable(false);
		area.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.add(area, SpringLayout.EAST);
		createPanelPhisical();
	}
public void createPanelPhisical(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.black);
		JLabel label = new JLabel();
		label.setIcon(routeurci);
		JScrollPane scrollPane = new JScrollPane(panel,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel.setSize(500, 500);

		this.add(scrollPane);
		ActionListener anActionListener = null;
		JButton Button1 = createButton(label, panel, Point(294, 87),
				anActionListener);
		Button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() == null) {
					area.append("hello");
				} else
					area.setText(null);
				area.append("hello");
			}
		});
		JButton Button3 = createButton(label, panel, Point(332, 61),
				anActionListener);
		Button1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() == null) {
					area.append("hello");
				}

				else
					area.setText(null);
				area.append("Port Console : fournit un accès au routeur" + "\n"
						+ "à travers une ligne RS-232 " + "\n"
						+ "'ligne série asynchrone'" + "\n"
						+ "pour la gestion et la configuration.");
				area.setFont(new Font("Verdana", 3, 18));

			}
		});

		JButton Button4 = createButton(label, panel, Point(294, 87),
				anActionListener);
		Button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() == null) {
					area.append("hello");
				} else
					area.setText(null);
				area.append("Port Ethernet :est un standard de transmission de données"
						+ "\n"
						+ "pour réseau local basé sur le principe d'un Toutes"
						+ "\n"
						+ "les machines du réseau Ethernet sont connectées"
						+ "\n"
						+ "à une même ligne de communication  constituée"
						+ "\n" + "de câbles cylindriques");
				area.setFont(new Font("Verdana", 3, 18));

			}
		});
		JButton Button5 = createButton(label, panel, Point(292, 61),
				anActionListener);
		Button5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() != null) {

					area.setText(null);
					area.append("Port FastEthernet : utilisé avec fonction d'auto-détection "
							+ "\n"
							+ "permettant de déterminer le débit maximum"
							+ "\n" + " des périphériques connectés");
					area.setFont(new Font("Verdana", 3, 18));

				}
			}
		});
		JButton Button6 = createButton(label, panel, Point(332, 86),
				anActionListener);
		Button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() != null) {

					area.setText(null);
					area.append("port auxiliaire : qui autorisera un communication "
							+ "\n"
							+ "physique serielle vers"
							+ "\n"
							+ " une station de travail sur laquelle "
							+ "\n"
							+ "est insalle un logiciel de communication ");
					area.setFont(new Font("Verdana", 3, 18));
				}
			}
		});

		JButton Button7 = createButton(label, panel, Point(420, 65),
				anActionListener);
		Button7.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() != null) {

					area.setText(null);
					area.append("Power Switch ON/OFF : Une fois la représentation ou "
							+ "\n"
							+ "l'exposé terminé(e), glisser le commutateur"
							+ "\n"
							+ " MARCHE/ARRÊT sur OFF (arrêt) pour"
							+ "\n" + " préserver les piles.");
					area.setFont(new Font("Verdana", 3, 18));
				}
			}
		});

		JButton Button8 = createButton(label, panel, Point(500, 86),
				anActionListener);
		Button8.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (area.getText() != null) {

					area.setText(null);
					area.append("Power input : Routeur Wifi fonctionnant sur batterie "
							+ "\n"
							+ " pouvant se connecter en RJ45  ou à une clé 3G ..."
							+ "\n"
							+ " Routeur 3G Wifi ... "
							+ "\n"
							+ "DC Power Input 5V 1.5A (12V CAR USE)");
					area.setFont(new Font("Verdana", 3, 18));
				}

			}
		});
		
}
	
	private JButton createButton(JLabel labelButton, JPanel ButtonPanel,
			Point PointLocation, ActionListener anActionListener) {
		JButton aButton = new JButton();
		aButton.setBackground(Color.black);
		aButton.setBounds(472, 278, 10, 10);
		aButton.setLocation(PointLocation);
		labelButton.add(aButton);
		aButton.addActionListener(anActionListener);
		ButtonPanel.add(labelButton);
		return aButton;
	}
	private Point Point(int i, int j) {
		Point PointLocation = new Point(i, j);
		return PointLocation;
	}

	
}