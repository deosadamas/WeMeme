package wememe.ca.Activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

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
import wememe.ca.Requetes.RegisterRequest;
import wememe.ca.Requetes.UserRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profil2 extends Fragment {


    public RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private int iduser;
    private Button follow;
    private TextView txtPost;
    private TextView txtFollowings;
    private TextView txtLaughtPerPosts;
    private TextView txtFollowers;
    private static final String trouveUser = "http://wememe.ca/mobile_app/index.php?prefix=json&p=follow=";
    public boolean first = true;

    public Profil2(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_profil2, container, false);

//        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_viewProfil);
        follow = (Button)view.findViewById(R.id.btnFollow);
  /*      txtPost = (TextView)view.findViewById(R.id.txtPosts);
        txtFollowings = (TextView)view.findViewById(R.id.txtFollowings);
        txtLaughtPerPosts = (TextView)view.findViewById(R.id.txtLaughtPerPosts);
        txtFollowers = (TextView)view.findViewById(R.id.txtFollowers);
        data_list = new ArrayList<>();
        load_data_from_server(36);

        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(data_list.get(data_list.size() - 1).getId());
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

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        UserRequest userRequest = new UserRequest(iduser, responseListener);
        queue.add(userRequest);

        FollowRequest followRequest = new FollowRequest();*/


/*        final Drawable icon= getContext().getResources().getDrawable( R.drawable.connexion_lock_no_focus);
        follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
        final Drawable icon2= getContext().getResources().getDrawable( R.drawable.connexion_lock_focus);*/
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                follow.setCompoundDrawablesWithIntrinsicBounds( icon, null, null, null );
 //               for (int i = 0; i <= dataFollow.size()-1; i++){
/*                    //int b = dataFollow.get(i).getFollowed();
                    //int c = dataFollow.get(i).getFollowing();
                    int b = 8;
                    int c = 10;*/
                follow.setText("dsfsdf");
/*                    if (b == 8 && 10 == c){
                        follow.setCompoundDrawablesWithIntrinsicBounds( icon2, null, null, null );
                        follow.setText("dsfsdf");
                    }*/
//                }
            }
        });

        if (!first){
            initSwipe(recyclerView, view);
        }

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


    private void load_data_from_server(int id) {

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

                    }

                    OkHttpClient clients = new OkHttpClient();
                    Request requests = new Request.Builder()
                            .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=datelike")
                            .build();
                    Response responses = clients.newCall(requests).execute();

                    JSONArray arrays = new JSONArray(responses.body().string());

                    for (int i = 0; i < arrays.length(); i++) {
                        JSONObject object = arrays.getJSONObject(i);

                        DataLike dataLikes = new DataLike(object.getInt("UserLaught"), object.getInt("MemeLaught"),
                                object.getString("couleur"));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                adapter.notifyDataSetChanged();
            }
        };
        task.execute(id);
    }

    private void initSwipe(RecyclerView recyclerView, final View view){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT){
                    Toast.makeText(view.getContext(), "Swipe error", Toast.LENGTH_LONG).show();
                    load_data_from_server(36);
                    load_data_from_server(data_list.get(data_list.size() - 1).getId());
                    first = false;
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
