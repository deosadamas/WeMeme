package wememe.ca.Activities;


import android.content.Context;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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
import wememe.ca.Requetes.DataLike;

public class Feed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private List<MyData> data_list;
    private List<DataLike> dataLike_list;
    private List<Like> like_list;
    public SwipeRefreshLayout swipeRefreshLayout;
    private View view_;
    MainActivity activity;

    public Feed(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View view = inflater.inflate(R.layout.fragment_feed, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewFeed);
        data_list = new ArrayList<>();
        dataLike_list = new ArrayList<>();
        like_list = new ArrayList<>();
        view_ = view;

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);


        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);



        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        //Bon le 36 doit etre changer par la variable dans la connection qui est feed_max_id.id
        //Passer intent de connextion a celle-ci
        activity = (MainActivity) getActivity();
        if(Splash.id_max == Splash.id_max)
        {
            load_data_from_server(Splash.id_max, view.getContext());
        }else
        {
            load_data_from_server(activity.id_max, view.getContext());
        }
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        adapter = new CustomAdapter(view.getContext(),data_list, dataLike_list, like_list, activity);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(data_list.get(data_list.size() - 1).getId(), view.getContext());
                }
            }
        });

        initSwipe(recyclerView, view);

        return view;
    }

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
                    dataLike_list.clear();
                    for (int i = 0; i < arrays.length(); i++) {
                        JSONObject object = arrays.getJSONObject(i);

                        DataLike dataLikes = new DataLike(object.getInt("UserLaught"), object.getInt("MemeLaught"));
                        dataLike_list.add(dataLikes);

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
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT){
                    MainActivity activity = (MainActivity) getActivity();
                    BottomBar myBottomBar = activity.getBottomBar();
                    myBottomBar.selectTabAtPosition(1);
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
        dataLike_list.clear();
        like_list.clear();
        activity.load_data_from_server();
        load_data_from_server(activity.id_max, view_.getContext());
        adapter = new CustomAdapter(getView().getContext(), data_list, dataLike_list, like_list, activity);
        recyclerView.setAdapter(adapter);
    }
}
