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
	private static final long serialVersionUID = 1L;
	private Long mRecordId;
	private String mHotelName;
	private String mLocation;
	private Integer mMaxOccupancy;
	private Boolean mIsSmoking;
	private String mPricePerNight;
	private Date mAvailableDate;
	private Long mCustomerId;
	private Boolean mIsValid;

	/**
	 * @return the mRecordId
	 */
	public Long getRecordId() {
		return mRecordId;
	}

	/**
	 * @param recordId the mRecordId to set
	 */
	public void setRecordId(Long recordId) {
		this.mRecordId = recordId;
	}

	/**
	 * @return the mHotelName
	 */
	public String getHotelName() {
		return mHotelName;
	}

	/**
	 * @param hotelName the mHotelName to set
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
	 * @param location the mLocation to set
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
	 * @param maxOccupancy the mMaxOccupancy to set
	 */
	public void setMaxOccupancy(Integer maxOccupancy) {
		this.mMaxOccupancy = maxOccupancy;
	}

	/**
	 * @return the mIsSmoking
	 */
	public Boolean getIsSmoking() {
		return mIsSmoking;
	}

	/**
	 * @param isSmoking the mIsSmoking to set
	 */
	public void setIsSmoking(Boolean isSmoking) {
		this.mIsSmoking = isSmoking;
	}

	/**
	 * @return the mPricePerNight
	 */
	public String getPricePerNight() {
		return mPricePerNight;
	}

	/**
	 * @param pricePerNight the mPricePerNight to set
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
	 * @param availableDate the mAvailableDate to set
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
	 * @param customerId the mCustomerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.mCustomerId = customerId;
	}

	/**
	 * @return the mIsValid
	 */
	public Boolean getIsValid() {
		return mIsValid;
	}

	/**
	 * @param isValid the mIsValid to set
	 */
	public void setIsValid(Boolean isValid) {
		this.mIsValid = isValid;
	}
}
