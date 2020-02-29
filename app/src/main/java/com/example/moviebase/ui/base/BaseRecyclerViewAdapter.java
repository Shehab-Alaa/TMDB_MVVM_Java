package com.example.moviebase.ui.base;


import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private List<T> items;

    public BaseRecyclerViewAdapter(List<T> items){
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    public void clearItems() {
        items.clear();
    }

    public void addItems(List<T> items){
        if (items != null) {
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    // TODO :: Empty Item For Trailer and Review Adapter
    // TODO :: Apply Data Binding to MovieDetails;
    // TODO :: make Empty take all the Screen and not to show first thing before progressBar;

    @Override
    public int getItemCount() {
        return items != null && items.size() > 0 ? items.size() : 1;
    } // 1 for Binding Empty Item;

    public List< T > getItems() {
        return items;
    }
}
