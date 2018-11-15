package tp.com.usrestaurants;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RestosViewHolder extends RecyclerView.ViewHolder {
     ImageView imageView;
     TextView restoNameTxt;
     TextView price;

    public RestosViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imgViewRecyclerView);
        restoNameTxt = itemView.findViewById(R.id.restoNameRecyclerViewTxt);
        price = itemView.findViewById(R.id.priceRecyclerViewTxt);
    }
}
