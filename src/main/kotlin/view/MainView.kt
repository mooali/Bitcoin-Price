
package view

import javafx.beans.property.SimpleStringProperty
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

        var history1: StringBuilder


        /**
         * set updatedTime,
         */
        bitcoinApi.setUpdatedTime(updatedTime)


        runAsync {
            bitcoinApi.setLabels(rateUSD, usdCurrency, Currency.USD, updatedTime)
            bitcoinApi.setLabels(rateGBP, gbpCurrency, Currency.GBP, updatedTime)
            bitcoinApi.setLabels(rateEUR, eurCurrency, Currency.EUR, updatedTime)
            bitcoinApi.setLabels(rateCHF, chfCurrency, Currency.CHF, updatedTime)
            bitcoinApi.setLabels(rateNZD, nzdCurrency, Currency.NZD, updatedTime)
        }


        top {
            vbox {
                label {
                    bind(updatedTime)
                    addClass(Styles.heading)
                }
                hbox {
                    vbox {
                        addClass(Styles.boxes)


                        imageview("file:src/main/resources/usa_flag.png") {
                            fitHeight = 60.0; fitWidth = 60.0
                        }

                        label {
                            bind(rateUSD)
                            addClass(Styles.priceLabel)
                        }
                    }
                    vbox {
                        addClass(Styles.boxes)

                        imageview("file:src/main/resources/uk_flag.png") {
                            fitHeight = 60.0; fitWidth = 60.0
                        }

                        label {
                            bind(rateGBP)
                            addClass(Styles.priceLabel)
                        }
                    }
                    vbox {
                        addClass(Styles.boxes)

                        imageview("file:src/main/resources/eu_flag.png") {
                            fitHeight = 60.0; fitWidth = 60.0
                        }

                        label {
                            bind(rateEUR)
                            addClass(Styles.priceLabel)
                        }
                    }
                    vbox {
                        addClass(Styles.boxes)

                        imageview("file:src/main/resources/swiss_flag.png") {
                            fitHeight = 60.0; fitWidth = 60.0
                        }

                        label {
                            bind(rateCHF)
                            addClass(Styles.priceLabel)
                        }
                    }
                    vbox {
                        addClass(Styles.boxes)

                        imageview("file:src/main/resources/newzealand_flag.png") {
                            fitHeight = 60.0; fitWidth = 60.0
                        }

                        label {
                            bind(rateNZD)
                            addClass(Styles.priceLabel)
                        }
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

                    tblItems = tableview(bitcoinApi.data) {

                        //this.isEditable = false
                        column("Price", Bitcoin::priceProperty)
                        column("Currency", Bitcoin::currencyProperty)
                        column("Time", Bitcoin::updatedTimeProperty)

                        minWidth = 200.0
                        minHeight = 500.0
                        //this.isWrapText = true
                        CoroutineScope(Main).launch {
                            while (i <= 60) {
                                history1 = StringBuilder("")
                                history1.append(rateEUR.value + " at " + updatedTime.value + "\n")
                                //newRow.(history1.toString())
                                i++
                                delay(1000)
                                if (i == 60) {
                                    i = 0
                                }
                            }
                        }
                    }

                    hbox {
                        addClass(Styles.bottomHbox)
                        var feedbackLabel = label()

                        button {
                            addClass(Styles.buttons)
                            text = "Export History"
                            action {
                                bitcoinApi.printOutHistory(bitcoinApi.data)
                                feedbackLabel.isVisible = true
                                CoroutineScope(IO).launch {
                                    delay(5000)
                                    feedbackLabel.isVisible = false
                                }
                            }
                        }
                        feedbackLabel = label {
                            isVisible = false
                            text = "History has been exported successfully"
                            addClass(Styles.errorLabel)
                        }
                    }
                }


            }
        }
    }
