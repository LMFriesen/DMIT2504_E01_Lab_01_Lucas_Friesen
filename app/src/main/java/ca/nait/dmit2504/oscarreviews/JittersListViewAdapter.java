package ca.nait.dmit2504.oscarreviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class JittersListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Jitter> jittersList;
    private LayoutInflater layoutInflater;

    public JittersListViewAdapter(final Context context, final List<Jitter> jittersList) {
        this.context = context;
        this.jittersList = jittersList;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return jittersList.size();
    }

    @Override
    public Jitter getItem(final int position) {
        return jittersList.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // Inflate the XML custom listview item layout file into a Java View object
        View rowView = layoutInflater.inflate(R.layout.view_reviews, null);

        // Find the individual views in the custom listview layout
        TextView dateTextView = rowView.findViewById(R.id.date);
        TextView reviewerTextView = rowView.findViewById(R.id.reviewer);
        TextView categoryTextView = rowView.findViewById(R.id.category);
        TextView nomineeTextView = rowView.findViewById(R.id.nominee);
        TextView reviewTextView = rowView.findViewById(R.id.review);

        // Change the text for each item
        Jitter currentJitter = jittersList.get(position);
        dateTextView.setText(currentJitter.getDate());
        reviewerTextView.setText(currentJitter.getReviewer());
        categoryTextView.setText(currentJitter.getCategory());
        nomineeTextView.setText(currentJitter.getNominee());
        reviewTextView.setText(currentJitter.getReview());

        return rowView;
    }
}
