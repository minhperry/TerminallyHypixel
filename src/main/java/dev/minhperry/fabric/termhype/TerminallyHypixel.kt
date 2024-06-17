package dev.minhperry.fabric.termhype

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer

class TerminallyHypixel: ClientModInitializer {
    companion object {
        const val NAMESPACE = "termhype"
        private val MOD: ModContainer = FabricLoader.getInstance().getModContainer(NAMESPACE).orElseThrow()
        var VERSION: String = MOD.metadata.version.friendlyString
    }


    private val INSTANCE: TerminallyHypixel = this
        get() = field


    override fun onInitializeClient() {
        // val bridge: BridgeConfig = BridgeConfig.createAndLoad()
    }


}