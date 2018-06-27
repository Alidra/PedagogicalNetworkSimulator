import java.awt.BorderLayout;
import java.awt.Color; 
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;  
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
 
public class GraphicalUserInterface extends JFrame implements MouseListener, MouseMotionListener {
	
	 
	private final byte NO_PREVIEUS_ACTION = 0;
	private final boolean NO_ROUTER_SELECTED = false;
	private final int MAX_OF_ROUTERS  =20;
    private final int MAX_OF_LINK =20;
	protected CreateProtocolRadioButton myCreateRadioButton ;
	protected NetButton activeButton;
	protected Project project;
	protected Network network;
	protected String activeProtocole;
	protected PanelDessin workSpace;
	public JToggleButton cableConnector, routerAction, pcAction, deltRouter,
			switch1;
	protected Router[] routerTable = new Router[MAX_OF_ROUTERS]; 
	public JTextArea sendInformation = new JTextArea();
	public int totalNumberOfRouter = -1;
	public DragOption[] listofRouter;
	public float[][] linkBetweenRouters = new float[MAX_OF_LINK][MAX_OF_LINK];;
	public byte aPreviousAction = NO_PREVIEUS_ACTION;
	public boolean aRouterSelected = NO_ROUTER_SELECTED;
	int strapTheLineConnector;
	  
	public JMenuBar createMenuBar() {		 
		final JMenuBar menuBar = new JMenuBar();
		final Color color = new Color(34, 133, 210);
		menuBar.setBackground(color);
//		final JMenu menuFile = createMenu("File", KeyEvent.VK_F);
//		menuBar.add(menuFile);
/*		final JMenu menuOptions = createMenu("Options", KeyEvent.VK_O);
		menuBar.add(menuOptions);
		final JMenu menuView = createMenu("View", KeyEvent.VK_V);
		menuBar.add(menuView);
		final JMenu menuTools = createMenu("Tools", KeyEvent.VK_T);
		menuBar.add(menuTools);
		final JMenu menuEdit = createMenu("Edit", KeyEvent.VK_E);
		menuBar.add(menuEdit);
		final JMenu menuHelp = createMenu("Help", KeyEvent.VK_H);
		menuBar.add(menuHelp);
*/
//		createMenuCommandAndAddToMenu("new", "image/new.png", 'n',	KeyEvent.VK_N, ActionEvent.CTRL_MASK, "NewProject", menuFile );
		createMenus("xml/MenuFileDescriptor.xml",menuBar);
		
//		createMenuCommandAndAddToMenu("Open", "image/dossier.png", 'o',KeyEvent.VK_O, ActionEvent.CTRL_MASK, "Load", menuFile );
//		createMenuCommandAndAddToMenu("Save as...", "image/z.jpg", 's',
//				KeyEvent.VK_S, ActionEvent.CTRL_MASK, "Save", menuFile );
//	  createMenuCommandAndAddToMenu("Exit", "image/exit.png", 'E',
//				KeyEvent.VK_F4, ActionEvent.ALT_MASK, "exit", menuFile 			 );
		 
/*		createMenuCommandAndAddToMenu("Preferences", "image/Preference.jpg",
				'p', KeyEvent.VK_P, ActionEvent.CTRL_MASK, "NewProject",
				menuOptions );
		createMenuCommandAndAddToMenu("User Profil", "image/userProfil.jpg" , 'u', KeyEvent.VK_U,
				ActionEvent.CTRL_MASK, "NewProject", menuOptions );
		createMenuCommandAndAddToMenu("Algorithm Setting",
				"image/algorithm.jpg", 'a', KeyEvent.VK_A,
				ActionEvent.CTRL_MASK, "NewProject", menuOptions );
		createMenuCommandAndAddToMenu("View Command Log", "image/View.jpg",
				'l', KeyEvent.VK_L, ActionEvent.CTRL_MASK, "NewProject",
				menuOptions );
		createMenuCommandAndAddToMenu("Zoom", "image/Zoom.jpg", 'z',
				KeyEvent.VK_Z, ActionEvent.CTRL_MASK, "NewProject", menuView );
		createMenuCommandAndAddToMenu("ToolBar", "image/toolbar.jpg", 't',
				KeyEvent.VK_T, ActionEvent.CTRL_MASK, "NewProject", menuView 
				 );
		createMenuCommandAndAddToMenu("DrawingPalette",
				"image/drawingPalette.jpg", 'd', KeyEvent.VK_D,ActionEvent.CTRL_MASK, "NewProject", menuTools );
		createMenuCommandAndAddToMenu("Copy", "image/z.jpg", 's',
				KeyEvent.VK_C, ActionEvent.CTRL_MASK, "Save", menuEdit );
		createMenuCommandAndAddToMenu("Past", "image/paste.jpg", 'v',
				KeyEvent.VK_V, ActionEvent.CTRL_MASK, "newProject", menuEdit 
				 );
		createMenuCommandAndAddToMenu("Undo", "image/undo.jpg", 'u',
				KeyEvent.VK_Z, ActionEvent.CTRL_MASK, "NewProject", menuEdit 
				 );
		createMenuCommandAndAddToMenu("Redo", "image/redo.jpg", 'R',

				KeyEvent.VK_Y, ActionEvent.CTRL_MASK, "NewProject", menuEdit 
				 );
	  	createMenuCommandAndAddToMenu("Help", "image/help.gif", 'h',
				KeyEvent.VK_F2, ActionEvent.CTRL_MASK, "help", menuHelp );		  
		createMenuCommandAndAddToMenu("Custom devices Dialog",
				"image/Custom.jpg", 'd', KeyEvent.VK_D, ActionEvent.CTRL_MASK,
				"NewProject", menuHelp );
*/
		return menuBar;
	}

