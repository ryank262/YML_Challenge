package com.abalone.ymlchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abalone.ymlchallenge.adapters.AvatarGalleryAdapter;
import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchDialog.SearchDialogListener {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 3;

    /* UI Elements */
    private RecyclerView avatars_recycler_view;
    private ProgressBar progressBar;
    private TextView error_txt;

    private AvatarGalleryAdapter avatars_adapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bind UI */
        avatars_recycler_view = findViewById(R.id.avatars_recycler_view);
        progressBar = findViewById(R.id.progressBar);
        error_txt = findViewById(R.id.error_txt);
        setTitle("Home");

        /* Initialize ViewModel */
        mMainActivityViewModel = new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(getCacheDir());
        mMainActivityViewModel.getProfiles().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if(profiles == null){
                    //User not found
                    avatars_adapter.setProfiles(new ArrayList<Profile>()); //Pass empty list
                    error_txt.setText(R.string.user_not_found);
                    error_txt.setVisibility(View.VISIBLE);
                }else if(profiles.size() == 0){
                    //User has no followers
                    avatars_adapter.setProfiles(profiles);
                    error_txt.setText(R.string.no_followers);
                    error_txt.setVisibility(View.VISIBLE);
                }else{
                    avatars_adapter.setProfiles(profiles);
                    error_txt.setVisibility(View.INVISIBLE);

                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        initRecyclerView();
    }

    /* Add search to the action bar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                showSearchDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDialogSearchClick(DialogFragment dialog, String username){
        error_txt.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mMainActivityViewModel.searchProfilesApi(username);
        setTitle("Home: " + username);
        dialog.dismiss();
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog){
        dialog.dismiss();
    }

    private void showSearchDialog() {
        DialogFragment dialog = new SearchDialog();
        dialog.show(getSupportFragmentManager(), "SearchDialog");
    }

    private void initRecyclerView(){
        avatars_adapter = new AvatarGalleryAdapter(this, mMainActivityViewModel.getProfiles().getValue());
        avatars_recycler_view.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
        avatars_recycler_view.setAdapter(avatars_adapter);
    }
}
