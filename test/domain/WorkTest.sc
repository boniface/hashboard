import java.net.URL
import org.joda.time.DateTime

import scala.collection.JavaConverters._
import scala.concurrent._

import com.rometools.rome.io.{XmlReader, SyndFeedInput}


class Multiplier(factor:Int)  {
  def apply(f:Int)={
    f*factor

  }

}

val m =new Multiplier(10)

val ans = m(10)