// CS 340: Software Design III
// Homework Assignment 2 Driver 
//
// Andrew Krause
//
// The driver tests the program in the class
// called SymbolTable.java.
//
// The asymptotic notation of the program varies
// throughout the methods.
//
public class HashTableDriver {
	
	/**
	 * The main method is used for testing and debugging
	 * the program. The test driver is used to ensure that
	 * the data structure runs in an efficient manner.
	 *
	 * @param args
	 */
	public static void main(String args[])
	{
		// A new instance of the class, SymbolTable.java, that this driver  
		// runs is created. A String separator variable is also created.
		System.out.println("{{Tests for SymbolTable.java}}\n");
		SymbolTable test1 = new SymbolTable(1);
		String sepLine = "-----------------------------";
		
		// Methods are called to fill the hash table as well as for testing.
		System.out.println("Testing the insert method (should not \ninsert the same String value twice):");
		System.out.println(sepLine + sepLine);
		System.out.println("Inserting the Item 'Andrew' 0..." + test1.insert("Andrew", 0));
		System.out.println("Inserting the Item 'Andrew' 5..." + test1.insert("Andrew", 5));
		System.out.println("Inserting the Item 'Alex' 12..." + test1.insert("Alex", 12));
		System.out.println("Inserting the Item 'Anthony' 345..." + test1.insert("Anthony", 345));	
		System.out.println("Inserting the Item 'Dakota' 3..." + test1.insert("Dakota", 3));
		System.out.println("Inserting the Item 'Ila' 85..." + test1.insert("Ila", 85));
		System.out.println("Inserting the Item 'Dan' 43..." + test1.insert("Dan", 43));
		System.out.println("Inserting the Item 'Foo' 23..." + test1.insert("Foo", 23));
		System.out.println("Inserting the Item 'Riley' 1..." + test1.insert("Riley", 1));
		System.out.println("Inserting the Item 'Gendreau' 67..." + test1.insert("Gendreau", 67));
		System.out.println("Inserting the Item 'Krissy' 203..." + test1.insert("Krissy", 203));
//		System.out.println("Inserting the Item 'Erika' 17..." + test1.insert("Erika", 17));
//		System.out.println("Inserting the Item 'Colin' 98..." + test1.insert("Colin", 98));
//		System.out.println("Inserting the Item 'Isaiah' 25..." + test1.insert("Isaiah", 25));
//		System.out.println("Inserting the Item 'Mike' 43..." + test1.insert("Mike", 43));
//		System.out.println("Inserting the Item 'Nolan' 897..." + test1.insert("Nolan", 897));
//		System.out.println("Inserting the Item 'Jordan' 57..." + test1.insert("Jordan", 57));
		System.out.println(sepLine + sepLine);
		System.out.println("\n");

		// Testing remove method
		System.out.println("Testing the remove mehtod:");
		System.out.println(sepLine + sepLine);
		System.out.println("Removing the Item 'Foo' 23..." + test1.remove("Foo"));
		System.out.println(sepLine + sepLine);
		System.out.println("\n");
		
		// Testing search method
		System.out.println("Testing the search method:");
		System.out.println(sepLine + sepLine);
		System.out.println("Searching for the object value (23) of 'Foo'..." + test1.find("Foo"));
		System.out.println("Searching for the object value (345) of 'Anthony'..." + test1.find("Anthony"));
		System.out.println(sepLine + sepLine);
		System.out.println("\n");
		
		// Print out the hash table data structure.
		System.out.println("Hash Table Data Structure");
		System.out.println(sepLine + sepLine);
		System.out.print(test1.printHashTable()); 
		System.out.println(sepLine + sepLine + "\n\n");
		
		// The iterator is called to help print out the list.
		// Iterator<String> testIterator = test1.iterator();
		
		// ------------------------------------------------------------------------------------------------------------
		
		// A new instance of the class, H2.java, that this driver runs 
		// is created. A String separator variable is also created.
		System.out.println("{{Tests for H2.java}}");
		System.out.println("\nThe tests are on separate files that\n"
							+ "are passed through the H2.java program.");
	} 
	
}