	public  void createMenus( String	fichierxml, JMenuBar menuBar){
		List<Element> Menus = getListOfMenus(fichierxml);
		  for(int i = 0;i<Menus.size();i++){
			  String MenuName=(Menus.get(i)).getAttributeValue("MenuName");
			  int MenuKey=Integer.parseInt((Menus.get(i)).getAttributeValue("MenuKey"));			  
			  JMenu aJmenu=createMenu(MenuName, MenuKey);
				menuBar.add(aJmenu);
	
			  List<Element> Commands=(Menus.get(i)).getChildren();
		  	
			  for(int j = 0;j<Commands.size();j++){
			String commandName=((Element) Commands.get(j)).getChild("name").getValue();
			String anImage=((Element) Commands.get(j)).getChild("imageAccessPath").getValue();
			int shortcutKey=(((Element) Commands.get(j)).getChild("shortcutKey").getValue()).charAt(0);
			System.out.println(((Element) Commands.get(j)).getChild("ShortCutCombinationKey").getValue());
			int aShortcutKey=Integer.parseInt(((Element) Commands.get(j)).getChild("ShortCutCombinationKey").getValue());
			final JMenuItem aMenuItem = new JMenuItem(commandName);
			int ctrlMask=Integer.parseInt(((Element) Commands.get(j)).getChild("ShortcutControlKey").getValue());
			String aMethodName=((Element) Commands.get(j)).getChild("ShortcutControlKey").getValue();			
						
			aMenuItem.setIcon(new ImageIcon(anImage));
			aMenuItem.setMnemonic(shortcutKey);
			aMenuItem.setAccelerator(KeyStroke.getKeyStroke(aShortcutKey, ctrlMask));
			final ActionListener anActionListener = new ExtendedActionLisetener<Project>(project, aMethodName);
			aMenuItem.addActionListener(anActionListener);
			aJmenu.add(aMenuItem);
		  }
}

	}
	private List getListOfMenus(String fichierxml) {
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		Document document = null;
		   
			try {
				 document = builder.build(new File(fichierxml));
				   
			} catch (JDOMException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  Element rootelement = document.getRootElement();
		 return rootelement.getChildren();
	}

/*	public JMenuItem createMenuCommandAndAddToMenu(String commandName,
			String anImage, char accessCharacter, int aShortcutKey,
			int ctrlMask, final String aMethodName, JMenu aJmenu) {
		final JMenuItem aMenuItem = new JMenuItem(commandName);
		aMenuItem.setIcon(new ImageIcon(anImage));
		aMenuItem.setMnemonic(accessCharacter);
		aMenuItem.setAccelerator(KeyStroke.getKeyStroke(aShortcutKey, ctrlMask));
		final ActionListener anActionListener = new ExtendedActionLisetener<Project>(project, aMethodName);
		aMenuItem.addActionListener(anActionListener);
		aJmenu.add(aMenuItem);
return aMenuItem ;
	}
*/	 
	private JMenu createMenu(String menuName, int aKeyEvent) {
		final JMenu aJmenu = new JMenu(menuName);
		aJmenu.setMnemonic(aKeyEvent);
		return aJmenu;
	}	

	private void initialiseTheMainGUIWindow() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (final Exception e) {
		}
		final ImageIcon jficon = new ImageIcon("image/images.jpg");
		if (jficon.getIconHeight() > 10) {
			setIconImage(jficon.getImage());
		}
		setTitle("Cisco");
		addMouseListener(this);
		addMouseMotionListener(this);
		setJMenuBar(createMenuBar());
	 //	menuCommand. createMenuCommand(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(800, 500, 800, 500);
		final Dimension Dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) ((Dim.getWidth() - getWidth()) / 2),
				(int) ((Dim.getHeight() - getHeight()) / 2));
	}
	private void createDrawingPanel() {
		workSpace = new PanelDessin( this  );
		workSpace.setBackground(Color.white);
		workSpace.setLayout(null);
		workSpace.setBorder(new JTextField().getBorder());
		workSpace.addMouseListener(this);
		workSpace.addMouseMotionListener(this);
		final JScrollPane scrollPane = new JScrollPane(workSpace,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVisible(false);
		this.add(scrollPane);
		getContentPane().add(workSpace);
	}

	public GraphicalUserInterface() {	
		myCreateRadioButton=new CreateProtocolRadioButton(this );
		project = new Project( this );
		network  = new Network(this);
		initialiseTheMainGUIWindow();
		network.intialiseTheNetwork( ) ; 
		createDrawingPanel();
		createSouthPanel();
		creatingShortcutsProjectToolbar();
		createPreferencesToolBar();
		createNorthToolPane();
		creatingNetworkShortcutsRightToolbar( );
	   selectionProtocolsToView (    myCreateRadioButton  );
		DisplayTheWindow();
	}	
public void selectionProtocolsToView( CreateProtocolRadioButton myCreate ) {		
		final JToolBar leftToolBar = new JToolBar(SwingConstants.VERTICAL);
		leftToolBar.setBackground(new Color(225, 248, 253));
		final ButtonGroup ButtonGroupExclusif = new ButtonGroup();
		final JRadioButton aRadioProtocol = new JRadioButton();
		  JRadioButton opsf = this. myCreateRadioButton.createProtocolRadioButton(this,"opsf", true , 
				leftToolBar,   ButtonGroupExclusif);
		JRadioButton nat = this. myCreateRadioButton.createProtocolRadioButton (this,"NAT", false, leftToolBar,ButtonGroupExclusif);
	JRadioButton rip  =	this. myCreateRadioButton.createProtocolRadioButton(this, "RIP", false, leftToolBar,ButtonGroupExclusif);
	JRadioButton icmp  =this. myCreateRadioButton.createProtocolRadioButton(this, "ICMP", false, leftToolBar,ButtonGroupExclusif);  
	aRadioProtocol.add(opsf);
	aRadioProtocol.add(nat);
	aRadioProtocol.add(rip);
	aRadioProtocol.add(icmp);
	
	    getContentPane().add(leftToolBar, BorderLayout.WEST);
		 
	}		
	private void DisplayTheWindow() {
		boolean visibleFrame = true;
		setVisible(visibleFrame);
	}
	private void createNorthToolPane() {
		final JPanel toolPane = new JPanel();
		toolPane.setLayout(new GridLayout(2, 1));
		toolPane.add(creatingShortcutsProjectToolbar());
		toolPane.add(createPreferencesToolBar());
		getContentPane().add(toolPane, BorderLayout.NORTH);
	} 
private void creatingNetworkShortcutsRightToolbar() {		
		JToolBar RightToolBar = new JToolBar(SwingConstants.VERTICAL);
		RightToolBar.setBackground(new Color(225, 248, 253));
		RightToolBar.setBorder(new JTextField().getBorder());
	    createShortcuts("image/msg.jpg", "ecranView", RightToolBar);			 
		createShortcuts("image/del.png", "Reboot", RightToolBar);
		createShortcuts("image/selector.jpg", "this instruction is not implemented ", RightToolBar);
		getContentPane().add(RightToolBar, BorderLayout.EAST);
	}
	private JToggleButton createShortcuts(String ImageIcon,final String aMethodName,
			  JToolBar aRightToolBar) {
		final JToggleButton aButtonRight = new JToggleButton(new ImageIcon(ImageIcon));
		aButtonRight.setBorderPainted(false);
		final ActionListener anActionListener = new ExtendedActionLisetener<Project>(project,aMethodName);
		aButtonRight .addActionListener(anActionListener);
		aRightToolBar.add(aButtonRight);
         return aButtonRight;
	}
	 private void prepareTiledBackround (){
			final JFrame  MiniFrameBackground= new JFrame("Image Of the backround");
			MiniFrameBackground.setSize(500, 600);
			 final JPanel panelGlobal = new JPanel(); 
				panelGlobal.setLayout(new GridLayout(4, 1));
				final JPanel panelFirst = createImageSelector("Backgound City", "image/gCiscoCampus.jpg");
				final JPanel panelSecond = createImageSelector("Backgound ViewCity", "image/gGeoViewBuilding.jpg ");
				final JPanel panelThird = createImageSelector("Backgound ViewCity", "image/gGeoViewCity.png");
				final JPanel panelFourth = createImageSelector("Backgound InterCity","image/gGeoViewInterCity.jpg");				 
				panelGlobal.add((panelFirst));
				panelGlobal.add((panelSecond));
				panelGlobal.add((panelThird));
				panelGlobal.add((panelFourth));	 		
			MiniFrameBackground.add(panelGlobal, BorderLayout.CENTER);				   
			MiniFrameBackground.add(creatingBrowseSelector(), BorderLayout.NORTH);			
			MiniFrameBackground.setLocationRelativeTo(null);
			MiniFrameBackground.setVisible(true);
		 }
	 private ExtendedJPanel createImageSelector(String anImageName,final String anImageIcon){
			final ExtendedJPanel  anImagePanel = new ExtendedJPanel (this);
			final JLabel aLabelImage = new JLabel(  anImageName );
			final JButton anImageButton = new JButton(new ImageIcon( anImageIcon));
			anImageButton.setBounds(20, 50, 150, 90);
			aLabelImage.setBounds(180, 50, 150, 90);
			anImagePanel.setLayout(null);
			anImagePanel.add(anImageButton);
			anImagePanel.add(aLabelImage);
			anImagePanel.anImageIcon=anImageIcon;
			anImageButton.addActionListener(new ExtendedActionLisetener<ExtendedJPanel>(anImagePanel,"selectBagkround"));  
			return anImagePanel;
		}	
	 private JPanel creatingBrowseSelector(){
		    final JPanel firstPanelBagk = new JPanel();
			final JLabel lImge = new JLabel();
			lImge.setText("Select Backround Image from the listofRouter or click Browse to select:  ");
			final JButton bBrowse = new JButton("Browse");
			firstPanelBagk.add(lImge);
			firstPanelBagk.add(bBrowse);
			return firstPanelBagk;
		 }		 
	private void setTiledBackround() {
		prepareTiledBackround ();
		creatingBrowseSelector();			 		
	} 
	
	private JToolBar createPreferencesToolBar() {
		JToolBar northToolBarPreferenceButton = new JToolBar();
		northToolBarPreferenceButton.setBackground(new Color(249, 228, 157));
		northToolBarPreferenceButton.setLayout(new GridLayout(1, 5));
		final JLabel labelLogical = new JLabel("LOGICIEL");
		northToolBarPreferenceButton.add(labelLogical);
		preferenceButton("[Root]" , northToolBarPreferenceButton);
		preferenceButton("New Cluster" , northToolBarPreferenceButton);
		final JButton  Background= preferenceButton("Set Tiled Background", northToolBarPreferenceButton);	
	    Background.addActionListener(new ActionListener() {				
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTiledBackround( );
			}			
		});
		preferenceButton("Viewport", northToolBarPreferenceButton);
		preferenceButton("[Root]", northToolBarPreferenceButton);
		return northToolBarPreferenceButton;
	}
	private JButton preferenceButton(String buttonText  ,JToolBar aToolbar ) {
		final JButton aJButton = new JButton(buttonText);
		aJButton.setBackground(new Color(253, 203, 117));		 
		aToolbar.add(aJButton);		 
		return aJButton;
	}
	private JToolBar creatingShortcutsProjectToolbar() {
		JToolBar northToolBarShortcutIcons = new JToolBar();
		northToolBarShortcutIcons.setBackground(new Color(102, 232, 242));		 
	 	createShortcutIcons("image/help2.jpg","helpMe","help", northToolBarShortcutIcons);		 
		JToggleButton  print =createShortcutIcons("image/print.jpg","print","", northToolBarShortcutIcons);
		 print.addActionListener(new PrintU(this));
		createShortcutIcons("image/file.jpg","new","NewProject", northToolBarShortcutIcons);
		createShortcutIcons("image/folder.jpg","open","Load", northToolBarShortcutIcons);
		createShortcutIcons("image/save.png","saveAs","Save", northToolBarShortcutIcons);
		return northToolBarShortcutIcons;
	}
	private JToggleButton createShortcutIcons(String ImageFile ,String IconText,final String aMethodName ,JToolBar aToolbar) {
		final JToggleButton aButton = new JToggleButton(new ImageIcon(ImageFile));
		aButton.setToolTipText(IconText);
		aButton.setBorderPainted(false);
		aToolbar.add(aButton);
		final ActionListener anActionListener = new ExtendedActionLisetener<Project>(project,aMethodName); 		 
		aButton.addActionListener(anActionListener);
		return aButton;
	}
 	private void createSouthPanel() {
		JToolBar tools = new JToolBar(SwingConstants.HORIZONTAL);
		final JPanel southPanel = new JPanel();
		southPanel.add(tools);
		southPanel.setBackground(new Color(225, 248, 253));
		routerAction = addNetworkComponentsButtons("image/gh.png", "ROUTEUR",
				tools);
		routerAction.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(routerAction)){
					cableConnector.setSelected(false);
                    pcAction.setSelected(false);
                    switch1.setSelected(false);
                    deltRouter.setSelected(false);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}				
			}			
		});
		
		pcAction = addNetworkComponentsButtons("image/pc1.jpg", "PC", tools);
		pcAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(pcAction)){
					cableConnector.setSelected(false);
					routerAction.setSelected(false);
                    switch1.setSelected(false);
                    deltRouter.setSelected(false);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}				
			}			
		});
		 	switch1 = addNetworkComponentsButtons("image/Switch.png", "Switch",
				tools);
		 	switch1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(e.getSource().equals(switch1)){
						cableConnector.setSelected(false);
	                    pcAction.setSelected(false);
	                    routerAction.setSelected(false);
	                    deltRouter.setSelected(false);
	                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}					
				}				
			});
		deltRouter = addNetworkComponentsButtons("image/cor.jpg",
				"Delete Router", tools);
		deltRouter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(deltRouter)){
					cableConnector.setSelected(false);
                    pcAction.setSelected(false);
                    switch1.setSelected(false);
                    routerAction.setSelected(false);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}				
			}			
		});
		cableConnector = addNetworkComponentsButtons("image/cable2.jpg", "cableConnector",
				tools);
		
		cableConnector.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(		cableConnector)){
					routerAction.setSelected(false);
                    pcAction.setSelected(false);
                    switch1.setSelected(false);
                    deltRouter.setSelected(false);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));}				
			}
			
		});
		  JPanel pane = new JPanel(new GridLayout(1, 2));
		pane.add(southPanel, BorderLayout.WEST);
		getContentPane().add(pane, BorderLayout.SOUTH);
	}

	private JToggleButton addNetworkComponentsButtons(String ImageFile,
			String IconText , JToolBar aToolBar) {
		final JToggleButton aButton = new JToggleButton(new ImageIcon(ImageFile));
		aButton.setToolTipText(IconText);
			
		 	aToolBar.add(aButton);
		return aButton;
	}	@Override
		public void mouseClicked(MouseEvent e) {	
		 if ( (e.getSource().equals(workSpace) && ( aRouterSelected  == true)));
         workSpace.repaint();
		byte noRouterPlaced = -1;
		for (byte i = 0; i <= totalNumberOfRouter; i++) {
			if (e.getSource().equals(listofRouter[i])) {
				noRouterPlaced = i;
			}
		}
		// private void createNewRouter(){  
		if ((routerAction.isSelected()) && (e.getSource().equals(workSpace))) {
			 
			totalNumberOfRouter = totalNumberOfRouter + 1;
			listofRouter[totalNumberOfRouter] = new DragOption("image/gh.png", totalNumberOfRouter, this);
			listofRouter[totalNumberOfRouter].setBounds(e.getX(), e.getY(), 50, 40);
			listofRouter[totalNumberOfRouter].addMouseListener(this);
		 	Router first=null ;
		 
		 try {
				listofRouter[totalNumberOfRouter].addMouseListener(first = new Router("Router" + totalNumberOfRouter,
						  totalNumberOfRouter));
				 
			} catch (final InvalidIPAdressException e1) {
				e1.printStackTrace();
			} catch (final NoNetMaskException e1) {
				e1.printStackTrace();
			}

		 final Thread thread = new Thread(first);
			thread.start();
			routerTable[totalNumberOfRouter] = first;
			first.setId(totalNumberOfRouter);
			listofRouter[totalNumberOfRouter].addMouseMotionListener(this);
			workSpace.add(listofRouter[totalNumberOfRouter], null);		
			workSpace.repaint();
			aRouterSelected = false;  
		}//	 }
		//private void dssineAcableConnector(){
		if ((cableConnector.isSelected())) {

			if (noRouterPlaced != -1) {// Check that the workSpace click is on a router
							// icon. s is the first selected router

				if (aRouterSelected == false) {// If this is the first selection click (to
								// select the first icon)
					aPreviousAction = noRouterPlaced;
					aRouterSelected = true;
				}
// Private void connectionBetweenRouters(){ 
				if ((aRouterSelected == true) && (aPreviousAction != noRouterPlaced)) {
					workSpace.repaint();
					if ((aPreviousAction != -1) && (linkBetweenRouters[aPreviousAction][noRouterPlaced] == -1)
							&& (linkBetweenRouters[noRouterPlaced][aPreviousAction] == -1)) {
															
						workSpace.repaint();
						listofRouter[aPreviousAction].repaint();
						listofRouter[noRouterPlaced].repaint();
						final int x1 = listofRouter[aPreviousAction].getX() + 16;
						final int y1 = listofRouter[aPreviousAction].getY() + 16;
						final int x2 = listofRouter[noRouterPlaced].getX() + 16;
						final int y2 = listofRouter[noRouterPlaced].getY() + 16;
						workSpace.getGraphics().drawLine(x1, y1, x2, y2);	
				//	}
						// 
						final Router first;
						first = routerTable[aPreviousAction];

						final int FirstInterface = first.getFreeInterface();
						if (FirstInterface != -1) {
							try {
								first.getInterfaceRouter()
										.elementAt(FirstInterface)
										.setPhysicalyconnected(true);
								first.getRoutingTable().update();
							} catch (final InvalidIPAdressException e1) {

								e1.printStackTrace();
							} catch (final NoNetMaskException e1) {

								e1.printStackTrace();
							}

						}
						final Router second;
						second = routerTable[noRouterPlaced];
						final int secondInterface = second.getFreeInterface();
						if (secondInterface != -1) {
							try {
								second.getInterfaceRouter()
										.elementAt(secondInterface)
										.setPhysicalyconnected(true);
								second.getRoutingTable().update();
							} catch (final InvalidIPAdressException e1) {

								e1.printStackTrace();
							} catch (final NoNetMaskException e1) {

								e1.printStackTrace();
							}

							try {
								first.getInterfaceRouter()
										.elementAt(FirstInterface)
										.setConnectedEthernet(
												second.getInterfaceRouter()
														.elementAt(
																secondInterface));
								second.getInterfaceRouter()
										.elementAt(secondInterface)
										.setConnectedEthernet(
												first.getInterfaceRouter()
														.elementAt(
																FirstInterface));
								second.nbrVoisin++;

								 
							} catch (final Exception ee) {
							}
						}
						linkBetweenRouters[aPreviousAction][noRouterPlaced] = 0;
						linkBetweenRouters[noRouterPlaced][aPreviousAction] = 0;
					}
					aRouterSelected = false;
					aPreviousAction = -1;
				}
			} else {
				aRouterSelected = false;
			}
		}

		 
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		 
		  }


	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	
	

	@Override
	public void mouseDragged(MouseEvent arg0) {
		for (int i = 0; i <= totalNumberOfRouter; i++)
		if (arg0.getSource().equals(listofRouter[i])) {
			strapTheLineConnector++;
			 
			if (strapTheLineConnector == 2) {
				workSpace.repaint();
				strapTheLineConnector = 0;
			}
		}
		 
	}
	public static void main(String[] args) {
		 new GraphicalUserInterface( );		  
	         }
	}
class PrintU implements Printable, ActionListener {
	JFrame frameToPrint;

	@Override
	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {
		if (page > 0)
			return NO_SUCH_PAGE;
		final Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		frameToPrint.printAll(g); 
		return PAGE_EXISTS;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		final boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (final PrinterException ex) {
			}
		}
	}

	public PrintU(JFrame f) {
		frameToPrint = f;
	}
}
