package dev.minhperry.fabric.termhype.utils

import com.mojang.logging.LogUtils
import org.slf4j.Logger
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path


object FileUtils {
    val LOGGER: Logger = LogUtils.getLogger()

    @Throws(IOException::class)
    fun recursiveDelete(dir: Path): Unit {
        if (!Files.exists(dir)) {
            return
        }

        if (Files.isDirectory(dir) && !Files.isSymbolicLink(dir)) {
            Files.list(dir).use { stream ->
                stream.forEach { child ->
                    try {
                        recursiveDelete(child)
                    } catch (e: Exception) {
                        LOGGER.error("[TH] Exception while deleting path: ${child.toAbsolutePath()}", e)
                    }
                }
            }
        }

        if (!Files.isWritable(dir) && !dir.toFile().setWritable(true)) {
            LOGGER.error("[TH] Failed to make writable at path: ${dir.toAbsolutePath()}")
        }

        Files.delete(dir)
    }

    // Normalize to regex: [^a-z0-9_.-]
    fun normalizePath(path: Path): String {
        return path.toString().lowercase().replace("[^a-z0-9_.-]".toRegex(), "_")
    }
}