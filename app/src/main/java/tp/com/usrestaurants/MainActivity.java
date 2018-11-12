package tp.com.usrestaurants;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private RestaurantsAdapter restaurantsAdapter;

    private RestaurantService restaurantService;

    private Page pageNumber;

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

        getRestaurant(1);
        pageNumber = new Page(1);


    }


    public void getRestaurant(int page){
        Call<RestaurantData> call = restaurantService.getRestaurantPerPage("US",page);

        call.enqueue(new Callback<RestaurantData>() {
            @Override
            public void onResponse(Call<RestaurantData> call, Response<RestaurantData> response) {
                if (response.isSuccessful()) {
                    RestaurantData result = response.body();
                    restaurantsAdapter = new RestaurantsAdapter(MainActivity.this, result.restaurants);
                    listViewRestaurants.setAdapter(restaurantsAdapter);
                    progressBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(MainActivity.this, "Le serveur a rencontré une erreur " +response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantData> call, Throwable t) {
                Toast.makeText(MainActivity.this, "L'appel a échoué", Toast.LENGTH_LONG).show();

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




}
