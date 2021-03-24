# Longest Common Substring Web App

## Functional Requirements
Users should be able to request the longest common substring (LCS) of a set of Strings by sending a POST request to the server at http://<host>/lcs. 
The body of the POST request must be a JSON object representing a set of strings in the following format:

```{
	"setOfStrings": [
		{"value": "supersonic"},
		{"value": "sonically"},
		{"value": "sonic the hedgehog"}
	]
}```

The server should enforce the following rules upon receiving a request to solve LCS:
- If there is no POST body in the request or if the POST body is not in the correct format, the server should respond with an appropriate HTTP status code, and a response body explaining that the format of the request was not acceptable.
- If `setOfStrings` is empty the server should respond with an appropriate HTTP status code with a response body explaining that `setOfStrings` should not be empty.
- If the `setOfStrings` supplied is not a Set (i.e. there are duplicate stsings) the server should respond with an appropriate HTTP status code, and a response body explaining that `setOfStrings` must be a Set
- If the above conditions are met, the server should run the algorithm to find the LCS among each `value` in the POST body. 
- Once the server has computed the LCS, it should respond with an appropriate HTTP status code and a body in the following format:

```{
	"lcs": [
		{"value": "sonic"}
	]
}```


### Examples:
1. POST body:

```{
	"setOfStrings": [
		{"value": "supersonic"},
		{"value": "sonically"},
		{"value": "sonic the hedgehog"}
	]
}```

LCS should be {"sonic"}.

2. POST body:

```{
	"setOfStrings": [
		{"value": "Sonic Adventure"},
		{"value": "Sega Dreamcast"},
		{"value": "Jet Set Radio was released for the Dreamcast in 2000"},
		{"value": "Sonic Rush Adventure"}
	]
}```

LCS(s) should be {" Adventure", " Dreamcast"}.
If there are more than one LCS, they should all be returned in alphabetical order.

3. POST body:

```{
	"setOfStrings": [
		{"value": "ABCDEFG"},
		{"value": "HIJKLMN"},
		{"value": "OPQRSTU"},
		{"value": "VWXYZ?!"},
		{"value": "123456789"}
	]
}```

LCS(s) should be {"123456789"}.
If there aren't any common substrings, just return the longest substring(s) available.

## How To Run
In order to run the code, you can use this Maven command to run Apache Tomcat:
`mvn tomcat7:run`

The web app can then be visited by going to this link in a web browser:
http://localhost:8080/LongestCommonSubstringWebApp/ 
