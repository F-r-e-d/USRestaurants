package tp.com.usrestaurants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RestaurantsAdapter extends ArrayAdapter {

    public RestaurantsAdapter(Context context, List<Restaurant> restaurants) {
        super(context, R.layout.listview_layout, restaurants);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        final Restaurant restaurant = (Restaurant) getItem(position);

        if (restaurant == null){
            throw new IllegalStateException("A null restaurant finf in RestaurantAdapter");
        }

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_layout, parent, false);
        }

        TextView restoName = convertView.findViewById(R.id.restos_name_txt);
        restoName.setText(restaurant.name);

        return convertView;
    }
}
