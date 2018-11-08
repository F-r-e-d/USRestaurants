package tp.com.usrestaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {
    @GET("restaurants")
    Call<RestaurantData> getRestaurantPerPage(@Query("country") String country, @Query("page") int page);
}
