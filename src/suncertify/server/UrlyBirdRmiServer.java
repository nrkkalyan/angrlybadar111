/**
 * 
 */
package suncertify.server;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;
import suncertify.common.CommonConstants.ApplicationMode;
import suncertify.gui.PropertiesDialog;

/**
 * The <code>UrlyBirdRmiServer</code> class starts up the RMI server for the
 * application. Once the server is started a panel will be displayed with a
 * startup message.
 * 
 * @author nrkkalyan
 * 
 */
public class UrlyBirdRmiServer extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	private static final TextArea		mTextArea			= new TextArea();
	private final JMenuBar				mMenuBar			= new JMenuBar();
	private static PropertiesDialog		mUBPropertyDialog;
	private static UrlyBirdRmiImpl		mUrlyBirdRmiImpl;
	private static UrlyBirdRmiServer	mUrlyBirdRmiServer;
	private static final String			HOST				= "localhost";
	
	/**
	 * Private constructor as the instance of this class must be created from
	 * the start method.
	 * */
	private UrlyBirdRmiServer() throws Exception {
		super(CommonConstants.APPLICATION_NAME + "Server");
		setLayout(new BorderLayout());
		initGui();
		setResizable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(BorderLayout.NORTH, mMenuBar);
		add(BorderLayout.SOUTH, mTextArea);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				log(CommonConstants.APPLICATION_NAME + "Server shutdown.");
				mUrlyBirdRmiImpl.close();
			}
		});
		
		pack();
	}
	
	/**
	 * Prepares the Gui screen to display
	 */
	private void initGui() {
		mTextArea.setEditable(false);
		mTextArea.append(CommonConstants.APPLICATION_NAME + " ServerLog.");
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setActionCommand(ActionCommand.EXIT.name());
		JMenu file = new JMenu("File");
		file.add(exit);
		mMenuBar.add(file);
	}
	
	/**
	 * Display the log message on the screen. The method is static so that the
	 * server messages can be logged easily and thus display on the screen.
	 * Currently this method is not been called from any other place other than
	 * within this class.
	 * 
	 * @param log
	 * */
	public static void log(String log) {
		if (log != null) {
			mTextArea.append("\n" + new Date() + ": " + log);
		}
	}
	
	/**
	 * This is the method creates the instance of itself using the private
	 * constructor. The <code>PropertiesDialog</code> reads the database file
	 * path location and port number from the suncertify.properties file and
	 * bind the server instance to that port. Finally show the screen showing
	 * the log messages.
	 * 
	 * The class uses <code>LocateRegistry.createRegistry(int)</code> in order
	 * to creates and export the registry instance.
	 * 
	 * 
	 * @throws Exception
	 */
	public static void start() throws Exception {
		try {
			mUrlyBirdRmiServer = new UrlyBirdRmiServer();
			mUBPropertyDialog = new PropertiesDialog(ApplicationMode.SERVER);
			
			Properties props = mUBPropertyDialog.loadProperties();
			String port = props.getProperty(CommonConstants.SERVER_PORT).trim();
			if (port == null || port.trim().isEmpty()) {
				throw new IllegalArgumentException("Port is required");
			}
			
			LocateRegistry.createRegistry(Integer.parseInt(port));
			String name = getRmiUrl(HOST, port);
			mUrlyBirdRmiImpl = new UrlyBirdRmiImpl(props.getProperty(CommonConstants.DB_FILE));
			Naming.rebind(name, mUrlyBirdRmiImpl);
			
			mUrlyBirdRmiServer.setVisible(true);
			
			log("UrlyBird Server started.");
			log("Running on " + HOST + ":" + port);
		} catch (Exception e) {
			String message = "Could not able to start RMI server. Some error occured. Details :" + e.getMessage();
			JOptionPane.showMessageDialog(null, message, "UB Message", JOptionPane.ERROR_MESSAGE);
			throw new Exception(message);
		}
	}
	
	/**
	 * Overrides java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent) to handle events generated in menu File->Exit
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals(ActionCommand.EXIT.name())) {
			
			int choice = JOptionPane.showConfirmDialog(null, "Do you really want to exit?", CommonConstants.APPLICATION_NAME, JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				log(CommonConstants.APPLICATION_NAME + "Server shutdown.");
				mUrlyBirdRmiImpl.close();
				System.exit(0);
			}
		}
		
	}
	
	/**
	 * Returns a rmi url. Both networked client and the network server use the
	 * same the same url.
	 * 
	 * @param host
	 *            host name to connect to
	 * @param port
	 *            port number to bind
	 * @return string in url format
	 * 
	 * @throws IllegalArgumentException
	 *             if host or port are null or empty
	 * */
	public static String getRmiUrl(String host, String port) {
		if (host == null || host.trim().isEmpty()) {
			throw new IllegalArgumentException("Host is required");
		}
		if (port == null || port.trim().isEmpty()) {
			throw new IllegalArgumentException("Port is required");
		}
		return "rmi://" + host + ":" + port + CommonConstants.REMOTE_SERVER_NAME;
	}
	
}
