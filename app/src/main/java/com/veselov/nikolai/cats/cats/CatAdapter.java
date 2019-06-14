package com.veselov.nikolai.cats.cats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import ru.veselov.nikolai.cats.R;
import com.veselov.nikolai.cats.data.CatItem;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private final List<CatItem> items = new ArrayList<>();

    private final LayoutInflater inflater;
    private final RequestManager imageLoader;
    @Nullable
    private final OnItemClickListener clickListener;

    public CatAdapter(Context context, @Nullable OnItemClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;

        final RequestOptions imageOption = new RequestOptions()
            .placeholder(R.drawable.image_placeholder)
            .fallback(R.drawable.image_placeholder)
            .centerCrop();
        this.imageLoader = Glide.with(context).applyDefaultRequestOptions(imageOption);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_news, parent, false), clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void replaceItems(@NonNull List<CatItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull CatItem newsItem);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView titleView;

        ViewHolder(@NonNull View itemView, @Nullable OnItemClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(items.get(position));
                }
            });
            imageView = itemView.findViewById(R.id.item_image);
            titleView = itemView.findViewById(R.id.item_title);
        }

        void bind(CatItem newsItem) {
            imageLoader.load(newsItem.getImageUrl())
                       .transition(DrawableTransitionOptions.withCrossFade())
                       .into(imageView);
            titleView.setText(newsItem.getTitle());
        }
    }

}
