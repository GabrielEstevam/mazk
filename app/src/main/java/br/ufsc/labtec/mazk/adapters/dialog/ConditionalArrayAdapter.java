package br.ufsc.labtec.mazk.adapters.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import br.ufsc.labtec.mazk.R;

/**
 * Created by Mihael Zamin on 15/04/2015.
 */
public class ConditionalArrayAdapter extends ArrayAdapter<String> {
    private EnableItemCondition condition;
    private int resource;

    public void setCondition(EnableItemCondition condition) {
        this.condition = condition;
    }

    public ConditionalArrayAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi;
            vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.setTextView((TextView)convertView.findViewById(R.id.tvOption));
            holder.getTextView().setText(getItem(position));
            convertView.setTag(holder);

        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return (condition == null) ? super.isEnabled(position) : condition.enableItem(position);
    }

    private class ViewHolder
    {
        private TextView textView;

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }
    public interface EnableItemCondition
    {
        public boolean enableItem(int position);
    }
}
