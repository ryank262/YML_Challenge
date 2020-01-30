package com.abalone.ymlchallenge.repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.model.User;
import com.abalone.ymlchallenge.requests.ProfileApiClient;

import java.io.File;
import java.util.List;

public class ProfileRepository {

    private static final String TAG = "Repository";

    /* Singleton */
    private static ProfileRepository instance;
    private ProfileApiClient profileApiClient;

    public static ProfileRepository getInstance(@Nullable File cacheDir){
        if(instance == null){
            instance = new ProfileRepository(cacheDir);
        }
        return instance;
    }

    private ProfileRepository(File cacheDir){
        profileApiClient = ProfileApiClient.getInstance(cacheDir);
    }

    public LiveData<List<Profile>> getProfiles(){
        return profileApiClient.getProfiles();
    }

    public void searchProfilesApi(String query) {
        profileApiClient.searchProfilesApi(query);
    }

    public LiveData<User> getUserInfo(String query){
        return profileApiClient.getUserInfo(query);
    }
}
