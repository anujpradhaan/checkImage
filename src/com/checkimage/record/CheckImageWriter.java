package com.checkimage.record;

import java.io.*;

/**
 * @author : anuj.kumar
 */
public class CheckImageWriter {
	public void extractImagesFromData(String inputFile, String outputDirectory) {
		try (
				InputStream inputStream =
						new BufferedInputStream(new FileInputStream(inputFile));
		) {

			FileHeaderRecord fileHeaderRecord = new FileHeaderRecord(inputStream);
			for (int i = 0; i < fileHeaderRecord.getNumberOfCheckRecords(); i++) {
				CheckIndexRecord checkIndexRecord = new CheckIndexRecord(inputStream, fileHeaderRecord.isFixedLength());
				ImageHeaderRecord imageHeaderRecord = new ImageHeaderRecord(inputStream, fileHeaderRecord.isFixedLength());

				readImageDataAndWriteToDataFiles(inputStream, checkIndexRecord.getCheckNumber(), imageHeaderRecord.getImageType(),
						imageHeaderRecord.getNumberOfImageDataRecords(), outputDirectory);
				// There can be multiple data records, we will have to loop through all the data and then write them into a single file
				new CheckTrailerRecord(inputStream, fileHeaderRecord.isFixedLength());
			}
			new FileTrailerRecord(inputStream, fileHeaderRecord.isFixedLength());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void readImageDataAndWriteToDataFiles(InputStream inputStream, String checkNumber, String imageType, short numberOfImageDataRecords,
			String outputDirectory)
			throws IOException {
		String fileName = String.format("%s%s.%s", outputDirectory, checkNumber, imageType);
		FileOutputStream outputStream = new FileOutputStream(fileName);
		for (short i = 0; i < numberOfImageDataRecords; i++) {
			ImageDataRecord imageDataRecord = new ImageDataRecord(inputStream);
			byte[] bytes = new byte[imageDataRecord.getLengthOfImageData()];
			inputStream.read(bytes);
			outputStream.write(bytes);
		}
		outputStream.close();
	}
}
