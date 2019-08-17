package com.ryeslim.senekakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_list_of_proverbs.*

class ListOfProverbsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_proverbs)

        // 1) Create an ArrayAdapter, whose data source is a list of Proverb objects.
        val adapter = ItemArrayAdapter(
            this,
            R.layout.one_line, WorkWithProverbs.getInstance().readBookmarks()
        )
        // 2) Find the ListView object (the location where to drop the whole list)
        // 3) Make the ListView use the ArrayAdapter
        listView.adapter = adapter
    }

    //When < icon is clicked, returns to MainActivity
    fun carryOn(view: View) {
        this.finish()
    }
}
