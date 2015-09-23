package br.ufsc.labtec.mazk.activities.fragments.main.manager.area;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.ufsc.labtec.mazk.R;

/**
 * Created by Mihael Zamin on 12/08/2015.
 */
public class ListAreasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listareas, container, false);

        
        return v;

    }


}
