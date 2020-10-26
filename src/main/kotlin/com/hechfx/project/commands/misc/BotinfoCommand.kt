package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

class BotinfoCommand: CommandBuilder("botinfo", "misc") {
    override fun onCommand(event: GuildMessageReceivedEvent, context: CommandContext) {
        val runtimeMXBean: RuntimeMXBean = ManagementFactory.getRuntimeMXBean()
        val uptime = runtimeMXBean.uptime

        val uptimeInSeconds = uptime / 1000
        val hours = uptimeInSeconds / (60 * 60) % 60
        val minutes = (uptimeInSeconds / 60) - (hours * 60) % 60
        val seconds = uptimeInSeconds % 60

        val replies = listOf(
            Reply(
                "Shard: `${event.jda.shardInfo.shardId} (Guild)/${event.jda.shardManager?.shardsTotal} (Total)`"
            ).build(context),
            Reply(
                "RestPing: `${event.jda.restPing.complete()}ms`"
            ).build(context),
            Reply(
                "GatewayPing: `${event.jda.gatewayPing}ms`"
            ).build(context),
            Reply(
                "Uptime: `$hours h, $minutes m, $seconds s`"
            ).build(context)
        )
        context.sendMessage(replies.joinToString("\n"))
    }
}