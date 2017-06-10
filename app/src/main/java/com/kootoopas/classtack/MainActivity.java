package com.kootoopas.classtack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.kootoopas.classtack.internals.BaseActivity;
import com.kootoopas.classtack.models.Note;
import com.kootoopas.classtack.services.NotesService;
import com.kootoopas.classtack.services.NotesServiceSingleton;
import com.kootoopas.classtack.utils.Services;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    private NotesService notesService = NotesServiceSingleton.getInstance();
    private ListView lvNotes;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvNotes = (ListView) findViewById(R.id.lv_notes);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (notes != null) {
                    startActivity(NoteDetailsActivity.getIntent(MainActivity.this, notes.get(position)));
                }
            }
        });

        _getNotesFromServer();

        FloatingActionButton fabCreateNote = (FloatingActionButton) findViewById(R.id.fab_create_note);
        fabCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(CreateNoteActivity.getIntent(MainActivity.this));
            }
        });
    }

    @Override
    protected void onPostResume() {
        _getNotesFromServer();
        // Delegate to super chain.
        super.onPostResume();
    }

    private void _getNotesFromServer() {
        notesService.getAllNotes()
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                        if (response.isSuccessful()) {
                            // Since the notes response is always sorted by id (ASC), list reversal
                            // is sufficient to achieve newest to oldest order.
                            notes = response.body();
                            Collections.sort(notes);

                            ArrayAdapter adapter = _getLvNotesAdapter();
                            lvNotes.setAdapter(adapter);
                        } else {
                            showToast(R.string.msg_notes_not_fetched);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {
                        showToast(R.string.msg_server_error);
                    }
                });
    }

    private ArrayAdapter _getLvNotesAdapter() {
        return new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, notes) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(notes.get(position).getContent());
                text1.setTextColor(getResources().getColor(R.color.black, null));
                text1.setSingleLine();

                text2.setText(notes.get(position).getFormattedDate());
                text2.setTextColor(getResources().getColor(R.color.lightGray, null));

                return view;
            }
        };
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }
}
