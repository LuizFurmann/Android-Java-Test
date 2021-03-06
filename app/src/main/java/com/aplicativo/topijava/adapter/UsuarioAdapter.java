package com.aplicativo.topijava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.aplicativo.topijava.R;
import com.aplicativo.topijava.model.Data;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UserViewHolder> implements Filterable{

    private List<Data> listItems;
    private List<Data> filteredListItems;

    public void setListItems(List<Data> listItems){
        this.listItems = listItems;
        filteredListItems = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.name.setText(listItems.get(position).getName());
        holder.description.setText(listItems.get(position).getDescription());
        holder.forks.setText(listItems.get(position).getForsk());
        holder.stargazers_count.setText(listItems.get(position).getStargazers_count());
        holder.full_name.setText(listItems.get(position).getFull_name());
        holder.login.setText(listItems.get(position).getLogin());
        Glide.with(holder.avatar_image)
                .load(listItems.get(position).getOwner().getAvatar_url())
                .into(holder.avatar_image);
    }

    @Override
    public int getItemCount() {
        if(listItems == null)
            return 0;
        else
            return listItems.size();
    }

    //Filter method
    @Override
    public Filter getFilter() {
        return FilterUser;
    }
    private Filter FilterUser = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText = charSequence.toString().toLowerCase();
            List<Data>newList = new ArrayList<>();
            if(searchText.length()==0 || searchText.isEmpty()){
                newList.addAll(filteredListItems);
            }else{
                for(Data item: filteredListItems){
                    if(item.getName().toLowerCase().contains(searchText)){
                        newList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = newList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listItems.clear();
            listItems.addAll((Collection<? extends Data>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar_image;
        TextView name;
        TextView description;
        TextView forks;
        TextView stargazers_count;
        TextView full_name;
        TextView login;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar_image = itemView.findViewById(R.id.avatar_image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            forks = itemView.findViewById(R.id.forks);
            stargazers_count = itemView.findViewById(R.id.star);
            full_name = itemView.findViewById(R.id.full_name);
            login = itemView.findViewById(R.id.login);
        }
    }
}
