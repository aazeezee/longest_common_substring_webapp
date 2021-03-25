package com.example.lcswebapp;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class LcsProcessorTest {
    private LcsProcessor lcsProcessor = new LcsProcessor();

    @Test
    public void testGetLcs() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add(" Adventure");
        String str1 = "Sonic 2 Battle Adventure";
        String str2 = "Sonic DX! Adventure";
        List<String> testInputList = new ArrayList<String>(Arrays.asList(str1, str2));
        Set<String> actualResultSet = lcsProcessor.getLongestCommonSubstrings(testInputList);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains(" Adventure"));
    }

    @Test
    public void testGetLcsWithMultipleCommonSubstrings() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add("*Adventure *");
        testResultSet.add("* Dreamcast*");
        testResultSet.add("* GameCube *");
        String str1 = "\"Sonic Adventure\" was released in 1999 on the Sega* Dreamcast*, " +
                "then remade in 2003 for the Nintendo* GameCube *as \"Sonic *Adventure *DX\"";
        String str2 = "The sequel, \"*Adventure *2\" was also first launched on the* Dreamcast* in 2001, " +
                "then ported in an enhanced version for Nintendo's* GameCube *in 2002 as \"Adventure 2: Battle\"";
        String str3 = "Will there be an '*Adventure *3'? No one really knows";
        List<String> testInputList = new ArrayList<String>(Arrays.asList(str1, str2, str3));
        Set<String> actualResultSet = lcsProcessor.getLongestCommonSubstrings(testInputList);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains("*Adventure *"));
        assertTrue(actualResultSet.contains("* Dreamcast*"));
        assertTrue(actualResultSet.contains("* GameCube *"));
    }

    @Test
    public void testGetLcsWithNoCommonSubstrings() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add("56789_@$#&");
        String str1 = "ABCDEFG";
        String str2 = "HIJKLMN";
        String str3 = "OPQRSTUV";
        String str4 = "WXYZ?!";
        String str5 = "01234";
        String str6 = "56789_@$#&";
        List<String> testInputList = new ArrayList<String>(Arrays.asList(str1, str2, str3, str4, str5, str6));
        Set<String> actualResultSet = lcsProcessor.getLongestCommonSubstrings(testInputList);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains("56789_@$#&"));
    }

    @Test
    public void testGetLcsWithNoCommonSubstringsMultipleLongest() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add("RSTUVWXYZ?!");
        testResultSet.add("456789_@$#&");
        String str1 = "ABCDEFG";
        String str2 = "HIJKLMNOPQ";
        String str3 = "RSTUVWXYZ?!";
        String str4 = "0123";
        String str5 = "456789_@$#&";
        List<String> testInputList = new ArrayList<String>(Arrays.asList(str1, str2, str3, str4, str5));
        Set<String> actualResultSet = lcsProcessor.getLongestCommonSubstrings(testInputList);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains("RSTUVWXYZ?!"));
        assertTrue(actualResultSet.contains("456789_@$#&"));
    }

    @Test
    public void testHandler() {
        List<String> testValuesList = new ArrayList<>(0);
        testValuesList.add("Sonic DX! Adventure");
        testValuesList.add("Sonic 3 Advance");
        testValuesList.add("Sonic 3 &Knuckles");
        testValuesList.add("Sonic Mania &Knuckles");
        testValuesList.add("Sonic 2 Battle Adventure");
        testValuesList.add("Sonic DX! Mega Collection");
        List<String> testLcsList = lcsProcessor.handler(testValuesList);

        assertEquals(3, testLcsList.size());
        assertTrue(testLcsList.contains(" &Knuckles"));
        assertTrue(testLcsList.contains(" Adventure"));
        assertTrue(testLcsList.contains("Sonic DX! "));
    }

    @Test
    public void testRetainMax() {
        Set<String> testSet = new HashSet<>(0);
        testSet.add("Big");
        testSet.add("Bigger");
        testSet.add("Humungous");
        testSet.add("The Entire Universe");
        testSet.add("Biggest Of All Time");
        Set<String> resultSet = lcsProcessor.retainMax(testSet, 0);

        assertEquals(2, resultSet.size());
        assertTrue(resultSet.contains("The Entire Universe"));
        assertTrue(resultSet.contains("Biggest Of All Time"));
    }

    @Test
    public void testOldGetLongestCommonSubstring() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add(" Adventure");
        String str1 = "Sonic 2 Battle Adventure";
        String str2 = "Sonic DX! Adventure";
        Set<String> actualResultSet = lcsProcessor.oldGetLCSs(str1, str2);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains(" Adventure"));
    }

    @Test
    public void testOldGetLongestCommonSubstringWithMultipleCommonSubstrings() {
        Set<String> testResultSet = new HashSet<>(0);
        testResultSet.add("*Adventure *");
        testResultSet.add("* Dreamcast*");
        testResultSet.add("* GameCube *");
        String str1 = "\"Sonic Adventure\" was released in 1999 on the Sega* Dreamcast*, " +
                "then remade in 2003 for the Nintendo* GameCube *as \"Sonic *Adventure *DX\"";
        String str2 = "The sequel, \"*Adventure *2\" was also first launched on the* Dreamcast* in 2001, " +
                "then ported in an enhanced version for Nintendo's* GameCube *in 2002 as \"Adventure 2: Battle\"";
        Set<String> actualResultSet = lcsProcessor.oldGetLCSs(str1, str2);

        assertEquals(testResultSet.size(), actualResultSet.size());
        assertTrue(actualResultSet.contains("*Adventure *"));
        assertTrue(actualResultSet.contains("* Dreamcast*"));
        assertTrue(actualResultSet.contains("* GameCube *"));
    }
}