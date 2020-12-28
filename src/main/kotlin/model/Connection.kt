package model

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset
import org.json.JSONArray
import org.json.JSONObject


class Connection {
    private var connection: HttpURLConnection? = null
    private val UTF8 = Charset.forName("UTF-8")
    val responseContent = StringBuffer()
    var reader: BufferedReader? = null
    var line: String? = null
    //val responseContent: StringBuffer = Connection.responseContent

    fun connect(inputURL: String?) {
        try {
            val url = URL(inputURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            val status: Int = connection.responseCode
            println(status) //200 means connection successful
            println("HttpURL connected successfully") //200 means connection successful
            if (status > 299) {
                reader = BufferedReader(InputStreamReader(connection.errorStream))
                while (reader?.readLine().also { line = it } != null) {
                    responseContent.append(line)
                }
                reader!!.close()
            } else {
                reader = BufferedReader(InputStreamReader(connection.inputStream, UTF8))
                while (reader?.readLine().also { line = it } != null) {
                    responseContent.append(line)
                }
                reader!!.close()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            connection?.disconnect()
        }
    }

    fun disconnect() {
        connection?.disconnect()
    }

}