package com.example.vlearn;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
public class CustomListAdapter  extends ArrayAdapter<Post> {

    private static final String TAG = "CustomListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    private static class ViewHolder {
        TextView title;
        TextView author;
        TextView date_updated;
        ProgressBar mProgressBar;
        ImageView thumbnailURL;
    }
    public CustomListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

        //sets up the image loader library
        setupImageLoader();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        //get the person's information
        final String title = getItem(position).getTitle();
        String imgUrl = getItem(position).getThumbnailURL();
        final String author = getItem(position).getAuthor();
        final String updated = getItem(position).getDate_updated();
        final String description = getItem(position).getDescription();
        final String link =getItem(position).getPostURL();
        final String source = getItem(position).getSource();

        try{


            //create the view result for showing the animation
            final View result;

            //ViewHolder object
            final ViewHolder holder;

            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(mResource, parent, false);
                holder= new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.cardTitle);
                holder.thumbnailURL = (ImageView) convertView.findViewById(R.id.cardImage);
                holder.author = (TextView) convertView.findViewById(R.id.cardAuthor);
                holder.date_updated = (TextView) convertView.findViewById(R.id.cardUpdated);
                holder.mProgressBar = (ProgressBar)convertView.findViewById(R.id.cardProgressDialog);

                result = convertView;

                convertView.setTag(holder);
            }
            else{
                holder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            holder.title.setText(title);
            holder.author.setText(author);
            holder.date_updated.setText(updated);


            //create the imageloader object
            ImageLoader imageLoader = ImageLoader.getInstance();

            int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed",null,mContext.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                    .cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage)
                    .showImageOnFail(defaultImage)
                    .showImageOnLoading(defaultImage).build();

            //download and display image from url
            imageLoader.displayImage(imgUrl, holder.thumbnailURL,options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    holder.mProgressBar.setVisibility(View.VISIBLE);
                }
                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    holder.mProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    holder.mProgressBar.setVisibility(View.GONE);
                }
                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    holder.mProgressBar.setVisibility(View.GONE);
                }

            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NewsFeedDetails.class);

                    intent.putExtra("title",title);
                    intent.putExtra("author",author);
                    intent.putExtra("updated",updated);
                    intent.putExtra("description",description);
                    intent.putExtra("link",link);
                    intent.putExtra("source",source);

                    view.getContext().startActivity(intent);
                }
            });


            return convertView;
        }catch (IllegalArgumentException e){
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );
            return convertView;
        }



    }

    /**
     * Required for setting up the Universal Image loader Library
     */
    private void setupImageLoader(){
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP
    }
}
