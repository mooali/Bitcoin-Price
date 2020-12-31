package model

import javafx.beans.property.SimpleStringProperty

class Bitcoin (price: String, currency: String, updatedTime: String) {

    val priceProperty = SimpleStringProperty(price)
    val currencyProperty = SimpleStringProperty(currency)
    val updatedTimeProperty = SimpleStringProperty(updatedTime)


}
