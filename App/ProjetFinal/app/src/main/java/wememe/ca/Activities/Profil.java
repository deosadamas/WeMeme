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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
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
import wememe.ca.R;
import wememe.ca.Requetes.DataFollow;
import wememe.ca.Requetes.DataLike;
import wememe.ca.Requetes.FollowListRequest;
import wememe.ca.Requetes.FollowRequest;
import wememe.ca.Requetes.LaugthsPost;
import wememe.ca.Requetes.ProfilRequest;

/**
 * A simple {@link Fragment} subclass.
 * implements SwipeRefreshLayout.OnRefreshListener
 */
public class Profil extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {


    public RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private List<DataLike> datalike_list;
    private List<Like> like_list;
    public SwipeRefreshLayout swipeRefreshLayout;
    private List<DataFollow> follow_list;

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

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_viewProfil);
        follow = (Button)view.findViewById(R.id.btnFollow);
        txtPost = (TextView)view.findViewById(R.id.txtPost);
        txtLaughtPerPosts = (TextView)view.findViewById(R.id.txtLaughtsPost);
        txtFollowings = (TextView)view.findViewById(R.id.txtfollowings);
        txtFollowers = (TextView)view.findViewById(R.id.txtfollowers);
        imgProfilPicture = (ImageView)view.findViewById(R.id.imgProfilPicture);

        data_list = new ArrayList<>();
        datalike_list = new ArrayList<>();
        like_list = new ArrayList<>();
        follow_list = new ArrayList<>();
        view_ = view;

        activity = (MainActivity) getActivity();
        load_data_from_server(view.getContext());
        load_data__profil(view.getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(view.getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);

        final Drawable icon= getContext().getResources().getDrawable( R.drawable.connexion_lock_no_focus);
        follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
        final Drawable icon2= getContext().getResources().getDrawable( R.drawable.connexion_lock_focus);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                if(buttonFollow)
                {
                    follow.setBackgroundColor(Color.GREEN);
                    buttonFollow = false;
                }else{
                    follow.setBackgroundColor(Color.WHITE);
                    buttonFollow = true;
                }
                FollowRequest followRequest = new FollowRequest(MainActivity.utilisateur.getId(), MainActivity.id_user_post, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(activity, response, Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(followRequest);
            }
        });

        initSwipe(recyclerView, view);

        return view;
    }

    private void load_data_from_server(final Context view) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... Void) {
                int id_user_post;
                RequestQueue queue = Volley.newRequestQueue(view);
                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                {
                    id_user_post = MainActivity.id_user_post;
                }else{
                    id_user_post = MainActivity.utilisateur.getId();
                }
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=feed_profil&id_user_post=" + id_user_post)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);

                        MyData data = new MyData(object.getInt("id"), object.getString("sujet"), object.getString("nom"),
                                object.getString("description"),
                                object.getString("images"), object.getInt("nbreLike"), object.getInt("id_user_post"));

                        Like likes = new Like(object.getInt("id"), object.getInt("nbreLike"));

                        data_list.add(data);
                        like_list.add(likes);
                    }

                    OkHttpClient clients = new OkHttpClient();
                    Request requests = new Request.Builder()
                            .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=datelike")
                            .build();
                    okhttp3.Response responses = clients.newCall(requests).execute();

                    JSONArray arrays = new JSONArray(responses.body().string());
                    datalike_list.clear();
                    for (int i = 0; i < arrays.length(); i++) {
                        JSONObject object = arrays.getJSONObject(i);

                        DataLike dataLikes = new DataLike(object.getInt("UserLaught"), object.getInt("MemeLaught"));
                        datalike_list.add(dataLikes);
                    }
                    activity.load_data_from_server();
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
        task.execute();
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

    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
        data_list.clear();
        datalike_list.clear();
        like_list.clear();
        load_data_from_server(view_.getContext());
        load_data__profil(view_.getContext());
        adapter = new CustomAdapter(getView().getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);
    }

    private void load_data__profil(final Context view)
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                com.android.volley.Response.Listener<String> responseListenerProfil = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            String profil = object.getString("profilpic");
                            numberpost = object.getInt("numberpost");
                            int numberFollowed = object.getInt("numberFollowed");
                            int numberFollowing = object.getInt("numberFollowing");
                            txtPost.setText(String.valueOf(numberpost));
                            txtFollowings.setText(String.valueOf(numberFollowing));
                            txtFollowers.setText(String.valueOf(numberFollowed));
                            Glide.with(view.getApplicationContext()).load(profil).into(imgProfilPicture);
                            load_data_follow_profil(view.getApplicationContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(view);
                ProfilRequest profilRequest;
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

    private void load_data_follow_profil(final Context view) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                RequestQueue queue = Volley.newRequestQueue(view);
                LaugthsPost laugthsPost;
                FollowListRequest followListRequest;
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
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                com.android.volley.Response.Listener<String> responseListenerFollow = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                DataFollow dataFollow = new DataFollow(object.getInt("UserFollowed"), object.getInt("UserFollowing"), object.getString("DateFollow"));
                                follow_list.add(dataFollow);
                            }
                            for (int i = 0; i <= follow_list.size()-1; i++){
                                int following = follow_list.get(i).getFollowing();
                                    if (following ==  MainActivity.id_user_post || following ==  MainActivity.utilisateur.getId()){
                                        follow.setBackgroundColor(Color.GREEN);
                                    }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if(!(MainActivity.id_user_post == MainActivity.utilisateur.getId()))
                {
                    laugthsPost = new LaugthsPost(MainActivity.id_user_post, responseListenerLaugthsPost);
                }else{
                    laugthsPost = new LaugthsPost(MainActivity.id_user_post, responseListenerLaugthsPost);
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
