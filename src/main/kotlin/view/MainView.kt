
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

class MainView : View("Hello TornadoFX") {
    override val root = borderpane {

        prefHeight = 800.0
        prefWidth = 500.0

        val bitcoinAPI = BitcoinAPI()
        val updatedTime = SimpleStringProperty()
        val rateUSD = SimpleStringProperty()
        val rateGBP = SimpleStringProperty()
        val rateEUR = SimpleStringProperty()
        val history1 = SimpleStringProperty()

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
                history1.value="Price: "+bitcoinAPI.getRateEUR()+"€" + " at "+updatedTime.value
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

            scrollpane {
                prefHeight = 200.0
                prefWidth = 500.0
                textflow() {
                    prefHeight = 200.0
                    prefWidth = 500.0
                    for(i in 1..5){
                        label {
                            bind(history1)
                        }
                }
                }
            }
        }
    }
}
