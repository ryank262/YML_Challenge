package com.abalone.ymlchallenge.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.abalone.ymlchallenge.model.Profile;
import com.abalone.ymlchallenge.model.User;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileApiClient {

    private static final String TAG = "ProfileApiClient";

    /* URLs for GitApi */
    private static final String FOLLOW_PREXIF = "https://api.github.com/users/";
    private static final String FOLLOW_SUFFIX = "/followers";

    private static final String DETAIL_PREFIX = "https://api.github.com/users/";

    /* Volley */
    private static RequestQueue requestQueue;

    private static ProfileApiClient instance;

    private MutableLiveData<List<Profile>> profiles;
    private MutableLiveData<User> userDetails;

    public static ProfileApiClient getInstance(File cacheDir){
        if(instance == null){
            instance = new ProfileApiClient();
            Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024); //1MB cap
            Network network = new BasicNetwork(new HurlStack());
            requestQueue = new RequestQueue(cache, network);
            requestQueue.start();
        }
        return instance;
    }

    private ProfileApiClient() {
        profiles = new MutableLiveData<>();
    }

    public LiveData<List<Profile>> getProfiles(){
        return profiles;
    }

    public void searchProfilesApi(String query) {
        String url = FOLLOW_PREXIF + query.trim() + FOLLOW_SUFFIX;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        profiles.setValue(parseProfiles(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        profiles.setValue(null);
                    }
                });
        requestQueue.add(request);
    }

    public LiveData<User> getUserInfo(String query){
        userDetails = new MutableLiveData<>();
        String url = DETAIL_PREFIX + query;
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userDetails.setValue(parseUserDetails(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                    }
                });
        requestQueue.add(request);
        return userDetails;
    }

    private User parseUserDetails(String raw) {

        User user = null;

        try{
            JSONObject details = new JSONObject(raw);
            String username = details.optString("login", "");
            String full_name = details.optString("name", "");
            int followers = details.getInt("followers");
            int following = details.getInt("following");
            int repositories = details.getInt("public_repos");
            String bio = details.optString("bio", "");
            String company = details.optString("company", "");
            String location = details.optString("location", "");
            String email = details.optString("email", "");
            String avatar_url = details.optString("avatar_url", "");

            user = new User(username, full_name, followers, following, repositories,
                    bio, company, location, email, avatar_url);

        } catch (JSONException e){
            //TODO Handle Exception
        }

        return user;
    }

    private List<Profile> parseProfiles(String raw){

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
            return null;
        }

        return newProfiles;

    }
}
