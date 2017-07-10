package com.anand.nobroker.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anand.nobroker.R;
import com.anand.nobroker.model.pojo.Datum;
import com.anand.nobroker.model.pojo.ImagesMap;
import com.anand.nobroker.model.pojo.Photo;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertiesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Datum> dataList;
    private Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    public PropertiesAdapter() {
        this.dataList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ItemView(itemView);
        } else if (viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_loader, parent, false);
            return new LoaderView(itemView);
        }
        throw new RuntimeException("No view type mentioned");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemView) {
            Datum datum = dataList.get(position);

            ItemView itemView = (ItemView) holder;
            //Set title
            itemView.txtTitle.setText(datum.getTitle());

            //Street address
            itemView.txtAddress.setText(datum.getStreet());

            //Rent
            NumberFormat nf = NumberFormat.getInstance();
            StringBuilder builder = new StringBuilder(context.getResources().getString(R.string.Rs));
            long price = datum.getRent();
            String formatted = nf.format(price);
            builder.append(" ");
            builder.append(formatted);
            itemView.txtPrice.setText(builder);

            //Furnishing
            itemView.txtDesc.setText(datum.getFurnishingDesc());

            //Lease type
            String firstLetter = datum.getLeaseType().substring(0, 1).toUpperCase();
            String restLetters = datum.getLeaseType().substring(1).toLowerCase();
            itemView.txtAllowed.setText(new StringBuilder(firstLetter).append(restLetters));

            //Area
            StringBuilder builder1 = new StringBuilder();
            builder1.append(datum.getPropertySize());
            builder1.append(" ");
            builder1.append("Sq.ft");
            itemView.txtArea.setText(builder1);

            //Build type

            //Load first original image
            if (datum.getPhotoAvailable()) {
                Photo photo = datum.getPhotos().get(0);
                ImagesMap imagesMap = photo.getImagesMap();
                String imgKey = imagesMap.getOriginal();
                loadImage(imgKey, itemView.img);
            } else {
                ((ItemView) holder).img.setImageResource(R.drawable.ic_action_home);
            }
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position > dataList.size() ? TYPE_FOOTER : TYPE_ITEM);
    }

    public void addPosts(List<Datum> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public void clearList() {
        dataList.clear();
        notifyDataSetChanged();
    }

    class ItemView extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.img) ImageView img;
        @BindView(R.id.txtAddress) TextView txtAddress;
        @BindView(R.id.txtDesc) TextView txtDesc;
        @BindView(R.id.txtPrice) TextView txtPrice;
        @BindView(R.id.txtAllowed) TextView txtAllowed;
        @BindView(R.id.txtArea) TextView txtArea;
        ItemView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class LoaderView extends RecyclerView.ViewHolder {
        LoaderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadImage(final String imgId, final ImageView imageView) {

        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(getUrl(imgId))
                .centerInside()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .into(imageView, new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess () {
                        Log.e("Url ", "success");
                    }

                    @Override
                    public void onError () {
                        Picasso.with(context)
                                .load(getUrl(imgId))
                                .fit()
                                .centerInside()
                                .into(imageView);
                        Log.e("Url ", "error");

                    }
                });
    }

    private String getUrl(String imgKey) {
        String[] seperated = imgKey.split("_");
        return  "http://d3snwcirvb4r88.cloudfront.net/images/" + seperated[0] + "/" + imgKey;
    }
}
