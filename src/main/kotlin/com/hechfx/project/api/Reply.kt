package com.hechfx.project.api

import com.hechfx.project.commands.CommandContext


class Reply(private val content: String, private val prefix: String? = null, private val mention: Boolean? = false) {
    fun build(context: CommandContext): String {
        return if (mention == true) {
            "${prefix ?: "\uD83D\uDD38"} **|** ${context.author.asMention} $content"
        } else {
            "${prefix ?: "\uD83D\uDD38"} **|** $content"
        }
    }
}