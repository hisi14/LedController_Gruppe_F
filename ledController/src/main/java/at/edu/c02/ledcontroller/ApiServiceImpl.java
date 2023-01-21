package at.edu.c02.ledcontroller;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

    private HttpURLConnection connectAPI(String getUrl, String method) throws IOException
    {
        // Connect to the server
        URL url = new URL(getUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // and send a GET request
        connection.setRequestMethod(method.toUpperCase());
        connection.setRequestProperty("X-Hasura-Group-ID", "e3b0c44298fc1c149afbf4c8996fb");
        if(method.equals("PUT"))
        {
            connection.setDoOutput(true);
        }
        else
        {
            // Read the response code
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                // Something went wrong with the request
                throw new IOException("Error: getLights request failed with response code " + responseCode);
            }
        }

        return connection;
    }

    private JSONObject getAPICall(String getUrl) throws IOException
    {
        // The request was successful, read the response
        BufferedReader reader = new BufferedReader(new InputStreamReader(connectAPI(getUrl, "GET").getInputStream()));
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


    private void putApi(String url, String jsonInputString) throws IOException
    {
        HttpURLConnection connection = connectAPI(url,"PUT");

        OutputStream os  = connection.getOutputStream();
        byte[] input = jsonInputString.getBytes("utf-8");
        os.write(input, 0, input.length);

        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_OK) {
            // Something went wrong with the request
            throw new IOException("Error: getLights request failed with response code " + responseCode);
        }
    }

    @Override
    public void setLight(int id, String color, boolean on) throws IOException
    {
        String url = "https://balanced-civet-91.hasura.app/api/rest/setLight";
        String jsonInputString = "{\"id\":" +id + ",\"color\":\"" +color + "\",\"state\":" + on + "}";
        System.out.println(jsonInputString);

        putApi(url,jsonInputString);
    }
}
