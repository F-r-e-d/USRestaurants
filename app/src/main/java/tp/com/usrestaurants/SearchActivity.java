package tp.com.usrestaurants;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    AutoCompleteTextView cityEditTextAutoComplete;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;

    private RestaurantService restaurantService;
    List<String> city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        cityEditTextAutoComplete = findViewById(R.id.cityEditTextAutoComplete);
        checkBox1 = findViewById(R.id.checkBox1);

        getRestosCity();

    }




    public void searchImgButAction(View view){
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        String city = cityEditTextAutoComplete.getText().toString();
        Boolean checkBoxRes = checkBox1.isChecked();
        intent.putExtra("CITY_NAME", city);
        intent.putExtra("CHECKBOX_PRICE_1", checkBoxRes);
        startActivity(intent);
    }


    public void getRestosCity(){
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.addInterceptor(loggingInterceptor);

        Retrofit.Builder builder1 = new Retrofit.Builder()
                .baseUrl("http://opentable.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder1.build();

        restaurantService = retrofit.create(RestaurantService.class);

        Call<CityData> call = restaurantService.getCity();

      call.enqueue(new Callback<CityData>() {
          @Override
          public void onResponse(Call<CityData> call, Response<CityData> response) {
              city = response.body().cities;

              ArrayAdapter<String> adapter = new ArrayAdapter<String>
                      (SearchActivity.this,android.R.layout.simple_list_item_1,city);
              cityEditTextAutoComplete.setAdapter(adapter);
          }

          @Override
          public void onFailure(Call<CityData> call, Throwable t) {
              Toast.makeText(SearchActivity.this, "Error", Toast.LENGTH_LONG);

          }
      });

    }
}
