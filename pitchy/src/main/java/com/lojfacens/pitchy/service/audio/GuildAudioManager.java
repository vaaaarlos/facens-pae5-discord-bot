package com.lojfacens.pitchy.service.audio;

import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;

@Slf4j
@Getter
public class GuildAudioManager {

  private final JdaLink link;
  private final LavalinkPlayer player;
  private final TrackScheduler scheduler;

  public GuildAudioManager(AudioManager audioManager, Guild guild, LavalinkManager lavalinkManager) {
    this.link = lavalinkManager.getLavalink().getLink(guild);
    this.player = link.getPlayer();
    this.scheduler = new TrackScheduler(audioManager, guild, player);
    this.player.addListener(scheduler);
  }

  public void resetPlayer() {
    link.resetPlayer();
  }

  public void openConnection(VoiceChannel channel) {
    link.connect(channel);
  }

  public void destroyConnection() {
    player.removeListener(scheduler);
    link.resetPlayer();
    link.destroy();
    log.info("Destroyed audio manager");
  }

}
