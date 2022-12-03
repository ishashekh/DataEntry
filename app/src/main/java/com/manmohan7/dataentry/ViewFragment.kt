package com.manmohan7.dataentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.sqlitedbproj.DBHelper

/**
 * Main fragment used to display the information and navigate to other screens.
 */
class ViewFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view, container, false)

        // redirect user to fetch data fragment
        val fetchBtn = view.findViewById<Button>(R.id.goto_fetch)
        fetchBtn.setOnClickListener {
            view.findNavController().navigate(R.id.action_viewFragment_to_fetchDataFragment)
        }

        // redirect user to add fragment
        val button = view.findViewById<Button>(R.id.add_student)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.action_viewFragment_to_addFragment)
        }

        // get table from xml
        val table = view.findViewById<TableLayout>(R.id.table_view)

        // get students from DB
        val dbHelper = DBHelper(context)
        val cursor = dbHelper.viewStudents()
        cursor!!.moveToFirst()

        // iterate over the rows using cursor
        while (cursor.isAfterLast() == false) {

            // retrieve values for row
            val id = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ID_COL))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.NAME_COL))
            val grade = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.GRADES_COL))

            // dynamically create table rows
            val row = TableRow(context)
            row.setPadding(0, 10, 0, 10)

            // created different layout params for all the views
            val lp = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            val doubleLP = TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 2f)
            val imageLP = TableRow.LayoutParams(0, 48, 1f)

            // creating new textview to display the id
            val idText = TextView(context)
            idText.setText(id)
            idText.setLayoutParams(lp)
            // adding textview to row
            row.addView(idText)


            // creating new textview to display the name
            val nameText = TextView(context)
            nameText.setText(name)
            nameText.setLayoutParams(doubleLP)
            row.addView(nameText)


            // creating textview for grades
            val gradeText = TextView(context)
            gradeText.setText(grade)
            gradeText.setLayoutParams(lp)
            row.addView(gradeText)


            // creating imageview for edit button
            val editImage = ImageView(context)
            // setting the image from drawable
            editImage.setImageResource(R.drawable.editing)
            editImage.setLayoutParams(imageLP)
            // binding click event for edit image
            editImage.setOnClickListener {
                // passing values to the update fragment
                val action = ViewFragmentDirections.actionViewFragmentToUpdateFragment(id, name, grade)
                view.findNavController().navigate(action)
            }
            row.addView(editImage)


            // creating imageview for delete button
            val deleteImage = ImageView(context)
            deleteImage.setImageResource(R.drawable.delete)
            deleteImage.setLayoutParams(imageLP)
            // binding click event for delete button
            deleteImage.setOnClickListener{
                // invoke the delete student function from dbhelper
                dbHelper.removeStudent(id)

                // navigate to same fragment to refresh the page
                view.findNavController().navigate(R.id.action_viewFragment_self)
            }
            row.addView(deleteImage)

            // add row to the table
            table.addView(row)

            // move to the entry
            cursor.moveToNext()
        }

        return view
    }
}