package com.kootoopas.classtack;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kootoopas.classtack.internals.BaseActivity;
import com.kootoopas.classtack.models.Note;
import com.kootoopas.classtack.services.NotesService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fotis on 09/06/2017.
 */

public class NoteDetailsActivity extends BaseActivity {

    private static final String EXTRA_CONTENT = "content";
    private static final String EXTRA_DATE = "date";
    private static final String EXTRA_ID = "id";

    private int id;

    // This is a good practice to ensure that your Activity can get all the extras in an easy way
    public static Intent getIntent(Context context, Note note) {
        return new Intent(context, NoteDetailsActivity.class)
            .putExtra(EXTRA_CONTENT, note.getContent())
            .putExtra(EXTRA_DATE, note.getFormattedDate())
            .putExtra(EXTRA_ID, note.getId());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        Intent intent = getIntent();
        String content = intent.getStringExtra(EXTRA_CONTENT);
        String date = intent.getStringExtra(EXTRA_DATE);
        id = intent.getIntExtra(EXTRA_ID, 0);

        TextView tvContent = (TextView) findViewById(R.id.content);
        TextView tvDate = (TextView) findViewById(R.id.date);

        tvContent.setText(content);
        tvDate.setText(date);

        setActionBarTitle(R.string.note_details);

        Button btnDelete = (Button) findViewById(R.id.btn_delete_note);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _deleteNote();
            }
        });
    }

    private void _deleteNote() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NotesService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NotesService notesService = retrofit.create(NotesService.class);

        notesService.deleteNote(id)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()) {
                            case 200:
                                showToast(R.string.msg_note_deleted);
                                break;
                            case 304:
                                // NOTE: I could have shown the following message. However, if a
                                // user would tap multiple times to delete the note he'd get
                                // msg_note_deleted followed by R.string.msg_note_already_deleted,
                                // therefore bad UX.
                                // showToast(R.string.msg_note_already_deleted);
                                break;
                        }

                        startActivity(MainActivity.getIntent(NoteDetailsActivity.this));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showToast(R.string.msg_note_not_deleted);
                    }
                });
    }
}
