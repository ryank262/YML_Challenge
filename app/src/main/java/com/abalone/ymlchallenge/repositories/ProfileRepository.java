package com.abalone.ymlchallenge.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.requests.ProfileApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileRepository {

    private static final String TAG = "Repository";

    /* Singleton */
    private static ProfileRepository instance;
    private ProfileApiClient profileApiClient;

    public static ProfileRepository getInstance(File cacheDir){
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

/*    private static void initVolley(){
        cache = new DiskBasedCache(cacheDir, 1024 * 1024); //1MB cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
    }*/

/*    public void getFollowers(String username, MutableLiveData<List<Profile>> liveData){

        if(profiles == null){
            profiles = liveData;
            profiles.setValue(new ArrayList<Profile>());
        }
        if(username == null){
            return;
        }

        String url = FOLLOW_PREXIF + username + FOLLOW_SUFFIX;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseProfiles(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO: Handle error
                    }
                });
        requestQueue.add(stringRequest);
    }*/

    private void parseProfiles(String raw){

        List<Profile> newProfiles = new ArrayList<>();

        try{
            JSONArray followers = new JSONArray(raw);

            /* Iterate through JSON array creating Profile Objects*/
            for(int i = 0; i < followers.length(); i++){
                Log.d(TAG, "parseProfiles: parsing: " + i);
                JSONObject follower = followers.getJSONObject(i);
                String login = follower.optString("login", "");
                String avatarUrl = follower.optString("avatar_url", "");
                Log.d(TAG, "parseProfiles: " + login + " " + avatarUrl);
                newProfiles.add(new Profile(avatarUrl, login));
            }
        }catch (JSONException e){
            //TODO Handle exception
        }

        /* Set the data */
        //profiles.setValue(newProfiles);
    }
}
