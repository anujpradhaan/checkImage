package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
class CheckTrailerRecord extends AbstractRecords {
	private static final short CHECK_TRAILER_MIN_BUFFER = 4;
	private static final String CHECK_TRAILER_RECORD_TYPE = "1204";

	private String recordTypeCode;

	public CheckTrailerRecord(InputStream inputStream, boolean isFixedLength) throws IOException {
		byte[] buffer = new byte[CheckTrailerRecord.CHECK_TRAILER_MIN_BUFFER];
		inputStream.read(buffer);
		String headerString = new String(buffer, "UTF8");
		if (headerString.length() < CheckTrailerRecord.CHECK_TRAILER_MIN_BUFFER) {
			throw new CorruptedFileException(Constants.CORRUPTED_FILE);
		}
		recordTypeCode = headerString.substring(0, 4);
		if (isInvalidValidHeaderType()) {
			throw new CorruptedFileException(Constants.INVALID_CHECK_TRAILER_RECORD);
		}
		ignoreFillers(inputStream, isFixedLength ? 252 : 86);
	}

	@Override
	public boolean isInvalidValidHeaderType() {
		return !CheckTrailerRecord.CHECK_TRAILER_RECORD_TYPE.equals(recordTypeCode);
	}

	public String getRecordTypeCode() {
		return recordTypeCode;
	}

	public void setRecordTypeCode(String recordTypeCode) {
		this.recordTypeCode = recordTypeCode;
	}

	@Override public String toString() {
		return "CheckTrailerRecord{" +
				"recordTypeCode='" + recordTypeCode + '\'' +
				'}';
	}
}
