/**
 * 
 */
package suncertify.gui;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * @author Koosie
 * 
 */
public class EightDigitsTextField extends JTextField {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	private final static String	BADCHARS			= "-`~!@#$%^&*()_+=\\|\"':;?/>.<, ";
	private static int			MAX_LENGTH			= 8;
	
	@Override
	protected Document createDefaultModel() {
		return new EightDigitDocument();
	}
	
	@Override
	public void processKeyEvent(KeyEvent ev) {
		if (ev.getKeyCode() == KeyEvent.VK_ENTER) {
			super.processKeyEvent(ev);
			return;
		}
		char c = ev.getKeyChar();
		
		if ((Character.isLetter(c) && !ev.isAltDown()) || BADCHARS.indexOf(c) > -1) {
			ev.consume();
			return;
		}
		
		super.processKeyEvent(ev);
	}
	
	public boolean isEditValid() {
		return getText() != null && getText().length() == MAX_LENGTH;
	}
	
	private class EightDigitDocument extends PlainDocument {
		
		private static final long	serialVersionUID	= 1L;
		
		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if (str == null) {
				return;
			}
			
			// Accept only digits
			char[] charArray = str.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (!Character.isDigit(charArray[i])) {
					return;
				}
			}
			if (getLength() + str.length() <= MAX_LENGTH) {
				super.insertString(offs, str, a);
			}
		}
	}
	
}
