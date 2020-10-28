package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PingCommand : CommandBuilder(
    "ping",
    arrayOf("latency"),
    "Shows my ping!"
) {
    override fun onCommand(context: CommandContext) {
        val replies = listOf(
                "BOT: `${context.jda.gatewayPing}ms`",
                "API: `${context.jda.restPing.complete()}`"
        )

        context.sendMessage(replies.joinToString("\n"))
    }
}