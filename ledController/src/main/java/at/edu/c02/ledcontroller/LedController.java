package at.edu.c02.ledcontroller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public interface LedController {
    void demo() throws IOException;

    public ArrayList<String> getGroupLed = null;
}