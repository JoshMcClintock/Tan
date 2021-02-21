package who.is.myapplication.introActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import who.is.myapplication.BudgetActivity;
import who.is.myapplication.HomeFragment;
import who.is.myapplication.OverviewActivity;
import who.is.myapplication.R;


public class introductionScreen extends Fragment{

    Button nextBtn;
    Button backBtn;
    public introductionScreen(){
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.introscreen, container, false);

        nextBtn = (Button) view.findViewById(R.id.Next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide1 slip = new slide1();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, slip );
                extract.commit();
            }
        });

        backBtn = (Button) view.findViewById(R.id.Back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BudgetActivity back = new BudgetActivity();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, back);
                extract.commitAllowingStateLoss();

            }
        });
        return view;
    }
}
