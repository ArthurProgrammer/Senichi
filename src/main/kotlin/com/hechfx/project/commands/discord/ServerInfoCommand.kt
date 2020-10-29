package com.hechfx.project.commands.discord

import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.DISCORD_BLURPLE
import net.dv8tion.jda.api.EmbedBuilder

class ServerInfoCommand: CommandBuilder(
        "serverinfo",
        arrayOf("guildinfo"),
        "Shows server's info",
        "discord"
) {
    override fun onCommand(context: CommandContext) {

        val guild = if (context.rawArgs.isEmpty()) {
            context.guild
        } else {
            context.guild(0)
        }
        val guildTimeCreated = guild?.timeCreated
        val guildTimeCreatedYear = guildTimeCreated?.year
        val guildTimeCreatedMonth = guildTimeCreated?.monthValue
        val guildTimeCreatedDay = guildTimeCreated?.dayOfMonth
        val guildTimeCreatedHour = guildTimeCreated?.hour
        val guildTimeCreatedMinute = guildTimeCreated?.minute

        val clientAsMember = guild?.getMember(context.clientUser)
        val clientTimeJoined = clientAsMember?.timeJoined
        val clientTimeJoinedYear = clientTimeJoined?.year
        val clientTimeJoinedMonth = clientTimeJoined?.monthValue
        val clientTimeJoinedDay = clientTimeJoined?.dayOfMonth
        val clientTimeJoinedHour = clientTimeJoined?.hour
        val clientTimeJoinedMinute = clientTimeJoined?.minute

        val emotes = guild?.emotes?.joinToString(", ") {
            it.asMention
        }

        val guildBanner = guild?.bannerUrl
        val guildIcon = guild?.iconUrl ?: "https://static.thenounproject.com/png/340719-200.png"
        val guildRoles = guild?.roles?.size.toString()
        val membersSize = guild?.memberCount.toString()
        val owner = guild?.ownerId?.let { context.jda.getUserById(it) }
        val guildName = guild?.name
        val guildId = guild?.id
        val voiceChannelsSize = guild?.voiceChannels?.size.toString()
        val textChannelsSize = guild?.textChannels?.size.toString()
        val guildRegionEmoji = guild?.region?.emoji
        val guildRegionName = guild?.region?.getName()
        val guildCreatedInBuilder = "$guildTimeCreatedDay/$guildTimeCreatedMonth/$guildTimeCreatedYear $guildTimeCreatedHour:$guildTimeCreatedMinute"
        val clientTimeJoinedBuilder = "$clientTimeJoinedDay/$clientTimeJoinedMonth/$clientTimeJoinedYear $clientTimeJoinedHour:$clientTimeJoinedMinute"
        val embed = EmbedBuilder()
                .setTitle("Server's information!")
                .setColor(DISCORD_BLURPLE)
                .setThumbnail(guildIcon)
                .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
                .addField("Name", guildName, true)
                .addField("ID", guildId, true)
                .addField("Owner", "`${owner?.asTag} (${owner?.id})`", true)
                .addField("Emotes", emotes, true)
                .addField("Roles", guildRoles, true)
                .addField("Members", membersSize, true)
                .addField("VoiceChannels/TextChannels", "$voiceChannelsSize/$textChannelsSize", true)
                .addField("Region", "$guildRegionEmoji $guildRegionName", true)
                .addField("Created in", guildCreatedInBuilder, true)
                .addField("I entered here in", clientTimeJoinedBuilder, true)
        if (guildBanner != null) {
            embed.setImage(guildBanner)
        }
        context.sendMessage(embed.build())
    }
}