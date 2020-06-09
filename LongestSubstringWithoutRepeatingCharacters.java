/**
 * Leetcode - https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * Given a string, find the length of the longest substring without repeating characters
 */
class LongestSubstringWithoutRepeatingCharacters
{
    /**
     * Approach: Create the largest starting from every character of the string in one direction
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
}