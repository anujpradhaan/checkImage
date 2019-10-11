package com.checkimage.record;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author : anuj.kumar
 */
abstract class AbstractRecords {

	protected abstract boolean isInvalidValidHeaderType();

	protected void ignoreFillers(InputStream inputStream, int numberOfFillers) throws IOException {
		byte[] buffer = new byte[numberOfFillers];
		inputStream.read(buffer);
	}
}
