import java.util.*;

// CS 340: Software Design III
// Homework Assignment 2 Part 1
//
// Andrew Krause
//
// The program uses a data structure, a Hash Table
// using separate chaining, to organize data in an
// effective manner. The program Implements the
// Hash Table, called Symbol Table, that stores 
// String called keys and associated data of type 
// Object.
//
// The asymptotic notation of the program varies
// throughout the different methods.
//
public class SymbolTable {
	
	/**
	 * Creates linked list 
	 * for use in hash table.
	 * 
	 * @author andrewkrause
	 */
	private class Node
	{
		// Nodes used to build the linked lists used in 
		// the separate chaining hash table.
		private String key;
		private Object data;
		private Node next;
		
		// Constructor for inner private class.
		private Node(String k, Object d, Node n)
		{
			key = k;
			data = d;
			next = n;
		}
	}
	
	// An array of nodes, used for reference, are created for
	// the hash table data structure along with a size variable.
	private Node table[];
	private int tableSize;
	
	// Array of primes values used to determine the size of the hash table.
	private final int primes[] = {7, 23, 59, 131, 271, 563, 1171, 2083, 4441, 
								   8839, 16319, 32467, 65701, 131413, 263983, 528991};
			 
	// Global instance variables used for hash table size.
	private int primeIndex;
	private int filled;
	
	/**
	 * The method finds the next largest
	 * prime number for the table size 
	 * based on the current value of the
	 * table size.
	 * 
	 * @param p
	 * @return
	 */
	private int nextPrime(int p)
	{
		// Finds the next primes value in the
		// array of prime values.
		while (primes[primeIndex] <= p)
		{
			primeIndex++;
		}
		return primes[primeIndex];
	}
	
	/**
	 * Class constructor used to 
	 * determine table size as well 
	 * as set the current number of 
	 * filled slots in the table to
	 * zero.
	 * 
	 * @param s
	 */
	public SymbolTable(int s)
	{
		primeIndex = 0;
		tableSize = nextPrime(s);
		table = new Node[tableSize];
		filled = 0;
	}
	
	/**
	 * The method resizes the current hash table,
	 * and reallocates the items in the hash table. 
	 * 
	 */
	private void resize()
	{
		// The size of the original hash table is kept
		// obtaining the values in the different locations.
		int oldTableSize = tableSize; 
		
		// Original slots in each section of the old table.
		Node[] oldTable = table;
		
		// A new table size is created by calling the nextPrime() method.
		// New empty slots for the lists are created based on the new table size.
		tableSize = nextPrime(oldTableSize);
		table = new Node[tableSize]; 
		
		// Loop traverses through the old table and inserts all of 
		// the items in new locations.
		for (int i = 0;  i < oldTableSize; i++)
		{
			if (oldTable[i] != null)
			{
				Node temp = oldTable[i];
				while (temp != null)
				{
					// The insert method is called to insert the values from
					// the old table into the new table with new locations.
					insert(temp.key, temp.data);
					temp = temp.next;
				}
			}
		}
	}
	
	/**
	 * Method completes the hashing computation
	 * for the hash table. Each value in the table 
	 * is used in the formula to determine its 
	 * location.
	 * 
	 * @param k
	 * @return
	 */
	private int hash(String k)
	{
		// Return the hash function value for k.
		return Math.abs(k.hashCode() % tableSize); 
	}
	
	/**
	 * If k is not in the table, insert a new item (k,d) and return true.
	 * Otherwise, return false. If, after inserting a new item, the table  
	 * has more than 2*tableSize items in it, then resize the table.
	 * 
	 * @param k
	 * @param d
	 * @return
	 */
	public boolean insert(String k, Object d)
	{
		// A boolean, integer for location, and Node with 
		// location stored for traversal are created.
		boolean wasInserted = false;
		int location = hash(k);
		Node temp = table[location];

		// Loop is used to check if the value being inserted
		// is already in the list.
		while (temp != null) 
		{
			// If value is already in list, do not insert.
			if (temp.key.equals(k))
			{
				// Return boolean value as false if value already in list.
				return wasInserted;
			}
			temp = temp.next; 					  
		}
		
		// If the slot at the given location is empty, then
		// create a new space to insert the item.
		if (temp == null)
		{
			filled++;
		}
				
		// Insert the item, and set the boolean value
		// equal to true.
		table[location] = new Node(k, d, table[location]);
		wasInserted = true;
		
		// If the result of inserting the value exceeds the 
		// table size, call the resize() method.
		if (filled > 2 * tableSize) 
		{
			// We want the hash table to resize after the table
			// contains twice as many values as its size.
			resize();
		}
		// Return boolean as true if value inserted.
		return wasInserted;
	}
	
