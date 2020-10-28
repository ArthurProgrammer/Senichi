package com.hechfx.project.commands.`fun`

import com.fasterxml.jackson.databind.ObjectMapper
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration.DISCORD_BLURPLE
import net.dv8tion.jda.api.EmbedBuilder
import okhttp3.Request
import java.net.URL

class CatCommand: CommandBuilder(
    "cat",
    arrayOf("randomcat", "gato"),
    "Shows cute cats!"
) {
    override fun onCommand(context: CommandContext) {
        val url = URL("https://some-random-api.ml/img/cat")

        val client = context.httpClient

        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val res = client.newCall(request).execute()
        val resBody = res.body()!!.string()

        val mapperAll = ObjectMapper()
        val objData = mapperAll.readTree(resBody)

        val catLink = objData["link"].asText()

        val embed = EmbedBuilder()
            .setTitle("Cats!")
            .setImage(catLink)
            .setColor(DISCORD_BLURPLE)
            .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
        context.reply(embed.build())
    }
}