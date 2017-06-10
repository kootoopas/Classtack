package com.kootoopas.classtack.models;

import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Fotis on 08/06/2017.
 */

public class Note implements Comparable<Note> {

    @Expose(serialize = false)
    private int id;

    @Expose
    private String content;

    @Expose
    private long date;

    public Note() {
    }

    /**
     * Used for note creation.
     * @param content
     */
    public Note(String content) {
        this.content = content;
        this.date = System.currentTimeMillis();
    }

    /**
     * Used to instantiate note from server response.
     * @param id
     * @param content
     * @param date
     */
    public Note(int id, String content, int date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public long getDate() {
        return date;
    }

    public String getFormattedDate() {
        return DateFormat.format("MM/dd/yyyy hh:mm:ss a", new Date(date)).toString();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(@NonNull Note o) {
        return (int) (o.getDate() - date);
    }
}
