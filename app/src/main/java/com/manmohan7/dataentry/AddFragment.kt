package com.manmohan7.dataentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sqlitedbproj.DBHelper

/**
 * Add Fragment used to Add new student to the DB.
 */
class AddFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // fetch button from view
        val addButton = view.findViewById<Button>(R.id.add_student_db)

        // set on click listener event
        addButton.setOnClickListener {

            // retrieve input values from view
            val studentName = view.findViewById<EditText>(R.id.name_text_input).text.toString()
            val studentGrade = view.findViewById<EditText>(R.id.grade_text_input).text.toString()

            // check for empty values
            if(studentName.isEmpty() || studentGrade.isEmpty()) {
                // if any value is empty then show error message
                Toast.makeText(context, "Values cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener;
            }

            // get reference of DBHelper class
            val dbHelper = DBHelper(context)

            // invoke add student function
            dbHelper.addNewStudent(studentName, studentGrade)

            // navigate back to main screen
            view.findNavController().navigate(R.id.action_addFragment_to_viewFragment)
        }

        return view
    }
}