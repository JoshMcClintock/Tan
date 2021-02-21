package who.is.myapplication.introActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import who.is.myapplication.BudgetActivity;
import who.is.myapplication.R;

public class slide2 extends Fragment {
    Button nextBtn;
    Button backBtn;

    public slide2(){
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.intro2, container, false);


        nextBtn = (Button) view.findViewById(R.id.Next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide3 slip = new slide3();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, slip);
                extract.commitAllowingStateLoss();
            }
        });

        backBtn = (Button) view.findViewById(R.id.Back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide1 slip = new slide1();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, slip);
                extract.commitAllowingStateLoss();

            }
        });
        return view;

    }
}
