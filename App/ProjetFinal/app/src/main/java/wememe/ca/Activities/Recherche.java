package wememe.ca.Activities;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import wememe.ca.Class.RechercherProfil;
import wememe.ca.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recherche extends ListFragment implements android.widget.SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    List<String> listeRecherche;
    List<RechercherProfil> listeRechercheToute;
    private ArrayAdapter<String> mAdapter;
    private Context mContext;
    ListView listView;
    MainActivity activity;

    public Recherche() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        activity = (MainActivity) getActivity();
        dataProfil();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_recherche, container, false);
        listView = (ListView)layout.findViewById(android.R.id.list);
        TextView emptyTextView = (TextView) layout.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);
        populateList();
        return layout;
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = (String) listView.getAdapter().getItem(position);
        activity.changerFragment(new Profil());
        for(RechercherProfil profil : listeRechercheToute)
        {
            if(profil.getUsername().equals(item))
            {
                BottomBar myBottomBar = activity.getBottomBar();
                myBottomBar.selectTabAtPosition(4);
                MainActivity.id_user_post = profil.getId();
            }
        }
        if (getActivity() instanceof OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).OnItem1SelectedListener(item);
        }
        getFragmentManager().popBackStack();
    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<String> filteredValues = new ArrayList<String>(listeRecherche);
        for (String value : listeRecherche) {
            if (!value.toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, filteredValues);
        listView.setAdapter(mAdapter);
        return false;
    }
    public void resetSearch() {
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, listeRecherche);
        listView.setAdapter(mAdapter);
    }

    private void populateList(){
        listeRecherche = new ArrayList<>();
        listeRechercheToute = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, listeRecherche);
        listView.setAdapter(mAdapter);
    }

    private void dataProfil()
    {
        //Utilise le AsyncTask simplement pour  télécharger l'information dans le background de l'application
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... Void) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://wememe.ca/mobile_app/index.php?prefix=json&p=recherche")// Avec la requete php id descend de -3
                        .build();                                                                // A chaque fois que la requete est appeller
                try {
                    Response response = client.newCall(request).execute();

                    //Converti la reponse du serveur (JSON) dans un tableau de json pour ensuite etre capable de trier l'information
                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);
                        RechercherProfil rechercherProfil = new RechercherProfil(object.getInt("id"), object.getString("username"));
                        listeRecherche.add(object.getString("username"));
                        listeRechercheToute.add(rechercherProfil);
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
                mAdapter.notifyDataSetChanged();
            }
        };
        task.execute();//Execute la asynctaks
    }
}
