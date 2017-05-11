package wememe.ca.Activities;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.roughike.bottombar.BottomBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wememe.ca.Class.Data_Feed;
import wememe.ca.Class.Like;
import wememe.ca.R;
import wememe.ca.Class.DataFollow;
import wememe.ca.Class.DataLike;
import wememe.ca.Requetes.FollowListRequest;
import wememe.ca.Requetes.FollowRequest;
import wememe.ca.Requetes.LaugthsPost;
import wememe.ca.Requetes.ProfilRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profil extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {


    //Variable pour les objects du layout profil
    public RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;

    // Les listes pour stocker les information du  serveurs
    private List<DataFollow> follow_list;
    private List<Data_Feed> data_list;
    private List<DataLike> datalike_list;
    private List<Like> like_list;
    private int compteurButtonFollow = 0;


    public SwipeRefreshLayout swipeRefreshLayout;
    private Button follow;
    private ImageView imgProfilPicture;
    private TextView txtPost;
    private TextView txtFollowings;
    private TextView txtLaughtPerPosts;
    private TextView txtFollowers;
    private View view_;
    private int numberpost;
    MainActivity activity;
    private boolean buttonFollow = true;

    public Profil(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_profil, container, false);

        //Assignation des objects
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_viewProfil);
        follow = (Button)view.findViewById(R.id.btnFollow);
        txtPost = (TextView)view.findViewById(R.id.txtPost);
        txtLaughtPerPosts = (TextView)view.findViewById(R.id.txtLaughtsPost);
        txtFollowings = (TextView)view.findViewById(R.id.txtfollowings);
        txtFollowers = (TextView)view.findViewById(R.id.txtfollowers);
        imgProfilPicture = (ImageView)view.findViewById(R.id.imgProfilPicture);

        // Initialiser les listes pour les utiliser et les variables
        data_list = new ArrayList<>();
        datalike_list = new ArrayList<>();
        like_list = new ArrayList<>();
        follow_list = new ArrayList<>();
        view_ = view;
        activity = (MainActivity) getActivity();

        // Telecharge les information du serveur pour le feed profil et les inforamtion personnelle du profil de la personne
        load_data_from_server(view.getContext());
        load_data__profil(view.getContext());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        //Dans le Custom adapter, celui-ci a besoin du context du layout feed, des 3 listes d'information et de le contexte de la MainActivity
        adapter = new CustomAdapter(view.getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);

