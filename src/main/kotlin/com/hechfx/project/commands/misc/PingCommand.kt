package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PingCommand : CommandBuilder("ping", arrayOf("latency"),"misc") {
    override fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext) {
        val replies = listOf(
            Reply(
                "API: `${event.jda.gatewayPing}ms`"
            ).build(context),
            Reply(
                "BOT: `${event.jda.restPing.complete()}ms`"
            ).build(context)
        )

        context.sendMessage(replies.joinToString("\n"))
    }
}