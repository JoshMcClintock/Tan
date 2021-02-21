package who.is.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import who.is.myapplication.R;

public class DashboardFragment extends Fragment {

    Intent myIntent;
    Button viewBudgetbtn;

    public DashboardFragment(){
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        viewBudgetbtn = (Button)view.findViewById(R.id.button2Add);
        viewBudgetbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                BudgetActivity budget = new BudgetActivity();//could probably just use the mainActivity for this

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, budget);
                extract.commitAllowingStateLoss();
            }
        });

        return view;
        }
    }
