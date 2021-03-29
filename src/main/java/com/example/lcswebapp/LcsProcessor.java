package com.example.lcswebapp;

import java.util.*;

public class LcsProcessor {
    /**
     * Custom data structure for storing a Map (where the Keys are substrings and the Values are Sets of
     * original strings to which the substrings belong), and the maxLength of the longest substring Key
     */
    class MapMaxPair {
        public Map<String, Set<String>> substringMap = new HashMap<String, Set<String>>();
        public Integer maxLength = 0;

        public MapMaxPair() {}
        public MapMaxPair(Map<String, Set<String>> substringMap, int maxLength) {
            this.substringMap.putAll(substringMap);
            this.maxLength = maxLength;
        }
    }

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
            Set<String> lcsSet = new HashSet<String>();
            MapMaxPair mapMaxPair = getAllCommonSubstrings(values);
            Map<String, Set<String>> commonSubstrMap = mapMaxPair.substringMap;
            int maxLcsLength = mapMaxPair.maxLength;
            for (String subStr : commonSubstrMap.keySet()) {
                if (subStr.length() == maxLcsLength) {
                    lcsSet.add(subStr);
                }
            }
            List<String> lcsList = new ArrayList<String>(lcsSet);
            Collections.sort(lcsList);
            return lcsList;
        }
    }

    /**
     * Finds all common substrings of every String in the passed-in List of Strings,
     * then stores these substrings as keys in a Map, where the value of each key is a
     * Set of Strings. These Sets of String values will each be a Set of the original String(s)
     * to which the key is a substring of.
     * For example: "Sonic" is a substring of both "Sonic Adventure" and "Sonic & Knuckles",
     * and would therefore lead to an entry in the Map where the key-value pair is:
     * Key = "Sonic", Value = {"Sonic Adventure", "Sonic & Knuckles"}
     *
     * Returns a Map of all common substrings paired with the length of the longest substring Key
     *
     * If there are no common substrings found, then a Map of all substrings is returned, paired with
     * the length of the longest substring
     * @param originalStrings
     * @return
     */
    public MapMaxPair getAllCommonSubstrings(List<String> originalStrings) {
        Map<String, Set<String>> allSubstrings = new HashMap<String, Set<String>>();
        Map<String, Set<String>> commonSubstrings = new HashMap<String, Set<String>>();
        int maxSubstrLength = 0;
        int maxLcsLength = 0;
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
                        Set<String> originalStrSet = allSubstrings.get(subStr);
                        // Handling the case where there are duplicate substrings in an original string
                        // E.g.: "KA ME HA ME HA !"
                        // "ME", "ME HA", and "HA" are repeated, which could lead to miscalculation of the maxLcsLength
                        if (!originalStrSet.contains(original)) {
                            originalStrSet.add(original);
                            commonSubstrings.put(subStr, originalStrSet);
                            if (maxLcsLength < subStr.length()) {
                                maxLcsLength = subStr.length();
                            }
                        }
                    }
                    // Retrieving the max length of all substrings, regardless of whether an actual LCS has been found
                    // This accounts for the case where there aren't any common substrings
                    if (maxSubstrLength < subStr.length()) {
                        maxSubstrLength = subStr.length();
                    }
                }
            }
        }
        if (commonSubstrings.keySet().size() > 0) {
            return new MapMaxPair(commonSubstrings, maxLcsLength);
        } else {
            return new MapMaxPair(allSubstrings, maxSubstrLength);
        }
    }

    public List<String> oldHandler(List<String> values) {
        Set<String> lcsSet = new HashSet<String>();
        if (values.size() == 1) {
            return values;
        } else {
            for (int str1 = 0; str1 < values.size() - 1; str1++) {
                for (int str2 = str1 + 1; str2 < values.size(); str2++) {
                    lcsSet.addAll(oldGetLCSs(values.get(str1), values.get(str2)));
                }
            }
        }
        return new ArrayList<String>(retainMax(lcsSet, 0));
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
