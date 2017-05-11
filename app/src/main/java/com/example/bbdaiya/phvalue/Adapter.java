package com.example.bbdaiya.phvalue;

/**
 * Created by bbdaiya on 12-May-17.
 */




        import android.content.Context;
        import android.support.v7.widget.RecyclerView;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Data> results = new ArrayList<>();
    public Adapter(Context mContext, ArrayList<Data> results) {
        this.mContext = mContext;
        this.results = results;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        String val = results.get(position).getValue();
        String date = results.get(position).getDate_added();
        holder.phValue.setText(val);
        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView phValue;
        public TextView date;

        public MyViewHolder(View itemView) {
            super(itemView);
            phValue = (TextView) itemView.findViewById(R.id.pHvalue);
            date = (TextView) itemView.findViewById(R.id.date);
        }

    }

}

