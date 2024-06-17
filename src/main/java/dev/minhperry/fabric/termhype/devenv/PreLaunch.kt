package dev.minhperry.fabric.termhype.devenv

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint

class PreLaunch: PreLaunchEntrypoint {
    override fun onPreLaunch() {
        System.setProperties(System.getProperties().apply {
            setProperty("devauth.enabled", "true")
            setProperty("devaut.configDir", ".\\.devauth")
            setProperty("devauth.account", "main")
        })
    }
}