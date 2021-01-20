package model
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BitcoinAPI {

    val URL = "https://api.coindesk.com/v1/bpi/currentprice/"
    var data = FXCollections.observableArrayList<Bitcoin>()


    /**
     * give a Currency (CHF,EUR,USD,NZD,GBP) fetch the data the api
     * return Bitcoin
     */
    suspend fun getCurruncyData(currency: Currency): Bitcoin = withContext(Dispatchers.IO){
        val mapper = jacksonObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        val json = URL("$URL$currency").readText()
        val data = mapper.readValue<Bitcoin.DataRequest>(json)
        return@withContext Bitcoin(data.bpi[currency]!!.rate_float, data.bpi[currency]!!.code,data.time.updatedISO)
    }


    /**
     * return the updatedTime (updatedISO)
     * every 60 seconds
     */
    fun setUpdatedTime(updatedTime:SimpleStringProperty){
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                updatedTime.value = getCurruncyData(Currency.CHF).time
                delay(60000)
            }
        }
    }

    /**
     * set and update the every 60 seconds
     * add the bitcoin to the data, to be shown in the history
     */
    fun setLabels(rate:SimpleStringProperty,currencyCode:SimpleStringProperty,currency: Currency,updatedTime: SimpleStringProperty){
        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                rate.value = getCurruncyData(currency).price
                currencyCode.value = getCurruncyData(currency).currencyCode
                val bitcoin = Bitcoin(rate.value, currencyCode.value, updatedTime.value)
                data.add(bitcoin)
                delay(60000)
            }
        }
    }


    /**
     * give an ObserableList of type Bitcoin
     * print out the properties into a csv file
     */
    fun printOutHistory(bitcoin: ObservableList<Bitcoin>){
        val fileName = "src/main/output/BitcoinHistory"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH_mm_ss"))+".csv"
        val outputFile = File(fileName)
        try {
            if (!outputFile.exists()) {
                return outputFile.bufferedWriter().use { out ->
                    for (coin in bitcoin) {
                        val txt = coin.priceProperty.value + "," + coin.currencyProperty.value + "," + coin.updatedTimeProperty.value + "\n"
                        out.write(txt)
                    }
                }
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
