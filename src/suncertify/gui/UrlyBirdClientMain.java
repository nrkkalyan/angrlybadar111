/**
 * 
 */
package suncertify.gui;

/**
 * This class instantiates the client side of the application. The client
 * application follows MVC design pattern. Where UrlyBirdClientController is the
 * controller and UrlyBirdClientFrame is the frame containing the gui elements
 * of the application.
 * 
 * @author Koosie
 * 
 */
public class UrlyBirdClientMain {
	
	/**
	 * Starts the client application. This method takes the client type one wish
	 * to run. The value could be 'rmi' or 'none'. 'rmi' starts the client
	 * application in network mode 'none' starts the client application in
	 * non-network mode where the local database file will be used.
	 * 
	 * 
	 * @param clientType
	 */
	public static void start(final String clientType) {
		UrlyBirdClientFrame frame = new UrlyBirdClientFrame();
		UrlyBirdClientController controller = new UrlyBirdClientController(frame, clientType);
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		controller.showAllRooms();
		
	}
	
}
