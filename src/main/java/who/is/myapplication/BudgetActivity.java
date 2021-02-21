package who.is.myapplication;

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

public class BudgetActivity extends Fragment {

    Button AddToListBtn;
    public BudgetActivity(){
        //Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.budget, container, false);
        AddToListBtn = (Button)view.findViewById(R.id.button2Add);
        AddToListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AdditionActivity add  = new AdditionActivity();

                FragmentTransaction extract = getActivity().getSupportFragmentManager().beginTransaction();
                extract.replace(R.id.nav_host_fragment, add);
                extract.commit();//change this eventually
            }
        });

        return view;
    }
}