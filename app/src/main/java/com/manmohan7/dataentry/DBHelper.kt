package com.example.sqlitedbproj

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    /**
     * default function to create the table inside the DB
     */
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ($ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, $NAME_COL TEXT, $GRADES_COL TEXT)")

        db.execSQL(query)
    }

    /**
     * function to add student in the table
     * @param studentName String - name of the student
     * @param grades String - grade of the student
     */
    fun addNewStudent(studentName: String?, grades: String?) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(NAME_COL, studentName)
        values.put(GRADES_COL, grades)

        // insert query
        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    /**
     * function to get the entries inside the students table
     * @return Cursor to access the rows
     */
    fun viewStudents(): Cursor? {
        val db = this.readableDatabase

        // return cursor
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    /**
     * function to update the student info
     * @param id String - id of the student for which updation will run
     * @param name String - updated name of the student
     * @param grade String - updated grade of the student
     */
    fun updateStudentInfo(id: String, name: String, grade: String?) {
        val db = this.writableDatabase

        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(GRADES_COL, grade)

        // update query
        db.update(TABLE_NAME, values, "$ID_COL=?", arrayOf(id))
        db.close()
    }

    /**
     * function to remove the student from the table
     * @param id String - id of the student to be removed
     */
    fun removeStudent(id: String) {
        val db = this.writableDatabase

        // delete query
        db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id))
        db.close()
    }

    // if ther version of the db changes then this function will execute
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /**
     * constant values are defined for the Table
     */
    companion object {
        // below variable is for our database name.
        private const val DB_NAME = "studentdb"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        const val TABLE_NAME = "StudentGrades"

        // below variable is for our id column.
        const val ID_COL = "student_id"

        // below variable is for our course name column
        const val NAME_COL = "student_name"

        // below variable is for our grades column
        const val GRADES_COL = "grades"
    }
}