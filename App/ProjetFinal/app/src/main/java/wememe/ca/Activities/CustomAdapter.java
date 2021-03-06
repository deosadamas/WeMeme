package wememe.ca.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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

import wememe.ca.Class.Data_Feed;
import wememe.ca.Class.Like;
import wememe.ca.Class.Utilisateur;
import wememe.ca.R;
import wememe.ca.Class.DataLike;
import wememe.ca.Requetes.LikeRequest;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    //Declaration des variables
    private Context context;
    private List<Data_Feed> datameme_list; // Liste qui contient tout les Memes
    private List<DataLike> dataLike_list; // Liste qui contient toute la table de like selon l'id de l'utilisateur
    private List<Like> like_list; // Liste qui contient le nombre de like selon le Meme en question
    private int id;
    private MainActivity activity;
    Utilisateur utilisateur;
    boolean doubleClick  = false;


    //Constructeur qui permet d'assigner les variable en haut pour les assigner
    public CustomAdapter(Context context, List<Data_Feed> my_data, List<DataLike> likes, List<Like> like_list, MainActivity activity) {
        this.context = context;
        this.activity = activity;
        utilisateur = MainActivity.utilisateur;
        this.datameme_list = my_data;
        this.dataLike_list = likes;
        this.like_list = like_list;
    }

    //Cette methode override de viewHolder va simplement chercher le layout qu'il correspond
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    // Cette methode override onBindViewHolder a deux paramettre tres important
    // Le premier est le holder, le holder est l'object qui permet d'aller chercher les objects du layout
    // Le deuxieme est la position, simplment la position de l'élément dont il est dans la recycleview
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //Ce thread handler remplace simplement le doubleclick
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleClick = false;
                handler.postDelayed(this, 650);
            }
        }, 650);

        //Selon la position  du onBindViewHolder
        // On recherche un objec selon la postion du onBinViewHolder et on lui
        // assigne son type selon les choses qu'ont veux
        id = datameme_list.get(position).getId(); // Id du meme
        holder.Option.setImageResource(R.drawable.troispoints);// L'image en haut a droite de la cardView l'option Report un Meme
        holder.nom.setText(datameme_list.get(position).getNom());// Le nom de la personne qui a poster ce Meme

        //Simplement lors des test on regarder si les memes est dans le bon ordre et on regarde si
        // le like etait a la bonne place.
        holder.id_user_post.setText(String.valueOf(datameme_list.get(position).getId_user_post()));
        holder.ID.setText(String.valueOf(datameme_list.get(position).getId()));

        holder.like.setText(String.valueOf(like_list.get(position).getLike()));// Le nombre de like du Meme
        // Le Glide est une librairie https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en
        Glide.with(context).load(datameme_list.get(position).getImage_link()).into(holder.imagePhoto);//L'image de photo de la personne qui a poster le Meme
        Glide.with(context).load(datameme_list.get(position).getImage_link()).into(holder.imageView);// L'image du Meme
        Glide.with(context).load(R.drawable.nonelike).into(holder.Like);// l'image du like R.drawable.nonelike est simplement ceux qui sont pas like

        //Cette boucle parcoure la liste dataLike
        // Celle-ci a un condition si le meme est égale a l'id de la postion de la liste (datameme_list) ET
        // que l'id de l'utilisateur est égale a id_user_post dans la liste dataLike_list
        // Va changer l'image de base pour un image qui est liker
        for (int i = 0; i <= dataLike_list.size()-1; i++){
            int getMeme = dataLike_list.get(i).getMeme();
            int getUser = dataLike_list.get(i).getUser();

            if (id == getMeme && utilisateur.getId() == getUser){
                Glide.with(context).load(R.drawable.tbk).into(holder.Like);
            }
        }

        //Cette boucle parcoure la liste la liste like_list
        // La condition est que selon id (datameme_list.get(position).getId()) -> La postion du onBindViewHolder dans la liste
        // est égale a getMeme dans la liste qu'on parcoure, on lui assigne sa valeur
        // Sa valeur = Le nombre de like que le meme a dans la base de donner
        for (int i = 0; i <= like_list.size()-1; i++){
            int getMeme = like_list.get(i).getMeme();
            if(id == getMeme)
            {
                holder.like.setText(String.valueOf(like_list.get(i).getLike()));
            }
        }


        // Ce methode est que lorsque que l'utilisateur clique sur l'imageview du bonhomme sourrire (Like)
        // 1) Va ajouter dans la base de donnée +1 le nombre de like et
        // 2) Va ajouter dans la base de donnée dans la table Laught l'id de utilisateur qui a like le meme et id du meme
        holder.Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                int pos = position;//Pour refresh la position du onBindViewHolder
                int id_like_meme = datameme_list.get(pos).getId(); // Va chercher id_like_meme dans la liste de chaque meme
                int nbrelike = 0; //Initialise la variable int du nbrelike

                //Simplement la requete pour ajouter un like sinon explication au debut du setOnClickListener le 1) et 2)
                LikeRequest likeRequest = new LikeRequest(utilisateur.getId(), id_like_meme, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                });
                queue.add(likeRequest);

                //Création d'un object DataLike qui prend en paramettre l'id de l'utilisateur et id du like
                DataLike dataLike = new DataLike(utilisateur.getId(), id_like_meme);
                boolean image_liker = false; // Boolean qui regarder si l'image est liker ou pas
                int cpt = 0;//Initialise la variable int du cpt

                // Une boucle qui parcoure la liste (dataLike_list)
                // Cette boucle va simplement regarder si ya un element (dataLike_list.get(i).getMeme()) id_meme
                // Si celle-ci est égale a id_like_meme
                // Donc si c'est vrai sa veut dire que l'utilisateur a deja liker ce meme donc le boolean image_liker est true
                for(int i = 0; i < dataLike_list.size(); i++)
                {
                    if(dataLike_list.get(i).getMeme() == id_like_meme)
                    {
                        image_liker = true;
                        cpt = i;
                        break;
                    }
                }
                boolean likes = false;// Un boolean pour voir si le nbreLike d'une 0hoto a été rechercher
                int compteur = 0;//Initialise la variable int du compteur
                // Une boucle qui parcoure la liste like_list pour trouver sa postion selon id_meme qui est rechercher
                for(int i = 0; i < like_list.size(); i++)
                {
                    if(like_list.get(i).getMeme() == id_like_meme)
                    {
                        likes = true;
                        compteur = i;
                        break;
                    }
                }
                //Meme est deja liker par l'utilisateur donc il veut déliker (enlever son like de la photo)
                if(image_liker)
                {
                    //Va chercher le nbre de like de la photo (Meme)
                    int nbre = like_list.get(compteur).getLike();
                    nbrelike = nbre - 1;// le soustrait avec le nbre de like de la photo vu que l'utilisateur avait deja liker cette photo
                    dataLike_list.remove(cpt);//Enleve l'object selon la position qu'il lui correspond de la liste (dataLike_list)
                    notifyItemChanged(position);//Rafraichit la position de l'item qui a ete supprimer
                    image_liker = false; //Le boolean image_liker redevient false vu que celui-ci n'est plus liker
                }
                else // Veut simplment dire que c'est la premier fois que l'utilisateur clique sur la photo (Like la photo)
                {
                    //Pacoure la liste pour aller chercher la photo (Meme) que l'utilisateur veut liker
                    for(int i = 0; i<like_list.size(); i++)
                    {
                        //Contiditon pour trouver la postion de la photo dans la liste
                        if(id_like_meme == like_list.get(i).getMeme())
                        {
                            //Va chercher le nbre de like de la photo (Meme)
                            int nbre = like_list.get(i).getLike();
                            nbrelike = nbre + 1;// Prend le nombre de like de la photo et +1 vu que c'est la premiere fois que l'utilisateur like la photo
                            dataLike_list.add(dataLike);//L'ajouter dans la liste
                            notifyItemChanged(position);//Rafraichit la position de l'item qui a ete ajouter
                        }
                    }
                }
                if(likes)//L'élément nbrelike de la photo a été rechercher donc
                {
                    //On enleve l'ancien élément vu que celui est remplacer par un nouveau une nouvelle photo liker par utilisateur
                    /*
                        Explication plus détailler :

                        0hoto 23 like (Utilisateur la pas like)
                        donc int nbrelike = 23;
                        mais utilisateur decide de like cette photo
                        nbrelike = 23 + 1(Like de l'utilisateur);

                        On enleve selon sa position dans la liste (like_list) qui contient id_meme et le nombre de like
                        ensuite on crée un nouvelle object Like avec la position du id_meme et le nbreLike
                        On l'ajoute dans la liste like_list
                        Et on notifyItemChanged(position) -> Qui va simplement rafraichir la postion de l'item dans le recycleview
                    */
                    like_list.remove(compteur);
                    Like like = new Like(datameme_list.get(pos).getId(), nbrelike);
                    like_list.add(like);
                    notifyItemChanged(position);
                    likes = false;
                }
            }
        });


        // Imageview, si on clique sur celle-ci qui est la photo en haut a gauche d'un meme
        // Va simplement forcer l'utilisateur daller sur le profil de la personne
        // Donc va changer de fragment pour aller dans le fragment Profil
        holder.imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomBar myBottomBar = activity.getBottomBar();
                myBottomBar.selectTabAtPosition(4);
                MainActivity.id_user_post = datameme_list.get(position).getId_user_post();
            }
        });

        //Lorsque que l'utilisateur clique sur les trois-petit-point
        holder.Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_meme = datameme_list.get(position).getId();
                activity.StartReport(id_meme);//On commence l'activity report et en paramettre on prends l'id du meme en question
            }
        });

        //Lorsqu'on clique sur la photo (image) du meme
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Regarde si la personne a bien doubleClick sur l'image
                if (doubleClick){
                    //On appelle la methode static de l'activity de BigImage et on met en paramettre le String de l'image
                    BigImage.getImage(datameme_list.get(position).getImage_link());
                    Intent myIntent = new Intent(context, BigImage.class);
                    context.startActivity(myIntent);
                }
                doubleClick = true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return datameme_list.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        //Déclaration des variables dans la layout utiliser
        public TextView ID;
        public TextView id_user_post;
        public TextView nom;
        public TextView like;
        public ImageView imageView;
        public ImageView imagePhoto;
        public ImageView Like;
        public ImageView Option;

        public ViewHolder(View itemView) {
            super(itemView);

            //Assignation des variable
            id_user_post = (TextView)itemView.findViewById(R.id.txt_id_user_post);
            nom = (TextView) itemView.findViewById(R.id.nom);
            Like = (ImageView) itemView.findViewById(R.id.imageView4);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imagePhoto = (ImageView) itemView.findViewById(R.id.circleImageView);
            ID = (TextView) itemView.findViewById(R.id.txt_ID);
            like = (TextView) itemView.findViewById(R.id.txtlike);
            Option = (ImageView) itemView.findViewById(R.id.iv_Option);
        }
    }


}
