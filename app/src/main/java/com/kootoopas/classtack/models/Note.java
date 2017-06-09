package com.kootoopas.classtack.models;

import java.util.Date;

/**
 * Created by Fotis on 08/06/2017.
 */

public class Note {

    private int id;
    private String content;
    private long date;

    public Note() {
    }

    /**
     * Used for note creation.
     * @param content
     */
    public Note(String content) {
        this.content = content;
        this.date = new Date().getTime() / 1000L;
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
        return new Date(date).toString();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
