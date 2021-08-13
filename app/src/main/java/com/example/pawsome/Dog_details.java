package com.example.pawsome;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Dog_details extends AppCompatActivity {
    private final String TAG="Dog_details";
    TextView text_bred_for,text_breed_group,text_description,text_himperial,text_hmetric,text_wimperial,text_wmetric,text_origin,text_lifespan,text_temperament,text_name;
    ImageView imageView;
    String details="";
    //String check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        imageView=(ImageView)findViewById(R.id.image_pos);
        text_name=(TextView)findViewById(R.id.name);
        text_bred_for=(TextView)findViewById(R.id.text_bred_for);
        text_breed_group=(TextView)findViewById(R.id.text_breed_grp);
        text_description=(TextView)findViewById(R.id.text_description);
        text_himperial=(TextView)findViewById(R.id.text_imperial_height);
        text_hmetric=(TextView)findViewById(R.id.text_metric_height);
        text_wimperial=(TextView)findViewById(R.id.text_imperial_weight);
        text_wmetric=(TextView)findViewById(R.id.text_metric_weight);
        text_origin=(TextView)findViewById(R.id.text_origin);
        text_lifespan=(TextView)findViewById(R.id.text_life_span);
        text_temperament=(TextView)findViewById(R.id.text_temperament);

      /*  Intent intent=new Intent();
        check=intent.getStringExtra("name");*/
        Log.d(TAG,"Position passed="+Recyclerview_adapter.check);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#73767d"));
        }
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        JsonHolder jsonHolder= retrofit.create(JsonHolder.class);
        Call<List<Breed>> call=jsonHolder.getBreed();
        call.enqueue(new Callback<List<Breed>>() {
            @Override
            public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Response="+String.valueOf(response.code()),Toast.LENGTH_LONG).show();
                    return;
                }
                    List<Breed> breedList=response.body();
                  for(Breed breed:breedList) {
                      if(breed.name.equals(Recyclerview_adapter.check)) {
                          text_name.setText("Name:-" + breed.name);
                          Picasso.get().load(breed.image.getUrl()).resize(409, 300).into(imageView);
                          if (breed.bred_for != null)
                              text_bred_for.setText(breed.bred_for);
                          else
                              text_bred_for.setText("Not Known");

                          if (breed.breed_group != null)
                              text_breed_group.setText(breed.breed_group);
                          else
                              text_breed_group.setText("Not Known");
                          if (breed.description != null)
                              text_description.setText(breed.description);
                          else
                              text_description.setText("Not Known");
                          if (breed.height != null) {
                              if (breed.height.getImperial() != null)
                                  text_himperial.setText(breed.height.getImperial());
                              else
                                  text_himperial.setText("Not Known");
                              if (breed.height.getMetric() != null)
                                  text_hmetric.setText(breed.height.getMetric());
                              else
                                  text_hmetric.setText("Not Known");
                          }
                          if (breed.life_span != null)
                              text_lifespan.setText(breed.life_span);
                          else
                              text_lifespan.setText("Not Known");
                          if (breed.origin != null )
                              text_origin.setText(breed.origin);
                          else
                              text_origin.setText("Not Known");
                          if (breed.temperament != null)
                              text_temperament.setText(breed.temperament);
                          else
                              text_temperament.setText("Not Known");
                          if (breed.weight != null) {
                              if (breed.weight.getImperial() != null)
                                  text_wimperial.setText(breed.weight.getImperial());
                              else
                                  text_wimperial.setText("Not Known");
                              if (breed.weight.getMetric() != null)
                                  text_wmetric.setText(breed.weight.getMetric());
                              else
                                  text_wmetric.setText("Not Known");
                          }
                      }
                  }
            }
            @Override
            public void onFailure(Call<List<Breed>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Error="+(t.getMessage()),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.share,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.share){

        }
        return super.onOptionsItemSelected(item);
    }
}