import scala.io.Source
import scala.io.StdIn._
import java.io.{FileWriter,PrintWriter}
import xml._
import scala.collection.mutable
import scala.collection.immutable

case class Pi(piID:Int,location:String,fileLocation:String)
case class Estimote(estimoteID:String,partNumber:String)

def XMLToEstimote(unit:xml.Node):Estimote = new Estimote( (unit \ "estimote").text, (unit \ "partNumber").text )
def XMLToPi(unit:xml.Node):Pi = new Pi((unit \ "piNumber").text.toInt, (unit \ "location").text, (unit \ "fileLocation").text)

def finalListToXML(unit:(String,String)):Node = {
	<item>{
		<partNumber>{unit._1}</partNumber>
		<location>{unit._2}</location>
	}</item>
}

val estimotePartNumberList = XML.loadFile("estimotePartNumberList.xml")
val estimoteList = (estimotePartNumberList \ "item").map(XMLToEstimote)

val piLocationList = XML.loadFile("piLocationList.xml")
val piList = (piLocationList \ "item").map(XMLToPi)

var finalList = List[(String,String)]()

estimoteList.foreach(a => {
	var temp = List[Double]()
	piList.foreach(b => {
		val XMLFile = XML.loadFile(b.fileLocation)
		val res = ((XMLFile \ "location").filter(c => (c \ "id").text == a.estimoteID) \ "rssi").toList.map(d => d.text.toDouble)
		temp ::= (res.sum)/(res.length)
	})
	//println(temp.reverse)
	finalList ::= (a.partNumber -> piList(temp.reverse.indexOf(temp.max)).location)
})

//println(finalList.reverse)

XML.save("finalFile.xml",
<locationdata>
	{finalList.reverse.map(finalListToXML)}
</locationdata>)
