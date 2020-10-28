package com.hechfx.project.commands.misc

import com.hechfx.project.commands.CommandContext
import com.hechfx.project.api.Reply
import com.hechfx.project.commands.CommandBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.lang.management.ManagementFactory
import java.lang.management.RuntimeMXBean

class BotinfoCommand: CommandBuilder(
    "botinfo",
    arrayOf("infobot"),
    "Shows my informations!") {
    override fun onCommand(context: CommandContext) {
        val runtimeMXBean: RuntimeMXBean = ManagementFactory.getRuntimeMXBean()
        val uptime = runtimeMXBean.uptime

        val uptimeInSeconds = uptime / 1000
        val hours = uptimeInSeconds / (60 * 60) % 60
        val minutes = (uptimeInSeconds / 60) - (hours * 60) % 60
        val seconds = uptimeInSeconds % 60

        val replies = listOf(
                "Shard: `${context.jda.shardInfo.shardId} (Guild)/${context.jda.shardManager?.shardsTotal} (Total)`",
                "RestPing: `${context.jda.restPing.complete()}ms`",
                "GatewayPing: `${context.jda.gatewayPing}ms`",
                "Uptime: `$hours h, $minutes m, $seconds s`",
                "Users: `${context.jda.users.size}`",
                "Guilds: `${context.jda.guilds.size}`"
        )
        context.reply(replies.joinToString("\n"))
    }
}