package tp.com.usrestaurants;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    public static final String RESTOS_EXTRA_KEY = "RESTOS_EXTRA_KEY";
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private MapView mapView;
    private GoogleMap mGoogleMap;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> restaurantList2 = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewHorizontalAdapter horizontalAdapter;
    private RestaurantService restaurantService;
    private int pageNumber;
    private ProgressBar progressBar;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ButterKnife.bind(this);

        Intent mapIntent = getIntent();
        pageNumber = mapIntent.getIntExtra("PAGE_NUMBER", 1);
        restaurantList2 = (List<Restaurant>) mapIntent.getSerializableExtra("RESTOS_LIST");

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

        horizontalAdapter = new RecyclerViewHorizontalAdapter(MapActivity.this, restaurantList2);
        recyclerView.setAdapter(horizontalAdapter);
        RestosViewHolder holder = horizontalAdapter.onCreateViewHolder()

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
    public void onMapReady(GoogleMap googleMap) {
        Marker marker;

        mGoogleMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        int position = 0;

        for ( Restaurant restos : restaurantList2){
            LatLng ny = new LatLng(restos.getLatitude(), restos.getLongitude());
            markerOptions.position(ny)
            .title(restos.getName());
            marker = mGoogleMap.addMarker(markerOptions);
            marker.setTag(position);


            if (position == 0) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ny, 10));

            }

            position++;

        }
        progressBar.setVisibility(View.GONE);

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                try {
                    recyclerView.getLayoutManager().scrollToPosition((Integer) marker.getTag());
                }catch (Exception e){
                    Log.e("Erreur", e.toString());
                }
                return false;
            }
        });

    }

    public void zoomToMarker(View v){
       int pos = recyclerView.getLayoutManager().getPosition(v);
       Restaurant resto = restaurantList2.get(pos);
        LatLng latLng = new LatLng(resto.getLatitude(), resto.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 1500, null);
        try {
            letsGoTo(resto.getLatitude(), resto.getLongitude());
        }catch (Exception e){

        }
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void letsGoTo(Double lat, Double lon){
        String uri = "waze://?ll="+lat+", "+ lon +"&navigate=yes";
        Intent wazeIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        if (wazeIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(wazeIntent);
        }else {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+", "+lon);
            Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            googleMapIntent.setPackage("com.google.android.apps.maps");
            if (googleMapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(googleMapIntent);
            }
        }
    }
}
