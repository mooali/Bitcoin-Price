package model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


class BitcoinAPI {

    private var connection = Connection()
    private var responseContent: StringBuffer? = null
    private var updatedTime: String? = null

    private val objectMapper: ObjectMapper = getDefaultObjectMapper()


    private fun getDefaultObjectMapper(): ObjectMapper{
        return ObjectMapper()
    }

     fun parse(src:String): JsonNode{
        return objectMapper.readTree(src)
    }


    fun apiRequestJson() {
         connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
         responseContent = connection.responseContent

    }


    suspend fun getUpdateTime(): String {
        CoroutineScope(IO).launch {
            println("in get update time")
            while (true) {
                apiRequestJson()
                val jsonObject = JSONObject(responseContent.toString())
                var node = parse(jsonObject.getJSONObject("time").toString())
                updatedTime = node.get("updated").asText()
                println(updatedTime)
                connection = Connection()
                delay(6000)
            }
        }
        return this.updatedTime.toString()

        //return jsonObject.getJSONObject("time").getString("updated")
        //return jsonObject.getJSONObject("bpi").getJSONObject("USD").getString("description")
    }

}