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
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        getRestosCity();

    }




    public void searchImgButAction(View view){
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        String city = cityEditTextAutoComplete.getText().toString();
        Boolean checkBoxRes1 =  checkBox1.isChecked();
        Boolean checkBoxRes2 =  checkBox2.isChecked();
        Boolean checkBoxRes3 =  checkBox3.isChecked();
        Boolean checkBoxRes4 =  checkBox4.isChecked();

        intent.putExtra("CITY_NAME", city);
        intent.putExtra("CHECKBOX_PRICE_1", checkBoxRes1);
        intent.putExtra("CHECKBOX_PRICE_2", checkBoxRes2);
        intent.putExtra("CHECKBOX_PRICE_3", checkBoxRes3);
        intent.putExtra("CHECKBOX_PRICE_4", checkBoxRes4);
        startActivity(intent);
    }


    // Auto-completion lorsque l'on tape les 2 premières lettres d'une ville
    public void getRestosCity(){
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
