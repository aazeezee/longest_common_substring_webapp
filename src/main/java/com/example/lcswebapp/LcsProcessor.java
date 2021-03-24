package com.example.lcswebapp;

import java.util.*;

public class LcsProcessor {
    /**
     * Method meant to be the entry point for making use of this utility class in order to
     * find the longest common substring in a List of Strings
     * @param values
     * @return A List of Strings containing the longest common substrings
     */
    public List<String> handler(List<String> values) {
        if (values.size() == 1) {
            return values;
        } else {
            List<String> lcsList = new ArrayList<String>(getLongestCommonSubstrings(values));
            Collections.sort(lcsList);
            return lcsList;
        }
    }

    /**
     * Finds all substrings of every String in the passed-in Set of Strings,
     * then stores these substrings as keys in a Map, where the value of each key is a
     * Set of Strings. These Sets of String values will each be a Set of the original String(s)
     * to which the key is a substring of.
     * For example: "Sonic" is a substring of both "Sonic Adventure" and "Sonic & Knuckles",
     * and would therefore lead to an entry in the Map where the key-value pair is:
     * Key = "Sonic", Value = {"Sonic Adventure", "Sonic & Knuckles"}
     * @param originalStrings
     * @return
     */
    public Set<String> getLongestCommonSubstrings(List<String> originalStrings) {
        Map<String, Set<String>> allSubstrings = new HashMap<String, Set<String>>();
        Set<String> currentLcsSet = new HashSet<String>();
        for (int origIdx = 0; origIdx < originalStrings.size(); origIdx++) {
            String original = originalStrings.get(origIdx);
            for (int leftIdx = 0; leftIdx < original.length(); leftIdx++) {
                for (int rightIdx = leftIdx; rightIdx < original.length(); rightIdx++) {
                    String subStr = original.substring(leftIdx, rightIdx + 1);
                    if (!allSubstrings.containsKey(subStr)) {
                        allSubstrings.put(
                                subStr,
                                new HashSet<String>(Arrays.asList(original))
                        );
                    } else {
                        allSubstrings.get(subStr).add(original);
                    }
                }
            }
            if (origIdx >= 1) {
                currentLcsSet = truncateAllSubstringsMap(allSubstrings);
            }
        }
        return retainMax(currentLcsSet, 0);
    }

    /**
     * Method to optimize space complexity by truncating the "allSubstrings" Map.
     * Takes in the "allSubstrings" Map and finds all keys where the value is a Set with
     * a size greater than 1. Then, among those keys, finds the key(s) with the longest length
     * (the current LCS(s)). Finally, removes all keys whose lengths are less than the current LCS(s)
     * and returns that Set of substring keys
     * @param allSubstrings
     * @return The current Set of LCS(s) found so far
     */
    public Set<String> truncateAllSubstringsMap(Map<String, Set<String>> allSubstrings) {
        Map<String, Set<String>> truncatedSubStrMap = new HashMap<String, Set<String>>();
        Set<String> currentLcsSet = new HashSet<String>();
        Set<String> keysStillInPlay = new HashSet<String>();
        // Finding all the keys that belong to more than one String in the original List of String values
        // (meaning they are mapped to a Set with a size greater than 1).
        // In other words, finding common substrings.
        int maxLcsLength = 0;
        for (String subStr : allSubstrings.keySet()) {
            if (allSubstrings.get(subStr).size() > 1) {
                currentLcsSet.add(subStr);
                if (maxLcsLength < subStr.length()) {
                    maxLcsLength = subStr.length();
                }
            }
        }
        // If we haven't found any common substrings, we don't want to remove anything from the map yet
        if (currentLcsSet.size() != 0) {
            // Retrieving all substrings from the current map that are the same length or longer than
            // the current LCS(s)
            for (String subStr : allSubstrings.keySet()) {
                if (subStr.length() >= maxLcsLength) {
                    keysStillInPlay.add(subStr);
                }
            }
            // Removing all keys whose lengths are less than the current LCS(s) by leaving them out
            // of the truncated Map.
            // We don't care about substrings that are shorter than the current LCS(s), no matter
            // how many times they appear, as they can never be the LONGEST common substring
            for (String key : allSubstrings.keySet()) {
                if (keysStillInPlay.contains(key)) {
                    truncatedSubStrMap.put(key, allSubstrings.get(key));
                }
            }
            allSubstrings.clear();
            allSubstrings.putAll(truncatedSubStrMap);
            return currentLcsSet;
        } else {
            return retainMax(allSubstrings.keySet(), 0);
        }
    }

    /**
     * Finds the longest common substring(s) between the two passed in Strings
     * @param str1
     * @param str2
     * @return
     */
    public Set<String> oldGetLCSs(String str1, String str2) {
        int max = 0;
        // The matrix created by the cross-section of the two parameter strings
        int[][] matrix = new int[str1.length()][str2.length()];
        Set<String> commonStrSet = new HashSet<>(0);

        // Loop for the first string, which will be the outer string
        for (int outer = 0; outer < str1.length(); outer++) {
            // Loop for the second string, which will be the inner string
            for (int inner = 0; inner < str2.length(); inner++) {
                if (str1.charAt(outer) == str2.charAt(inner)) {
                    // If currently in the first row or first column of the matrix
                    if (outer == 0 || inner == 0) {
                        matrix[outer][inner] = 1;
                    } else {
                        // Adding 1 to the value located one row behind and one column back (diagonally up-left)
                        matrix[outer][inner] = matrix[outer - 1][inner - 1] + 1;
                    }
                    // Updating the 'max' and adding the current max length substring to the lcsSet
                    if (matrix[outer][inner] > max) {
                        max = matrix[outer][inner];
                        commonStrSet.add(str1.substring(outer - max + 1, outer + 1));
                    } else if (matrix[outer][inner] == max) {
                        commonStrSet.add(str1.substring(outer - max + 1, outer + 1));
                    }
                }
            }
        }
        return retainMax(commonStrSet, max);
    }

    /**
     * Takes in a Set of Strings and removes all Strings except for the longest one(s)
     * @param stringSet
     * @param max Can be passed in as 0 when the max length is unknown
     * @return A new Set of Strings containing only the longest String(s) from the passed in Set
     */
    public Set<String> retainMax(Set<String> stringSet, int max) {
        Set<String> maxSet = new HashSet<String>();
        if (max == 0) {
            for (String str : stringSet) {
                if (max < str.length()) {
                    max = str.length();
                }
            }
        }

        for (String str : stringSet) {
            if (str.length() == max) {
                maxSet.add(str);
            }
        }
        return maxSet;
    }
}
