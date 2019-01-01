package tp.com.usrestaurants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewHorizontalAdapter extends RecyclerView.Adapter<RestosViewHolder> {

    List<Restaurant> restaurants;
    static Context context;
    LayoutInflater inflater;


    public RecyclerViewHorizontalAdapter(Context context, List<Restaurant> restaurants) {
        this.context = context;
        this.restaurants = restaurants;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RestosViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = inflater.inflate(R.layout.horizontal_recycler_view_list, parent, false);
        RestosViewHolder restosViewHolder = new RestosViewHolder(view);
        return restosViewHolder;
    }



    @Override
    public void onBindViewHolder(final RestosViewHolder holder, final int position) {
        Picasso.get()
                .load(restaurants.get(position).getImage_url())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //imgLoadProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
        holder.restoNameTxt.setText(restaurants.get(position).getName());
        holder.addressTxt.setText(restaurants.get(position).getAddress());
        holder.countryTxt.setText(restaurants.get(position).getCity());

        switch (restaurants.get(position).getPrice()){
            case 1:
                holder.dollarImg1.setVisibility(View.VISIBLE);
                break;

            case 2:
                holder.dollarImg1.setVisibility(View.VISIBLE);
                holder.dollarImg2.setVisibility(View.VISIBLE);
                break;

            case 3:
                holder.dollarImg1.setVisibility(View.VISIBLE);
                holder.dollarImg2.setVisibility(View.VISIBLE);
                holder.dollarImg3.setVisibility(View.VISIBLE);
                break;


        }

        holder.directionFloatingBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Restaurant resto = restaurants.get(position);
                   letsGoTo(resto.getLatitude(), resto.getLongitude());
                }catch (Exception e){

                }
            }
        });

    }

    public static void letsGoTo(Double lat, Double lon){
        String uri = "waze://?ll="+lat+", "+ lon +"&navigate=yes";
        Intent wazeIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        if (wazeIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(wazeIntent);
        }else {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat+", "+lon);
            Intent googleMapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            googleMapIntent.setPackage("com.google.android.apps.maps");
            if (googleMapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(googleMapIntent);
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = restaurants.size();
        return size;
    }


}
