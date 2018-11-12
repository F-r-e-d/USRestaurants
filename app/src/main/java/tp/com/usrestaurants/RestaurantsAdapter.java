package tp.com.usrestaurants;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantsAdapter extends ArrayAdapter {

    public RestaurantsAdapter(Context context, List<Restaurant> restaurants) {
        super(context, R.layout.listview_layout, restaurants);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final Restaurant restaurant = (Restaurant) getItem(position);
        ImageView imgResto;


        if (restaurant == null){
            throw new IllegalStateException("A null restaurant finf in RestaurantAdapter");
        }

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.listview_layout, parent, false);

        }
        imgResto  = convertView.findViewById(R.id.restoImgView);
        TextView restoName = convertView.findViewById(R.id.restosNameTxt);
        TextView city = convertView.findViewById(R.id.cityTxt);
        final ProgressBar imgLoadProgressBar = convertView.findViewById(R.id.imgLoadProgressBar);


                String url = ((Restaurant) getItem(position)).image_url;

        Picasso.get()
                .load(url)
                .into(imgResto, new Callback() {
                    @Override
                    public void onSuccess() {
                        imgLoadProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

        restoName.setText(restaurant.name);
        city.setText(restaurant.city);

        return convertView;
    }
}
