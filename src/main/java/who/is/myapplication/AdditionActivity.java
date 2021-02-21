package who.is.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class AdditionActivity extends Fragment {
    Button goBack;

    public AdditionActivity() {
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.addtolist, container, false);
        goBack = (Button) view.findViewById(R.id.goBackToBudget);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardFragment dash = new DashboardFragment();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, dash); //figure out how to save items added to the list!
                extract.commitAllowingStateLoss();
            }
        });
        return view;
    }
}
