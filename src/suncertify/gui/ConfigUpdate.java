package suncertify.gui;

/**
 * 
 * */
public class ConfigUpdate {
	
	/** Different configuration parameters that could be updated */
	public enum ConfigUpdatesEnum {
		DB_FILE_LOCATION_UPDATE, HOSTNAME_UPDATE, PORT_UPDATE
	}
	
	private final ConfigUpdatesEnum	mConfigUpdateType;
	private final String			mUpdatedValue;
	
	/**
	 * Initiates {@link ConfigUpdate} with the type and new value.
	 * 
	 * @param type
	 *            value from the enum ConfigUpdatesEnum.
	 * @param updatedValue
	 *            the payload (any object).
	 * */
	public ConfigUpdate(ConfigUpdatesEnum type, String updatedValue) {
		super();
		this.mConfigUpdateType = type;
		this.mUpdatedValue = updatedValue;
	}
	
	/**
	 * Returns the update type
	 * 
	 * @return {@link ConfigUpdatesEnum}
	 * */
	public ConfigUpdatesEnum getConfigUpdateType() {
		return this.mConfigUpdateType;
	}
	
	/**
	 * Returns the new updated value
	 * 
	 * @return the new value for the corresponding {@link ConfigUpdatesEnum}
	 *         (i.e Database file path, port or host name)
	 */
	public String getUpdatedValue() {
		return this.mUpdatedValue;
	}
	
}