/*        final Drawable icon= getContext().getResources().getDrawable( R.drawable.connexion_lock_no_focus);
        follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
        final Drawable icon2= getContext().getResources().getDrawable( R.drawable.connexion_lock_focus);*/

        //Lorsque que l'utilisateur clique sur le button follow
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                //Envoit une requete au serveur avec l'id de l'utilisateur et l'id du profil de la personne que l'utilisateur est dessus
                FollowRequest followRequest = new FollowRequest(MainActivity.utilisateur.getId(), MainActivity.id_user_post, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean validattiondejafollow = jsonResponse.getBoolean("dejafollow");
                            if(!validattiondejafollow)
                            {
                                follow.setBackgroundResource(R.drawable.folloon);
                                follow.setText("unfollow ");
                                load_data__profil(view.getContext());
                            }
                            else
                            {
                                follow.setBackgroundResource(R.drawable.follooff);
                                load_data__profil(view.getContext());
                                follow.setText("follow ");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                queue.add(followRequest);
            }
        });

        initSwipe(recyclerView, view);

        return view;
    }

    // Cette methode va aller chercher tout l'information du feed du profil de la personne est l'ajoute dans les listes informations
    private void load_data_from_server(final Context view) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... Void) {
                int id_user_post;
                RequestQueue queue = Volley.newRequestQueue(view);

                //Cette condition vérifie que sur qu'elle profile est l'utilisateur
                //Le profil de quelqu'un d'autre ou juste le sien
                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                {
                    id_user_post = MainActivity.id_user_post;
                }else{
                    id_user_post = MainActivity.utilisateur.getId();
                }

                //Cette requete va chercher dans la table Feed tout l'information des meme post par celui-ci est le stock 3 listes
                // 1 data_list dans la Table Feed, la classe Data_Feed(id, sujet, nom, description, images, nbrelike, id_user_post)
                // 2 like_list dans la Table Feed, la classe Like(id, nbreLike)
                // 3 dataLike_list dans la Table Laught, la classe DataLike(UserLaught, MemeLaught)
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=feed_profil&id_user_post=" + id_user_post)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        Data_Feed data = new Data_Feed(object.getInt("id"), object.getString("sujet"), object.getString("nom"),
                                object.getString("description"),
                                object.getString("images"), object.getInt("nbreLike"), object.getInt("id_user_post"));

                        Like likes = new Like(object.getInt("id"), object.getInt("nbreLike"));

                        data_list.add(data);
                        like_list.add(likes);
                    }
                    OkHttpClient clients = new OkHttpClient();
                    Request requests = new Request.Builder()
                            .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=datelike&id_utilisateur=" + MainActivity.utilisateur.getId())
                            .build();
                    okhttp3.Response responses = clients.newCall(requests).execute();

                    JSONArray arrays = new JSONArray(responses.body().string());
                    datalike_list.clear();
                    for (int i = 0; i < arrays.length(); i++) {
                        JSONObject object = arrays.getJSONObject(i);

                        DataLike dataLikes = new DataLike(object.getInt("UserLaught"), object.getInt("MemeLaught"));
                        datalike_list.add(dataLikes);
                    }
                    activity.load_data_from_server();// Cette methode va chercher id_max du serveur en d'autre mot rafraichit
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };
        task.execute();//Execute la asynctaks
        }

    private void initSwipe(RecyclerView recyclerView, final View view){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT){
                    MainActivity activity = (MainActivity) getActivity();
                    BottomBar myBottomBar = activity.getBottomBar();
                    myBottomBar.selectTabAtPosition(3);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //Cette method rafraichit les donnees du serveur va chercher les nouvelle information s'il y en a
    public void onRefresh() {
        //Un thread qui force un delai de 1,5 seconde pour que cette méthode soit utiliser a nouveau
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

        //Vide les liste
        data_list.clear();
        datalike_list.clear();
        like_list.clear();
        follow_list.clear();
        // Remplit les liste avec de nouvelle information
        load_data_from_server(view_.getContext());
        load_data__profil(view_.getContext());

        //Ajouter dans le CustomAdapter les nouvelle listes
        adapter = new CustomAdapter(getView().getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);
    }

    // Cette methode va chercher l'information de l'utilisateur et l'affiche
    private void load_data__profil(final Context view)
    {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //Reponse du serveur su l'information du profil de la personne
                com.android.volley.Response.Listener<String> responseListenerProfil = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);

                            //Les information trier et ensuite sont afficher
                            String profil = object.getString("profilpic");
                            numberpost = object.getInt("numberpost");
                            int numberFollowed = object.getInt("numberFollowed");
                            int numberFollowing = object.getInt("numberFollowing");
                            txtPost.setText(String.valueOf(numberpost));
                            txtFollowings.setText(String.valueOf(numberFollowing));
                            txtFollowers.setText(String.valueOf(numberFollowed));
                            Glide.with(view.getApplicationContext()).load(profil).into(imgProfilPicture);

                            //Appelle de la methode follow
                            //Cette methode fait 2 requete la premiere calcul le nombre de like par post
                            // et affiche la couleur du button si la personne follow deja ou pas
                            load_data_follow_profil(view.getApplicationContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(view);
                ProfilRequest profilRequest;

                //Cette condition vérifie que sur qu'elle profile est l'utilisateur
                //Le profil de quelqu'un d'autre ou juste le sien
                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                {
                    profilRequest = new ProfilRequest(MainActivity.id_user_post, responseListenerProfil);
                }else{
                    profilRequest = new ProfilRequest(MainActivity.utilisateur.getId(), responseListenerProfil);
                }
                queue.add(profilRequest);
                return null;
            }
        };
        task.execute();
    }

    //Cette methode fait 2 requete la premiere calcul le nombre de like par post
    // et affiche la couleur du button si la personne follow deja ou pas
    private void load_data_follow_profil(final Context view) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                RequestQueue queue = Volley.newRequestQueue(view);
                LaugthsPost laugthsPost;
                FollowListRequest followListRequest;

                //Reponse du serveur de la requete pour calcul le nombre de like par post
                com.android.volley.Response.Listener<String> responseListenerLaugthsPost = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            int number = 0;
                            for(int i = 0; i< array.length(); i++)
                            {
                                JSONObject object = array.getJSONObject(i);
                                number += object.getInt("nbreLike");
                            }
                            if(numberpost != 0)
                            {
                                int moyennelaughtspost = number / numberpost;
                                txtLaughtPerPosts.setText(String.valueOf(moyennelaughtspost));
                            }else {
                                txtLaughtPerPosts.setText(String.valueOf(number));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //Reponse du serveur pour pour ajouter les informations de la table follow
                com.android.volley.Response.Listener<String> responseListenerFollow = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            follow_list.clear();
                            // Ajoute toute l'information de la table follow dans la liste follow_list
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                DataFollow dataFollow = new DataFollow(object.getInt("UserFollowed"), object.getInt("UserFollowing"), object.getString("DateFollow"));
                                follow_list.add(dataFollow);
                            }
                            //Ensuite cette boucle va simplement parcourir la liste follow_list
                            for (int i = 0; i <= follow_list.size()-1; i++){
                                int following = follow_list.get(i).getFollowing();//Stocke dans une variable la position de la personne qui follower
                                //Cette condition vérifie que si un element de la liste et pareil avec l'id de l'utlisateur
                                // ou du profil de la personne dont l'utilisateur est dessus
                                // elle affiche la couleur du button car celle-ci veut simplement dire qu'il follow déja cette personne
                                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                                {
                                    if (following ==  MainActivity.id_user_post){
                                        follow.setBackgroundResource(R.drawable.folloon);
                                        follow.setText("unfollow");
                                    }
                                }else{
                                    if (following ==   MainActivity.utilisateur.getId()){
                                        follow.setBackgroundResource(R.drawable.folloon);
                                        follow.setText("unfollow");
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                //Cette condition vérifie que sur qu'elle profile est l'utilisateur
                //Le profil de quelqu'un d'autre ou juste le sien
                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                {
                    laugthsPost = new LaugthsPost(MainActivity.id_user_post, responseListenerLaugthsPost);
                }else{
                    laugthsPost = new LaugthsPost(MainActivity.utilisateur.getId(), responseListenerLaugthsPost);
                }
                followListRequest = new FollowListRequest(responseListenerFollow);
                queue.add(laugthsPost);
                queue.add(followListRequest);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
            }
        };
        task.execute();
    }
}
