import java.util.*;

/**
 * Class used to manage and solve sentences with a personally made BNF.
 * The class stores the information, sees if certains symbols are available, 
 * gets what symbols are available to be called, and also generates the sentences.
 *  
 * @author Hunter J. McClure
 * @version (May 7 2019)
 */
public class GrammarSolver
{
   private Map<String, ArrayList<String[]>> Rule = new TreeMap<>();
   
   /**
    * Creates a constructor for the sentence generator that
    * stores the starting dictionary, the length of the words being used, 
    * and stores the max starting amount of guesses. But also, I made the
    * constructor get rid of all the words in the set that are bigger or smaller
    * than the length of the words that was chosen. 
    * 
    * @param Rules It is a list that contains each line from the text waiting to be broken down and trimmed
    * @return nothing
    * @throws IllegalArgumentException When the list is empty or null OR when the file contains a line for a non-terminal more than once
    */
   public GrammarSolver(List<String> Rules)
   {
      if(Rules.isEmpty() || Rules == null)
      {
         throw new IllegalArgumentException();
      }
      
       for(String aRule : Rules)
       {
         //splits the first line into a key and a sentence and trims out extra spaces
         String breakdown[] = aRule.split("::=");
         for(int i = 0; i < breakdown.length; i++)
         {
            breakdown[i] = breakdown[i].trim();
         }
         
         //splits the terminals and non-terminals apart
         String[] temp1 = breakdown[1].split("[|]");
         ArrayList<String[]> temp2 = new ArrayList<>();
         
         for(String tempString : temp1) //helps get rid of unwanted spaces
         {
            tempString = tempString.trim();
            String[] breakdown2 = tempString.split("[ \t]+");
            temp2.add(breakdown2);
         }
         
         if(contains(breakdown[0]))
         {
            throw new IllegalArgumentException();
         } 
         
         else 
         {
           Rule.put(breakdown[0], temp2); //creates the keys and data for each rule
         }
         
       }
   }
   
   /**
    * Sends back a true or false depending on if a certain symbol/key is in the map of rules 
    * 
    * @param Symbol A string sent in to check if it is a key in the map
    * @return True or false depending on if the symbol is a key in the map
    * @throws IllegalArgumentException When the string's length is 0 or the string is null
    */
   public boolean contains(String Symbol)
   {
      if(Symbol.length() < 0 || Symbol == null)
      {
         throw new IllegalArgumentException();
      }
      
      return Rule.containsKey(Symbol);
   }
   
   /**
    * Sends back a set of the symbols/keys that can be used in the sentence builder 
    * 
    * @param none
    * @return A set of symbols/keys in the map that are strings
    */
   public Set<String> getSymbols()
   {
      return Rule.keySet();
   }
   
   /**
    * Builds the sentences by using recursion and going through all the symbols and 
    * if they are terminal or non-terminal and then building it till it's all terminals 
    * that are left and then returning the generated phrase to the main client class.
    * 
    * @param Symbol A string that is a key to the map that determines if it is terminal or non-terminal
    * @return The generated phrase after all the recursion has finished
    * @throws IllegalArgumentException When the string's length is 0 or the string is null
    */
   public String generate(String symbol)
   {
      Random rand = new Random();
            
      if(contains(symbol))
      {
         ArrayList<String[]> theRules = Rule.get(symbol);
         int x = rand.nextInt(theRules.size()); //chooses a random number to help randomly choose the next non-terminal or terminal
         
         String generatedPhrase = "";
         
         //this is where the recursion occurs and it goes through the lines until there is no more non-terminals left
         for(String tempSymbol : theRules.get(x))
         {
            if(!(tempSymbol.length() > 0) || tempSymbol == null)
            {
               throw new IllegalArgumentException();
            }
            
            generatedPhrase = generatedPhrase + " " + generate(tempSymbol);
         }
         
         return generatedPhrase;
      }
      
      else return symbol; //if it's not a non-terminal
   } 
}