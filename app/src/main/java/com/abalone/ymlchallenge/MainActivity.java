package com.abalone.ymlchallenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.abalone.ymlchallenge.adapters.AvatarGalleryAdapter;
import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchDialog.SearchDialogListener {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 3;

    /* UI Elements */
    private RecyclerView avatars_recycler_view;

    private AvatarGalleryAdapter avatars_adapter;
    private MainActivityViewModel mMainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bind UI */
        avatars_recycler_view = findViewById(R.id.avatars_recycler_view);
        setTitle("Home");

        /* Initialize ViewModel */
        mMainActivityViewModel = new ViewModelProvider(MainActivity.this).get(MainActivityViewModel.class);
        mMainActivityViewModel.init(getCacheDir());
        mMainActivityViewModel.getProfiles().observe(this, new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                avatars_adapter.setProfiles(profiles);
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
        avatars_adapter = new AvatarGalleryAdapter(mMainActivityViewModel.getProfiles().getValue());
        avatars_recycler_view.setLayoutManager(new GridLayoutManager(this, NUM_COLUMNS));
        avatars_recycler_view.setAdapter(avatars_adapter);
    }
}
