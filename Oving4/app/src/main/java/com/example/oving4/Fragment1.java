package com.example.oving4;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Roger on 29.09.2017.
 */

public class Fragment1 extends Fragment{
    private TextView title, description;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        return inflater.inflate(R.layout.detail, viewGroup);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        title = getView().findViewById(R.id.title_);
        description = getView().findViewById(R.id.description_);
        image = getView().findViewById(R.id.image_);
    }

    public void update(Object object){
        title.setText(object.getTitle());
        description.setText(object.getDescription());
        image.setImageDrawable(getResources().getDrawable(object.getImageID(), null));
    }


}
