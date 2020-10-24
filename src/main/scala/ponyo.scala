import java.nio.file.Paths

import ackcord._
import ackcord.data._
import ackcord.syntax._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.control.Breaks.break

object ponyo extends App{
  println("test")
  val token = sys.env("scala_ponyo_TOKEN")
  val clientSettings = ClientSettings(token)
  var channelId:TextChannel = _
  var isMUHOUTITAI = false

  //In real code, please dont block on the client construction
  val client = Await.result(clientSettings.createClient(), Duration.Inf)
  client.onEventSideEffects {
    implicit c => {
      case APIMessage.MessageCreate(message, _) =>
        if(message.authorUser(c).get.bot.get) break
        println(message.authorUser(c).get.username + " >> " + message.content)
        channelId = c.getTextChannel(message.channelId).get
        if(message.content.containsSlice("おちんちん")){
          val channel = c.getTextChannel(message.channelId).get
          client.requestsHelper.run(channel.sendMessage(content = "さいこう！"))
        }
      case APIMessage.VoiceStateUpdate(voiceState,_) =>
        println(voiceState.userId)
        println(voiceState.channelId)
        if (voiceState.userId.toString == "585424179709607940" && voiceState.channelId == "683939861539192865"){
          isMUHOUTITAI = true
        }
        if (voiceState.userId.toString == "585424179709607940" && voiceState.channelId.isEmpty && isMUHOUTITAI){
          println("konnkaihakokomade")
          val files: Seq[java.nio.file.Path] = Seq(Paths.get("konkaihakokomade.png"))
          client.requestsHelper.run(channelId.sendMessage(files = files))
          isMUHOUTITAI = false
        }
    }
  }
  client.login()
}