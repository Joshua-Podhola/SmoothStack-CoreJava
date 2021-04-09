package com.smoothstack.corejava.dayfour;

public class DCLSingleton {
    private static volatile DCLSingleton instance = null;
    //This is only here so my IDE shuts up about this being a utility class; ignore it.
    public int i;

    private DCLSingleton() {
        i = 42;
    }

    /**
     * Get the instance of DCLSingleton. Creates on first call.
     * @return DCLSingleton object
     */
    public static DCLSingleton getInstance() {
        if(instance == null) {
            synchronized (DCLSingleton.class) {
                if(instance == null) instance = new DCLSingleton();
            }
        }
        return instance;
    }
}
