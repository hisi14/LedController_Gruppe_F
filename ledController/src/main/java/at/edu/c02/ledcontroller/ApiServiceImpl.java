package at.edu.c02.ledcontroller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class should handle all HTTP communication with the server.
 * Each method here should correspond to an API call, accept the correct parameters and return the response.
 * Do not implement any other logic here - the ApiService will be mocked to unit test the logic without needing a server.
 */
public class ApiServiceImpl implements ApiService {
    /**
     * This method calls the `GET /getLights` endpoint and returns the response.
     * TODO: When adding additional API calls, refactor this method. Extract/Create at least one private method that
     * handles the API call + JSON conversion (so that you do not have duplicate code across multiple API calls)
     *
     */

    private JSONObject getAPICall(String getUrl) throws IOException
    {
        // Connect to the server
        URL url = new URL(getUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // and send a GET request
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Hasura-Group-F", "e3b0c44298fc1c149afbf4c8996fb");
        // Read the response code
        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {
            // Something went wrong with the request
            throw new IOException("Error: getLights request failed with response code " + responseCode);
        }

        // The request was successful, read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        // Save the response in this StringBuilder
        StringBuilder sb = new StringBuilder();

        int character;
        // Read the response, character by character. The response ends when we read -1.
        while((character = reader.read()) != -1) {
            sb.append((char) character);
        }

        String jsonText = sb.toString();
        // Convert response into a json object
        return new JSONObject(jsonText);
    }


     /* @return `getLights` response JSON object
     * @throws IOException Throws if the request could not be completed successfully
     */
    @Override
    public JSONObject getLights() throws IOException
    {
        return getAPICall("https://balanced-civet-91.hasura.app/api/rest/getLights");
    }

    @Override
    public JSONObject getLight(int id) throws IOException
    {
        return getAPICall("https://balanced-civet-91.hasura.app/api/rest/lights/"+id);
    }
}
