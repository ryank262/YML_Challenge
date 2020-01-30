package com.abalone.ymlchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abalone.ymlchallenge.model.User;
import com.abalone.ymlchallenge.viewmodels.UserDetailViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UserDetailActivity extends AppCompatActivity {

    /* UI Elements */
    private ImageView blurred_avatar;
    private CircleImageView avatar;
    private TextView username_txt;
    private TextView full_name_txt;
    private TextView followers_cnt_txt;
    private TextView following_cnt_txt;
    private TextView repositories_cnt_txt;
    private TextView bio_txt;
    private TextView company_txt;
    private TextView location_txt;
    private TextView email_txt;

    private LinearLayout bio_layout;
    private LinearLayout company_layout;
    private LinearLayout location_layout;
    private LinearLayout email_layout;

    private UserDetailViewModel mUserDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        /* Bind UI */
        blurred_avatar = findViewById(R.id.blurred_avatar);
        avatar = findViewById(R.id.detail_avatar_image);
        username_txt = findViewById(R.id.username_txt);
        full_name_txt = findViewById(R.id.fullname_text);
        followers_cnt_txt = findViewById(R.id.followers_cnt_txt);
        following_cnt_txt = findViewById(R.id.following_cnt_txt);
        repositories_cnt_txt = findViewById(R.id.repositories_cnt_txt);
        bio_txt = findViewById(R.id.bio_txt);
        company_txt = findViewById(R.id.company_txt);
        location_txt = findViewById(R.id.location_txt);
        email_txt = findViewById(R.id.email_txt);

        bio_layout = findViewById(R.id.bio_layout);
        company_layout = findViewById(R.id.company_layout);
        location_layout = findViewById(R.id.location_layout);
        email_layout = findViewById(R.id.email_layout);
        
        bio_layout.setVisibility(View.GONE);
        company_layout.setVisibility(View.GONE);
        location_layout.setVisibility(View.GONE);
        email_layout.setVisibility(View.GONE);

        /* Shared Element Transition */
        String transName = getIntent().getStringExtra("transition_name");
        avatar.setTransitionName(transName);
        String avatarUrl = getIntent().getStringExtra("avatar_url");
        Glide.with(this)
                .load(avatarUrl)
                .into(avatar);

        /* Initialize ViewModel */
        mUserDetailViewModel = new ViewModelProvider(UserDetailActivity.this).get(UserDetailViewModel.class);
        mUserDetailViewModel.getUserInfo(getIntent().getStringExtra("username")).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null){
                    setUI(user);
                }
            }
        });
    }

    private void setUI(User user) {
        username_txt.setText(user.getUsername());

        /* Name can be "null" */
        if(isNull(user.getFull_name())){
            full_name_txt.setText("");
        }else{
            full_name_txt.setText(user.getFull_name());
        }

        followers_cnt_txt.setText(Integer.toString(user.getFollowers()));
        following_cnt_txt.setText(Integer.toString(user.getFollowing()));
        repositories_cnt_txt.setText(Integer.toString(user.getRepositories()));

        String bio = user.getBio();
        String company = user.getCompany();
        String location = user.getLocation();
        String email = user.getEmail();
        String avatar_url = user.getAvatar_url();

        /* Set the background avatar */
        RequestOptions requestOptions = new RequestOptions();

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(avatar_url)
                .transform(new BlurTransformation())
                .into(blurred_avatar);

        /* Show and hide the appropriate views*/
        if(isNull(bio)){
            bio_layout.setVisibility(View.GONE);
        }else{
            bio_txt.setText(bio);
            bio_layout.setVisibility(View.VISIBLE);
        }

        if(isNull(company)){
            company_layout.setVisibility(View.GONE);
        }else{
            company_txt.setText(company);
            company_layout.setVisibility(View.VISIBLE);

        }
        
        if(isNull(location)){
            location_layout.setVisibility(View.GONE);
        }else{
            location_txt.setText(location);
            location_layout.setVisibility(View.VISIBLE);
        }
        
        if(isNull(email)){
            email_layout.setVisibility(View.GONE);
        }else{
            email_txt.setText(email);
            email_layout.setVisibility(View.VISIBLE);
        }

    }

    private boolean isNull(String s){
        if(s.equals("null")){
            return true;
        }else{
            return false;
        }
    }


}
