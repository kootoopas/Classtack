package com.kootoopas.classtack.services;

import static com.kootoopas.classtack.utils.Services.buildNotesService;

/**
 * Created by Fotis on 10/06/2017.
 */

public class NotesServiceSingleton {
    private static final NotesService ourInstance = buildNotesService();

    public static NotesService getInstance() {
        return ourInstance;
    }
}
