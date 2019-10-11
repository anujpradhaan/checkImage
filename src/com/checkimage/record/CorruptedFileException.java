package com.checkimage.record;

/**
 * @author : anuj.kumar
 */
class CorruptedFileException extends RuntimeException {
	public CorruptedFileException() {
		super();
	}

	public CorruptedFileException(String message) {
		super(message);
	}

	public CorruptedFileException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
