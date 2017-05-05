package wememe.ca.Activities;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
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
import com.android.volley.toolbox.StringRequest;
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
import wememe.ca.Requetes.FollowRequest;
import wememe.ca.Requetes.ProfilRequest;
import wememe.ca.Requetes.UserRequest;

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
    private int iduser;
    private List<DataFollow> dataFollow;

    private Button follow;
    private ImageView imgProfilPicture;
    private TextView txtPost;
    private TextView txtFollowings;
    private TextView txtLaughtPerPosts;
    private TextView txtFollowers;
    private static final String trouveUser = "http://wememe.ca/mobile_app/index.php?prefix=json&p=follow=";
    private View view_;
    MainActivity activity;

    public Profil(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_profil, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_viewProfil);
       /* follow = (Button)view.findViewById(R.id.btnFollow);
        txtPost = (TextView)view.findViewById(R.id.txtPosts);
        txtFollowings = (TextView)view.findViewById(R.id.txtFollowings);
        txtLaughtPerPosts = (TextView)view.findViewById(R.id.txtLaughtPerPosts);
        txtFollowers = (TextView)view.findViewById(R.id.txtFollowers);*/
        imgProfilPicture = (ImageView)view.findViewById(R.id.imgProfilPicture);

        data_list = new ArrayList<>();
        datalike_list = new ArrayList<>();
        like_list = new ArrayList<>();
        view_ = view;

        activity = (MainActivity) getActivity();
        load_data_from_server(Splash.id_max, view.getContext());

        load_data__profil(view.getContext(), activity);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new CustomAdapter(view.getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(data_list.get(data_list.size() - 1).getId(), view.getContext());
                }
            }
        });

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray array = new JSONArray(response);
                    for(int i = 0; i <= array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        iduser = object.getInt("id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

/*        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        UserRequest userRequest = new UserRequest(iduser, responseListener);
        queue.add(userRequest);
        FollowRequest followRequest = new FollowRequest();*/


  //      final Drawable icon= getContext().getResources().getDrawable( R.drawable.connexion_lock_no_focus);
/*        follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
        final Drawable icon2= getContext().getResources().getDrawable( R.drawable.connexion_lock_focus);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
 //               for (int i = 0; i <= dataFollow.size()-1; i++){
                    //int b = dataFollow.get(i).getFollowed();
                    //int c = dataFollow.get(i).getFollowing();
                    int b = 8;
                    int c = 10;

                    if (b == 8 && 10 == c){
                        follow.setCompoundDrawablesWithIntrinsicBounds( icon2, null, null, null );
                    }
//                }
            }
        });*/

        initSwipe(recyclerView, view);

        return view;
    }

//    private StringRequest load_user_from_server = new String(com.android.volley.Request.Method.GET, trouveUser, new Response(). ->{
//
//        try{
//            JSONArray array = new JSONArray(response);
//            JSONObject object = array.getJSONObject(0);
//
//        } catch (JSONException e){
//            e.printStackTrace();
//        }
//    }, null);


    private void load_data_from_server(int id, final Context view) {
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=feed&id=" + integers[0])
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

                        DataLike dataLikes = new DataLike(String.valueOf(object.getInt("UserLaught")), object.getInt("MemeLaught"));
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

        task.execute(id);
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
        load_data_from_server(activity.id_max, view_.getContext());
        adapter = new CustomAdapter(getView().getContext(), data_list, datalike_list, like_list, activity);
        recyclerView.setAdapter(adapter);
    }

    private void load_data__profil(final Context view, final MainActivity mainactivity) {

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            JSONObject object = array.getJSONObject(0);
                            String profil = object.getString("profilpic");
                            Glide.with(view.getApplicationContext()).load(profil).into(imgProfilPicture);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(view);
                if(!(MainActivity.id_user_post.equals(MainActivity.utilisateur.getId())))
                {
                    ProfilRequest registerRequest = new ProfilRequest(MainActivity.id_user_post, responseListener);
                    queue.add(registerRequest);
/*               FollowRequest followRequest = new FollowRequest();
                queue.add(followRequest.stringRequest);*/

                }else{
                    ProfilRequest registerRequest = new ProfilRequest(MainActivity.utilisateur.getId(), responseListener);
                    queue.add(registerRequest);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        };
        task.execute();
    }
}
