package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class ImageHeaderRecord extends AbstractRecords {
	private static final int IMAGE_HEADER_MIN_BUFFER = 19;
	private static final String IMAGE_HEADER_RECORD_TYPE = "1202";

	private String recordTypeCode;
	private String imageType;
	private String imageSide;
	private short numberOfImageDataRecords;
	private short imageDataLength;

	public ImageHeaderRecord(InputStream inputStream, boolean isFixedLength) throws IOException {
		byte[] buffer = new byte[IMAGE_HEADER_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");

		if (headerString.length() < ImageHeaderRecord.IMAGE_HEADER_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}
		recordTypeCode = headerString.substring(0, 4);
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_IMAGE_HEADER_RECORD);
		}
		imageType = headerString.substring(4, 8);
		imageSide = headerString.substring(8, 9);
		numberOfImageDataRecords = Short.parseShort(headerString.substring(9, 13));
		imageDataLength = Short.parseShort(headerString.substring(13, 19));
		ignoreFillers(inputStream, isFixedLength ? 231 : 71);
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !ImageHeaderRecord.IMAGE_HEADER_RECORD_TYPE.equals(recordTypeCode);
	}

	public String getRecordTypeCode() {
		return recordTypeCode;
	}

	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImageSide() {
		return imageSide;
	}

	public void setImageSide(String imageSide) {
		this.imageSide = imageSide;
	}

	public short getNumberOfImageDataRecords() {
		return numberOfImageDataRecords;
	}

	public void setNumberOfImageDataRecords(short numberOfImageDataRecords) {
		this.numberOfImageDataRecords = numberOfImageDataRecords;
	}

	public short getImageDataLength() {
		return imageDataLength;
	}

	public void setImageDataLength(short imageDataLength) {
		this.imageDataLength = imageDataLength;
	}

	@Override public String toString() {
		return "ImageHeaderRecord{" +
				"recordTypeCode='" + recordTypeCode + '\'' +
				", imageType='" + imageType + '\'' +
				", imageSide='" + imageSide + '\'' +
				", numberOfImageDataRecords=" + numberOfImageDataRecords +
				", imageDataLength=" + imageDataLength +
				'}';
	}
}

