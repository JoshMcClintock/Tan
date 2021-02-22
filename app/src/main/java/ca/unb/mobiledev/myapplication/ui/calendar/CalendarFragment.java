package ca.unb.mobiledev.myapplication.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ca.unb.mobiledev.myapplication.MainActivity;
import ca.unb.mobiledev.myapplication.R;

public class CalendarFragment extends Fragment {

    private static CalendarViewModel calendarViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendarView = getView().findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(getContext(), CalendarDayDetails.class)
                        .putExtra("date", dayOfMonth + ", " + month + ", " + year);
                startActivity(intent);

            }
        });

    }

    public static CalendarViewModel calendarViewModel() {
        return calendarViewModel;
    }

}