package com.checkimage;

import com.checkimage.record.CheckImageWriter;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please provide the input file as well as output directory");
			System.exit(0);
		}

		String inputFile = args[0];
		String outputDirectory = args[1];
		if (outputDirectory.charAt(outputDirectory.length() - 1) != File.separatorChar) {
			System.out.println("Please provide the output directory with file separator at end");
			System.exit(0);
		}
		CheckImageWriter checkImageWriter = new CheckImageWriter();
		checkImageWriter.extractImagesFromData(inputFile, outputDirectory);
		System.out.println(String.format("Files generate successfully, Check %s for all the generated files", args[1]));
	}

}
