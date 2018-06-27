
public class Binaries {

	private Cli Mycli;

	public Binaries(Cli Mycli) {
		this.Mycli = Mycli;

	}

	public Binaries() {
	}

	public void unimplemented(String parameter) {
		Mycli.appendJTextArea("\n" + "this method is unimplemented method");
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	public void showArp(String parameter) throws InvalidIPAdressException,
			NoNetMaskException {
		ArpCache arpCache = this.Mycli.Myrouter.getMyArpCache();
		int size = arpCache.getArpCache().size();
		if (size != 0)
			Mycli.appendJTextArea("\n Protocol      Address       Age(min)            Hardware Addr                   Type        Interface");
		for (int i = 0; i < arpCache.getArpCache().size(); i++) {
			if (!arpCache.getArpCache().elementAt(i).isStaticEntry())
				Mycli.appendJTextArea("\n"
						+ Constante.PROTOCOL
						+ "      "
						+ arpCache.getArpCache().elementAt(i).getIpAddress()
						+ "                  "
						+ arpCache.getArpCache().elementAt(i).getAge()
						/ 60
						+ "              "
						+ arpCache.getArpCache().elementAt(i).getMacAddress()
						+ "           "
						+ Constante.ENCAPSULATION_TYPE
						+ "        "
						+ arpCache.getArpCache().elementAt(i).getMyEthernet().name);
			else

				Mycli.appendJTextArea("\n" + Constante.PROTOCOL + "      "
						+ arpCache.getArpCache().elementAt(i).getIpAddress()
						+ "                  " + "-" + "               "
						+ arpCache.getArpCache().elementAt(i).getMacAddress()
						+ "            " + Constante.ENCAPSULATION_TYPE
						+ "            -");

		}

	}

	public void clearArpCache(String parameter) {
		Mycli.Myrouter.getMyArpCache().clearArpCache();

	}

	// ///////////////////////////////////
	public void timeout(String parameter) {
		String[] param = parameter.split(" ");
		Mycli.Myrouter.getMyArpCache().setArpTimeout(new String(param[2]));
	}

	// ////////////////////////////////////////////////////

	public void removeEntry(String parameter) {
		String param[] = parameter.split(" ");
		IpAddress ipAddress;
		try {
			ipAddress = new IpAddress(param[2]);
			Mycli.Myrouter.getMyArpCache().removeEntry(ipAddress);
		} catch (InvalidIPAdressException e) {
			
			e.printStackTrace();
		}
	}

	// ////////////////////////////////////////////////////////////////////

	public void addEntry(String parameter) {
		String param[] = parameter.split(" ");

		try {
			IpAddress ipAddress = new IpAddress(param[1]);
			MacAddress macAddress = new MacAddress(param[2]);
			Mycli.Myrouter.getMyArpCache()
					.addStaticEntry(ipAddress, macAddress);
		} catch (InvalidIPAdressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ///////////////////////////////////////////////////////////////////////////////////
	public void autoConf(String parameter) {
		String param[] = parameter.split(" ");

		enable("enable");
		Configterm("configure terminal");
		try {
			Interface("interface FastEthernet 0");
			ipadress("ip address " + param[1] + " 255.0.0.0");
			noShutDown("no shutdown");
		} catch (InvalidIPAdressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoNetMaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void enable(String parameter) {
		Mycli.setMainprompt(Mycli.Myrouter.Nom + "#");
		Mycli.element = Mycli.Compile("xml/privileiged.xml");

	}

	// /////////////////////////////////////////////////////////////////////////

	public void disable(String parameter) {

		Mycli.setMainprompt(Mycli.Myrouter.Nom + ">");
		Mycli.element = Mycli.Compile("xml/User.xml");

	}

	public void ping(String parameter) {
		String param[] = parameter.split(" ");
		try {
			this.Mycli.Myrouter.getInternetProtocol().getIcmp()
					.send(new Message("bonjour"), new IpAddress(param[1]));

		} catch (InvalidIPAdressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoNetMaskException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public void Configterm(String parameter) {

		Mycli.appendJTextArea("\n"
				+ "Enter configuration commands, one per line. End with CNTL/Z");
		Mycli.setMainprompt(Mycli.Myrouter.Nom + "(config)#");
		Mycli.element = Mycli.Compile("xml/configuration.xml");

	}

	// ////////////////////////////////////////////////////////////////////////

	private String getnomInterface(String parameter) {
		return parameter;
	}

	// //////////////////////////////////////////////////////////////////////////

	public void Interface(String parameter) throws InvalidIPAdressException,
			NoNetMaskException {

		String param[] = parameter.split(" ");
		String NomInterface = getnomInterface(param[1] + param[2]);
		Mycli.setCurrentinterface(NomInterface);
		Mycli.setMainprompt(Mycli.Myrouter.Nom + "(config-if)#");
		Mycli.element = Mycli.Compile("xml/interface.xml");
	}

	public void addStaticRouteConncet(String parameter)
			throws InvalidIPAdressException, NoNetMaskException {
		String param[] = parameter.split(" ");
		IpAddress networkAddress = new IpAddress(param[2]);
		NetMask netmask = new NetMask(param[3]);
		String name = param[4] + param[5];
		IpAddress netAddress = new IpAddress("0.0.0.0");

		for (int i = 0; i < Mycli.Myrouter.getInterfaceRouter().size(); i++) {
			if (Mycli.Myrouter.getInterfaceRouter().elementAt(i).getName()
					.equals(name)) {
				if (networkAddress.getNetworkAdress(netmask).toString()
						.equals(networkAddress.toString())) {
					Mycli.Myrouter.getRoutingTable().addRouter("s", networkAddress,
							netmask, netAddress,
							Mycli.Myrouter.getInterfaceRouter().elementAt(i),
							0, (byte) 1, -1, true);
					Mycli.Myrouter.getRoutingTable().update();
					break;
				} else {
					Mycli.appendJTextArea("\n"
							+ "%Inconsistent address and mask");
					break;
				}
			}
		}
	}

	// //////////////////////////////////////////Static Route
	// ///////////////////////////////////////////////////
	public void addStaticRoute(String parameter)
			throws InvalidIPAdressException, NoNetMaskException {
		String param[] = parameter.split(" ");
		IpAddress networkAddress = new IpAddress(param[2]);
		NetMask netmask = new NetMask(param[3]);
		IpAddress netAddress = new IpAddress(param[4]);
		Ethernet ether = null;

		if (networkAddress.getNetworkAdress(netmask).toString()
				.equals(networkAddress.toString())) {
			Mycli.Myrouter.getRoutingTable().addRouter("s", networkAddress, netmask,
					netAddress, ether, 0, (byte) 1, -1, true);
			Mycli.Myrouter.getRoutingTable().update();

		} else {
			Mycli.appendJTextArea("\n" + "%Inconsistent address and mask");

		}

	}

	public void ipadress(String parameter) throws InvalidIPAdressException,
			NoNetMaskException {

		String param[] = parameter.split(" ");
		IpAddress ip = new IpAddress(param[2]);
		NetMask mask = new NetMask(param[3]);
		IpAddress ipWork = ip.getNetworkAdress(mask);

		
		for (int i = 0; i < Mycli.Myrouter.getInterfaceRouter().size(); i++) {
			if (Mycli.Myrouter.getInterfaceRouter().elementAt(i).getName()
					.equals(Mycli.getCurrentInterface().getName())) {
				Mycli.Myrouter.getInterfaceRouter().elementAt(i)
						.setInterfaceAddress(ip);
				Mycli.Myrouter.getInterfaceRouter().elementAt(i)
						.setMaskInterface(mask);
				Mycli.Myrouter.getRoutingTable().update();

			}
		}
		for (int i = 0; i < Mycli.Myrouter.getRoutingTable()
				.getRoutingTableLength(); i++) {
			if (Mycli.Myrouter.getRoutingTable().getEntry(i).getName()
					.equals(Mycli.getCurrentInterface().getName())) {
				if (Mycli.Myrouter.getRoutingTable().getType(i).equals("strapTheLineConnector")) {
					Mycli.Myrouter.getRoutingTable().setNetWorkAddress(ipWork,
							i);
					Mycli.Myrouter.getRoutingTable().setMask(mask, i);
					Mycli.Myrouter.getRoutingTable().update();

				}
			}
		}
	}

	public void removeStaticRoute(String parameter)
			throws InvalidIPAdressException, NoNetMaskException {
		String param[] = parameter.split(" ");

		IpAddress networkAddress = new IpAddress(param[3]);
		NetMask netmask = new NetMask(param[4]);
		IpAddress ipDestination = new IpAddress(param[5]);

		Mycli.Myrouter.getRoutingTable().removeRouter(ipDestination, networkAddress,
				netmask);
		Mycli.Myrouter.getRoutingTable().update();

	}

	public void removeStaticRouteInterface(String parameter)
			throws InvalidIPAdressException, NoNetMaskException {
		String param[] = parameter.split(" ");
		IpAddress networkAddress = new IpAddress(param[3]);
		NetMask netmask = new NetMask(param[4]);

		String delete = param[5] + param[6];

		Mycli.Myrouter.getRoutingTable().removeInterface(delete,
				networkAddress, netmask);
		Mycli.Myrouter.getRoutingTable().update();

	}

	public void showStaticRoute(String parameter)
			throws InvalidIPAdressException, NoNetMaskException {
		Mycli.appendJTextArea("\n"
				+ "\n"
				+ "Codes: "
				+ "  "
				+ "C - connected , S - static, I - IGRP, R - RIP, M - mobile, B - BGP"
				+ "\n"
				+ "               "
				+ "D - EIGRP, EX - EIGRP external, O - OSPF, IA - OSPF inter area"
				+ "\n"
				+ "               "
				+ "N1 - OSPF NSSA external type 1, N2 - OSPF NSSA external type 2"
				+ "\n"
				+ "               "
				+ "E1 - OSPF external type 1, E2 - OSPF external type 2, E - EGP"
				+ "\n"
				+ "               "
				+ "i - IS-IS, L1 - IS-IS level-1, L2 - IS-IS level-2, ia - IS-IS inter area"
				+ "\n" + "               "
				+ "* - candidate default, U - per-user static route, o - ODR"
				+ "\n" + "               "
				+ "P - periodic downloaded static route" + "\n" + "\n"
				+ "Gateway of last resort is not set" + "\n" + "\n");

		Mycli.Myrouter.getRoutingTable().showIpRoute(Mycli);

	}

	// ////////////////////////********************************************************************************
	public void shutDown(String parameter) throws InvalidIPAdressException,
			NoNetMaskException {
		for (int j = 0; j < Mycli.Myrouter.getInterfaceRouter().size(); j++) {

			if (Mycli
					.getCurrentInterface()
					.getName()
					.equals(Mycli.Myrouter.getInterfaceRouter().elementAt(j)
							.getName())) {
				Mycli.Myrouter.getInterfaceRouter().elementAt(j)
						.setAdmistrativelyup(false);
				Mycli.Myrouter.getRoutingTable().update();
				Mycli.appendJTextArea("\n \n%LINK -5-CHANGED :"
						+ Mycli.getCurrentInterface().getName()
						+ "changed state to administratively down"
						+ "\n \n %LINE PROTO -5- UPDOWN: line Protocol on interface "
						+ Mycli.getCurrentInterface().getName()
						+ " changed state to down");

				break;
			}
		}

	}

	public void noShutDown(String parameter) throws InvalidIPAdressException,
			NoNetMaskException {

		for (int j = 0; j < Mycli.Myrouter.getInterfaceRouter().size(); j++) {

			if (Mycli
					.getCurrentInterface()
					.getName()
					.equals(Mycli.Myrouter.getInterfaceRouter().elementAt(j)
							.getName())) {
				Mycli.Myrouter.getInterfaceRouter().elementAt(j)
						.setAdmistrativelyup(true);
				Mycli.Myrouter.getRoutingTable().update();
				Mycli.appendJTextArea("\n  %LINK -5-CHANGED :"
						+ Mycli.getCurrentInterface().getName()
						+ " changed state to up"
						+ "\n  %LINE PROTO -5- UPDOWN: line Protocol on interface "
						+ Mycli.getCurrentInterface().getName()
						+ " changed state to up \n ");
				break;
			}
		}

	}

	// //////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) throws InvalidIPAdressException,
			NoNetMaskException {
		Binaries b = new Binaries();
		b.addStaticRoute("ip route 10.0.0.0 255.0.0.0 12.0.0.3");
		b.Interface("interface FastEthernet 2");
		b.ipadress("ip address 13.0.0.0 255.0.0.0");
		System.out.println(b.Mycli.Myrouter.getRoutingTable()
				.getRoutingTableLength());
		b.showStaticRoute("show ip route");
	}

	// //////////////////////////////////////////////////////////////////////////

	public void end(String parameter) {
		Mycli.appendJTextArea("\n"
				+ "% SYS-5-CONFIG_I: Configured from console by console");
		Mycli.setMainprompt(Mycli.Myrouter.Nom + "#");
		Mycli.element = Mycli.Compile("xml/privileiged.xml");

	}

	// //////////////////////////////////////////////////////////////////////////

	public void exitFromInterfaceMode(String parameter) {

		Mycli.setMainprompt(Mycli.Myrouter.Nom + "(config)#");
		Mycli.element = Mycli.Compile("xml/configuration.xml");

	}

	public void exitFromconfMode(String parameter) {

		Mycli.appendJTextArea("\n"
				+ "% SYS-5-CONFIG_I: Configured from console by console");
		Mycli.setMainprompt(Mycli.Myrouter.Nom + "#");
		Mycli.element = Mycli.Compile("xml/privileiged.xml");

	}

	public void exitFromprivilegedMode(String parameter) {

		Mycli.setMainprompt(Mycli.Myrouter.Nom + ">");
		Mycli.element = Mycli.Compile("xml/user.xml");

	}

	// ///////////////////////////////////////////////////////////////////////////////////////////
	public void hostname(String parameter) {
		String param[] = parameter.split(" ");
		Mycli.Myrouter.Nom = param[1];
		Mycli.setMainprompt(param[1] + "(config)#");

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	public void showprotocols(String parameter) {
		Mycli.appendJTextArea("\n"
				+ " Internet Protocol routing is enabled"
				+ "\n FastEthernet0/0 is administratively down line protocol is down");
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	public void showVersion(String parameter) {
		Mycli.appendJTextArea("\n" + " Cisco IOS software"
				+ "\n ROM:System Bootstrap" + "\n Simulateur Pédagogique"
				+ "\n Name:CiscoSim ");

	}

	// ////////////////////////////////////////////////////////////////////////////////////////////

	public boolean LINE(String jeton) {
		if (jeton != null) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////

	public boolean zeroa255(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 255)) {
			return true;
		} else
			return false;
	}

	// ///////////////////////////////////////////////////////////////////

	public boolean zeroa2147483(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 2147483)) {
			return true;
		} else

			return false;
	}

	// /////////////////////////////////////////////////////////////////////
	public boolean una10000000(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 10000000)) {

			return true;
		} else

			return false;
	}

	// ///////////////////////////////////////////////////////////////////
	public boolean una16777215(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 16777215)) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////
	public boolean una4096(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 4096)) {

			return true;
		} else

			return false;
	}

	// /////////////////////////////////////////////////////////////////////
	public boolean zeroa1000(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 1000)) {

			return true;
		} else

