package com.manmohan7.dataentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header

/**
 * Fetch data fragment is only used to demonstrate the functioning of web services.
 */
class FetchDataFragment : Fragment() {
    // variables declaration
    lateinit var fetchBtn: Button
    lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fetch_data, container, false)

        // variable initialised for button and textview
        fetchBtn = view.findViewById<Button>(R.id.fetch_button)
        textView = view.findViewById<TextView>(R.id.text_display)

        fetchBtn.setOnClickListener {
            getData()
        }

        return view
    }

    /**
     * function to retrieve the data from API.
     * This will function will update the text of the textview
     * based upon response from the API.
     */
    private fun getData() {
        val url = "https://jsonplaceholder.typicode.com/todos/1"
        AsyncHttpClient().get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?) {
                val str = String(responseBody!!)
                textView.text = str
            }

            override fun onFailure(statusCode: Int, headers: Array<Header?>?, responseBody: ByteArray?, error: Throwable?) {
                textView.text = "Error in calling api"
            }
        })
    }
}