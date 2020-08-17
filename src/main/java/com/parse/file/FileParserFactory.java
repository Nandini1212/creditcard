package com.parse.file;

public class FileParserFactory {
	public static FileParser GetFileParser(String fileName) {
		String[] fileParts = fileName.split("\\.");
		if (fileParts.length <= 0) {
			return null;
		}
		String extension = fileParts[fileParts.length - 1];
		if (extension.equals("csv")) {
			return new CSVFileParser(fileName);
		} else if (extension.equals("json")) {
			return new JSONFileParser(fileName);
		} else if (extension.equals("xml")) {
			return new XMLFileParser(fileName);
		}
		return null;
	}
}
