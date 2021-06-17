import java.io.*;
import java.util.*;

// CS 340: Software Design III
// Homework Assignment 2 Part 2
//
// Andrew Krause
//
// This class uses the iterator created in the 
// SymbolTable class. The program uses arguments
// and command line instructions to read in text
// files and manipulate them.
//
// The asymptotic notation of the program varies
// throughout the different methods.
//
public class H2 {

	/**
	 * The global instance variable is of 
	 * type SymbolTable, which allows the 
	 * class, H2.java, to access all of the 
	 * components of the SymbolTable.java
	 * class through this single variable.
	 * 
	 */
	private SymbolTable words;
	
	/**
	 * The constructor for the class
	 * contains different methods that
	 * read in different arguments, which
	 * are text files. 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public H2(String args[]) throws IOException
	{
		// Method for argument 1 that reads in words from text file
		readWords(args[0]); 
		
		// Method prints words that were read in from text file
		printWords();
		
		// Method for argument 2 that finds words read in from text file
		findWords(args[1]);
		
		// Method for argument 3 that removes words specified in a different
		// text file from the main text file in argument 1
		removedWords(args[2]);
		
		// Method prints words that were changed from other methods in program
		printWords();

		// If there are more than 3 arguments, make a call to  
		// the third argument and print in output file. 
		// If there are not more than 3, than print the text.
		if (args.length == 4)
		{
			printingWords(args[3]);
		}
		else
		{
			printWords();
		}
	}
	
	/**
	 * The method reads the words in the file. A scanner 
	 * is created to scan through the the text document 
	 * and obtain the words in the file. Method opens the 
	 * file, which is called infile. Method first opens 
	 * the file before parsing.
	 * 
	 * @param infile
	 * @throws IOException
	 */
	private void readWords(String infile) throws IOException
	{
		// A new instance of BufferedReader is created. This allows
		// you to interact with text files.
		BufferedReader input = new BufferedReader(new FileReader(infile));
		
		// The global instance variable of type SymbolTable
		// is created and assigned a value, for size, of 100.
		words = new SymbolTable(100);
		
		// Variable of type String is created to move through each word in line of text
		String line = input.readLine();
		
		// Counter variable tracks the line that you are at
		int counter = 1;
		
		// The nested while loops use the iterator in the SymbolTable class.
		// The loop runs as long as the line of text is not null.
		while (line != null)
		{
			// The scanner is created to scan through the text in the files
			Scanner scan = new Scanner(line); 
			
			// Loop uses the iterator from SymbolTable.java. 
			// As long as there is a next value, the loop will 
			// continue to run.
			while (scan.hasNext())
			{
				// The String stores a word found at each point in the text.
				// Text is put to lower case to avoid scanning complications.
				String newInsert = scan.next().toLowerCase(); 
				
				// A new instance of a linked list is created to help 
				// store the data while traversing through text
				LinkedList<Integer> link = new LinkedList<Integer>(); 
				
				// Determine if the word was inserted in our hash table
				boolean inserted = words.insert(newInsert, link);
				
				// Add the counter to determine which lines each word is on
				link.add(counter);
				
				// Conditional determines if there are duplicates
				if (inserted == false)
				{
					// Type-casting occurs here in order obtain the 
					// the data values of the words on their different
					// lines
					link = (LinkedList<Integer>) words.find(newInsert);
					
					// If the line number that the word exists on has
					// not been accounted for, then add the counter 
					// to the link Linked List
					if (!link.contains(counter)) 
					{
						link.add(counter);
					}
				}
			}
			// Increase the counter variable
			counter++;
			
			// Read the next word in the line of text
			line = input.readLine();
			
			// Close scanners
			scan.close(); 
		}
		// Close the input
		input.close();
	}
	
	 /**
	  * The method takes a separate text file with words and
	  * determines if its contents are found in the original 
	  * file, which is the first argument passed through the
	  * readWords() method. This method is implemented after
	  * the readWords() method is executed.
	  * 
	  * @param searchfile
	  * @throws IOException
	  */
	private void findWords(String searchfile) throws IOException
	{
		// A new instance of BufferedReader is created. This allows
		// you to interact with text files.
		BufferedReader input = new BufferedReader(new FileReader(searchfile));
		
		// String variable for each word in the line of text
		String line = input.readLine();

		// Loop runs as long as there is a word 
		// in the line of text
		while (line != null)
		{
			// Set print to blank
			String print = "";
			
			// Convert the word stored from the 
			// line to lower case for simplicity
			line = line.toLowerCase();
			
			// Find which line(s) the words are on
			Object find = words.find(line);

			// If there is no line that the word appears on,
			// then not found
			if (find == null)
			{
				print = print + ", Not Found";
			}
			// If the word is found, list the line(s) that the word is
			// found on
			else
			{
				// A new instance of a linked list is created to help 
				// store the data objects (the line numbers)
				LinkedList<Integer>link = (LinkedList<Integer>)find;
				
				// Convert the list of line numbers to an array
				Object data[] = link.toArray();
					
				// Traverse through the array of found line
				// numbers and prepare to print out
				for (int i = 0; i < data.length; i++)
				{
					print = print + ", " + data[i];
				}
			}
			// Print out the word with its respective line numbers
			// that it appears on, and go to the next word
			System.out.println(line + print);
			line = input.readLine();
		}
		// Print out a new line for separation, and close the input
		System.out.println();
		input.close();
	}
	
