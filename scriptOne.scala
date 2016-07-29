import scala.io.Source
import scala.io.StdIn._
import java.io.{FileWriter,PrintWriter}
import xml._

val fileSource = Source.fromFile("LocationData/fileOne_1.txt")
var list = fileSource.getLines.toList.map(x => x.trim)
fileSource.close

case class LocationData(id:String,date:String,time:String,rssi:Double)
var resList = List[LocationData]()

def createLocationData {
	var loc = list.indexWhere(x => x.slice(0,4) == "RSSI")
	while(loc > 1) {
		val id = list(loc-1).slice(7,24)
		val date = list(loc-2).slice(0,10)
		val time = list(loc-2).slice(11,19)
		val rssi = list(loc).slice(6,list(loc).length).toDouble
		resList ::= new LocationData(id,date,time,rssi)
		list = list.drop(loc+1)
		loc = list.indexWhere(x => x.slice(0,4) == "RSSI")
	}
}

def locationDataToXML(unit:LocationData):Node = {
	<location>{
		<id>{unit.id}</id>
		<date>{unit.date}</date>
		<time>{unit.time}</time>
		<rssi>{unit.rssi}</rssi>
	}</location>
}

createLocationData
XML.save("LocationData/fileTwo_1.xml",
<locationdata>
	{resList.map(locationDataToXML)}
</locationdata>)
