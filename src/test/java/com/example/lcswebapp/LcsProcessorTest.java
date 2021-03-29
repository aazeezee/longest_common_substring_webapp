package com.example.lcswebapp;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class LcsProcessorTest {
    private LcsProcessor lcsProcessor = new LcsProcessor();

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
    public void testHandlerStress() {
        List<String> testValuesList = new ArrayList<>(0);
        String str1 = "\"Sonic Adventure\" was released in 1999 on the Sega* Dreamcast*, " +
                "then remade in 2003 for the Nintendo* GameCube *as \"Sonic *Adventure *DX\"";
        String str2 = "The sequel, \"*Adventure *2\" was also first launched on the* Dreamcast* in 2001, " +
                "then ported in an enhanced version for Nintendo's* GameCube *in 2002 as \"Adventure 2: Battle\"";
        String str3 = "Multiple subsequent Sonic games have been considered a de facto '*Adventure *3'," +
                "like a spiritual successor to the 'Adventure' games, much like 'Sonic Mania' is a spiritual" +
                " successor to 'Sonic * &Knuckles*'";
        String str4 = "The quick brown foxy fox jumped over the lazy sleeping dog.... " +
                "BOOTY BUTT, BOOTY BUTT, BOOTY BUTT CHEEKS....* &Knuckles*";
        testValuesList.add(str1);
        testValuesList.add(str2);
        testValuesList.add(str3);
        testValuesList.add(str4);
        List<String> testLcsList = lcsProcessor.handler(testValuesList);

        assertEquals(4, testLcsList.size());
        assertTrue(testLcsList.contains("*Adventure *"));
        assertTrue(testLcsList.contains("* Dreamcast*"));
        assertTrue(testLcsList.contains("* GameCube *"));
        assertTrue(testLcsList.contains("* &Knuckles*"));
    }

    @Test
    public void testOldHandler() {
        List<String> testValuesList = new ArrayList<>(0);
        testValuesList.add("Sonic DX! Adventure");
        testValuesList.add("Sonic 3 Advance");
        testValuesList.add("Sonic 3 &Knuckles");
        testValuesList.add("Sonic Mania &Knuckles");
        testValuesList.add("Sonic 2 Battle Adventure");
        testValuesList.add("Sonic DX! Mega Collection");
        List<String> testLcsList = lcsProcessor.oldHandler(testValuesList);

        assertEquals(3, testLcsList.size());
        assertTrue(testLcsList.contains(" &Knuckles"));
        assertTrue(testLcsList.contains(" Adventure"));
        assertTrue(testLcsList.contains("Sonic DX! "));
    }

    @Test
    public void testOldHandlerStress() {
        List<String> testValuesList = new ArrayList<>(0);
        String str1 = "\"Sonic Adventure\" was released in 1999 on the Sega* Dreamcast*, " +
                "then remade in 2003 for the Nintendo* GameCube *as \"Sonic *Adventure *DX\"";
        String str2 = "The sequel, \"*Adventure *2\" was also first launched on the* Dreamcast* in 2001, " +
                "then ported in an enhanced version for Nintendo's* GameCube *in 2002 as \"Adventure 2: Battle\"";
        String str3 = "Multiple subsequent Sonic games have been considered a de facto '*Adventure *3'," +
                "like a spiritual successor to the 'Adventure' games, much like 'Sonic Mania' is a spiritual" +
                " successor to 'Sonic * &Knuckles*'";
        String str4 = "The quick brown foxy fox jumped over the lazy sleeping dog.... " +
                "BOOTY BUTT, BOOTY BUTT, BOOTY BUTT CHEEKS....* &Knuckles*";
        testValuesList.add(str1);
        testValuesList.add(str2);
        testValuesList.add(str3);
        testValuesList.add(str4);
        List<String> testLcsList = lcsProcessor.oldHandler(testValuesList);

        assertEquals(4, testLcsList.size());
        assertTrue(testLcsList.contains("*Adventure *"));
        assertTrue(testLcsList.contains("* Dreamcast*"));
        assertTrue(testLcsList.contains("* GameCube *"));
        assertTrue(testLcsList.contains("* &Knuckles*"));
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