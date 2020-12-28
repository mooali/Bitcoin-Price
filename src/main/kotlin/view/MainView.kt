
package view

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import model.Styles
import model.BitcoinAPI
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = hbox {
        var test = BitcoinAPI()
        runBlocking {
            label(test.getUpdateTime()) {
                addClass(Styles.heading)
            }
        }

    }
}