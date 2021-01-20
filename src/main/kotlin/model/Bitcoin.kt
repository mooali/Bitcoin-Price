package model

import javafx.beans.property.SimpleStringProperty
import java.util.*

class Bitcoin(var price: String,var currencyCode: String, var time: String) {

    val updatedTimeProperty = SimpleStringProperty(time)
    val priceProperty = SimpleStringProperty(price)
    val currencyProperty = SimpleStringProperty(currencyCode)


    data class Time(
            val updatedISO: String
    )
    data class Bpi(
            val rate_float: String,
            val code: String
    )
    data class DataRequest(
            val time: Time,
            val bpi: EnumMap<Currency, Bpi>
    )

}
