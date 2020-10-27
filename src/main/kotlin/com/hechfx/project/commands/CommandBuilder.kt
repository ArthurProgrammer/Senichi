package com.hechfx.project.commands

import org.jetbrains.annotations.NotNull

abstract class CommandBuilder(@NotNull val name: String, val aliases: Array<String>, val category: String? = null) {
    abstract fun onCommand(context: CommandContext)
}