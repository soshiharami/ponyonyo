import java.nio.file.Paths

import ackcord._
import ackcord.data._
import ackcord.syntax._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ponyo extends App{
  println("test")
  val token = "NzI3NTI5NjUzNTY0MzQyMzgz.XvtK5Q.vVWS_iq7NgJC3DGmfMq00zsP_us"
  val clientSettings = ClientSettings(token)
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
