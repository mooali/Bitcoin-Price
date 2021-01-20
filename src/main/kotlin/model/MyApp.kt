package model

import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import view.MainView
import view.SplashScreen

class MyApp: App(MainView::class, Styles::class){
    override fun start(stage: Stage) {
        super.start(stage)
        find<SplashScreen>().openModal(stageStyle = StageStyle.UNDECORATED)
    }
    }

