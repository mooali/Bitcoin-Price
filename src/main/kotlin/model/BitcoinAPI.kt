package model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


class BitcoinAPI {

    private var responseContent: StringBuffer? = null
    private var updatedTime: String = ""
    private var rateUSD: String = ""
    private var rateGBP: String = ""
    private var rateEUR: String = ""


    private val objectMapper: ObjectMapper = getDefaultObjectMapper()


    private fun getDefaultObjectMapper(): ObjectMapper{
        return ObjectMapper()
    }

     fun parse(src:String): JsonNode{
        return objectMapper.readTree(src)
    }




     fun getUpdateTime():String? {
         val jsonObject = getAPIdata()
         val node = parse(jsonObject.getJSONObject("time").toString())
         updatedTime = node.get("updated").asText()
         println("in get update time " + updatedTime)
         this.updatedTime = node.get("updated").asText()
         return this.updatedTime
        //return jsonObject.getJSONObject("time").getString("updated")
        //return jsonObject.getJSONObject("bpi").getJSONObject("USD").getString("description")
    }


    fun getRateUSD():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("USD").toString())
        this.rateUSD = node.get("rate").asText()
        return this.rateUSD
    }

    fun getRateGBP():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("GBP").toString())
        rateEUR = node.get("rate").asText()
        return this.rateGBP
    }

    fun getRateEUR():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("EUR").toString())
        rateEUR = node.get("rate").asText()
        return this.rateEUR
    }




    fun getAPIdata(): JSONObject {
        val connection = Connection()
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
        return JSONObject(responseContent.toString())
    }




}