
package view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.TableView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.*
import tornadofx.*
import java.lang.StringBuilder

class MainView : View("Bitcoin Price") {

    private var data = FXCollections.observableArrayList<Bitcoin>()
    var tblItems : TableView<Bitcoin> by singleAssign()
    val bitcoinApi = BitcoinAPI()

    override val root = borderpane {

        prefHeight = 800.0
        prefWidth = 700.0


        val updatedTime = SimpleStringProperty()
        val rateUSD = SimpleStringProperty()
        val rateGBP = SimpleStringProperty()
        val rateEUR = SimpleStringProperty()
        val rateCHF = SimpleStringProperty()
        val rateNZD = SimpleStringProperty()
        val usdCurrency = SimpleStringProperty()
        val gbpCurrency = SimpleStringProperty()
        val eurCurrency = SimpleStringProperty()
        val chfCurrency = SimpleStringProperty()
        val nzdCurrency = SimpleStringProperty()

        var history1: StringBuilder;




        CoroutineScope(Main).launch {
            while (true) {
                println("starting.....")
                updatedTime.value = bitcoinApi.getCurruncyData(Currency.CHF).time
                delay(2000)
            }
        }

        runAsync {
            CoroutineScope(Main).launch {
                while (true) {
                    println("starting.....")
                    rateUSD.value = bitcoinApi.getCurruncyData(Currency.USD).price
                    usdCurrency.value = bitcoinApi.getCurruncyData(Currency.USD).currencyCode
                    val bitcoin = Bitcoin(rateUSD.value, usdCurrency.value, updatedTime.value)
                    data.add(bitcoin)
                    delay(2000)
                }
            }
            CoroutineScope(Main).launch {
                while (true) {
                    rateGBP.value = bitcoinApi.getCurruncyData(Currency.GBP).price
                    gbpCurrency.value = bitcoinApi.getCurruncyData(Currency.GBP).currencyCode
                    val bitcoin = Bitcoin(rateGBP.value, gbpCurrency.value, updatedTime.value)
                    data.add(bitcoin)
                    delay(2000)
                }
            }
            CoroutineScope(Main).launch {
                while (true) {
                    rateEUR.value = bitcoinApi.getCurruncyData(Currency.EUR).price
                    eurCurrency.value = bitcoinApi.getCurruncyData(Currency.EUR).currencyCode
                    val bitcoin = Bitcoin(rateEUR.value, eurCurrency.value, updatedTime.value)
                    data.add(bitcoin)
                    delay(2000)
                }
            }
            CoroutineScope(Main).launch {
                while (true) {
                    rateCHF.value = bitcoinApi.getCurruncyData(Currency.CHF).price
                    chfCurrency.value = bitcoinApi.getCurruncyData(Currency.CHF).currencyCode
                    val bitcoin = Bitcoin(rateCHF.value, chfCurrency.value, updatedTime.value)
                    data.add(bitcoin)
                    delay(2000)
                }
            }
            CoroutineScope(Main).launch {
                while (true) {
                    rateNZD.value = bitcoinApi.getCurruncyData(Currency.NZD).price
                    nzdCurrency.value = bitcoinApi.getCurruncyData(Currency.NZD).currencyCode
                    val bitcoin = Bitcoin(rateCHF.value, nzdCurrency.value, updatedTime.value)
                    data.add(bitcoin)
                    delay(2000)
                }
            }

        }



        top{
            hbox {
                label {
                    bind(updatedTime)
                    addClass(Styles.heading)
                }
            }
        }

        center{
            hbox {
                vbox {
                    addClass(Styles.boxes)


                    imageview("file:src/main/resources/usa_flag.png"){
                        fitHeight = 60.0; fitWidth = 60.0 }

                    label {
                        bind(rateUSD)
                        addClass(Styles.priceLabel)
                    }
                }
                vbox {
                    addClass(Styles.boxes)

                    imageview("file:src/main/resources/uk_flag.png"){
                        fitHeight = 60.0; fitWidth = 60.0 }

                    label {
                        bind(rateGBP)
                        addClass(Styles.priceLabel)
                    }
                }
                vbox {
                    addClass(Styles.boxes)

                    imageview("file:src/main/resources/eu_flag.png"){
                        fitHeight = 60.0; fitWidth = 60.0 }

                    label {
                        bind(rateEUR)
                        addClass(Styles.priceLabel)
                    }
                }
                vbox {
                    addClass(Styles.boxes)

                    imageview("file:src/main/resources/swiss_flag.png"){
                        fitHeight = 60.0; fitWidth = 60.0 }

                    label {
                        bind(rateCHF)
                        addClass(Styles.priceLabel)
                    }
                }
                vbox {
                    addClass(Styles.boxes)

                    imageview("file:src/main/resources/newzealand_flag.png"){
                        fitHeight = 60.0; fitWidth = 60.0 }

                    label {
                        bind(rateNZD)
                        addClass(Styles.priceLabel)
                    }
                }
            }
        }
        bottom {
            addClass(Styles.boxes)
            var i = 0
            label {
                text = "History"
            }

            vbox {

                tblItems = tableview(data) {

                    //this.isEditable = false
                    column("Price",Bitcoin::priceProperty)
                    column("Currency",Bitcoin::currencyProperty)
                    column("Time",Bitcoin::updatedTimeProperty)

                    minWidth = 200.0
                    minHeight = 500.0
                    //this.isWrapText = true
                    CoroutineScope(Main).launch {
                        while (i<=60) {
                            history1 = StringBuilder("")
                            history1.append(rateEUR.value + " at " + updatedTime.value + "\n")
                            //newRow.(history1.toString())
                            i++
                            delay(1000)
                            if(i == 60){
                                i = 0
                            }
                        }
                    }
                 }

                hbox {
                    addClass(Styles.bottomHbox)
                    var feedbackLabel = label()

                    button {
                        text = "Export History"
                        var txt = ""
                        action {
                            bitcoinApi.printOutHistory(data)
                            feedbackLabel.isVisible = true
                            CoroutineScope(IO).launch {
                                delay(5000)
                                feedbackLabel.isVisible = false
                            }
                        }
                    }
                    feedbackLabel= label {
                        isVisible = false
                        text = "History has been exported successfully"
                        addClass(Styles.errorLabel)
                    }
                }
            }


        }
        }
    }
