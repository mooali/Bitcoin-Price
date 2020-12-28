package model

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.mortbay.util.ajax.JSON


class BitcoinAPI {

    private val connection = Connection()
    private var responseContent: StringBuffer? = null


    fun test() {
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
        println(responseContent!![5].toString())
    }


    fun getUpdatedTime(): String {
        val jsonObject = JSONObject(responseContent.toString())
        var updatedTime :String = jsonObject.getJSONObject("time").getString("updated")
        return updatedTime
}



}