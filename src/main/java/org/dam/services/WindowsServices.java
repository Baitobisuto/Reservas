package org.dam.services;

import org.dam.views.InterfaceViews;

import java.util.HashMap;
import java.util.Map;

public class WindowsServices {
    private static Map<String, InterfaceViews> windowsList;

public WindowsServices() {
    windowsList = new HashMap<>();
}

public void registerWindows(String name, InterfaceViews window){
    windowsList.put(name, window);

}

public static InterfaceViews getWindows(String name){
    return windowsList.get(name);
}



}
