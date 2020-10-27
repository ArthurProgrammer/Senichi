package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

class BotinfoCommand: CommandBuilder("botinfo", arrayOf("infobot"),"misc") {
    override fun onCommand(context: CommandContext) {
        val runtimeMXBean: RuntimeMXBean = ManagementFactory.getRuntimeMXBean()
        val uptime = runtimeMXBean.uptime

        val uptimeInSeconds = uptime / 1000
        val hours = uptimeInSeconds / (60 * 60) % 60
        val minutes = (uptimeInSeconds / 60) - (hours * 60) % 60
        val seconds = uptimeInSeconds % 60

        val replies = listOf(
            Reply(
                "Shard: `${context.jda.shardInfo.shardId} (Guild)/${context.jda.shardManager?.shardsTotal} (Total)`",
                    mention = true
            ).build(context),
            Reply(
                "RestPing: `${context.jda.restPing.complete()}ms`"
            ).build(context),
            Reply(
                "GatewayPing: `${context.jda.gatewayPing}ms`"
            ).build(context),
            Reply(
                "Uptime: `$hours h, $minutes m, $seconds s`"
            ).build(context),
            Reply(
                "Users: `${context.jda.users.size}`"
            ).build(context),
            Reply(
                "Guilds: `${context.jda.guilds.size}`"
            ).build(context)
        )
        context.textChannel.sendMessage(replies.joinToString("\n")).queue()
    }
}