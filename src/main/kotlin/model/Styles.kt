package model

import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val boxes by cssclass()
        val bottomHbox by cssclass()
        val errorLabel by cssclass()

    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            cursor = Cursor.TEXT
        }
        boxes {
            padding = box(20.px)
            alignment = Pos.CENTER
        }
        bottomHbox{
            alignment = Pos.CENTER_LEFT
            spacing = 10.px
        }

        errorLabel{
            textFill = Color.RED
        }
    }
}