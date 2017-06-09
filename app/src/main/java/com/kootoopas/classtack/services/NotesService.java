package com.kootoopas.classtack.services;

import com.kootoopas.classtack.models.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotesService {

    static final String BASE_URL = "http://notes-sdmdcity.rhcloud.com/rest/";
    static final String NOTES_URL = "notes";

    @GET(NOTES_URL)
    Call<List<Note>> getAllNotes();

    @POST(NOTES_URL)
    Call<Integer> createNote(@Body Note note);

    @DELETE(NOTES_URL + "/{id}")
    Call<Void> deleteNote(@Path("id") int id);
}