	/**
	 * The method takes a separate text file with words and
	 * determines if its contents are found in the original 
	 * file, which is the first argument passed through the
	 * readWords() method. If the words in the separate text
	 * file are found in the original, they are removed from
	 * the original file.
	 * 
	 * @param removefile
	 * @throws IOException
	 */
	private void removedWords(String removefile) throws IOException 
	{
		// A new instance of BufferedReader is created. This allows
		// you to interact with text files.
		BufferedReader input = new BufferedReader(new FileReader(removefile));
		
		// String variable used to read each word in line of text
		String line = input.readLine();

		// Until the end of the line of text is not 
		// reached, loop through each word in the text.
		while (line != null)
		{
			// Make each word lower case for simple implementation
			line = line.toLowerCase();
			
			// Call remove method to remove each word specified in
			// text file from the original text file
			words.remove(line);
					
			// Move on to the next word
			line = input.readLine();
		}
		// Close the input
		input.close();
	}
	
	/**
	 * The method prints out the words evaluated from 
	 * implementing the previous methods, which are
	 * readWords(), findWords(), and removedWords().
	 * The printing is executed in a style that 
	 * shows the word listed a single time along
	 * with a list of the line numbers that the word 
	 * appears on.
	 * 
	 * @throws IOException
	 */
	private void printWords() throws IOException
	{
		// New instance of iterator is created with words from text
		// stored in hash table data structure in SymbolTable.java
		Iterator<String> iterator = words.iterator();
		
		// Using iterator, determine if next space has a word in it
		while (iterator.hasNext())
		{
			// Set String value to blank
			String printing = " ";
			
			// String variable used for moving through hash table and printing
			// out the words stored in the data structure
			String word = iterator.next();
			
			// Store line numbers word appears on in the linked list
			LinkedList<Integer> link = (LinkedList<Integer>)words.find(word);
			
			// Convert the Linked List to an array for easier traversal
			Object data[] = link.toArray();
			
			// Print out the word being evaluated
			System.out.print(word);
			
			// Print out the line numbers that the word appears on. The
			// data is separated by commas for simple readability.
			for (int i = 0; i < data.length; i++)
			{
				if (i < data.length - 1)
				{
					printing = " " + data[i] + ",";
				}
				else
				{
					printing = " " + data[i];
				}
				System.out.print(printing);
			}
			// Two print line statements for organization
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * If there is a fourth argument passed through the
	 * class, then this method is implemented to print
	 * out the additional arguments in a different 
	 * output file. 
	 * 
	 * @param printWords
	 * @throws IOException
	 */
	private void printingWords(String argument) throws IOException
	{
		// A new instance of BufferedReader is created. This allows
		// you to interact with text files.
		BufferedWriter buffer = new BufferedWriter(new FileWriter(argument));
		
		// New instance of iterator is created with words from text
		// stored in hash table data structure in SymbolTable.java
		Iterator<String> iterator = words.iterator();

		// Using iterator, while there is a filled slot after the current
		// slot, run loop
		while (iterator.hasNext())
		{
			// Store the next value in the String variable
			String key = iterator.next();
			
			// Linked List stores data found on which lines the word appears on
			LinkedList<Integer> link = (LinkedList<Integer>)words.find(key);
			
			// The Linke List is converted to an array for easier traversal
			Object data[] = link.toArray();
			
			// Write the word out to new file
			buffer.write(key);

			// Write data to output file and separate using print line 
			for (int i = 0; i < data.length; i++)
			{
				buffer.write(", " + data[i]);
			}
			buffer.write("\n");
		}
		// Close buffer
		buffer.close();
	}
	
	/**
	 * The main method for this class determines there
	 * is a sufficient number of arguments provided to 
	 * the class in order to enable the program to 
	 * execute in a proper manner. 
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws IOException
	{
		// If the number of arguments is three or four, pass
		// arguments through constructor
		if (args.length == 3 || args.length == 4)
		{
			new H2(args);
		}
		// Prompt user to enter the sufficient number of command line
		// arguments
		else
		{
			System.out.println("You must provide 3 command line arguments");
		}
	}
	
}
