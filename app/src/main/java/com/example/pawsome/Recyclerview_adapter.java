package com.example.pawsome;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
public class Recyclerview_adapter extends RecyclerView.Adapter<Recyclerview_adapter.Recyclerview_Viewholder> {
    int count=0;
    public static String check;
    List<String> name,list_img_url;
    public Recyclerview_adapter(List<String> name,List<String> list_img_url)
    {
        this.name=name;
        this.list_img_url=list_img_url;
        notifyDataSetChanged();
    }
    public final String TAG="Recyclerview_adapter";
    @NonNull
    @Override
    public Recyclerview_Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        count++;
        Log.d(TAG,"count=" +String.valueOf(+count));
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.design_card,viewGroup,false);
        Recyclerview_Viewholder recyclerview_viewholder=new Recyclerview_Viewholder(view);
        return recyclerview_viewholder;
    }
    @Override
    public void onBindViewHolder(@NonNull Recyclerview_Viewholder recyclerview_viewholder, @SuppressLint("RecyclerView") int i) {
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
               Home_Screen.Fav=TableRoomDatabase.getInstance(recyclerview_viewholder.itemView.getContext()).table_breed_dao().get_fav_dog();
                Log.d(TAG, String.valueOf(Home_Screen.Fav));
            }
        });
        thread1.start();
        recyclerview_viewholder.textView.setText(name.get(i));
        Picasso.get().load(list_img_url.get(i)).resize(379,379).into(recyclerview_viewholder.imageView);
        int c=0;
        for (int j=0;j<Home_Screen.Fav.size();j++){
            if(name.get(i).equals(Home_Screen.Fav.get(j).getDog_name())){
                c=1;
            }
        }
        if(c==1)
        {
            recyclerview_viewholder.button_un_fav.setVisibility(View.VISIBLE);
            recyclerview_viewholder.button_fav.setVisibility(View.INVISIBLE);
        }
        if(c==0)
        {
            recyclerview_viewholder.button_fav.setVisibility(View.VISIBLE);
            recyclerview_viewholder.button_un_fav.setVisibility(View.INVISIBLE);
        }
        recyclerview_viewholder.button_fav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Table_Breed table_breed=new Table_Breed(name.get(i),list_img_url.get(i));
                        TableRoomDatabase.getInstance(v.getContext()).table_breed_dao().insert_fav(table_breed);
                        Home_Screen.Fav.add(table_breed);
                    }
                });
                thread.start();

                recyclerview_viewholder.button_un_fav.setVisibility(View.VISIBLE);
            }
        });
        recyclerview_viewholder.button_un_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerview_viewholder.button_un_fav.setVisibility(View.INVISIBLE);
                recyclerview_viewholder.button_fav.setVisibility(View.VISIBLE);
                Thread thread_dislike=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Table_Breed dog=TableRoomDatabase.getInstance(v.getContext()).table_breed_dao().find_the_dog(name.get(i));
                        Log.d(TAG, String.valueOf(dog));
                        TableRoomDatabase.getInstance(v.getContext()).table_breed_dao().delete_fav(dog);

                    }
                });
                thread_dislike.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }
    public class Recyclerview_Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;
        ImageButton button_fav,button_un_fav;
        public Recyclerview_Viewholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView2);
            textView=itemView.findViewById(R.id.textView2);
            button_fav=itemView.findViewById(R.id.like);
            button_un_fav=itemView.findViewById(R.id.unlike);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            check= String.valueOf(textView.getText());
            Log.d(TAG,"position name="+textView.getText());
            Intent intent=new Intent(itemView.getContext(),Dog_details.class);
            intent.putExtra("name",textView.getText());
            itemView.getContext().startActivity(intent);


        }
    }
}
