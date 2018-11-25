package tp.com.usrestaurants;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewHorizontalAdapter extends RecyclerView.Adapter<RestosViewHolder> {

    List<Restaurant> restaurants;
    Context context;
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
    public void onBindViewHolder(RestosViewHolder holder, final int position) {
        //holder.imageView.setImageResource(restaurants.get(position).getImage_url());
        Picasso.get()
                .load(restaurants.get(position).getImage_url())
                //.resize(80, 80)
                //.centerCrop()
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


    }

    @Override
    public int getItemCount() {
        int size = restaurants.size();
        return size;
    }


}
