
package view

import javafx.beans.property.SimpleStringProperty
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
    override val root = hbox {
        prefWidth = 800.0
        prefHeight = 600.0
        val bitcoinAPI = BitcoinAPI()
        var some = SimpleStringProperty()
        some.value = bitcoinAPI.getUpdateTime()

        CoroutineScope(Main).launch {
            while (true) {
                some.value = bitcoinAPI.getUpdateTime()
                delay(5000)
            }
        }
        label {
            bind(some)
            addClass(Styles.heading)
        }
    }
}