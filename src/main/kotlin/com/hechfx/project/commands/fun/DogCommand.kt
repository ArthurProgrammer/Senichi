package com.hechfx.project.commands.`fun`

import com.fasterxml.jackson.databind.ObjectMapper
import com.hechfx.project.commands.CommandBuilder
import com.hechfx.project.commands.CommandContext
import com.hechfx.project.config.Configuration
import net.dv8tion.jda.api.EmbedBuilder
import okhttp3.Request
import java.net.URL

class DogCommand: CommandBuilder("dog", arrayOf("doggo", "cachorro"), "fun") {
    override fun onCommand(context: CommandContext) {
        val url = URL("https://some-random-api.ml/img/dog")

        val client = context.httpClient

        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val res = client.newCall(request).execute()
        val resBody = res.body()!!.string()

        val mapperAll = ObjectMapper()
        val objData = mapperAll.readTree(resBody)

        val dogLink = objData["link"].asText()

        val embed = EmbedBuilder()
            .setTitle("Dogs!")
            .setImage(dogLink)
            .setColor(Configuration.DISCORD_BLURPLE)
            .setFooter("Command executed by ${context.author.asTag}", context.author.effectiveAvatarUrl)
        context.textChannel.sendMessage(embed.build()).queue()
    }
}