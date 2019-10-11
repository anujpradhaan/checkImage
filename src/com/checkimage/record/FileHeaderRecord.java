package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class FileHeaderRecord extends AbstractRecords {

	private static final String FILE_HEADER_RECORD_TYPE = "1200";
	private static final String FIXED_RECORD_TYPE = "0256";
	private static final int FILE_HEADER_MIN_BUFFER = 62;

	private String recordTypeCode;
	private String fileId;
	private String requestId;
	private String fileVersion;
	private String fileCreationDate;
	private String fileCreationTime;
	private short numberOfCheckRecords;
	private String recordSize;

	public FileHeaderRecord(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[FileHeaderRecord.FILE_HEADER_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");
		if (headerString.length() < FileHeaderRecord.FILE_HEADER_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}
		/**
		 * Might have to drop trim here.
		 */
		recordTypeCode = headerString.substring(0, 4).trim();
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_FILE_HEADER_RECORD);
		}

		fileId = headerString.substring(4, 19).trim();
		requestId = headerString.substring(19, 34).trim();
		fileVersion = headerString.substring(34, 38).trim();
		fileCreationDate = headerString.substring(38, 46).trim();
		fileCreationTime = headerString.substring(46, 52).trim();
		numberOfCheckRecords = Short.parseShort(headerString.substring(52, 58).trim());
		recordSize = headerString.substring(58, 62).trim();
		ignoreFillers(inputStream, isFixedLength() ? 194 : 28);
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !FileHeaderRecord.FILE_HEADER_RECORD_TYPE.equals(recordTypeCode);
	}

	public boolean isFixedLength() {
		return FileHeaderRecord.FIXED_RECORD_TYPE.equals(recordSize);
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

	public String getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(String recordSize) {
		this.recordSize = recordSize;
	}

	@Override public String toString() {
		return "FileHeaderRecord{" +
				"recordTypeCode='" + recordTypeCode + '\'' +
				", fileId='" + fileId + '\'' +
				", requestId='" + requestId + '\'' +
				", fileVersion='" + fileVersion + '\'' +
				", fileCreationDate='" + fileCreationDate + '\'' +
				", fileCreationTime='" + fileCreationTime + '\'' +
				", numberOfCheckRecords=" + numberOfCheckRecords +
				", recordSize='" + recordSize + '\'' +
				'}';
	}
}
