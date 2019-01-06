package tp.com.usrestaurants;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewRestaurants)
    ListView listViewRestaurants;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.mapFloatingButton)
    FloatingActionButton mapFloatingButton;

    @BindView(R.id.nextImgBut)
    ImageButton nextImgBut;

    @BindView(R.id.prevImgBut)
    ImageButton prevImgBut;


    private RestaurantsAdapter restaurantsAdapter;

    private RestaurantService restaurantService;

    private List<Restaurant> listRestaurant = new ArrayList<>();

    private Page pageNumber;
    private String citySearch = null;
    private Boolean checkBoxPrice1;
    private Boolean checkBoxPrice2;
    private Boolean checkBoxPrice3;
    private Boolean checkBoxPrice4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://opentable.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restaurantService = retrofit.create(RestaurantService.class);

//        Récupération du nom du champs de recherche
        Intent mainIntent = getIntent();
        citySearch = mainIntent.getStringExtra("CITY_NAME");
        checkBoxPrice1 = mainIntent.getBooleanExtra("CHECKBOX_PRICE_1", true);
        checkBoxPrice2 = mainIntent.getBooleanExtra("CHECKBOX_PRICE_2", true);
        checkBoxPrice3 = mainIntent.getBooleanExtra("CHECKBOX_PRICE_3", true);
        checkBoxPrice4 = mainIntent.getBooleanExtra("CHECKBOX_PRICE_4", true);

        //Si citySearch n'est pas null, On affiche les restos correspondants, sinon on affiche les retos de la première page
        if (! TextUtils.isEmpty(citySearch)) {
            getRestaurantByCity(citySearch);
            nextImgBut.setVisibility(View.INVISIBLE);
            prevImgBut.setVisibility(View.INVISIBLE);
        }else {
            getRestaurant(1);
        }

        pageNumber = new Page(1);

        //Bouton pour ouvrir l'activiyé Map. On envoie le numéro de page ainsi que la liste des restos
        mapFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainActivity.this, MapActivity.class);
                mapIntent.putExtra("PAGE_NUMBER", pageNumber.getPage());
                mapIntent.putExtra("RESTOS_LIST", (Serializable) listRestaurant);
                startActivity(mapIntent);
            }
        });

    }


    //Récupération des restos par page
    public void getRestaurant(int page){
        Call<RestaurantData> call = restaurantService.getRestaurantPerPage("US",page);

        call.enqueue(new Callback<RestaurantData>() {
            @Override
            public void onResponse(Call<RestaurantData> call, Response<RestaurantData> response) {
                if (response.isSuccessful()) {
                    RestaurantData result = response.body();
                    restaurantsAdapter = new RestaurantsAdapter(MainActivity.this, result.restaurants);
                    listRestaurant.clear();
                    listRestaurant.addAll(result.restaurants);
                    listViewRestaurants.setAdapter(restaurantsAdapter);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(MainActivity.this, "Le serveur a rencontré une erreur " +response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "L'appel a échoué", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    //Récupération des restos par ville
     public void getRestaurantByCity(String city){
        Call<RestaurantData> call = restaurantService.getRestaurantByCity(city, 1);

        call.enqueue(new Callback<RestaurantData>() {
            @Override
            public void onResponse(Call<RestaurantData> call, Response<RestaurantData> response) {
                if (response.isSuccessful()) {
                    RestaurantData result = response.body();
                    restaurantsAdapter = new RestaurantsAdapter(MainActivity.this, result.restaurants);
                    listRestaurant.clear();
                    listRestaurant.addAll(result.restaurants);
                    listViewRestaurants.setAdapter(restaurantsAdapter);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(MainActivity.this, "Le serveur a rencontré une erreur " +response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "L'appel a échoué", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);

            }
        });

    }


    @OnClick(R.id.nextImgBut)
    public void nextPage(){
        int nextPage = pageNumber.getPage()+1;
        getRestaurant(nextPage);
        pageNumber.setPage(nextPage);
    }

    @OnClick(R.id.prevImgBut)
    public void prevPage(){
        int prevPage = pageNumber.getPage() - 1;
        if (prevPage > 0) {
            getRestaurant(prevPage);
            pageNumber.setPage(prevPage);
        }
    }

    //Ouverture de l'url du resto correspondant dans une webView
    @OnItemClick(R.id.listViewRestaurants)
    public void onRestaurantClick(int position){
        Restaurant restoSelected = (Restaurant) restaurantsAdapter.getItem(position);
        String urlMobileResto = restoSelected.mobile_reserve_url;
        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra("URL_MOBILE", urlMobileResto);
        startActivity(webViewIntent);
    }

    @OnClick(R.id.searchActivityBut)
    public void searchBut(){
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
    }

}
