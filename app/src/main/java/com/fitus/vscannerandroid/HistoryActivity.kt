package com.fitus.vscannerandroid

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class HistoryActivity : AppCompatActivity() {
//    lateinit var dateTV: TextView
//    lateinit var calendarView: CalendarView
//
//
//    var selectDate: Button? = null
//    var date: TextView? = null
//    var datePickerDialog: DatePickerDialog? = null
//    var year = 0
//    var month = 0
//    var dayOfMonth = 0
//    var calendar: Calendar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


//        dateTV = findViewById(R.id.idTVDate)
//        calendarView = findViewById(R.id.calendarView)
//
//        // on below line we are adding set on
//        // date change listener for calendar view.
//        calendarView
//            .setOnDateChangeListener(
//                OnDateChangeListener { view, year, month, dayOfMonth ->
//                    // In this Listener we are getting values
//                    // such as year, month and day of month
//                    // on below line we are creating a variable
//                    // in which we are adding all the cariables in it.
//                    val Date = (dayOfMonth.toString() + "-"
//                            + (month + 1) + "-" + year)
//
//                    // set this date in TextView for Display
//                    dateTV.setText(Date)
//                })
    }
}