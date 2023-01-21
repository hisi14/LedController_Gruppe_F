package at.edu.c02.ledcontroller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;


public class LedControllerTest {
    /**
     * This test is just here to check if tests get executed. Feel free to delete it when adding your own tests.
     * Take a look at the stack calculator tests again if you are unsure where to start.
     */

    JSONObject lights = new JSONObject();

    @Before
    public void initalize()
    {
        JSONObject light1 = new JSONObject();
        light1.put("id", 5);
        light1.put("color", "#fff");
        light1.put("state", "true");
        JSONObject groupByGroup = new JSONObject();
        groupByGroup.put("name", "F");
        light1.put("groupByGroup", groupByGroup);

        JSONArray lightsList = new JSONArray();
        lightsList.put(light1);

        lights.put("lights", lightsList);

    }
    @Test
    public void getGroupLeds() throws IOException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        ledController.getGroupLed();
        verify(apiService).getLights();
        verifyNoMoreInteractions(apiService);
    }
}
