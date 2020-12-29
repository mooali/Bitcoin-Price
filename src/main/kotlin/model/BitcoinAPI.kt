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
    private var updatedTime: String? = null

    private val objectMapper: ObjectMapper = getDefaultObjectMapper()


    private fun getDefaultObjectMapper(): ObjectMapper{
        return ObjectMapper()
    }

     fun parse(src:String): JsonNode{
        return objectMapper.readTree(src)
    }




    suspend fun getUpdateTime() {
        CoroutineScope(IO).launch {
            while (true) {
                val jsonObject = getAPIdata()
                val node = parse(jsonObject.getJSONObject("time").toString())
                updatedTime = node.get("updated").asText()
                println("in get update time " + updatedTime)
                setUpdateTime(node.get("updated").asText())
                delay(3000)
            }
        }

        //return jsonObject.getJSONObject("time").getString("updated")
        //return jsonObject.getJSONObject("bpi").getJSONObject("USD").getString("description")
    }

    suspend fun getUpdateT(): String{
        getUpdateTime()
        return this.updatedTime.toString()

    }


    fun setUpdateTime(str:String?){
        this.updatedTime = str
    }


    fun getAPIdata(): JSONObject {
        val connection = Connection()
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
        return JSONObject(responseContent.toString())
    }

}