package com.hechfx.project.commands

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.*
import okhttp3.OkHttpClient

class CommandContext(
    var rawArgs: List<String>,
    var guild: Guild,
    var textChannel: TextChannel,
    var message: Message,
    var jda: JDA,
    var author: User,
    var member: Member,
    var httpClient: OkHttpClient,
)