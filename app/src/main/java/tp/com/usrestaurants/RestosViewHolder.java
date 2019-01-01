package tp.com.usrestaurants;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.lang.String.valueOf;

public class RestosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView restoNameTxt;
        TextView addressTxt;
        TextView countryTxt;
        ImageView dollarImg1;
        ImageView dollarImg2;
        ImageView dollarImg3;
        FloatingActionButton directionFloatingBut;
        int position;

    public RestosViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imgViewRecyclerView);
        restoNameTxt = itemView.findViewById(R.id.restoNameRecyclerViewTxt);
        addressTxt = itemView.findViewById(R.id.adress_txt);
        countryTxt = itemView.findViewById(R.id.country_txt);
        dollarImg1 = itemView.findViewById(R.id.dollarImg1);
        dollarImg2 = itemView.findViewById(R.id.dollarImg2);
        dollarImg3 = itemView.findViewById(R.id.dollarImg3);
        directionFloatingBut = itemView.findViewById(R.id.directionFloatingButton);

        ButterKnife.bind(this, itemView);

    }

    @Override
    public void onClick(View view) {
        position = getAdapterPosition();
    }

}
