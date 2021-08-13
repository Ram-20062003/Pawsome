package com.example.pawsome;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    Recyclerview_adapter recyclerview_adapter;
    ImageButton imageButton;
    List<String> name_filter=new ArrayList<>();
    List<String> image_url_filter=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#79a9fc"));
        }
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        Log.d(TAG, String.valueOf(Home_Screen.name.size()));
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                Home_Screen.Fav=TableRoomDatabase.getInstance(getApplicationContext()).table_breed_dao().get_fav_dog();
                Log.d(TAG, String.valueOf(Home_Screen.Fav));
            }
        });
        thread1.start();
        recyclerview_adapter=new Recyclerview_adapter(Home_Screen.name,Home_Screen.image_url);
        recyclerView.setAdapter(recyclerview_adapter);
        EditText editText=(EditText)findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this,"no string",Toast.LENGTH_SHORT).show();
                    editText.clearFocus();
                    recyclerview_adapter=new Recyclerview_adapter(Home_Screen.name,Home_Screen.image_url);
                    recyclerView.setAdapter(recyclerview_adapter);
                }

                if(!s.toString().trim().isEmpty()){
                    filter(s.toString().toLowerCase().trim());
                    recyclerview_adapter=new Recyclerview_adapter(name_filter,image_url_filter);
                    recyclerView.setAdapter(recyclerview_adapter);
                    }
            }
        });
    }

    private void filter(String search) {
        if(name_filter!=null)
            name_filter.clear();
        if(image_url_filter!=null)
            image_url_filter.clear();
        for(int i=0;i<Home_Screen.name.size();i++)
        {
            if(Home_Screen.name.get(i).toLowerCase().contains(search))
            {

                name_filter.add(Home_Screen.name.get(i));
                image_url_filter.add(Home_Screen.image_url.get(i));
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.favorites,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.fav)
        {
            Intent intent=new Intent(MainActivity.this,Fav_Dog.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        if(item.getItemId()==R.id.upload_image)
        {
            Intent intent=new Intent(MainActivity.this,Upload.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}