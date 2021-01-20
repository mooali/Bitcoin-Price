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
        val priceLabel by cssclass()
        val buttons by cssclass()

    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            cursor = Cursor.TEXT
        }
        priceLabel{
            fontSize = 15.px
            fontWeight = FontWeight.BOLD

        }
        boxes {
            padding = box(20.px)
            alignment = Pos.CENTER
        }
        bottomHbox{
            alignment = Pos.CENTER_LEFT
            spacing = 10.px
            padding = box(10.px)
        }

        buttons{
            minWidth = 30.px
            minHeight = 20.px
            backgroundColor += Color.rgb(0,204,204)
            and(hover) {
                backgroundColor += Color.rgb(0,255,255)
            }
            fontSize = 15.px
            fontWeight = FontWeight.BOLD
            textFill = Color.BLACK
            cursor = Cursor.HAND

        }

        errorLabel{
            textFill = Color.RED
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }
    }
}