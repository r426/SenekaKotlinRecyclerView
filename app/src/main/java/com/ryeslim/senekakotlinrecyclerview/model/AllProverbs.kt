package com.ryeslim.senekakotlinrecyclerview.model

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.ryeslim.senekakotlinrecyclerview.activities.MainActivity
import com.ryeslim.senekakotlinrecyclerview.dataclass.Proverb
import org.json.JSONArray
import org.json.JSONException
import java.util.ArrayList
import kotlin.math.floor

class AllProverbs private constructor() {

    private val theListOfAll: ArrayList<Proverb> = ArrayList()

    val random: Proverb
        get() {
            val result: Proverb
            val i = floor(Math.random() * theListOfAll.size).toInt()
            result = theListOfAll[i]
            return result
        }


    private var loadingQue: RequestQueue? = null

    init {

        theListOfAll.ensureCapacity(1000)
        theListOfAll.add(
            Proverb(
                0.toShort(),
                "Melas – plonasienis daiktas: gerai įsižiūrėjus, jis persišviečia."
            )
        )
    }

    /**
     * Adapted from StackOverflow
     */
    fun setLoadingQue(loadingQue: RequestQueue) {
        this.loadingQue = loadingQue
        val arrReq = JsonArrayRequest(
            Request.Method.GET, "https://api.myjson.com/bins/yaa26",
            Response.Listener<JSONArray> { response ->
                // Check the length of our response (to see if the user has any repos)
                if (response.length() > 0) {
                    // The user does have repos, so let's loop through them all.
                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = response.getJSONObject(i)
                            println(jsonObject.getInt("id"))
                            println(jsonObject.getString("text"))
                            theListOfAll.add(
                                Proverb(
                                    jsonObject.getInt("id").toShort(),
                                    jsonObject.getString("text")
                                )
                            )
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
                MainActivity.instance?.goForward()
            },

            Response.ErrorListener { error -> println(error) }
        )
        // Add the request we just defined to our request queue.
        // The request queue will automatically handle the request as soon as it can.
        loadingQue.add(arrReq)
    }

    companion object {

        private var instance: AllProverbs? = null

        fun getInstance(): AllProverbs {
            if (instance == null) {
                instance =
                    AllProverbs()
            }
            return instance!!
        }
    }
}