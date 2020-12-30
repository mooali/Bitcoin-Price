
package view

import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import model.Styles
import model.BitcoinAPI
import tornadofx.*
import java.io.File
import java.lang.StringBuilder

class MainView : View("Hello TornadoFX") {
    override val root = borderpane {

        prefHeight = 800.0
        prefWidth = 500.0

        val bitcoinAPI = BitcoinAPI()
        val updatedTime = SimpleStringProperty()
        val rateUSD = SimpleStringProperty()
        val rateGBP = SimpleStringProperty()
        val rateEUR = SimpleStringProperty()
        var history1 = StringBuilder("")

        rateUSD.value = bitcoinAPI.getRateUSD()
        rateGBP.value = bitcoinAPI.getRateGBP()
        rateEUR.value = bitcoinAPI.getRateEUR()

        updatedTime.value = bitcoinAPI.getUpdateTime()

        CoroutineScope(Main).launch {
            while (true) {
                updatedTime.value = "UpdatedTime: "+bitcoinAPI.getUpdateTime()
                delay(5000)
            }
        }
        CoroutineScope(Main).launch {
            while (true) {
                rateUSD.value = "Price: "+bitcoinAPI.getRateUSD()+"$"
                delay(5000)
            }
        }

        CoroutineScope(Main).launch {
            while (true) {
                rateGBP.value = "Price: "+bitcoinAPI.getRateGBP()+"£"
                delay(5000)
            }
        }

        CoroutineScope(Main).launch {
            while (true) {
                rateEUR.value = "Price: "+bitcoinAPI.getRateEUR()+"€"
                delay(5000)
            }
        }
        top{
            vbox {

                addClass(Styles.boxes)

                label {
                    bind(updatedTime)
                    addClass(Styles.heading)
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
        }
        bottom {
            addClass(Styles.boxes)
            var i = 0
            var txtArea = textarea()

            vbox {
            scrollpane {
                addClass(Styles.boxes)
                prefHeight = 200.0
                prefWidth = 500.0
                 txtArea = textarea {
                    this.isEditable = false
                    prefHeight = 200.0
                    prefWidth = 500.0
                    //this.isWrapText = true
                    CoroutineScope(Main).launch {
                        while (i<=60) {
                            history1 = StringBuilder("")
                            history1.append(rateEUR.value + " at " + updatedTime.value + "\n")
                            appendText(history1.toString())
                            separator()
                            i++
                            delay(1000)
                            if(i == 60){
                                i = 0
                            }
                        }
                    }
                    }
                }
                hbox {
                    addClass(Styles.bottomHbox)
                  var feedbackLabel= label {
                      isVisible = false
                      text = "History has been exported successfully"
                    }
                    button {
                        text = "click me"
                        action {
                            bitcoinAPI.printOutHistory(txtArea.text)
                            println(txtArea.text)
                            feedbackLabel.isVisible = true

                        }
                    }
                }
                label {
                    text = "Powered by CoinDesk"
                }
            }


        }
        }
    }
