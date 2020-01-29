package com.abalone.ymlchallenge;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SearchDialog extends DialogFragment {

    public interface SearchDialogListener {
        void onDialogSearchClick(DialogFragment dialog, String username);
        void onDialogCancelClick(DialogFragment dialog);
    }

    private SearchDialogListener listener;
    private EditText username_txt;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.search_dialog,null);

        /* Setup the dialog functions */
        builder.setView(view);
        builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!username_txt.getText().toString().isEmpty()){
                    //Prevents searching for nothing
                    listener.onDialogSearchClick(SearchDialog.this, getUserNameText());
                }
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogCancelClick(SearchDialog.this);
            }
        });

        /* Bind the EditText */
        username_txt = view.findViewById(R.id.username_editTxt);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //verify host activity implements interface
        try{
            listener = (SearchDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException("Host activity must implement NoticeDialogListener");
        }
    }

    private String getUserNameText(){
        return username_txt.getText().toString().trim();
    }
}
