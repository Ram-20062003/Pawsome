package com.example.pawsome;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home_Screen extends AppCompatActivity {
    public static List<String> name=new ArrayList<>();
    public static List<String> image_url=new ArrayList<>();
    public static List<Table_Breed> Fav=new ArrayList<>();
    public static List<String> Un_Fav=new ArrayList<>();
    private static final String TAG = "Home_Screen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#0b1f6e"));
        }
        TextView t_main=(TextView)findViewById(R.id.pawsome);
        TextView t_sub=(TextView)findViewById(R.id.pawsome_text);
        ImageView imageView=(ImageView)findViewById(R.id.pawsome_image);
        Animation animation_image= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fading);
        imageView.setAnimation(animation_image);
        Animation animation_main=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.down);
        t_main.setAnimation(animation_main);
        t_sub.setAnimation(animation_main);
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonHolder jsonHolder=retrofit.create(JsonHolder.class);
        Call<List<Breed>> call=jsonHolder.getBreed();
        call.enqueue(new Callback<List<Breed>>() {
            @Override
            public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                if(!response.isSuccessful())
                {
                    Log.d(TAG,"Code="+response.code());
                    return;
                }
                if(response.isSuccessful()){
                    List<Breed> breedList=response.body();
                    for(Breed breed:breedList){
                        name.add(breed.name);
                        image_url.add(breed.image.getUrl());
                    }
                    Log.d(TAG, "length in response inside=" +name.size() +"\t");
                }}
            @Override
            public void onFailure(Call<List<Breed>> call, Throwable t) {
                Log.d(TAG,"Error message="+t.getMessage());
            }
        });
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                Home_Screen.Fav=TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().get_fav_dog();
                Log.d(TAG, String.valueOf(Home_Screen.Fav));
            }
        });
        thread1.start();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(7000);
                   // Log.d(TAG, "length in response=" +name.get(0) +"\t");
                    Intent intent=new Intent(Home_Screen.this,MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
thread.start();
    }
}

