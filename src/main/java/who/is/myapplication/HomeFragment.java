package who.is.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import who.is.myapplication.R;
import who.is.myapplication.introActivity.introductionScreen;


public class HomeFragment extends Fragment {
    Button btn;
    Button intro;

    public HomeFragment(){
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btn = (Button)view.findViewById(R.id.overviewButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               OverviewActivity Overview = new OverviewActivity();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, Overview);
                extract.commit();
            }
        });

        intro = (Button)view.findViewById(R.id.introButton);
        intro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                introductionScreen intro = new introductionScreen();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, intro);
                extract.commit();

            }
        });
        return view;
    }
}