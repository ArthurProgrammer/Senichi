package com.hechfx.project.api

import com.hechfx.project.commands.CommandContext


class Reply(private val content: String, private val prefix: String? = null) {
    fun build(context: CommandContext): String {

        return "${prefix ?: "\uD83D\uDD38"} **|** ${context.author.asMention} $content"
    }
}