package model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject


class BitcoinAPI {

    private val connection = Connection()
    private var responseContent: StringBuffer? = null

    private val objectMapper: ObjectMapper = getDefaultObjectMapper()


    private fun getDefaultObjectMapper(): ObjectMapper{
        return ObjectMapper()
    }

     fun parse(src:String): JsonNode{
        return objectMapper.readTree(src)
    }


    fun test() {
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
        println(responseContent!![5].toString())
    }


    fun getUpdatedTime(): String {
        val jsonObject = JSONObject(responseContent.toString())
        //return jsonObject.getJSONObject("time").getString("updated")

        var node = parse(jsonObject.getJSONObject("time").toString())
        return node.get("updated").asText()
        //return jsonObject.getJSONObject("bpi").getJSONObject("USD").getString("description")

    }



}