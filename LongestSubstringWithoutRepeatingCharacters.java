/**
 * Leetcode - https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Given a string, find the length of the longest substring without repeating characters
 */
class LongestSubstringWithoutRepeatingCharacters
{
    /**
     * Approach: Create the largest starting from every character of the string in one direction
     * i.e start the substring with every character and check the non repeating condition
     * Time: n + (n-1)....+1 = n2
     */
    public int lengthOfLongestSubstring(String s) {
        int start = 0;
        int end = s.length();

        int maxLength = 0;
        String maxStr = "";

        while(start < end)
        {
            Set<Character> seen = new HashSet();
            StringBuilder sb = new StringBuilder();

            for(int i = start; i < end; i++)
            {
                Character c = s.charAt(i);

                if(seen.contains(c))
                {
                    break;
                }

                seen.add(c);
                sb.append(c);
            }

            String finalStr = sb.toString();

            if(finalStr.length() > maxLength)
            {
                maxStr = finalStr;
                maxLength = finalStr.length();
            }

            start++;
        }

        return maxLength;
    }
    
    
    
    // This one is Linear O(N)
    public int lengthOfLongestSubstring(String s) {
        
        // Map for character to index it is found on
        Map<Character, Integer> map= new HashMap<>();
        int start=0, len=0;
        
        // abba
        for(int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            
            // If the character is already seen, then we need to consider the substring starting from the element after that
            // eg. if the string was abab and we reach the 3rd a, then the next substring to consider will start from the b in the second position
            if (map.containsKey(c)) {
                if (map.get(c) >= start) 
                    start = map.get(c) + 1;
            }
            
            // For the above case for example, this will be 2 (for the first ab string)
            len = Math.max(len, i-start+1);
            
            // Add the character index to the map
            map.put(c, i);
        }
        
        return len;
    }
}
