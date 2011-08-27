package suncertify.db;

import java.io.Serializable;
import java.util.Date;

/**
 * This class represents an record in the database file provided.
 * 
 * @author eradkal
 * 
 */
public class Room implements Serializable {
	
	/**
	 * A version number for this class so that serialization can occur without
	 * worrying about the underlying class changing between serialization and
	 * deserialization.
	 */
	private static final long	serialVersionUID	= 1L;
	private Integer				mRecordId;
	private String				mHotelName;
	private String				mLocation;
	private Integer				mMaxOccupancy;
	private boolean				mIsSmoking;
	private String				mPricePerNight;
	private Date				mAvailableDate;
	private Long				mCustomerId;
	private boolean				mIsValid;
	
	/**
	 * @return the mRecordId
	 */
	public Integer getRecordId() {
		return mRecordId;
	}
	
	/**
	 * @param recordId
	 *            the mRecordId to set
	 */
	public void setRecordId(Integer recordId) {
		this.mRecordId = recordId;
	}
	
	/**
	 * @return the mHotelName
	 */
	public String getHotelName() {
		return mHotelName;
	}
	
	/**
	 * @param hotelName
	 *            the mHotelName to set
	 */
	public void setHotelName(String hotelName) {
		this.mHotelName = hotelName;
	}
	
	/**
	 * @return the mLocation
	 */
	public String getLocation() {
		return mLocation;
	}
	
	/**
	 * @param location
	 *            the mLocation to set
	 */
	public void setLocation(String location) {
		this.mLocation = location;
	}
	
	/**
	 * @return the mMaxOccupancy
	 */
	public Integer getMaxOccupancy() {
		return mMaxOccupancy;
	}
	
	/**
	 * @param maxOccupancy
	 *            the mMaxOccupancy to set
	 */
	public void setMaxOccupancy(Integer maxOccupancy) {
		this.mMaxOccupancy = maxOccupancy;
	}
	
	/**
	 * @return the mIsSmoking
	 */
	public boolean getIsSmoking() {
		return mIsSmoking;
	}
	
	/**
	 * @param isSmoking
	 *            the mIsSmoking to set
	 */
	public void setIsSmoking(boolean isSmoking) {
		this.mIsSmoking = isSmoking;
	}
	
	/**
	 * @return the mPricePerNight
	 */
	public String getPricePerNight() {
		return mPricePerNight;
	}
	
	/**
	 * @param pricePerNight
	 *            the mPricePerNight to set
	 */
	public void setPricePerNight(String pricePerNight) {
		this.mPricePerNight = pricePerNight;
	}
	
	/**
	 * @return the mAvailableDate
	 */
	public Date getAvailableDate() {
		return mAvailableDate;
	}
	
	/**
	 * @param availableDate
	 *            the mAvailableDate to set
	 */
	public void setAvailableDate(Date availableDate) {
		this.mAvailableDate = availableDate;
	}
	
	/**
	 * @return the mCustomerId
	 */
	public Long getCustomerId() {
		return mCustomerId;
	}
	
	/**
	 * @param customerId
	 *            the mCustomerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.mCustomerId = customerId;
	}
	
	/**
	 * @return the mIsValid
	 */
	public boolean isValid() {
		return mIsValid;
	}
	
	/**
	 * @param isValid
	 *            the mIsValid to set
	 */
	public void setIsValid(boolean isValid) {
		this.mIsValid = isValid;
	}
	
	public boolean matchAlike(Room room) {
		if (room == null) {
			return false;
		}
		boolean match = true;
		match &= matchIfNotNull(this.mAvailableDate, room.mAvailableDate);
		match &= matchIfNotNull(this.mCustomerId, room.mCustomerId);
		match &= matchIfNotNull(this.mHotelName, room.mHotelName);
		match &= matchIfNotNull(this.mIsSmoking, room.mIsSmoking);
		match &= matchIfNotNull(this.mIsValid, room.mIsValid);
		match &= matchIfNotNull(this.mLocation, room.mLocation);
		match &= matchIfNotNull(this.mMaxOccupancy, room.mMaxOccupancy);
		match &= matchIfNotNull(this.mPricePerNight, room.mPricePerNight);
		return match;
	}
	
	private boolean matchIfNotNull(Object obj1, Object obj2) {
		if (obj1 != null && obj2 != null) {
			if (obj1 instanceof String && obj2 instanceof String) {
				// Match if the obj1 begins with obj2
				return ((String) obj1).startsWith((String) obj2);
			} else { // Rest all i.e Integer, Long, Boolean etc
				return obj1.equals(obj2);
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.mRecordId == null) ? 0 : this.mRecordId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		if (this.mRecordId == null) {
			if (other.mRecordId != null)
				return false;
		} else if (!this.mRecordId.equals(other.mRecordId))
			return false;
		return true;
	}
	
}
