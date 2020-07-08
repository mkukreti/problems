import java.util.*;


public class Substring{
     /**
      * Calculate substring of a string from length 1 -> to max length len
      * 
      * Total substrings = n * (n+1)/2
      * 
      * input = "hello"
      * output - [h, he, hel, e, el, ell, l, ll, llo]
     **/
     public static List<String> substring(String s, int len) {
         int l = s.length();
         List<String> r = new ArrayList();
         
         // First loop to choose every character as the first character
         for(int i = 0; i < l; i++) {
             
             // Start from the selected character, till the end (i.e. current character + len provided)
             int end = i + len;
             
             // Make sure j is not greater than the length of the string
             for(int j = i; j < end && end <= l; j++) {
                String s1 = Character.toString(s.charAt(i));
                
                 int start = i;
                 
                 // First append 0 chars to the right, then 2, then 3 and so forth till the lenght criteria is met
                 while(start++ < j) {
                     s1 += Character.toString(s.charAt(start));
                 }
                 
                 r.add(s1);
             }
         }
         
         return r;
     }
     
     public static void main(String []args) {
        System.out.println(substring("hello", 3));
     }
}
