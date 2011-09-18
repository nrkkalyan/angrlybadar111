/**
 * 
 */
package suncertify.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Koosie
 * 
 */

public abstract class PropertiesDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	protected int				mStatus				= -1;
	public static final int		OK					= 0;
	public static final int		CANCEL				= -1;
	private Properties			mProperties;
	
	public PropertiesDialog(JFrame parentFrame) {
		super(parentFrame);
		setTitle(GuiConstants.APPLICATION_NAME);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				PropertiesDialog.this.mStatus = CANCEL;
				synchronized (PropertiesDialog.this) {
					PropertiesDialog.this.notifyAll();
				}
			}
		});
	}
	
	public abstract void initGuiComponents();
	
	public int showDialog() {
		initGuiComponents();
		setLocationRelativeTo(null);
		setVisible(true);
		synchronized (this) {
			try {
				this.wait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setVisible(false);
		return mStatus;
	}
	
	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Properties properties) {
		this.mProperties = properties;
	}
	
	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return mProperties;
	}
	
	public Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		File file = null;
		try {
			file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			prop.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(rootPane, "Error Occured :" + e.getLocalizedMessage());
		}
		setProperties(prop);
		showDialog();
		return prop;
	}
	
}
