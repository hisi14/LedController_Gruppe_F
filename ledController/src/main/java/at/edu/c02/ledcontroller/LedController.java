package at.edu.c02.ledcontroller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public interface LedController {
    void demo() throws IOException;
    ArrayList<String> getGroupLed(Integer id) throws IOException;
    void turnOffAllLeds() throws IOException;
    void lauflicht(String color, int turns) throws IOException, InterruptedException;
    void setLedColor(int id, String color) throws IOException;
}