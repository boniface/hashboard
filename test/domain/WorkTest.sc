import java.net.URL
import scala.collection.JavaConverters._
import scala.concurrent._

import com.rometools.rome.io.{XmlReader, SyndFeedInput}

val sfi = new SyndFeedInput()

//val urlConnection = new URL("https://www.daily-mail.co.zm/?feed=atom").openConnection()
////      urlConnection.setConnectTimeout(4000)
////      urlConnection.setReadTimeout(4000)
//
//val read = new XmlReader(urlConnection)
//val entries = new SyndFeedInput().build(read).getEntries.asScala.toList
//
//entries foreach( entry => {
//  println(" The Links is ",entry.getTitle)
//})

val t = Thread.currentThread()
val name = t.getName
println(s"I am the Thread $name")



