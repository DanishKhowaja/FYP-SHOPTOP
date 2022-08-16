package com.example.shoptop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class RecyclerMarketsAdapter extends FirebaseRecyclerAdapter<MarketsModel,RecyclerMarketsAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerMarketsAdapter(@NonNull FirebaseRecyclerOptions<MarketsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerMarketsAdapter.ViewHolder holder, int position, @NonNull MarketsModel model) {
        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
    }

    @NonNull
    @Override
    public RecyclerMarketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.markets_recycler_view,parent,false);
        return new RecyclerMarketsAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.mname);
            Address = itemView.findViewById(R.id.maddress);

        }
    }
}
