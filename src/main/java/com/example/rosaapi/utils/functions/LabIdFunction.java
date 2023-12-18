package com.example.rosaapi.utils.functions;

public class LabIdFunction {
    private static final String ID_LAB_LINUX = System.getenv("LAB_LINUX");
    private static final String ID_LAB_WINDOWS = System.getenv("LAB_WINDOWS");

    public static String getLabId(int lab) {
        String response = "";
        if (lab == 1) {
            response = ID_LAB_WINDOWS;
        } else if (lab == 2) {
            response =  ID_LAB_LINUX;
        }
        return response;
    }
}
