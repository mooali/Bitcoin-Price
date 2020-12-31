
package view

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.ContentDisplay
import javafx.scene.control.TableView
import javafx.scene.image.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Bitcoin
import model.Styles
import model.BitcoinAPI
import tornadofx.*
import java.lang.StringBuilder

class MainView : View("Hello TornadoFX") {

    private var dat = FXCollections.observableArrayList<Bitcoin>()
    var tblItems : TableView<Bitcoin> by singleAssign()
    override val root = borderpane {

        prefHeight = 800.0
        prefWidth = 500.0


        val bitcoinAPI = BitcoinAPI()
        val updatedTime = SimpleStringProperty()
        val rateUSD = SimpleStringProperty()
        val rateGBP = SimpleStringProperty()
        val rateEUR = SimpleStringProperty()
        val usdCurrency = SimpleStringProperty()
        val gbpCurrency = SimpleStringProperty()
        val eurCurrency = SimpleStringProperty()


        var history1 = StringBuilder("")


        rateUSD.value = bitcoinAPI.getRateUSD()
        rateGBP.value = bitcoinAPI.getRateGBP()
        rateEUR.value = bitcoinAPI.getRateEUR()


        updatedTime.value = bitcoinAPI.getUpdateTime()

        CoroutineScope(Main).launch {
            while (true) {
                updatedTime.value = bitcoinAPI.getUpdateTime()
                delay(5000)
            }
        }
        CoroutineScope(Main).launch {
            while (true) {
                rateUSD.value = bitcoinAPI.getRateUSD()
                usdCurrency.value = bitcoinAPI.getUSDcurrency()
                val bitcoin = Bitcoin(rateUSD.value, usdCurrency.value, updatedTime.value)
                dat.add(bitcoin)
                delay(5000)
            }
        }

        CoroutineScope(Main).launch {
            while (true) {
                rateGBP.value = bitcoinAPI.getRateGBP()
                gbpCurrency.value = bitcoinAPI.getGBPcurrency()
                val bitcoin = Bitcoin(rateGBP.value, gbpCurrency.value, updatedTime.value)
                dat.add(bitcoin)
                delay(5000)
            }
        }

        CoroutineScope(Main).launch {
            while (true) {
                rateEUR.value = bitcoinAPI.getRateEUR()
                eurCurrency.value = bitcoinAPI.getEURcurrency()
                val bitcoin = Bitcoin(rateEUR.value, eurCurrency.value, updatedTime.value)
                dat.add(bitcoin)
                delay(5000)
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

                    label("Hi",imageview("file:file:./src/main/resources/usa_flag.png"){
                        fitHeight = 50.0; fitWidth = 50.0 }) {
                        contentDisplay = ContentDisplay.RIGHT
                    }

                    label {
                        bind(rateUSD)
                        addClass(Styles.heading)
                    }
                    label {
                        bind(rateGBP)
                        addClass(Styles.heading)
                    }
                    label {
                        bind(rateEUR)
                        addClass(Styles.heading)
                    }
                }
                vbox {
                    addClass(Styles.boxes)

                    label {
                        bind(rateUSD)
                        addClass(Styles.heading)
                    }
                    label {
                        bind(rateGBP)
                        addClass(Styles.heading)
                    }
                    label {
                        bind(rateEUR)
                        addClass(Styles.heading)
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
                tblItems = tableview(dat) {
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
                    var feedbackLabel = label()
                    addClass(Styles.bottomHbox)
                    button {
                        text = "Export History"
                        action {

                            feedbackLabel.isVisible = true

                        }
                    }
                    feedbackLabel= label {
                        isVisible = false
                        text = "History has been exported successfully"
                    }
                }
                label {
                    text = "Powered by CoinDesk"
                }
            }


        }
        }
    }
