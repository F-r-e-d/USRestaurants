package tp.com.usrestaurants;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    public static final String RESTOS_EXTRA_KEY = "RESTOS_EXTRA_KEY";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap googleMap;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewHorizontalAdapter horizontalAdapter;
    private RestaurantService restaurantService;


    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        progressBar =findViewById(R.id.progressBarRecyclerView);


        final Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(RESTOS_EXTRA_KEY);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }


        mapView = findViewById(R.id.mapActivityMapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        recyclerView = findViewById(R.id.restosCardsRecyclerView);

       // recyclerView.addItemDecoration(new DividerItemDecoration(MapActivity.this, LinearLayoutManager.HORIZONTAL));
        horizontalAdapter = new RecyclerViewHorizontalAdapter(MapActivity.this, restaurantList);
        //LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MapActivity.this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(horizontalAdapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://opentable.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restaurantService = retrofit.create(RestaurantService.class);

        Call<RestaurantData> call = restaurantService.getRestaurantPerPage("US",2);

        call.enqueue(new Callback<RestaurantData>() {
            @Override
            public void onResponse(Call<RestaurantData> call, Response<RestaurantData> response) {
                if (response.isSuccessful()) {
                    RestaurantData result = response.body();
                    restaurantList.addAll(result.restaurants);
                    //LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MapActivity.this, LinearLayoutManager.HORIZONTAL, false);
                    //recyclerView.setLayoutManager(horizontalLayoutManager);
                    //recyclerView.setAdapter(horizontalAdapter);
                    horizontalAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }else {
                    Toast.makeText(MapActivity.this, "Le serveur a rencontré une erreur " +response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RestaurantData> call, Throwable t) {
                Toast.makeText(MapActivity.this, "L'appel a échoué", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap = googleMap;
        googleMap.setMinZoomPreference(12);

        LatLng ny = new LatLng(41.085025, -81.515763);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}