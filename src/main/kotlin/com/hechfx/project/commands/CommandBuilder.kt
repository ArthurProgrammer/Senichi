package com.hechfx.project.commands

import org.jetbrains.annotations.NotNull

abstract class CommandBuilder(
        @NotNull val name: String,
        val aliases: Array<String>,
        val category: String? = null,
        val description: String,
        val dev: Boolean? = false
) {
    abstract fun onCommand(context: CommandContext)
}