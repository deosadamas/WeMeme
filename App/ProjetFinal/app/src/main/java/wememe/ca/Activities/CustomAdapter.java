package wememe.ca.Activities;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.roughike.bottombar.BottomBar;

import java.util.List;

import wememe.ca.R;
import wememe.ca.Requetes.CodeRequest;
import wememe.ca.Requetes.DataLike;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<MyData> datameme_list;
    private List<DataLike> dataLike_list;
    private List<Like> like_list;
    private int id;
    BottomBar bottomBar;
    private MainActivity activity;

    public CustomAdapter(Context context, List<MyData> my_data, List<DataLike> likes, List<Like> like_list, MainActivity activity) {
        this.context = context;
        this.datameme_list = my_data;
        this.dataLike_list = likes;
        this.like_list = like_list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.test,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        id = datameme_list.get(position).getId();
        holder.nom.setText(datameme_list.get(position).getNom());
        holder.like.setText(String.valueOf(like_list.get(position).getLike()));
        holder.ID.setText(String.valueOf(datameme_list.get(position).getId()));
        Glide.with(context).load(datameme_list.get(position).getImage_link()).into(holder.imagePhoto);
        Glide.with(context).load(datameme_list.get(position).getImage_link()).into(holder.imageView);
        Glide.with(context).load(R.drawable.nonelike).into(holder.Like);

        for (int i = 0; i <= dataLike_list.size()-1; i++){
            int b = dataLike_list.get(i).getMeme();
            int c = dataLike_list.get(i).getUser();

            if (id == b && 10 == c){
                Glide.with(context).load(R.drawable.testlike).into(holder.Like);
            }
        }

        for (int i = 0; i <= like_list.size()-1; i++){
            int b = like_list.get(i).getMeme();
            if(id == b)
            {
                holder.like.setText(String.valueOf(like_list.get(i).getLike()));
            }
        }


        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                int pos = position;//Pour refresh la position
                int id_like = datameme_list.get(pos).getId();
                int nbrelike = 0;
                CodeRequest codeRequest = new CodeRequest("10", id_like, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                });
                queue.add(codeRequest);

                DataLike dataLike = new DataLike(10, id_like, "");
                boolean image = false;
                int cpt = 0;
                for(int i = 0; i < dataLike_list.size(); i++)
                {
                    if(dataLike_list.get(i).getMeme() == id_like)
                    {
                        image = true;
                        cpt = i;
                        break;
                    }
                }
                boolean likes = false;
                int compteur = 0;
                for(int i = 0; i < like_list.size(); i++)
                {
                    if(like_list.get(i).getMeme() == id_like)
                    {
                        likes = true;
                        compteur = i;
                        break;
                    }
                }

                if(image)
                {
                    int nbre = like_list.get(compteur).getLike();
                    nbrelike = nbre - 1;
                    dataLike_list.remove(cpt);
                    notifyItemChanged(position);
                    image = false;
                }
                else
                {
                    for(int i = 0; i<like_list.size(); i++)
                    {
                        if(id_like == like_list.get(i).getMeme())
                        {
                            int nbre = like_list.get(i).getLike();
                            nbrelike = nbre + 1;
                            dataLike_list.add(dataLike);
                            notifyItemChanged(position);
                        }
                    }
                }
                if(likes)
                {
                    like_list.remove(compteur);
                    Like like = new Like(datameme_list.get(pos).getId(), nbrelike);
                    like_list.add(like);
                    notifyItemChanged(position);
                    likes = false;
                }
            }
        });


        holder.imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomBar myBottomBar = activity.getBottomBar();
                myBottomBar.selectTabAtPosition(4);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datameme_list.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView ID;
        public TextView nom;
        public TextView like;
        public ImageView imageView;
        public ImageView imagePhoto;
        public ImageView Like;


        public ViewHolder(View itemView) {
            super(itemView);

            nom = (TextView) itemView.findViewById(R.id.nom);
            Like = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imagePhoto = (ImageView) itemView.findViewById(R.id.circleImageView);
            ID = (TextView) itemView.findViewById(R.id.txt_ID);
            like = (TextView) itemView.findViewById(R.id.txtlike);
        }
    }


}
