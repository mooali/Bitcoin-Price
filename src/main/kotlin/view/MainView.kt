package view

import model.Styles
import model.BitcoinAPI
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = hbox {
        var test = BitcoinAPI()
        test.test()
        println(test.getUpdatedTime())
    }
}