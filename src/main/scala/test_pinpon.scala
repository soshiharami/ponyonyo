import ackcord._
import ackcord.cachehandlers.CacheSnapshotBuilder
import ackcord.data.ChannelType.GuildText
import ackcord.data._
import ackcord.requests._
import ackcord.syntax._
import ackcord.voice.AudioAPIMessage
import java.awt.{Color, Font, RenderingHints}
import java.awt.image.BufferedImage
import java.io.{File, FileOutputStream}
import java.nio.file.Paths

import javax.imageio.{IIOImage, ImageIO, ImageWriteParam}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.reflect.io.{File, Path}

object test_pinpon extends App{
  println("test")
  val token = "NzI3NTI5NjUzNTY0MzQyMzgz.XvtK5Q.vVWS_iq7NgJC3DGmfMq00zsP_us"
  val clientSettings = ClientSettings(token)
  import clientSettings.executionContext
  var channelId:TextChannel = _

  //In real code, please dont block on the client construction
  val client = Await.result(clientSettings.createClient(), Duration.Inf)
  client.onEventSideEffects {
    implicit c => {
      case APIMessage.MessageCreate(message, _) =>
        println(message.authorUser(c).get.username + " >> " + message.content)
        channelId = c.getTextChannel(message.channelId).get
        if(message.content.containsSlice("おちんちん")){
          val channel = c.getTextChannel(message.channelId).get
          client.requestsHelper.run(channel.sendMessage(content = "さいこう！"))
        }
      case APIMessage.VoiceStateUpdate(voiceState,_) =>
        println(voiceState.userId)
        println(voiceState.channelId)
        if (voiceState.userId.toString == "585424179709607940" & voiceState.channelId.isEmpty){
          println("konnkaihakokomade")
          val files: Seq[java.nio.file.Path] = Seq(Paths.get("konkaihakokomade.png"))
          client.requestsHelper.run(channelId.sendMessage(files = files))
        }
    }
  }
  client.login()
}
