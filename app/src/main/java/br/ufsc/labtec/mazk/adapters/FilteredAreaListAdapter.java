package br.ufsc.labtec.mazk.adapters;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

import br.ufsc.labtec.mazk.R;
import br.ufsc.labtec.mazk.beans.Area;
import br.ufsc.labtec.mazk.beans.Usuario;
import br.ufsc.labtec.mazk.services.AreaResource;
import br.ufsc.labtec.mazk.services.util.AreaService;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Mihael Zamin on 09/09/2015.
 */
public class FilteredAreaListAdapter extends AreaListAdapter implements Filterable {

    private AreaResource ar;

    public FilteredAreaListAdapter(Context context, Usuario u) {
        super(context);
        ar = new AreaService().createService(context.getString(R.string.server_url), u.getEmail(), u.getSenha());
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults filterResults = new FilterResults();
                filterResults.count = -2;
                if (constraint != null && constraint != "") {
                    ar.findArea(constraint.toString(), new Callback<List<Area>>() {
                        @Override
                        public void success(List<Area> areas, Response response) {
                            setListAndNotNotify(areas);
                            filterResults.count = areas.size();
                            filterResults.values = areas;
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            filterResults.count = -1;
                        }
                    });


                } else {
                    ar.getAreas(new Callback<List<Area>>() {
                        @Override
                        public void success(List<Area> areas, Response response) {
                            setListAndNotNotify(areas);
                            filterResults.count = areas.size();
                            filterResults.values = areas;
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            filterResults.count = -1;

                        }
                    });
                }
                while (filterResults.count == -2) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null) {
                    if (results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };
        return f;
    }
}
