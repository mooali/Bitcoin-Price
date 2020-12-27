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
    var reader: BufferedReader? = null
    var line: String? = null
    val responseContent: StringBuffer
        get() = Connection.Companion.responseContent

    fun connect(inputURL: String?) {
        try {
            var url = URL(inputURL)
            Connection.Companion.connection = url.openConnection() as HttpURLConnection
            Connection.Companion.connection!!.setRequestMethod("GET")
            Connection.Companion.connection!!.setConnectTimeout(5000)
            Connection.Companion.connection!!.setReadTimeout(5000)
            val status: Int = Connection.Companion.connection!!.getResponseCode()
            println(status) //200 means connection successful
            if (status > 299) {
                reader = BufferedReader(InputStreamReader(Connection.Companion.connection!!.getErrorStream()))
                while (reader!!.readLine().also { line = it } != null) {
                    Connection.Companion.responseContent.append(line)
                }
                reader!!.close()
            } else {
                reader = BufferedReader(InputStreamReader(Connection.Companion.connection!!.getInputStream(), Connection.Companion.UTF8))
                while (reader!!.readLine().also { line = it } != null) {
                    Connection.Companion.responseContent.append(line)
                }
                reader!!.close()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            Connection.Companion.connection?.disconnect()
        }
    }

    fun disconnect() {
        Connection.Companion.connection?.disconnect()
    }

    companion object {
        private var connection: HttpURLConnection? = null
        private val UTF8 = Charset.forName("UTF-8")
        private val responseContent = StringBuffer()
        fun parse(responseBody: String?): String? {
            val jsonObject = JSONObject(responseBody)
            val albums: JSONArray = jsonObject.getJSONArray("articles")
            print("""
    ID Title UserID
    
    """.trimIndent())
            for (i in 0 until albums.length()) {
                val album: JSONObject = albums.getJSONObject(i)
                val author: String = album.getString("author")
                val title: String = album.getString("title")
                println(author)
            }
            return null
        }
    }
}