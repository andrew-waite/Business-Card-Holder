package andrew.mobiletechnology_assignment1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import andrew.mobiletechnology_assignment1.BusinessCard;
import andrew.mobiletechnology_assignment1.R;

/**
 * Created by Andrew on 16/03/2017.
 */

public class ImageListAdapter extends ArrayAdapter<BusinessCard>
{
    private ArrayList<BusinessCard> cards;

    public ImageListAdapter(Context context, int resource, ArrayList<BusinessCard> objects)
    {
        super(context, resource, objects);
        this.cards = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_list_view, parent, false);
        }

        BusinessCard card = this.cards.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        icon.setImageResource(R.mipmap.ic_defualt_profile);

        TextView title = (TextView) convertView.findViewById(R.id.textViewTitle);
        title.setText(card.getFirstName() + " " + card.getLastName());

        return convertView;
    }
}
