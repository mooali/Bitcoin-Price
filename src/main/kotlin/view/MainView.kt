package view

import model.Styles
import model.Testo
import tornadofx.*

class MainView : View("Hello TornadoFX") {
    override val root = hbox {
        var test = Testo()
        test.test()
        println(test.getAuthor())
    }
}