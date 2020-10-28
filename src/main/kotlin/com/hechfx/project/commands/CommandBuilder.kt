package com.hechfx.project.commands

import org.jetbrains.annotations.NotNull

abstract class CommandBuilder(
        @NotNull val name: String,
        val aliases: Array<String>,
        val description: String,
        val dev: Boolean? = false,
        val onlyOnMainGuild: Boolean? = false
) {
    abstract fun onCommand(context: CommandContext)
}