package android.uom.trakt.Search;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import android.uom.trakt.R;
import android.uom.trakt.Movie;

import java.util.ArrayList;

/**
 * Created by Akshay on 6/23/2016.
 */
public class PopularMovieAdapter extends BaseAdapter {

    Context context;
    ArrayList<Movie> mMovieList;


    public PopularMovieAdapter(Context context, ArrayList<Movie> mMovieList) {
        this.context = context;
        this.mMovieList = mMovieList;
    }


    @Override
    public int getCount() {
        return PopularMoviesFragment.mMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return PopularMoviesFragment.mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // If holder not exist then locate all view from UI file.
        //if (convertView == null) {
        // inflate UI from XML file
        convertView = inflater.inflate(R.layout.show_item, parent, false);
        // get all UI view
        holder = new ViewHolder(convertView);
        // set tag for holder
        convertView.setTag(holder);
        //}  else {
        // if holder created, get tag from view
        //  holder = (ViewHolder) convertView.getTag();
        //}

        Movie movie = (Movie) getItem(position);

        holder.title.setText(movie.getTitle());
        holder.year.setText(""+movie.getYear());
        try{
            Picasso.with(context)
                    .load(movie.getImageUrl())
                    //.centerCrop()
                    .into(holder.banner);
        }catch (Exception e){ e.printStackTrace(); }
        return convertView;
    }

    private static class ViewHolder {
        private TextView title,year,rating;
        private ImageView banner;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.gridview_item_show_title);
            year = (TextView) v.findViewById(R.id.gridview_item_show_year);
            banner = (ImageView) v.findViewById(R.id.gridview_item_show_poster);
        }
    }
}