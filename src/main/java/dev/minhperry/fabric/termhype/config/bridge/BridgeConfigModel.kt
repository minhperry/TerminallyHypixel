package dev.minhperry.fabric.termhype.config.bridge

import dev.minhperry.fabric.termhype.TerminallyHypixel.Companion.NAMESPACE
import io.wispforest.owo.config.annotation.Config
import io.wispforest.owo.config.annotation.Modmenu

@Modmenu(modId = NAMESPACE)
@Config(name = "bridge", wrapperName = "BridgeConfig")
class BridgeConfigModel {
    var isEnabled: Boolean = true
    var autoconnect: Choice = Choice.ON

    enum class Choice {
        ON, OFF
    }
}