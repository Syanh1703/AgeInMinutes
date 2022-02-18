package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var txtViewSelectedDate :TextView? = null
    private var txtViewAgeInMinutes :TextView? = null
    private var txtViewAgeInHours :TextView? = null
    val dayToSecond = 86400000
    val dayToMin = 6000
    val dayToHours = 3600000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize items Id
        val datePickerBtn = findViewById<Button>(R.id.btnDatePicker)
        txtViewSelectedDate = findViewById(R.id.txtViewDateDisplay)
        txtViewAgeInMinutes = findViewById(R.id.txtViewUserMinutes)
        txtViewAgeInHours = findViewById(R.id.txtViewUserHours)
        //Let the User choose their date
        datePickerBtn.setOnClickListener {
            Toast.makeText(this, "Please choose your date", Toast.LENGTH_SHORT).show()
            clickDatePicker()
        }
    }
    private fun clickDatePicker()
    {
        //Initiate the year, month and day
        val myPresentCalendar = Calendar.getInstance()
        val year = myPresentCalendar.get(Calendar.YEAR)
        val month = myPresentCalendar.get(Calendar.MONTH)
        var dayOfMonth = myPresentCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                Toast.makeText(this, "Your chosen date is: $dayOfMonth/${month + 1}/$year", Toast.LENGTH_SHORT).show()
                //Store the corrected date
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                txtViewSelectedDate?.text = selectedDate

                //Create an object with the format
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                //Day in minutes
                val selectedDateToCalculate = theDate.time //Your date of birth in minutes since 1st Jan 1970
                theDate?.let { //the code runs if the date is not empty
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    val currentDates = currentDate.time//From 1 Jan 1970 till date in minutes
                    currentDate?.let {
                        val differenceInMinutes = (currentDates - selectedDateToCalculate)/dayToMin
                        val differenceInHours = (currentDates - selectedDateToCalculate)/dayToHours
                        txtViewAgeInMinutes?.text = differenceInMinutes.toString()
                        txtViewAgeInHours?.text = differenceInHours.toString()
                    }
                }

            },
            year, month, dayOfMonth)

        dpd.datePicker.maxDate = System.currentTimeMillis() - dayToSecond//The maximum date is today
        dpd.show()
    }
}