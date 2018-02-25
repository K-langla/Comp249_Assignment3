package driver;

import base.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class BibCreator {
	private static BufferedReader[] reader = new BufferedReader[10];
	private static PrintWriter[][] writer = new PrintWriter[3][10];
	
	public static void closeInputFilesUpToIndex(int index) {
		for(int i = 0; i < index; i++) {
			try {
				reader[i].close();
			} catch (IOException e) {
				System.out.println("Could not close input file");
			}
		}
	}
	
	public static void closeOutputFilesUpToIndices(int index1, int index2) {
		for(int i = 0; i < (index1); i++) {
			for(int j = 0; j < (index2); j++) {
				writer[i][j].close();
			}
		}
	}
	
	public static void deleteOutputFilesUpToIndices(int index1, int index2) {
		for(int i = 0; i < (index1); i++) {
			for(int j = 0; j < (index2); j++) {
				Path path = null;
				switch(i) {
				case 0:
					path = Paths.get("IEEE" + (j+1) + ".json");
					break;
				case 1:
					path = Paths.get("ACM" + (j+1) + ".json");
					break;
				case 2:
					path = Paths.get("NJ" + (j+1) + ".json");
					break;
				}
				try {
					Files.deleteIfExists(path);
				} catch(IOException e) {
					System.out.println("Could not delete output file: " + path.getFileName());
				}
			}
		}
	}
	
	public static void displayFile(Path path) throws NoSuchFileException, IOException {
		BufferedReader br = Files.newBufferedReader(path);
		String line = null;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}
		br.close();
	}
	/*//Awaiting implementation of FileInvalidException
	public static void processFilesForValidation() throws FileInvalidException{
		
	}*/

	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		for(int i = 0; i < reader.length; i++) {
			Path path = Paths.get("latex" + (i+1) + ".bib");
			try {
				reader[i] = Files.newBufferedReader(path);
			} catch(NoSuchFileException e) {
				System.out.println("Could not open input file " + path.getFileName() + 
						" for reading. Please check if file exists! Program will terminate after closing any opened files.");
				closeInputFilesUpToIndex(i);
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
						break;
					}
				} catch(FileNotFoundException e) {
					System.out.println("Could not create output file: " + writer[i][j]);
					System.out.println("Program will close");
					closeInputFilesUpToIndex(reader.length);
					closeOutputFilesUpToIndices(i+1, j+1);
					deleteOutputFilesUpToIndices(i+1, j+1);
					return;
				}
			}
		}
		/*// awaiting implementation of FileInvalidException
		try {
			processFilesForValidation();
		} catch(FileInvalidException e) {
			
		}*/
		int count = 0;
		do {
			System.out.print("Enter name of file to display: ");
			Path path = Paths.get(kb.next());
			try {
				displayFile(path);
				break;
			} catch(NoSuchFileException e) {
				System.out.println("Could not find file " + path.getFileName());
				System.out.println("Please try again");
				count++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				count++;
			}
		} while(count < 2);
		closeInputFilesUpToIndex(reader.length);
		closeOutputFilesUpToIndices(writer.length, writer[0].length);
		kb.close();
	}
}
