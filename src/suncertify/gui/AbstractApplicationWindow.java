package suncertify.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Abstract application window extends JFrame. Two of the GUI classes extend
 * this class. All extending classes must extend addGuiElements() method.
 * 
 * TODO : Add more comments
 * */

public abstract class AbstractApplicationWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;
	
	private final ExecutionMode			mExecutionMode;
	private final JPanel				mMainPanel;
	private ClientConfigurationDialog	mConfigDialog;
	
	public AbstractApplicationWindow(String title, ExecutionMode executionMode) {
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.mExecutionMode = executionMode;
		this.mMainPanel = new JPanel(new BorderLayout());
		add(mMainPanel);
		addMenuBar();
		displayConfigurationDialog();
		if (!mConfigDialog.connectButtonClicked()) {
			quickExitApplication();
		}
		
		addGuiElements();
		init();
		
	}
	
	/**
	 * Quit the application quickly without saving the records.
	 */
	protected final void quickExitApplication() {
		System.exit(0);
	}
	
	/**
	 * Add the component to the main panel of this JFrame.
	 * 
	 * @param component
	 *            the component to add to the JFrame.
	 * @param constraints
	 *            the constraints of the component to use.
	 */
	protected void addToMainPanel(Component component, Object constraints) {
		mMainPanel.add(component, constraints);
	}
	
	private void displayConfigurationDialog() {
		mConfigDialog = new ClientConfigurationDialog(this, mExecutionMode);
		mConfigDialog.display();
	}
	
	private void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitMenuItem.setMnemonic(KeyEvent.VK_X);
		fileMenu.add(exitMenuItem);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}
	
	private void init() {
		pack();
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
		setVisible(true);
	}
	
	/**
	 * All subclasses must implement this method inorder to add any specific Gui
	 * components. i.e Buttons, Labels, Textbox etc..
	 */
	protected abstract void addGuiElements();
	
	/**
	 * Message dialog displayed to the end user
	 * 
	 * @param message
	 *            Message to display.
	 */
	public void alertErrorMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "", JOptionPane.ERROR_MESSAGE);
	}
}
