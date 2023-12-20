package com.example.rosaapi.service;

import com.example.rosaapi.utils.exceptions.CalendarServiceException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";
    private static final String APPLICATION_NAME = "ROSA-API";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private GoogleAuthService() {

    }

    public static Calendar getCalendarService() {
        try{
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            GoogleCredentials serviceAccountCredentials = ServiceAccountCredentials
                    .fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                    .createScoped(Collections.singletonList(CalendarScopes.CALENDAR));

            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(serviceAccountCredentials);

            return new Calendar
                    .Builder(httpTransport, JSON_FACTORY, requestInitializer)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

        } catch(IOException | GeneralSecurityException e) {
            throw new CalendarServiceException("An error occurred:"+e);
        }
    }
}
