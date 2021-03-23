package com.example.lcswebapp;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import org.json.simple.parser.ParseException;

public class JsonServiceTest {
    private JsonService jsonService = new JsonService();
    private final String TEST_JSON = "{\n" +
            "\"setOfStrings\": [\n" +
            "{\"value\": \"Sonic DX! Adventure\"},\n" +
            "{\"value\": \"Sonic 3 Advance\"},\n" +
            "{\"value\": \"Sonic 3 &Knuckles\"},\n" +
            "]\n" +
            "}";
    private final String TEST_INVALID_JSON = "{\n" +
            "\"foobar\": [\n" +
            "{\"banana\": \"Sonic DX! Adventure\"},\n" +
            "{\"banana\": \"Sonic 3 Advance\"},\n" +
            "{\"banana\": \"Sonic 3 &Knuckles\"},\n" +
            "]\n" +
            "}";

    @Test
    public void testGetJsonValuesAsList() throws ParseException {
        List<String> testValuesList = jsonService.getJsonValuesAsList(TEST_JSON);
        assertEquals(3, testValuesList.size());
        assertEquals("Sonic 3 &Knuckles", testValuesList.get(2));
    }

    @Test(expected = ParseException.class)
    public void testGetJsonValuesAsListWithParseException() throws ParseException {
        List<String> testValuesList = jsonService.getJsonValuesAsList("This is not a JSON");
    }

    @Test
    public void testGetJsonValuesAsListWithInvalidJson() throws ParseException {
        List<String> testValuesList = jsonService.getJsonValuesAsList(TEST_INVALID_JSON);
        assertEquals(0, testValuesList.size());
    }

    @Test
    public void testGetListAsJson() {
        String sortedLcsJson = "{" +
                "\"lcs\":[" +
                "{\"value\":\"DarkMatter\"}," +
                "{\"value\":\"Relativity\"}," +
                "{\"value\":\"Supernovae\"}," +
                "{\"value\":\"Warp Drive\"}" +
                "]" +
                "}";

        List<String> testUnsortedLcsList = new ArrayList<>(0);
        testUnsortedLcsList.add("Warp Drive");
        testUnsortedLcsList.add("DarkMatter");
        testUnsortedLcsList.add("Relativity");
        testUnsortedLcsList.add("Supernovae");

        assertEquals(jsonService.getListAsJson(testUnsortedLcsList), sortedLcsJson);
    }
}
