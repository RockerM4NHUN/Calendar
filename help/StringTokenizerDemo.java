import java.util.StringTokenizer;

/**
 * Java program to show how to use StringTokenizer for breaking a delimited
 * String into tokens. StringTokenizer allows you to use multiple delimiters as
 * well. which means you can split String containing comma and colon in one call.
 *
 * @author Javin Paul
 */
public class StringTokenizerDemo{
   
    public static void main(String args[]) {

        // Example 1 - By default StringTokenizer breaks String on space
        System.out.println("StringTokenizer Example in Java, split String on whitespace");

        String word = "Which one is better, StringTokenizer vs Split?";
        StringTokenizer tokenizer = new StringTokenizer(word);
        while (tokenizer.hasMoreTokens()) {
            System.out.println(tokenizer.nextToken());
        }


        // Example 2 - StringTokenizer with multiple delimiter
        System.out.println("StringTokenizer multiple delimiter Example in Java");

        String msg = "http://192.173.15.36:8084/";
        StringTokenizer st = new StringTokenizer(msg, "://.");
        while (st.hasMoreTokens()) {
            System.out.println(st.nextToken());
        }
       
       
        // Example 3 - Counting number of String tokens
        System.out.println("StringTokenizer count Token Example");

        String records = "one,two,three,four,five,six,seven";
        StringTokenizer breaker = new StringTokenizer(records, ",");
        System.out.println("Total number of tokens : " + breaker.countTokens());
    }
}

/*
Output:
StringTokenizer Example in Java, split String on whitespace
Which
one
is
better,
StringTokenizer
vs
Split?

StringTokenizer multiple delimiter Example in Java
http
192
173
15
36
8084

StringTokenizer count Token Example
Total number of tokens : 7


Read more: http://javarevisited.blogspot.com/2014/02/stringtokenizer-example-in-java-multiple-delimiters.html#ixzz312uwClAD
*/