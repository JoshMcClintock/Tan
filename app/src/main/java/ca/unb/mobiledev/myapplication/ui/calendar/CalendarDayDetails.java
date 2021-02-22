package ca.unb.mobiledev.myapplication.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ca.unb.mobiledev.myapplication.MainActivity;
import ca.unb.mobiledev.myapplication.R;

public class CalendarDayDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_day_details);

        findViewById(R.id.buttonAddExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editTextExpense);
                String expense = editText.getText().toString();
                CalendarFragment.calendarViewModel().addExpense(expense);

                ListView listView = findViewById(R.id.list_view_expenses);

            }
        });



    }

}
