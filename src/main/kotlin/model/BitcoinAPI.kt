package model

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.json.JSONObject
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


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


    fun getAPIdata(): JSONObject {
        val connection = Connection()
        connection.connect("https://api.coindesk.com/v1/bpi/currentprice.json")
        responseContent = connection.responseContent
        return JSONObject(responseContent.toString())
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
        rateGBP = node.get("rate").asText()
        return this.rateGBP
    }


    fun getRateEUR():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("EUR").toString())
        rateEUR = node.get("rate").asText()
        return this.rateEUR
    }

    fun getEURcurrency():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("EUR").toString())
        return node.get("code").asText()
    }
    fun getUSDcurrency():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("USD").toString())
        return node.get("code").asText()
    }

    fun getGBPcurrency():String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject("bpi").getJSONObject("GBP").toString())
        return node.get("code").asText()
    }

    fun getInfo(jsonKey1: String, jsonKey2: String, infoKey: String):String{
        val jsonObject = getAPIdata()
        val node = parse(jsonObject.getJSONObject(jsonKey1).getJSONObject(jsonKey2).toString())
        return node.get(infoKey).asText()
    }

    fun printOutHistory(txt:String){
        var fileName = "src/main/output/BitcoinHistory"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH_mm_ss"))+".txt"
        var outputFile = File(fileName)
        if(!outputFile.exists()){
            return outputFile.bufferedWriter().use { out ->
                out.write("|PRICE|........|COURNNCY|......................|TIME|"+"\n")
                out.write(txt) }
        }
        }
    }