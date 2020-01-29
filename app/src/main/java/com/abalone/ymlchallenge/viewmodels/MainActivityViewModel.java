package com.abalone.ymlchallenge.viewmodels;

import androidx.lifecycle.ViewModel;

import com.abalone.ymlchallenge.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    public void init(){

    }

    public List<Profile> getProfiles(){
        List<Profile> profs = new ArrayList<>();
        profs.add(new Profile("asdf", "Donald"));
        profs.add(new Profile("asdf", "Barack"));
        profs.add(new Profile("asdf", "George"));
        profs.add(new Profile("asdf", "Bill"));
        profs.add(new Profile("asdf", "George"));
        profs.add(new Profile("asdf", "Ronald"));
        profs.add(new Profile("asdf", "Jimmy"));
        profs.add(new Profile("asdf", "Gerald"));
        profs.add(new Profile("asdf", "Richard"));
        profs.add(new Profile("asdf", "Lyndon"));
        profs.add(new Profile("asdf", "John"));
        profs.add(new Profile("asdf", "Dwight"));
        profs.add(new Profile("asdf", "Harry"));
        profs.add(new Profile("asdf", "Franklin"));
        profs.add(new Profile("asdf", "Herbert"));

        return profs;
    }

}
