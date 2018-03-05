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
	//Awaiting implementation of FileInvalidException
	public static void processFilesForValidation() throws FileInvalidException, IOException{
		String author = null;
		String journal = null;
		String title = null;
		int year = 0;
		String volume = null;
		String pages = null;
		String doi = null;
		String month = null;
		
		String line = null;
		String field = null;
		String value = null;
		int indexStart = 0;
		int indexFinish = 0;
		
		
		for (int j = 0; j < reader.length; j++) {
			int count = 0; // for testing
			while((line = reader[j].readLine()) != null) {
				if(line.indexOf("@ARTICLE{") > -1) {
					count ++; // for testing
					while((line = reader[j].readLine()) != null && line.indexOf("}") != 0) {
						// begin populating fields by first checking for valid lines
						if((indexStart = line.indexOf("={")) != -1) { // found a line with field + value(s)
							indexStart += 2; // set the index to proper location for extracting values
							if((indexFinish = line.indexOf("}")) > indexStart) { // line is valid (ie: not "={}")

								field = line.substring(0, (indexStart - 2)); // go back two spaces so we don't extract "<field>={"
								value = line.substring(indexStart, indexFinish);
								
								// System.out.println(field + " = " + value); // for testing

								switch (field) {
								case "author":
									author = value;
									break;
								case "journal":
									journal = value;
									break;
								case "title":
									title = value;
									break;
								case "year":
									year = Integer.parseInt(value);
									break;
								case "volume":
									volume = value;
									break;
								case "pages":
									pages = value;
									break;
								case "doi":
									doi = value;
									break;
								case "month":
									month = value;
									break;
								}

							} else {
								// line is not valid (ie: it contains empty values "={}")
								// throw new FileInvalidException();
								System.out.println("invalid field + value pair in article " + count + " in file " + (j+1)); // for testing
							}
						}
					}
					
					// All fields are set, create Article object
					// Article a = new Article(args);
					
					// write to file now?
					for(int i = 0; i < writer.length; i++) {
						switch(i) {
						case 0:
							//writer[i][j].println(a.IEEEtoString());
							break;
						case 1:
							//writer[i][j].println(a.ACMtoString());
							break;
						case 2:
							//writer[i][j].println(a.NJtoString());
							break;
						}
					}
				}
			}
		}
	}

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
					return; // Exit the program
				}
			}
		}
		// awaiting implementation of FileInvalidException
		try {
			processFilesForValidation();
		} catch(FileInvalidException e) {
			// close file and delete?
		} catch(IOException e) {
			
		}
		
		closeInputFilesUpToIndex(reader.length);
		closeOutputFilesUpToIndices(writer.length, writer[0].length);
		
		int count = 2;
		do {
			System.out.print("Enter name of file to display: ");
			Path path = Paths.get(kb.next());
			try {
				displayFile(path);
				break;
			} catch(NoSuchFileException e) {
				System.out.println("Could not find file " + path.getFileName() + ". " + (count-1) + " attempt(s) remaining.");
				count--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				count--;
			}
		} while(count > 0);
		
		kb.close();
		System.out.println("Goodbye.");
	}
}
