package com.example.pawsome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,RecyclerView.ViewHolder viewHolder1) {


                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int i) {
                //Log.d(TAG, String.valueOf(i));
                Delete_Dog delete_dog=new Delete_Dog();
                delete_dog.execute(Home_Screen.Fav.get(viewHolder.getAdapterPosition()).getDog_name());
                Log.d(TAG,Home_Screen.Fav.get(viewHolder.getAdapterPosition()).getDog_name());
                Home_Screen.Fav.remove(viewHolder.getAdapterPosition());
                recyclerview_fav_adapter.notifyDataSetChanged();
                recyclerview_fav_adapter=new Recyclerview_Fav_adapter(Home_Screen.Fav);
                recyclerView.setAdapter(recyclerview_fav_adapter);
                Thread thread1=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Home_Screen.Fav=TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().get_fav_dog();
                        Log.d(TAG, String.valueOf(Home_Screen.Fav));
                    }
                });
                thread1.start();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Fav_Dog.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    class Delete_Dog extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... voids) {
            Table_Breed dog=TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().find_the_dog(voids[0]);
            Log.d(TAG, String.valueOf(dog));
            TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().delete_fav(dog);
            return null;
        }
    }
}
