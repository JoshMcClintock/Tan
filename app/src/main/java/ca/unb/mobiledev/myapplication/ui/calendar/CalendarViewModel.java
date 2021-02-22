package ca.unb.mobiledev.myapplication.ui.calendar;

import android.widget.CalendarView;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CalendarViewModel extends ViewModel {

    private ArrayList<String> expenses;

    public CalendarViewModel() {

        expenses = new ArrayList<String>();

    }

    public ArrayList<String> getExpenses () {
        return expenses;
    }

    public void addExpense(String expense) {
        expenses.add(expense);
    }

}