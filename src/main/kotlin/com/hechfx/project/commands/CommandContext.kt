package com.hechfx.project.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.*
import okhttp3.OkHttpClient
import java.io.File

class CommandContext(
    var rawArgs: List<String>,
    var guild: Guild,
    var textChannel: TextChannel,
    var message: Message,
    var jda: JDA,
    var author: User,
    var member: Member,
    var httpClient: OkHttpClient,
    var clientUser: User
) {
    fun reply(message: Message) {
        return sendMessage("${author.asMention}, $message")
    }
    fun reply(text: CharSequence) {
        return sendMessage("${author.asMention}, $text")
    }
    fun reply(embed: MessageEmbed) {
        return sendMessage("${author.asMention}, $embed")
    }
    fun sendMessage(message: Message) {
        return textChannel.sendMessage(message).queue()
    }
    fun sendMessage(text: CharSequence) {
        return textChannel.sendMessage(text).queue()
    }
    fun sendMessage(embed: MessageEmbed) {
        return textChannel.sendMessage(embed).queue()
    }
    fun sendFile(file: File, fileName: String) {
        return textChannel.sendFile(file, fileName).queue()
    }
    fun sendFile(data: ByteArray, fileName: String) {
        return textChannel.sendFile(data, fileName).queue()
    }
    fun user(argument: Int): User? {
        return message.mentionedUsers.firstOrNull { it.asMention == rawArgs.getOrNull(argument)?.replace("!", "") } ?: rawArgs.getOrNull(argument)?.toLong()?.let {
            jda.getUserById(it)
        }
    }
    fun guild(argument: Int): Guild? {

        val guildId = validateSnowflake(rawArgs[argument].toLong())

        return guildId?.let { jda.getGuildById(it) }
    }

    fun validateSnowflake(snowflake: Long): Long? {

        if (snowflake.toString().length != 18) throw Exception()

        return snowflake
    }
}