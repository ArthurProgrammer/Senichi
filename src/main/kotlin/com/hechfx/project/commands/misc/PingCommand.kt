package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

class PingCommand : CommandBuilder("ping", arrayOf("latency"),"misc") {
    override fun onCommand(context: CommandContext) {
        val replies = listOf(
            Reply(
                "API: `${context.jda.gatewayPing}ms`",
                    mention = true
            ).build(context),
            Reply(
                "BOT: `${context.jda.restPing.complete()}ms`"
            ).build(context)
        )

        context.textChannel.sendMessage(replies.joinToString("\n")).queue()
    }
}