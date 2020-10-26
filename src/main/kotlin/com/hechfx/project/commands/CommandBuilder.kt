package com.hechfx.project.commands

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import org.jetbrains.annotations.NotNull

abstract class CommandBuilder(@NotNull val name: String, val category: String? = null) {
    abstract fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext)
}