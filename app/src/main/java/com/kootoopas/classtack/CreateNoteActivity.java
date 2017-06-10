package com.kootoopas.classtack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kootoopas.classtack.internals.BaseActivity;
import com.kootoopas.classtack.models.Note;
import com.kootoopas.classtack.services.NotesService;
import com.kootoopas.classtack.services.NotesServiceSingleton;
import com.kootoopas.classtack.ui.ActionEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Fotis on 09/06/2017.
 */

public class CreateNoteActivity extends BaseActivity {

    private static final String ET_CONTENT_STRING_KEY = "et_content_string";
    private NotesService notesService = NotesServiceSingleton.getInstance();
    private ActionEditText etContent;

    public static Intent getIntent(Context context) {
        return new Intent(context, CreateNoteActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        setActionBarTitle(R.string.create_new_note);

        setupEtContent(savedInstanceState);
    }

    private void setupEtContent(@Nullable Bundle savedInstanceState) {
        etContent = (ActionEditText) findViewById(R.id.et_content);

//        // +UX: Restore saved note text.
//        if (savedInstanceState != null) {
//            etContent.setText(savedInstanceState.getString(ET_CONTENT_STRING_KEY));
//        }

        etContent.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etContent, InputMethodManager.SHOW_IMPLICIT);


        etContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    sendNoteToServer();
                    return true;
                }
                return false;
            }
        });
    }


    private void sendNoteToServer() {
        notesService.createNote(
                new Note(etContent.getText().toString())
        )
                .enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("sendNoteToServer", response.code() + "");
                        if (response.isSuccessful()) {
                            showToast(R.string.note_created);

                            // Empty EditText.
                            etContent.setText("");

                            startActivity(MainActivity.getIntent(CreateNoteActivity.this));
                        } else {
                            showToast(R.string.note_not_created);
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        showToast(R.string.note_not_created);
                    }
                });
    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        // +UX: Save written contents in case user accidentally exits activity. (back button makes this not work :/)
//        outState.putString(ET_CONTENT_STRING_KEY, etContent.getText().toString());
//        super.onSaveInstanceState(outState);
//    }
}
