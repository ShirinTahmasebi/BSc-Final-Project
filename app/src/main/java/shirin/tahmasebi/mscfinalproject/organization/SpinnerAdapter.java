package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FontableTextView view = (FontableTextView) super.getView(position, convertView, parent);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        FontableTextView view = (FontableTextView)
                super.getDropDownView(position, convertView, parent);
        return view;
    }
}
