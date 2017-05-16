package si3.ihm.polytech.capsophia.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import si3.ihm.polytech.capsophia.R;

/**
 * Created by user on 08/05/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsModel> {

    public NewsAdapter(@NonNull Context context, @NonNull List<NewsModel> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.news, parent, false);
        }

        NewsModel p = getItem(position);

        TextView title = (TextView) v.findViewById(R.id.news_title);
        title.setText(p.getTitle());
        TextView subtitle = (TextView) v.findViewById(R.id.news_subtitle);
        subtitle.setText(p.getSubtitle());
        ImageView iv = (ImageView) v.findViewById(R.id.news_image);
        iv.setImageResource(p.getImgResId());

        return v;
    }
}
