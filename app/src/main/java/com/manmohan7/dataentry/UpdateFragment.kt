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
 * Update Fragment is used to update the student information.
 */
class UpdateFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        // variable for name input box
        val nameInput = view.findViewById<EditText>(R.id.update_name_input)

        // variable for grade input box
        val gradeInput = view.findViewById<EditText>(R.id.update_grade_input)

        // retrieve arguments passed from the parent
        val id = UpdateFragmentArgs.fromBundle(requireArguments()).studentId
        val name = UpdateFragmentArgs.fromBundle(requireArguments()).studentName
        val grade = UpdateFragmentArgs.fromBundle(requireArguments()).grade

        // display name and grade by default
        nameInput.setText(name)
        gradeInput.setText(grade)

        // define on click listener on the update button
        val updateButton = view.findViewById<Button>(R.id.update_student_db)
        updateButton.setOnClickListener {
            // fetch updated values for name and grade
            val newName = nameInput.text.toString()
            val newGrade = gradeInput.text.toString()

            // check if any field is empty
            if(newName.isEmpty() || newGrade.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // update the name and grade using DBHelper
            val dbHelper = DBHelper(context)
            dbHelper.updateStudentInfo(id, newName, newGrade)

            // navigate back to the main screen
            view.findNavController().navigate(R.id.action_updateFragment_to_viewFragment)
        }

        return view
    }
}