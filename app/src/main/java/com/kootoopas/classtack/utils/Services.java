package com.kootoopas.classtack.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kootoopas.classtack.services.NotesService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fotis on 10/06/2017.
 */

public class Services {

    public static NotesService buildNotesService() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NotesService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(NotesService.class);
    }
}
