package view

import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*
import kotlin.concurrent.thread

class SplashScreen : View("Welcome"){
    override val root = stackpane {
        prefHeight = 800.0
        prefWidth = 700.0
        vbox {
            alignment = Pos.CENTER
            progressindicator {
                minWidth = 200.0
                minHeight= 200.0
            }
            label("Loading...")
        }
        hbox {
            alignment = Pos.BOTTOM_CENTER
            label("Powered by CoinDesk"){
                style{
                    fontSize = 20.px
                    fontWeight = FontWeight.BOLD
                }

            }
        }
    }

    override fun onDock(){
        thread {
            Thread.sleep(3500)
            runLater {
                close()
                primaryStage.show()
            }
        }
    }

}