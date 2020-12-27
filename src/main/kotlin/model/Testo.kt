package model

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class Testo {

    private val connection = Connection()
    private var responseContent: StringBuffer? = null


    fun test() {
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
    }


    fun getAuthor(): JSONObject {
        val jsonObject = JSONObject(responseContent.toString())
        return jsonObject
    }



}