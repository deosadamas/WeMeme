package wememe.ca.Activities;

import android.graphics.Color;
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
import wememe.ca.Class.DataLike;

import static android.graphics.Color.*;

public class Feed extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    //Variable pour les objects du layout feed
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CustomAdapter adapter;
    private ConnectionDectetor connectionDectetor;

    // Les listes pour stocker les information du  serveurs
    private List<Data_Feed> data_list;
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

        // Initialiser les listes pour les utiliser
        data_list = new ArrayList<>();
        dataLike_list = new ArrayList<>();
        like_list = new ArrayList<>();
        view_ = view;

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_viewFeed);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        //////////////////////////////////
        /////////////////////////////////
        /////////////////////////////////
        /////////////////////////////////
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);//MODIFIER
        /////////////////////////////////
        /////////////////////////////////
        /////////////////////////////////
        /////////////////////////////////


        gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        activity = (MainActivity) getActivity();
        connectionDectetor = new ConnectionDectetor(activity);
        // La premiere fois que ce fragement est utiliser elle va chercher l'id le plus haut dans la table Feed
        // La deuxieme fois que ce fragement est utiliser elle relance la requete pour aller chercher encore l'id le plus haut
        // Simplement rafraichit si il y a de nouvelle information dans la table Feed
        load_data_from_server(MainActivity.utilisateur.getId());

        //Dans le Custom adapter, celui-ci a besoin du context du layout feed, des 3 listes d'information et de le contexte de la MainActivity
        adapter = new CustomAdapter(view.getContext(),data_list, dataLike_list, like_list, activity);
        recyclerView.setAdapter(adapter);

        // A chaque fois que la personne scroll down et atteind le dernier element afficher
        // la methode load_data_from_server est alors enclencher pour aller checher d'autre element
/*        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == data_list.size() - 1) {
                    load_data_from_server(data_list.get(data_list.size() - 1).getId());
                }
            }
        });*/

        initSwipe(recyclerView, view);

        return view;


    }

    //Cette methode va simplement aller chercher l'information du serveur et les ajoutes dans les 3 listes
    // 1 data_list dans la Table Feed, la classe Data_Feed(id, sujet, nom, description, images, nbrelike, id_user_post)
    // 2 like_list dans la Table Feed, la classe Like(id, nbreLike)
    // 3 dataLike_list dans la Table Laught, la classe DataLike(UserLaught, MemeLaught)
    private void load_data_from_server(final int id) {
            //Utilise le AsyncTask simplement pour  télécharger l'information dans le background de l'application
            AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
                @Override
                protected Void doInBackground(Integer... integers) {

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=feed_follow&id=" + id)// Avec la requete php id descend de -3
                            .build();                                                                // A chaque fois que la requete est appeller
                    try {
                        Response response = client.newCall(request).execute();

                        //Converti la reponse du serveur (JSON) dans un tableau de json pour ensuite etre capable de trier l'information
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
                        dataLike_list.clear();
                        for (int i = 0; i < arrays.length(); i++) {
                            JSONObject object = arrays.getJSONObject(i);

                            DataLike dataLikes = new DataLike(object.getInt("UserLaught"), object.getInt("MemeLaught"));
                            dataLike_list.add(dataLikes);

                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        System.out.println("End of content");
                    }
                    return null;
                }
                //A chaque fois qu'il a de nouvelle information sa les ajoutes et rafraichit le recycleview
                @Override
                protected void onPostExecute(Void aVoid) {
                    adapter.notifyDataSetChanged();
                }
            };
            task.execute(id);//Execute la asynctaks
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
                    ((MainActivity) getActivity()).getSupportActionBar().show();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //Cette method rafraichit les donnees du serveur va chercher les nouvelle information s'il y en a
    public void onRefresh() {
        if(connectionDectetor.isConnected())
        {
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
            dataLike_list.clear();
            like_list.clear();
            // Remplit les liste avec de nouvelle information
            load_data_from_server(MainActivity.utilisateur.getId());

            //Ajouter dans le CustomAdapter les nouvelle listes
            adapter = new CustomAdapter(getView().getContext(), data_list, dataLike_list, like_list, activity);
            recyclerView.setAdapter(adapter);
        }else
        {
            swipeRefreshLayout.setRefreshing(false);
            activity.showSnack();
        }
    }
}
