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
        Set<String> lcsSet = new HashSet<>(0);

        if (values.size() == 1) {
            return values;
        } else {
            for (int str1 = 0; str1 < values.size() - 1; str1++) {
                for (int str2 = str1 + 1; str2 < values.size(); str2++) {
                    lcsSet.addAll(getLongestCommonSubstrings(values.get(str1), values.get(str2)));
                }
            }
        }
        return new ArrayList<String>(retainMax(lcsSet));
    }

    /**
     * Finds the longest common substring(s) between the two passed in Strings
     * @param str1
     * @param str2
     * @return
     */
    public Set<String> getLongestCommonSubstrings(String str1, String str2) {
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
     * @return A new Set of Strings containing only the longest String(s) from the passed in Set
     */
    public Set<String> retainMax(Set<String> stringSet) {
        Set<String> maxSet = new HashSet<>(0);
        int max = 0;
        for (String str : stringSet) {
            if (max < str.length()) {
                max = str.length();
            }
        }

        for (String str : stringSet) {
            if (str.length() == max) {
                maxSet.add(str);
            }
        }
        return maxSet;
    }

    /**
     * Overloaded version of retainMax() with max value passed in
     * @param stringSet
     * @param max
     * @return A new Set of Strings containing only the longest String(s) from the passed in Set
     */
    public Set<String> retainMax(Set<String> stringSet, int max) {
        Set<String> maxSet = new HashSet<>(0);
        for (String str : stringSet) {
            if (str.length() == max) {
                maxSet.add(str);
            }
        }
        return maxSet;
    }
}
