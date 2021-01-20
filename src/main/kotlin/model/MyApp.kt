package model

import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import view.MainView
import view.SplashScreen

class MyApp: App(MainView::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        stage.icons += Image("bitcoin.png")
        find<SplashScreen>().openModal(stageStyle = StageStyle.UNDECORATED)
    }
    }

