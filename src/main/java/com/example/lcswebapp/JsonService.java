package com.example.lcswebapp;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import java.util.*;

public class JsonService {
    /**
     * Takes in a String expected to be in JSON format, then returns a List of Strings
     * built from the list of values contained in the JSONObject signified by "setOfStrings"
     * @param jsonAsString
     * @return List of Strings comprised of the values from the passed in JSON
     * @throws ParseException
     */
    public List<String> getJsonValuesAsList(String jsonAsString) throws ParseException {
        List<String> jsonValuesAsList = new ArrayList<>(0);
        List<JSONObject> jsonObjectList = new ArrayList<>(0);

        JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(jsonAsString);
        if (jsonObject.get("setOfStrings") != null) {
            jsonObjectList.addAll((List<JSONObject>) jsonObject.get("setOfStrings"));
        }
        for (JSONObject obj : jsonObjectList) {
            if (obj.get("value") != null) {
                jsonValuesAsList.add((String) obj.get("value"));
            }
        }
        return jsonValuesAsList;
    }

    /**
     * Takes in a List of Strings and constructs a JSON String using the values from the List
     * @param valueList
     * @return A JSON String
     */
    public String getListAsJson(List<String> valueList) {
        List<JSONObject> jsonObjectList = new ArrayList<>(0);

        Collections.sort(valueList);
        for (String str : valueList) {
            JSONObject obj = new JSONObject();
            obj.put("value", str);
            jsonObjectList.add(obj);
        }
        JSONObject lcsOuterJson = new JSONObject();
        lcsOuterJson.put("lcs", jsonObjectList);

        return lcsOuterJson.toJSONString();
    }
}
