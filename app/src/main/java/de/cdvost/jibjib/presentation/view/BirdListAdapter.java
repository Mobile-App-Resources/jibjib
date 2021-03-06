package de.cdvost.jibjib.presentation.view;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.cdvost.jibjib.R;
import de.cdvost.jibjib.domain.interactors.web.dto.MatchedBird;


public class BirdListAdapter extends RecyclerView.Adapter<BirdListAdapter.ViewHolder> {
    private List<String> nameValues;
    private List<String> accuracyValues;
    List<MatchedBird> birdDataset;
    MatchResultView mView;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView birdName;
        TextView birdAccuracy;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            birdName = (TextView) v.findViewById(R.id.match_bird_name);
            //birdAccuracy = (TextView) v.findViewById(R.id.match_bird_accuracy);
            //birdAccuracy.setVisibility(View.VISIBLE);
        }
    }

    public void add(int position, String item) {
        nameValues.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        nameValues.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BirdListAdapter(MatchResultView view, List<MatchedBird> myDataset) {
        mView = view;
        birdDataset = myDataset;
        nameValues = new ArrayList<>();
        accuracyValues = new ArrayList<>();
        for (MatchedBird bird : myDataset) {
            if (bird.getAccuracy() * 100 > 0.5) {
                nameValues.add(bird.getBird().getTitle_de() /*+ "\n"*/ + "  (" + bird.getBird().getName() + ")");
                accuracyValues.add(Float.toString(bird.getAccuracy()));
            }
        }
    }

    @Override
    public BirdListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.match_bird_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = nameValues.get(position);
        //final String accuracy = accuracyValues.get(position);
        holder.birdName.setText(name);
        switch (position) {
            case 0:
                holder.birdName.setTextColor(mView.getResources().getColor(R.color.ciRed));
                break;
            case 1:
                holder.birdName.setTextColor(mView.getResources().getColor(R.color.ciDarkBlue));
                break;
            case 2:
                holder.birdName.setTextColor(mView.getResources().getColor(R.color.ciMidBlue));
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.itemClick(birdDataset.get(position));
            }
        });

        //holder.birdAccuracy.setText(accuracy);
    }

    @Override
    public int getItemCount() {
        return nameValues.size();
    }

}