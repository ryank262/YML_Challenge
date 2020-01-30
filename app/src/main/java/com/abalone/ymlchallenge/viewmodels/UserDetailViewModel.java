package com.abalone.ymlchallenge.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.abalone.ymlchallenge.model.User;
import com.abalone.ymlchallenge.repositories.ProfileRepository;

public class UserDetailViewModel extends ViewModel {

    private ProfileRepository repo;

    public UserDetailViewModel(){
        repo = ProfileRepository.getInstance(null);
    }

    public LiveData<User> getUserInfo(String query){
        return repo.getUserInfo(query);
    }

}
