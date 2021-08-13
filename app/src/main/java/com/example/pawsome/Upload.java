package com.example.pawsome;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Upload extends AppCompatActivity {
    private static final String TAG = "Upload";
    Button b_select,b_upload,b_analysis,b_get;
    Uri image_select;
    String real_uri,imagePath;
    ImageView image_upload;
    TableLayout tableLayout;
    String output="";
    File file;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        output+="The Analysis of the above image is:-";
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.thedogapi.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();
        image_upload=(ImageView)findViewById(R.id.imageView_Upload);
        tableLayout=(TableLayout)findViewById(R.id.table_analysis);
        b_get=(Button)findViewById(R.id.get);
        b_select=(Button)findViewById(R.id.select);
        b_upload=(Button)findViewById(R.id.upload);
        b_analysis=(Button)findViewById(R.id.analysis);
        b_upload.setVisibility(View.GONE);
        b_analysis.setVisibility(View.GONE);
        b_get.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#beffa8"));
        }
        b_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
             }
        });
        b_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file=new File(real_uri);
                Log.d("Upload","file_path="+file.getAbsolutePath());
                JsonHolder jsonHolder=retrofit.create(JsonHolder.class);
                RequestBody requestBody_sub_id=RequestBody.create(MediaType.parse("text/plain"),"uploaded_image");
                RequestBody requestBody_file=RequestBody.create(MediaType.parse(getContentResolver().getType(image_select)),file);
                MultipartBody.Part multipartBody_file=MultipartBody.Part.createFormData("file",file.getName(),requestBody_file);
                Call<Image_Post> call=jsonHolder.createImage(multipartBody_file,requestBody_sub_id);
                call.enqueue(new Callback<Image_Post>() {
                    @Override
                    public void onResponse(Call<Image_Post> call, Response<Image_Post> response) {
                        if(response.code()==201){
                            Toast.makeText(getApplicationContext(),"Image uploaded successfully,It is a dog",Toast.LENGTH_LONG) .show();
                            b_analysis.setVisibility(View.VISIBLE);}
                            if(response.code()==400)
                            Toast.makeText(getApplicationContext(),"Sorry,It is not a dog",Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onFailure(Call<Image_Post> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Respone="+t.getMessage(),Toast.LENGTH_LONG) .show();
                    }
                });


            }
        });
        b_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonHolder jsonHolder=retrofit.create(JsonHolder.class);
                Call<List<Uploaded_image_id>> call_id=jsonHolder.getimageid();
                call_id.enqueue(new Callback<List<Uploaded_image_id>>() {
                    @Override
                    public void onResponse(Call<List<Uploaded_image_id>> call, Response<List<Uploaded_image_id>> response) {
                        List<Uploaded_image_id> list=response.body();
                        Uploaded_image_id uploaded_image_id=list.get(0);
                        imagePath= uploaded_image_id.getId();
                        //Toast.makeText(getApplicationContext(),"Respone="+uploaded_image_id.id,Toast.LENGTH_LONG) .show();
                        b_get.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Uploaded_image_id>> call, Throwable t) {

                    }
                });

            }
        });
        b_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViewsInLayout();
                if(imagePath!=null)
                { JsonHolder jsonHolder=retrofit.create(JsonHolder.class);
                    Call<List<Image_analysis>> listCall=jsonHolder.getanalysis(imagePath);

                    listCall.enqueue(new Callback<List<Image_analysis>>() {
                        @SuppressLint("WrongConstant")
                        @Override
                        public void onResponse(Call<List<Image_analysis>> call, Response<List<Image_analysis>> response) {
                            List<Image_analysis> image_analysisList = response.body();
                            //Toast.makeText(getApplicationContext(), "Response=" + response.code(), Toast.LENGTH_SHORT).show();
                            Image_analysis image_analysis = image_analysisList.get(0);
                            for (int i = 0; i < image_analysis.getLabels().size(); i++) {
                                TableRow tableRow_Heading = new TableRow(getApplicationContext());
                                TableLayout.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                layoutParams.setMargins(0, 10, 0, 10);
                                tableRow_Heading.setLayoutParams(layoutParams);
                                TextView t_heading = new TextView(getApplicationContext());
                                t_heading.setText("Analysis\t" + Integer.valueOf(i + 1));
                                t_heading.setTextColor(Color.BLACK);
                                t_heading.setTextSize(20);
                                tableRow_Heading.addView(t_heading);
                                tableRow_Heading.setBackgroundColor(Color.parseColor("#abed55"));
                                tableLayout.addView(tableRow_Heading);
                                TableRow tableRow = new TableRow(getApplicationContext());
                                tableRow.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                TextView t_name = new TextView(getApplicationContext());
                                t_name.setText("Name:" + image_analysis.getLabels().get(i).getName());
                                t_name.setTextSize(25);
                                t_name.setTextColor(Color.BLACK);
                                t_name.setGravity(Gravity.CENTER);
                                tableRow.addView(t_name);
                                tableRow.setGravity(Gravity.CENTER);
                                tableRow.setBackgroundColor(Color.parseColor("#c8fc83"));
                                tableLayout.addView(tableRow);
                                for (int j = 0; j < image_analysis.getLabels().get(i).getParents().size(); j++) {
                                    TableRow tableRow1 = new TableRow(getApplicationContext());
                                    tableRow1.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                    TextView t_parents = new TextView(getApplicationContext());
                                    t_parents.setText("Parent's Name");
                                    t_parents.setGravity(Gravity.CENTER);
                                    t_parents.setTextColor(Color.BLACK);
                                    tableRow1.addView(t_parents, 0);
                                    tableRow1.setBackgroundColor(Color.parseColor("#defcb6"));
                                    TextView t_details = new TextView(getApplicationContext());
                                    t_details.setText(image_analysis.getLabels().get(i).getParents().get(j).getName());
                                    t_details.setTextColor(Color.BLACK);
                                    tableRow1.addView(t_details, 1);
                                    tableLayout.addView(tableRow1);
                                }
                            }
                            imagePath = null;

                        }

                        @Override
                        public void onFailure(Call<List<Image_analysis>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Response=" +t.getMessage() , Toast.LENGTH_SHORT).show();

                        }
                    });}
                b_upload.setVisibility(View.GONE);
                b_analysis.setVisibility(View.GONE);
                b_get.setVisibility(View.GONE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&requestCode==3) {
            image_select = data.getData();
            String ip[]={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(image_select,ip,null,null,null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
                int index=cursor.getColumnIndex(ip[0]);
                real_uri=cursor.getString(index);
                //Toast.makeText(getApplicationContext(),"Real URI="+real_uri,Toast.LENGTH_SHORT).show();
                Picasso.get().load(image_select).resize(372,268).into(image_upload);
            }
            b_upload.setVisibility(View.VISIBLE);
        }
    }
}
