package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class handles the actual logic
 */
public class LedControllerImpl implements LedController {
    private final ApiService apiService;

    public LedControllerImpl(ApiService apiService)
    {
        this.apiService = apiService;
    }

    @Override
    public void demo() throws IOException
    {
        // Call `getLights`, the response is a json object in the form `{ "lights": [ { ... }, { ... } ] }`
        JSONObject response = apiService.getLights();
        // get the "lights" array from the response
        JSONArray lights = response.getJSONArray("lights");
        // read the first json object of the lights array
        JSONObject firstLight = lights.getJSONObject(0);
        // read int and string properties of the light
        System.out.println("First light id is: " + firstLight.getInt("id"));
        System.out.println("First light color is: " + firstLight.getString("color"));

        //verschachtelt
        String groupName = GetGroupName(firstLight);
        System.out.println("Gruppe ist: " + groupName);
    }

    public ArrayList<String> getGroupLed() throws IOException
    {
        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");
        ArrayList<String> lightsList = new ArrayList<>();

        for (int i = 0; i < lights.length(); i++)
        {
            JSONObject light = lights.getJSONObject(i);
            String groupName = GetGroupName(light);

            if (groupName.equals("F"))
                lightsList.add(light.getString("state"));
        }

        return lightsList;
    }

    private String GetGroupName(JSONObject light)
    {
        JSONObject groupByGroup = light.getJSONObject("groupByGroup");
        return groupByGroup.getString("name");
    }
}
