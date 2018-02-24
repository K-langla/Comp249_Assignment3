package driver;

import base.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class BibCreator {
	private static BufferedReader[] reader = new BufferedReader[10];
	private static PrintWriter[][] writer = new PrintWriter[3][10];

	public static void main(String[] args) {
		
		for(int i = 0; i < reader.length; i++) {
			Path path = Paths.get("latex" + (i+1) + ".bib");
			try {
				reader[i] = Files.newBufferedReader(path);
			} catch(IOException e) {
				System.out.println("Could not open input file " + path.getFileName() + 
						" for reading. Please check if file exists! Program will terminate after closing any opened files.");
				try {
					for(int j = 0; j < i; j++) {
						reader[j].close();
					}
				} catch (IOException io) {
					System.out.println("Could not close input file");
				}
				return;
			}
		}
		for(int i = 0; i < writer.length; i++) {
			for(int j = 0; j < writer[i].length; j++) {
				try {
					switch(i) {
					case 0:
						writer[i][j] = new PrintWriter("IEEE" + (j+1) + ".json");
						break;
					case 1:
						writer[i][j] = new PrintWriter("ACM" + (j+1) + ".json");
						break;
					case 2:
						writer[i][j] = new PrintWriter("NJ" + (j+1) + ".json");
					}
				} catch(FileNotFoundException e) {
					System.out.println("Could not create output file: " + e.getMessage());
				}
			}
		}
	}
}