	 /**
	  * Method determines if an item is in the
	  * hash table. If the item is in the hash
	  * table, the method returns the object value
	  * of that item. Return d if (k,d) is in the 
	  * table. Otherwise, return null.
	  * 
	  * @param k
	  * @return
	  */
	public Object find(String k)
	{	
		// The hash function is called on the item to determine
		// the correct location of slots to look through.
		int location = hash(k);
		Node temp = table[location];
		
		// Traverse through located section of slots to
		// determine if value being searched for exists.
		while (temp != null)
		{
			// If key is found, return the object of that key.
			if(temp.key.equals(k))
			{
				return temp.data;
			}
			temp = temp.next;
		}
		// Return null if object is not found.
		return null;
	}
	
	/**
	 * The nested inner class creates an iterator to 
	 * iterate or move through the hash table data 
	 * structure created in this program. This enables
	 * other programs to use the data structure through
	 * the iterator in this class.
	 * 
	 * @author andrewkrause
	 *
	 */
	public class STIterator implements Iterator<String>
	{
		// An iterator that iterates through the keys in the table.
		// Each call to next returns the next key in the table.
		// Global instance variables of type Node and int are created.
		private Node current;
		private int currentLoc;
		
		/**
		 * The constructor initializes the 
		 * global instance variables of the 
		 * nested inner class.
		 * 
		 */
		public STIterator() 
		{
			// Variables initialized.
			current = table[0];
			currentLoc = 0;
		}

		/**
		 * The boolean method determines if 
		 * the next item in the hash table 
		 * data structure is null. If the value
		 * is null, the method returns false. 
		 * If the value is not null, it returns 
		 * true.
		 * 
		 * @return
		 */
		public boolean hasNext()
		{
			// Loop finds the next open slot in the hash table 
			// with a linked list in it.
			while (currentLoc < tableSize - 1 && current == null)
			{
				// Increment the variable and  store in current.
				currentLoc++;
				current = table[currentLoc];
			}
			// If the current slot has a list in it, return true.
			if (current != null)
			{
				return true;
			}
			return false;
		}
		
		/**
		 * Method returns the String value of the
		 * item in a particular location of the hash
		 * table. The precondition is that the boolean
		 * value in hasNext() is true. 
		 * 
		 * @return
		 */
		public String next() 
		{
			// PRE: hasNext()
			// Conditional determines if the first row
			// in the hash table is null and returns  
			// the value or null.
			if (hasNext())
			{
				// Variable used for the String value at the current position.
				String currentStringVal = current.key;
				
				// At the given slot, move on to the next value in the linked list.
				current = current.next;
				return currentStringVal;
			}
			// If the next node is null, return null.  
			else 
			{
				return null;
			}
		}
		
		/**
		 * Unused method.
		 * 
		 */
		public void remove()
		{
			// Optional method that is not implemented.
		}
	}
	
	/**
	 * Method removes the specified value 
	 * from the hash table. If k is in the 
	 * table, remove (k,d) and return true.
	 * Otherwise return false.
	 * 
	 * @param k
	 * @return
	 */
	public boolean remove(String k)
	{
		// A variable storing the hash function for the 
		// location and a boolean.
		int location = hash(k);
		boolean wasRemoved = false;
		
		// Conditional determines if the vertical slot has
		// any items.
		if (table[location] == null)
		{
			return false;
		}
		
		// Check if the first item is equal to the value you want to remove.
		else if(table[location].key.equals(k)) 
		{
			// If first item is equal to value in need of removal, remove.
			table[location] = table[location].next;
			filled--;
			return true;
		}

		// A temp variable is created for traversing in the while loop.
		Node temp = table[location];

		// Loop searches for the value at the specific location.
		while (temp.next != null)
		{
			// If the value is found, remove and return true.
			if (temp.next.key.equals(k))
			{
				temp.next = temp.next.next;
				filled--; 
				return true;
			}
			// Move on to the next node.
			temp = temp.next;
		}
		// If value was not found, return false.
		return wasRemoved;
	}

	/**
	 * Method returns a new instance of the iterator
	 * in the program, which is used for referencing 
	 * each item in the hash table data structure.
	 * 
	 */
	public Iterator<String> iterator()
	{
		// Return a new STiterator object.
		STIterator iterator = new STIterator(); 
		return iterator;
	}
	
	/**
	 * Comment out this method before submitting.
	 * The method prints out the hash table 
	 * data structure. 
	 * 
	 */
	public String printHashTable()
	{
		// Several String variables are created to help 
		// display the values in the hash table.
		String hashTable = "";
		String space  = " ";
		String arrow = "-->";
		String quotation = "\"";
		
		// For loop traverses through the vertical portion of the 
		// the hash table data structure.
		for (int i = 0; i < tableSize; i++)
		{
			// Temp variable created for traversal. String  
			// variable is initialized.
			Node temp = table[i];
			hashTable = hashTable + "[" + i + "]";
			
			// While loop traverses through the horizontal portion in each 
			// vertical slot of the hash table data structure.
			while (temp != null)
			{
				// Add on to the String value storing the information in the hash table.
				hashTable = hashTable + space + arrow + space + quotation + temp.key + quotation + space + temp.data;
				temp = temp.next;
			}
			// After each traversal through the horizontal portion, a new line is created.
			hashTable = hashTable + "\n";
		}
		// Returns the String of information that was collected in the method.
		return hashTable;
	}
	
}
