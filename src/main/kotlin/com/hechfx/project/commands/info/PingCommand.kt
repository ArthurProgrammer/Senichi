package com.hechfx.project.commands.info

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.commands.CommandBuilder

class PingCommand : CommandBuilder(
        "ping",
        arrayOf("latency"),
        "Shows my ping!",
        "info"
) {
    override fun onCommand(context: CommandContext) {
        val replies = listOf(
                "BOT: `${context.jda.gatewayPing}ms`",
                "API: `${context.jda.restPing.complete()}`"
        )

        context.reply(replies.joinToString("\n"))
    }
}