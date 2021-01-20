package model
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import javafx.collections.ObservableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.lang.Exception
import java.net.URL
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BitcoinAPI {

    val URL = "https://api.coindesk.com/v1/bpi/currentprice/"

    suspend fun getCurruncyData(currency: Currency): Bitcoin = withContext(Dispatchers.IO){
        val mapper = jacksonObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        val json = URL("$URL$currency").readText()
        val data = mapper.readValue<Bitcoin.DataRequest>(json)
        return@withContext Bitcoin(data.bpi[currency]!!.rate_float, data.bpi[currency]!!.code,data.time.updatedISO)
    }



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
