package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class CheckIndexRecord extends AbstractRecords {
	private static final int FILE_HEADER_MIN_BUFFER = 89;
	private static final String CHECK_INDEX_HEADER_RECORD_TYPE = "1201";

	private String recordTypeCode;
	private String bankNumber;
	private String routingTransitNumber;
	private String accountNumber;
	private String checkNumber;
	private String amount;
	private String sequenceNumber;
	private String postedDate;
	private short numberOfImages;

	public CheckIndexRecord(InputStream inputStream, boolean isFixedLength) throws IOException {
		byte[] buffer = new byte[FILE_HEADER_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");

		if (headerString.length() < CheckIndexRecord.FILE_HEADER_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}
		recordTypeCode = headerString.substring(0, 4);
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_CHECK_INDEX_RECORD);
		}
		bankNumber = headerString.substring(4, 8);
		routingTransitNumber = headerString.substring(8, 17);
		accountNumber = headerString.substring(17, 37);
		checkNumber = headerString.substring(37, 52);
		amount = headerString.substring(52, 62);
		sequenceNumber = headerString.substring(62, 77);
		postedDate = headerString.substring(77, 85);
		numberOfImages = Short.parseShort(headerString.substring(85, 89));
		ignoreFillers(inputStream, isFixedLength ? 167 : 1);
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !CheckIndexRecord.CHECK_INDEX_HEADER_RECORD_TYPE.equals(recordTypeCode);
	}

	public String getRecordTypeCode() {
		return recordTypeCode;
	}

	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getRoutingTransitNumber() {
		return routingTransitNumber;
	}

	public void setRoutingTransitNumber(String routingTransitNumber) {
		this.routingTransitNumber = routingTransitNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public short getNumberOfImages() {
		return numberOfImages;
	}

	public void setNumberOfImages(short numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

	@Override public String toString() {
		return "CheckIndexRecord{" +
				"recordTypeCode='" + recordTypeCode + '\'' +
				", bankNumber='" + bankNumber + '\'' +
				", routingTransitNumber='" + routingTransitNumber + '\'' +
				", accountNumber='" + accountNumber + '\'' +
				", checkNumber='" + checkNumber + '\'' +
				", amount='" + amount + '\'' +
				", sequenceNumber='" + sequenceNumber + '\'' +
				", postedDate='" + postedDate + '\'' +
				", numberOfImages=" + numberOfImages +
				'}';
	}
}
