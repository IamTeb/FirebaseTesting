package com.example.codetribe.firebasetesting;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Codetribe on 2016/11/21.
 */

public class ResultsAdapter extends ArrayAdapter<RetrieveClass>{

    public ResultsAdapter(Context context, int resource)
    {
        super(context, resource);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.recycle,parent,false);
        }
        RetrieveClass currentWord = getItem(position);

        TextView emailTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        emailTextView.setText(currentWord.getDefaultEmail());

        TextView messageTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        messageTextView.setText(currentWord.getDefaultMessage());


        return listItemView;
    }
}
