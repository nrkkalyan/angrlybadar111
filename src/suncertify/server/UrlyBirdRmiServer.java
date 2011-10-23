/**
 * 
 */
package suncertify.server;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import suncertify.client.UBException;
import suncertify.common.CommonConstants;
import suncertify.common.CommonConstants.ActionCommand;
import suncertify.common.CommonConstants.ApplicationMode;
import suncertify.gui.PropertiesDialog;

/**
 * @author Koosie
 * 
 */
public class UrlyBirdRmiServer extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1L;
	private static final PropertiesDialog	mUBPropertyDialog	= new PropertiesDialog(null, ApplicationMode.SERVER);
	private static final TextArea			mTextArea			= new TextArea();
	private static final JMenuBar			mMenuBar			= new JMenuBar();
	
	public UrlyBirdRmiServer() {
		super(CommonConstants.APPLICATION_NAME + "Server");
		setLayout(new BorderLayout());
		initGui();
		setResizable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(BorderLayout.NORTH, mMenuBar);
		add(BorderLayout.SOUTH, mTextArea);
		pack();
	}
	
	/**
	 * 
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
	
	private static void log(String log) {
		if (log != null) {
			mTextArea.append("\n" + new Date() + ": " + log);
		}
	}
	
	/**
	 * @param string
	 */
	public void start() throws Exception {
		Properties props = mUBPropertyDialog.loadProperties();
		try {
			String host = props.getProperty(CommonConstants.SERVER_HOST).trim();
			String port = props.getProperty(CommonConstants.SERVER_PORT).trim();
			if (host == null || host.trim().isEmpty()) {
				throw new UBException("Host is required.");
			}
			if (port == null || port.trim().isEmpty()) {
				throw new UBException("Port is required");
			}
			LocateRegistry.createRegistry(Integer.parseInt(port));
			String name = "rmi://" + host + ":" + port + CommonConstants.REMOTE_SERVER_NAME;
			UrlyBirdRmiImpl theserver = new UrlyBirdRmiImpl(props.getProperty(CommonConstants.DB_FILE));
			Naming.rebind(name, theserver);
			setVisible(true);
			log("UrlyBird Server started.");
			log("Running on " + host + ":" + port);
		} catch (Exception e) {
			String message = "Could not able to start RMI server. Some error occured. Details :" + e.getMessage();
			JOptionPane.showMessageDialog(null, message, "UB Message", JOptionPane.ERROR_MESSAGE);
			throw new UBException(message);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals(ActionCommand.EXIT.name())) {
			
			int choice = JOptionPane.showConfirmDialog(null, "Do you really want to exit?", CommonConstants.APPLICATION_NAME, JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				log(CommonConstants.APPLICATION_NAME + "Server shutdown.");
				System.exit(0);
			}
		}
		
	}
	
}
