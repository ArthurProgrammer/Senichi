package com.hechfx.project

import com.hechfx.project.config.Configuration
import com.hechfx.project.listeners.MessageListener
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder

object Senichi {
    @JvmStatic
    fun main(args: Array<String>) {
        val shardManager = DefaultShardManagerBuilder.createDefault(Configuration.TOKEN)
            .build()
        shardManager.setStatus(OnlineStatus.IDLE)
        shardManager.setActivity(Activity.of(Activity.ActivityType.WATCHING, "you!"))
        shardManager.addEventListener(
            MessageListener()
        )
    }
}