package com.example.pawsome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.room.Delete;

import com.squareup.picasso.Picasso;

import java.util.List;


public class Recyclerview_Fav_adapter extends RecyclerView.Adapter<Recyclerview_Fav_adapter.Recyclerview_Fav_ViewHolder> {
  List<Table_Breed> table_breedList;
  Context context;
    private static final String TAG="Recyclerview_Fav_adapter";
    public Recyclerview_Fav_adapter(List<Table_Breed> table_breedList) {
        this.table_breedList = table_breedList;
    }

    @Override
    public Recyclerview_Fav_ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_card,viewGroup,false);
        Recyclerview_Fav_ViewHolder recyclerview_fav_viewHolder=new Recyclerview_Fav_ViewHolder(view);
        return recyclerview_fav_viewHolder;
    }

    @Override
    public void onBindViewHolder(Recyclerview_Fav_adapter.Recyclerview_Fav_ViewHolder recyclerview_fav_viewHolder, @SuppressLint("RecyclerView") int i) {
        recyclerview_fav_viewHolder.textView.setText(table_breedList.get(i).getDog_name());
        Picasso.get().load(table_breedList.get(i).getDog_image_url()).resize(379,379).into(recyclerview_fav_viewHolder.imageView);
        recyclerview_fav_viewHolder.button_fav.setVisibility(View.INVISIBLE);
        recyclerview_fav_viewHolder.button_un_fav.setVisibility(View.VISIBLE);
        recyclerview_fav_viewHolder.button_un_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerview_fav_viewHolder.button_fav.setVisibility(View.INVISIBLE);
                recyclerview_fav_viewHolder.button_un_fav.setVisibility(View.INVISIBLE);
                if(i>=0){
                Delete_Dog delete_dog=new Delete_Dog();
                delete_dog.execute(table_breedList.get(i).getDog_name());
                table_breedList.remove(i);
                notifyDataSetChanged();
                notifyItemRangeChanged(i,table_breedList.size());}
            }
        });
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                Home_Screen.Fav=TableRoomDatabase.getInstance(recyclerview_fav_viewHolder.itemView.getContext()).table_breed_dao().get_fav_dog();
                Log.d(TAG, String.valueOf(Home_Screen.Fav));
            }
        });
        thread1.start();
        int c=0;
        for (int j=0;j<Home_Screen.Fav.size();j++){
            if(table_breedList.get(i).getDog_name().equals(Home_Screen.Fav.get(j).getDog_name())){
                c=1;
            }
        }
        if(c==1)
        {
            recyclerview_fav_viewHolder.button_un_fav.setVisibility(View.VISIBLE);
            recyclerview_fav_viewHolder.button_fav.setVisibility(View.INVISIBLE);
        }
        if(c==0)
        {
            recyclerview_fav_viewHolder.button_fav.setVisibility(View.VISIBLE);
            recyclerview_fav_viewHolder.button_un_fav.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return table_breedList.size();
    }

    class Recyclerview_Fav_ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageButton button_fav,button_un_fav;
        public Recyclerview_Fav_ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView2);
            textView=itemView.findViewById(R.id.textView2);
            button_fav=itemView.findViewById(R.id.like);
            button_un_fav=itemView.findViewById(R.id.unlike);


        }
    }
     class Delete_Dog extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... voids) {
            Table_Breed dog=TableRoomDatabase.getInstance(context).table_breed_dao().find_the_dog(voids[0]);
            Log.d(TAG, String.valueOf(dog));
            TableRoomDatabase.getInstance(context).table_breed_dao().delete_fav(dog);
            return null;
        }
    }
}
