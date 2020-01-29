package com.abalone.ymlchallenge.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.repositories.ProfileRepository;

import java.io.File;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private ProfileRepository repo;

    public void init(File cacheDir) {
        repo = ProfileRepository.getInstance(cacheDir);
    }

    public MainActivityViewModel() {

    }

    public LiveData<List<Profile>> getProfiles(){
        return repo.getProfiles();
    }

    public void searchProfilesApi(String query){
        repo.searchProfilesApi(query);
    }
}
