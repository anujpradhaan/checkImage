package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class FileTrailerRecord extends AbstractRecords {
	private static final String FILE_HEADER_RECORD_TYPE = "1209";
	private static final int FILE_TRAILER_MIN_BUFFER = 58;

	private String recordTypeCode;
	private String fileId;
	private String requestId;
	private String fileVersion;
	private String fileCreationDate;
	private String fileCreationTime;
	private short numberOfCheckRecords;

	public FileTrailerRecord(InputStream inputStream, boolean isFixedLength) throws IOException {
		byte[] buffer = new byte[FileTrailerRecord.FILE_TRAILER_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");
		if (headerString.length() < FileTrailerRecord.FILE_TRAILER_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}
		/**
		 * Might have to drop trim here.
		 */
		recordTypeCode = headerString.substring(0, 4);
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_FILE_TRAILER_RECORD);
		}
		fileId = headerString.substring(4, 19);
		requestId = headerString.substring(19, 34);
		fileVersion = headerString.substring(34, 38);
		fileCreationDate = headerString.substring(38, 46);
		fileCreationTime = headerString.substring(46, 52);
		numberOfCheckRecords = Short.parseShort(headerString.substring(52, 58));
		ignoreFillers(inputStream, isFixedLength ? 198 : 32);
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !FileTrailerRecord.FILE_HEADER_RECORD_TYPE.equals(recordTypeCode);
	}

	public String getRecordTypeCode() {
		return recordTypeCode;
	}

	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public String getFileCreationDate() {
		return fileCreationDate;
	}

	public void setFileCreationDate(String fileCreationDate) {
		this.fileCreationDate = fileCreationDate;
	}

	public String getFileCreationTime() {
		return fileCreationTime;
	}

	public void setFileCreationTime(String fileCreationTime) {
		this.fileCreationTime = fileCreationTime;
	}

	public short getNumberOfCheckRecords() {
		return numberOfCheckRecords;
	}

	public void setNumberOfCheckRecords(short numberOfCheckRecords) {
		this.numberOfCheckRecords = numberOfCheckRecords;
	}
}
