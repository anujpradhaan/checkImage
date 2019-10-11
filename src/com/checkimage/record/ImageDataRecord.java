package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class ImageDataRecord extends AbstractRecords {
	private static final int IMAGE_DATA_MIN_BUFFER = 10;
	private static final String IMAGE_DATA_RECORD_TYPE = "1203";

	private String recordTypeCode;
	private short lengthOfImageData;

	public ImageDataRecord(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[ImageDataRecord.IMAGE_DATA_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");

		if (headerString.length() < ImageDataRecord.IMAGE_DATA_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}

		this.recordTypeCode = headerString.substring(0, 4);
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_IMAGE_DATA_RECORD);
		}
		this.lengthOfImageData = Short.parseShort(headerString.substring(4, 10));
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !ImageDataRecord.IMAGE_DATA_RECORD_TYPE.equals(recordTypeCode);
	}

	public String getRecordTypeCode() {
		return recordTypeCode;
	}

	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	public short getLengthOfImageData() {
		return lengthOfImageData;
	}

	public void setLengthOfImageData(short lengthOfImageData) {
		this.lengthOfImageData = lengthOfImageData;
	}

	@Override public String toString() {
		return "ImageDataRecord{" +
				"recordTypeCode='" + recordTypeCode + '\'' +
				", lengthOfImageData=" + lengthOfImageData +
				'}';
	}
}
