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
        String groupName = getGroupName(firstLight);
        System.out.println("Gruppe ist: " + groupName);
    }

    @Override
    public ArrayList<String> getGroupLed(Integer id) throws IOException
    {
        JSONObject response = apiService.getLights();
        JSONArray lights = response.getJSONArray("lights");
        ArrayList<String> lightsList = new ArrayList<>();

        if (id == null)
        {
            for (int i = 0; i < lights.length(); i++)
            {
                JSONObject light = lights.getJSONObject(i);
                String groupName = getGroupName(light);

                if (groupName.equals("F"))
                    lightsList.add(getLightDetails(light));
            }
        }
        else
        {
            for (int i = 0; i < lights.length(); i++)
            {
                JSONObject light = lights.getJSONObject(i);
                String groupName = getGroupName(light);

                if (groupName.equals("F") && light.getInt("id") == id)
                    lightsList.add(getLightDetails(light));
            }
        }

        return lightsList;
    }

    private String getLightDetails(JSONObject light)
    {
            String OnOffString = new String();

            if (light.getBoolean("on"))
                OnOffString = "on";
            else
                OnOffString = "off";

            return "LED " + light.getInt("id") + " is currently " + OnOffString + ". Color " + light.getString("color");
    }

    private String getGroupName(JSONObject light)
    {
        JSONObject groupByGroup = light.getJSONObject("groupByGroup");
        return groupByGroup.getString("name");
    }
}
