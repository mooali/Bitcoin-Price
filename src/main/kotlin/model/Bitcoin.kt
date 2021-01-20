package model

import javafx.beans.property.SimpleStringProperty
import java.util.*

class Bitcoin(var price: String,var currencyCode: String, var time: String) {

    /**
     * properties to be listed in view/MainView, in the tableview culmn
     * so we can update the UI accordingly
     */
    val updatedTimeProperty = SimpleStringProperty(time)
    val priceProperty = SimpleStringProperty(price)
    val currencyProperty = SimpleStringProperty(currencyCode)


    /**
     * updatedISO is the name of the property
     * we want to get from JSON
     */
    data class Time(
            val updatedISO: String
    )

    /**
     * rate_float , code
     * are the properties we want to get
     */
    data class Bpi(
            val rate_float: String,
            val code: String
    )

    /**
     * get the time property(updatedISO)
     * and currency that we are looking for
     */
    data class DataRequest(
            val time: Time,
            val bpi: EnumMap<Currency, Bpi>
    )

}