			return false;
	}

	// /////////////////////////////////////////////////////////////////////////
	public boolean zeroa4096(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 4096)) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////////
	public boolean una65535(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 65535)) {

			return true;
		} else

			return false;
	}

	// ///////////////////////////////////////////////////////////////////////
	public boolean una255(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 255)) {

			return true;
		} else

			return false;
	}

	// /////////////////////////////////////////////////////////////////////////////
	public boolean deuxcentquatrevingta1500(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 280) && (valeur <= 1500)) {

			return true;
		} else

			return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	public boolean zeroa4294967295(String jeton) {

		float valeur = Float.parseFloat(jeton);
		if ((valeur >= 0) && (valeur <= 429496725)) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////////////
	public boolean seizea4096(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 4096)) {

			return true;
		} else

			return false;
	}

	// //////////////////////////////////////////////////////////////////////////
	public boolean soixantequatrea1600(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 64) && (valeur <= 1600)) {

			return true;
		} else

			return false;
	}

	// /////////////////////////////////////////////////////////////////////////
	public boolean una199(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 199)) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////
	public boolean dix(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if (valeur == 10) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////////
	public boolean cent(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if (valeur == 100) {

			return true;
		} else

			return false;
	}

	// ////////////////////////////////////////////////////////////////////////
	public boolean una32767(String jeton) {

		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 32767)) {

			return true;
		} else

			return false;
	}

	/*********************************************** user ***********************************************/

	public boolean una99(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 99)) {

			return true;
		}
		return false;
	}

	/******************************************************************************************/

	public boolean una200(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 200)) {
			return true;
		}
		return false;
	}

	/******************************************************************************************/

	public boolean zeroa9(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 9)) {
			return true;
		}
		return false;
	}

	/******************************************************************************************/

	public boolean zeroa2147483647(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 0) && (valeur <= 2147483647)) {
			return true;
		}
		return false;
	}

	/******************************************************************************************/

	public boolean una655535(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 655535)) {
			return true;
		}
		return false;
	}

	/******************************************************************************************/

	public boolean una2(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 2)) {
			return true;
		}
		return false;
	}

	/*********************************************************************************************/
	public boolean una256(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 256)) {
			return true;
		}
		return false;
	}

	/********************************************************************************************/

	public boolean av(String jeton) {
		return false;
	}

	/********************************************************************************************/

	public boolean deux(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if (valeur == 2) {

			return true;
		}
		return false;
	}

	/*************************************************************************************************/

	public boolean al(String jeton) {

		return false;
	}

	/***********************************************************************************************/

	public boolean un(String jeton) {

		int valeur = Integer.parseInt(jeton);

		if (valeur == 1) {

			return true;
		}

		return false;
	}

	/**********************************************************************************************/

	public boolean sept(String jeton) {

		int valeur = Integer.parseInt(jeton);

		if (valeur == 7) {

			return true;
		}

		return false;
	}

	/**********************************************************************************************/

	public boolean una16(String jeton) {
		int valeur = Integer.parseInt(jeton);
		if ((valeur >= 1) && (valeur <= 16)) {

			return true;
		}

		return false;
	}

	/*************************************************************************************************/

	public boolean zeroa15(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 0) && (valeur <= 15)) {
			return true;
		}
		return false;
	}

	// /////////////////pri///////////////////////////////////////////////
	public boolean una999(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 999)) {
			return true;
		}
		return false;
	}

	// ////////////////////////////////////////////////////////////////
	public boolean milltroiscenta2699(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1300) && (valeur <= 2699)) {
			return true;
		}
		return false;
	}

	// /////////////////////////////////////////////////////////////
	public boolean MONTH(String jeton) {
		return false;
	}

	// ////////////////////////////////////////////////////////////////
	public boolean una31(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 31)) {
			return true;
		}
		return false;
	}

	// /////////////////////////////////////////////////////////////////////
	public boolean una2035(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 2035)) {
			return true;
		}
		return false;
	}

	// ////////////////////////////////////////////////////////////////////
	public boolean una6(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 6)) {
			return true;
		}
		return false;
	}

	// ///////////////////////////////////////////////////////////////////
	public boolean una4094(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 4094)) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////
	public boolean siezea1022(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 16) && (valeur <= 1022)) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////
	public boolean una1005(String jeton) {
		int valeur = Integer.parseInt(jeton);

		if ((valeur >= 1) && (valeur <= 1005)) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////

	public boolean WORD(String jeton) {

		if (jeton != null) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////

	public boolean thirtyto429496729(String jeton) {
		double valeur = Integer.parseInt(jeton);

		if ((valeur >= 30) && (valeur <= 429496729)) {
			return true;
		}
		return false;
	}

	// //////////////////////////////////////////////////////////////////////

	public boolean ABCD(String jeton) {
		char[] tab = new char[jeton.length()];
		tab = jeton.toCharArray();
		String A = null, B = null, C = null, D = null;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] != '.') {
				A = A + tab[i];
			} else {
				for (int j = i + 1; j < tab.length; j++) {
					if (tab[j] != '.') {
						B = B + tab[j];
					} else {
						for (int n = j + 1; n < tab.length; n++) {
							if (tab[n] != '.') {
								C = C + tab[n];

							} else {

								for (int m = n + 1; m < tab.length; m++) {
									if (tab[m] != '.') {
										D = D + tab[m];
									}
								}

							}
						}
					}

				}

			}
		}
		if (A.equals("") || B.equals("") || C.equals("") || D.equals("")) {
			return false;
		}
		return true;
	}

	public boolean ABCDEF(String jeton) {
		char[] tab = new char[jeton.length()];
		tab = jeton.toCharArray();
		String A = null, B = null, C = null, D = null, E = null, F = null;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i] != ':') {
				A = A + tab[i];
			} else {
				for (int j = i + 1; j < tab.length; j++) {
					if (tab[j] != ':') {
						B = B + tab[j];
					} else {
						for (int n = j + 1; n < tab.length; n++) {
							if (tab[n] != ':') {
								C = C + tab[n];

							} else {

								for (int m = n + 1; m < tab.length; m++) {
									if (tab[m] != ':') {
										D = D + tab[m];
									} else {
										for (int l = m + 1; l < tab.length; l++) {
											if (tab[l] != ':') {
												E = E + tab[l];
											} else {
												for (int k = l + 1; k < tab.length; k++) {
													if (tab[k] != ':') {
														F = F + tab[k];
													}
												}
											}
										}
									}
								}

							}
						}
					}

				}

			}
		}
		if (A.equals("") || B.equals("") || C.equals("") || D.equals("")) {
			return false;
		}
		return true;
	}

}