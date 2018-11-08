package tp.com.usrestaurants;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

    private RestaurantsAdapter restaurantsAdapter;

    private RestaurantService restaurantService;

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

        getRestaurant();

    }


    @OnClick(R.id.butGetResto)
    public void getRestaurant(){
        Call<RestaurantData> call = restaurantService.getRestaurantPerPage("US",2);

        call.enqueue(new Callback<RestaurantData>() {
            @Override
            public void onResponse(Call<RestaurantData> call, Response<RestaurantData> response) {
                if (response.isSuccessful()) {
                    RestaurantData result = response.body();
                    restaurantsAdapter = new RestaurantsAdapter(MainActivity.this, result.restaurants);
                    listViewRestaurants.setAdapter(restaurantsAdapter);

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


}
