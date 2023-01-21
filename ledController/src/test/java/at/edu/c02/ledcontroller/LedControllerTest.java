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
        light1.put("on", "true");
        JSONObject groupByGroup = new JSONObject();
        groupByGroup.put("name", "F");
        light1.put("groupByGroup", groupByGroup);

        JSONArray lightsList = new JSONArray();
        lightsList.put(light1);

        lights.put("lights", lightsList);

    }

    @Test
    public void getLight() throws IOException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        ledController.getGroupLed(1);
        verify(apiService).getLights();
        verifyNoMoreInteractions(apiService);
    }

    @Test
    public void getGroupLeds() throws IOException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        ledController.getGroupLed(null);
        verify(apiService).getLights();
        verifyNoMoreInteractions(apiService);
    }

    @Test
    public void getGroupLedsMain() throws IOException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        assertEquals("[LED 5 is currently on. Color #fff]", ledController.getGroupLed(null).toString());
    }

    @Test
    public void EndToEndSetLight() throws IOException
    {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setLight(1,"#f0f",true);
    }


    @Test
    public void turnOffAllLeds() throws IOException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        ledController.turnOffAllLeds();
        verify(apiService).getLights();
        verify(apiService).setLight(5, "#fff", false);
        verifyNoMoreInteractions(apiService);
    }

    @Test
    public void lauflicht() throws IOException, InterruptedException
    {
        ApiServiceImpl apiService = mock(ApiServiceImpl.class);
        when(apiService.getLights()).thenReturn(lights);
        LedControllerImpl ledController = new LedControllerImpl(apiService);
        ledController.lauflicht("#f0f", 1);
        verify(apiService, times(3)).getLights();
        verify(apiService, times(2)).setLight(5, "#fff", false);

        verify(apiService).setLight(5, "#f0f", true);
        verify(apiService).setLight(5, "#f0f", false);
        verifyNoMoreInteractions(apiService);
    }


}
