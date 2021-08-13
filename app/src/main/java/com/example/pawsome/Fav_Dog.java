package com.example.pawsome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class Fav_Dog extends AppCompatActivity {
    RecyclerView recyclerView;
    Recyclerview_Fav_adapter recyclerview_fav_adapter;
    private static final String TAG = "Fav_Dog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_fav);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                Home_Screen.Fav=TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().get_fav_dog();
                Log.d(TAG, String.valueOf(Home_Screen.Fav));
                recyclerview_fav_adapter=new Recyclerview_Fav_adapter(Home_Screen.Fav);
                recyclerView.setAdapter(recyclerview_fav_adapter);
            }
        });
        thread1.start();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Fav_Dog.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
