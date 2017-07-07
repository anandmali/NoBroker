package com.anand.nobroker.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.anand.nobroker.R;
import com.anand.nobroker.model.pojo.Datum;
import com.anand.nobroker.model.pojo.ImagesMap;
import com.anand.nobroker.model.pojo.Photo;
import com.anand.nobroker.util.ImageFetcher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.ViewHolder> {

    private List<Datum> dataList;
    private Context context;
    private ImageFetcher imageFetcher;
    int width;

    public PropertiesAdapter(ImageFetcher imageFetcher) {
        this.imageFetcher = imageFetcher;
        this.dataList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Datum datum = dataList.get(position);

        holder.txtTitle.setText(datum.getTitle());
        holder.txtAddress.setText(datum.getPropertyTitle());


        holder.img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                width = holder.img.getWidth();
            }
        });


        if (datum.getPhotoAvailable()) {
            Photo photo = datum.getPhotos().get(0);
            ImagesMap imagesMap = photo.getImagesMap();
            String imgKey = imagesMap.getOriginal();
//            loadImage(imgKey, holder.img);

            // Finally load the image asynchronously into the ImageView, this also takes care of
            // setting a placeholder image while the background thread runs

            imageFetcher.setImageSize(width, 200);
            imageFetcher.loadImage(getUrl(imgKey), holder.img);
        }

    }

    private Object getUrl(String imgKey) {
        String[] seperated = imgKey.split("_");
        return  "http://d3snwcirvb4r88.cloudfront.net/images/" + seperated[0] + "/" + imgKey;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addPosts(List<Datum> data) {
        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.txtAddress) TextView txtAddress;
        @BindView(R.id.txtDesc) TextView txtDesc;
        @BindView(R.id.txtPrice) TextView txtPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void loadImage(final String imgId, final ImageView imageView) {
        //get your image view
        //    final ImageView imageView = (ImageView) view.findViewById(R.id.imgFilePreview);

        String[] seperated = imgId.split("_");

        final String url = "http://d3snwcirvb4r88.cloudfront.net/images/" + seperated[0] + "/" + imgId;
        Log.e("Url ", url);
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(url)
                .centerInside()
                .fit()
                .into(imageView, new com.squareup.picasso.Callback(){
                    @Override
                    public void onSuccess () {
                        Log.e("Url ", "success");
                    }

                    @Override
                    public void onError () {
                        Picasso.with(context)
                                .load(url)
                                .fit()
                                .centerInside()
                                .into(imageView);
                        Log.e("Url ", "error");

                    }
                });
    }

}
