
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Router extends MouseAdapter implements Runnable {
	private InternetProtocol internetProtocol;
	Display display;
	private RoutingTable routingTable;
	private int id;
	public String Nom = "Router" + id;
	private Cli area;// winn tektob les command
	Log log;
	private Vector<Ethernet> interfaceRouter = new Vector<Ethernet>();
	private Vector<Router> Neighbours; // vecteur ta3 les routeur
	Ethernet InterfacesEther[] = new Ethernet[2];
	static ImageIcon routeurci = new ImageIcon("image/Router1.jpg");
	private JButton iconerouteur = new JButton(" ");
	private JScrollPane scrollPane1;
	private JScrollPane scrol;
	JScrollPane scrolpane3;
	public int nbrVoisin = 0;
	private static int nombreinterfaces = 6;
	JTextArea textarea;
	Physical cisco; // roteur interface garphiqye ta3o
	JScrollPane scrolpane2;
	ArpCache arpCache;

	public Router(String nom  , int id)
			throws InvalidIPAdressException, NoNetMaskException {
		textarea = new JTextArea();
		scrol = new JScrollPane(textarea);
		internetProtocol = new InternetProtocol( this);
		routingTable = new RoutingTable(internetProtocol);
		internetProtocol.routingTable = routingTable;
		arpCache = new ArpCache();
		Nom = nom;
		this.area = new Cli(this);
		scrollPane1 = new JScrollPane(area);
		this.display = new Display(this);
		scrolpane3 = new JScrollPane(display);
		cisco = new Physical();
		scrolpane2 = new JScrollPane(cisco);
		this.setId(id);
		 
		for (int i = 0; i < nombreinterfaces; i++) {
			Ethernet fer = new Ethernet(this);
			fer.setName("FastEthernet" + i);
			interfaceRouter.add(fer);

		}
	}
	int getNbrInterfaces() {
		return 4;
	}
	public   int getNombreinterfaces() {
		return nombreinterfaces;
	}

	public void setNombreinterfaces(int nombreinterfaces) {
		this.nombreinterfaces = nombreinterfaces;
	}

	public int getId() {
		return id;
	}

	public int getFreeInterface() { 
		for (int i = 0; i < nombreinterfaces; i++)
			if (!interfaceRouter.get(i).isPhysicalyconnected())
				return i;
		return -1;
	}

	public Vector<Router> getNeighbours() {
		Vector<Router> Voisins = new Vector<Router>();
		for (int i = 0; i <= getInterfaceRouter().size() - 1; i++) {
			for (int j = 0; j <= Voisins.size() - 1; j++) {
				Voisins.add(interfaceRouter.get(i).getNextHop());
			}
		}
		return Neighbours;
	}

	public void mouseReleased(MouseEvent e) {
		int buttonDown = e.getButton();
		if (buttonDown == MouseEvent.BUTTON3) {
			JFrame f = new JFrame("");
			f.setSize(670, 590);
			f.setLocation(100, 50);
			JPanel pannel = new JPanel();
			JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
			scrollPane1.setPreferredSize(new Dimension(650, 453));
			scrolpane2.setPreferredSize(new Dimension(400, 500));
			scrolpane3.setPreferredSize(new Dimension(450, 500));
			onglets.addTab("physical", (scrolpane2));
			onglets.addTab("CLI", (scrollPane1));
			onglets.addTab("Display", (scrolpane3));

			pannel.add(onglets);
			onglets.setOpaque(true);
			f.getContentPane().add(pannel);
			f.setVisible(true);
		}
	}

	public void run() {
		while (true) {
		}
	}

	public Display getDisplay() {
		return display;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoutingTable getRoutingTable() {
		return routingTable;
	}

	public Vector<Ethernet> getInterfaceRouter() {
		return interfaceRouter;
	}

	public void setInterfaceRouter(Vector<Ethernet> interfaceRouter) {
		this.interfaceRouter = interfaceRouter;
	}

	public InternetProtocol getInternetProtocol() {
		return internetProtocol;
	}

	public void setInternetProtocol(InternetProtocol internetProtocol) {
		this.internetProtocol = internetProtocol;
	}

	public ArpCache getMyArpCache() {
		return arpCache;
	}

}