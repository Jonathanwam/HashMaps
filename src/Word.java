import java.lang.String;

// You cannot extend a final class as subclassing final classes are not allowed.
// You can import String though as I've done above although redundant.

public class Word {
	public String s = new String();

	/**
	 * Creates a Word object representing the specified String
	 * 
	 * @param w the String version of this word.
	 */
	public Word(String w) {
		s = w;
		
	}
	
	/**
	 * Returns a hashcode for this Word -- an integer whose value is based on the 
	 * word's instance data. Words that are .equals() *must* have the same hashcode.
	 * However, the converse need not hold -- that is, it *is*  acceptable for 
	 * two words that are not .equals() to have the same hashcode.
	 */
	
	// old hashCode (summing up each letter) took 16 seconds for bigtext.txt
	// this version takes 18 seconds (at least on my PC)
	
	// old hashCode returns 10,944,460 collisions in bigtext.txt
	// this hashCode returns 184,249 collisions. About a 98.5% improvement!
	public int hashCode() {
		
		int PRIME = 0x01000193;
		int hash = 0x811c9dc5;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			
			hash ^= c;
			hash *= PRIME;
			
			String temp = String.valueOf(hash); // convert hash to string
			
			if(temp.length() > 5) hash = Integer.parseInt(temp.substring(0, 6)); // truncate first 6 digits

			if (hash < 0) hash *= -1; // convert negatives to positive
		}
		
		

		/* Old hashCode algorithm
		int hash = 0;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			hash += c;
		}
		*/
		

		return hash;
		
	}
	
	/**
	 * Returns true if and only if this Word object represents the same
	 * sequence of characters as the specified object. Here, you can assume
	 * that the object being passed in will be a Word. 
	 */
	public boolean equals(Object o) { 
		
		if (o instanceof Word && o.toString().equals(s)) return true;
		else if (o instanceof String && o.equals(s)) return true;
		else return false;
		
	}
	
	/**
	 * This method returns the string representation of the object.
	 * A correct implementation will return the String representation of the
	 * word that is actually being stored. ie., if you had a word object representing
	 * 'hi', it should return 'hi'
	 */
	public String toString() {
		
		// Seems like it just wants the string attached to the Word object
		return s;
		
	}
}