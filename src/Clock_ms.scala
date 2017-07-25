import java.io.FileWriter
import scala.io.Source
import scala.io.StdIn._
import scala.swing.Swing._
import scala.swing.{MainFrame, Panel, SimpleSwingApplication}
import scala.swing.event._
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scala.swing.BorderPanel
import scala.swing.Label
import scala.swing.TextField
import scala.swing.event.Key
import scala.swing.event.KeyPressed
import BorderPanel.Position._
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, RenderingHints, Font}
import java.awt.event.{ActionListener, ActionEvent}
import javax.swing.{Timer => SwingTimer, AbstractAction} // for the Timer

object Clock_ms extends SimpleSwingApplication {
  
  var xCoordinate = 220
  var yCoordinate = 70
  
  var kirjoitettuNimi= ""
  
  lazy val ui = new Panel {
    var startOrStop = 1
    var ms, msEiNollata = 0
    var str, s, min = "00"
    background = Color.white
    preferredSize = (420, 590)
  
    
    override def paintComponent(g: Graphics2D) = {
    //  var tallaHetkellaRuudulla = Buffer[String]()
      
      super.paintComponent(g)
      
      var double = (java.util.Calendar.getInstance().getTimeInMillis - ms )/10.0
      
      //set antialiasing for shapes
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON)
      // set antialiasing for text                   
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
      
      g.setFont(new Font("Batang", Font.PLAIN, 72))
      g.setColor(Color.BLUE)
      g.drawString(min + ":" + s + ".", xCoordinate - 200, yCoordinate)
      g.setFont(new Font("Batang", Font.PLAIN, 52))
      g.drawString(str, xCoordinate, yCoordinate)
     
      var y = 160
      g.setFont(new Font("Batang", Font.PLAIN, 22))
      
      var vertaaEdelliseen = 0
      for (aika <- ajat.sorted){
        if(vertaaEdelliseen != aika){
            
            var nimi = msRiviParit(aika.toString()).head.split(";")(0)
            if(nimi.equals(kirjoitettuNimi))
                 g.setColor(Color.BLUE)
            else g.setColor(Color.RED)   
            if(nimi.length()>22)
               g.drawString(nimi.substring(0, 22), 10, y)
            else  g.drawString(nimi, 10, y)  
            
            if(nimi.equals(kirjoitettuNimi))
                    g.setColor(Color.BLUE)
            else g.setColor(Color.RED)  
            g.drawString(msRiviParit(aika.toString()).head.split(";")(1), 310,y)  // tulos
            y += 30
               
            if(msRiviParit(aika.toString()).size > 1)   // useammalla sama aika
            for(i <-1 until msRiviParit(aika.toString()).size){
                 var nimi = msRiviParit(aika.toString())(i).split(";")(0)
                 if(nimi.equals(kirjoitettuNimi))
                    g.setColor(Color.BLUE)
                 else g.setColor(Color.RED)   
                 if(nimi.length()>22)
                    g.drawString(nimi.substring(0, 22), 10, y)
                 else  g.drawString(nimi, 10, y)  
                 if(nimi.equals(kirjoitettuNimi))
                    g.setColor(Color.BLUE)
                 else g.setColor(Color.RED)  
                 g.drawString(msRiviParit(aika.toString())(i).split(";")(1), 310,y)
                 y += 30
              }   
        }   
        vertaaEdelliseen = aika
      }
      
     
      
      ms += 1
      msEiNollata += 1
      if (ms < 10)
        str = "0" + ms
      else if (ms == 100) {ms=0; str = "0"; kasvataSekuntia() }
      else str = ms.toString()  
    }
    
   
    
    def kasvataSekuntia() = {
        var firstDigit = this.s.head.toInt -48
        if(firstDigit == 0 && this.s.last.toInt -48 < 9) this.s = "0" + (this.s.last.toInt - 47)
        else if(firstDigit == 0 && this.s.last.toInt -48 == 9) this.s =  (this.s.last.toInt - 47).toString()
        else if (this.s == "59") {this.s = "00"; this.min = "0" + (this.min.last.toInt - 47)}
        else this.s = (this.s.toInt +1).toString()  
    }
    
    def nollaa() = {
       startOrStop = 1;  ms= 0 ; msEiNollata = 0
       str= "00"; s= "00"; min = "00"
    }
    
    val timer = new SwingTimer(10, new ActionListener() {
        override def actionPerformed(e: ActionEvent) {
          repaint()
        } 
    })
  }   // end ui
  
  
  
  def top = new MainFrame {
    title = "Millisekuntikello"    
    val nimi:TextField = new TextField("")
   // nimi.columns=6
    contents= new BorderPanel (){
      add(nimi, North)
      add(ui, South)
    }
    
    top10();
    
    listenTo(nimi.keys)
    reactions += {
      case KeyPressed(_, Key.Enter, _, _) =>  {ui.nollaa(); kirjoitettuNimi = nimi.text  ; ui.timer.start }
      case KeyPressed(_, Key.Control, _, _) =>  {ui.timer.stop;  talleta();  top10() } 
    }
  }
  
  
  
  def talleta() = {
    val kohdetiedosto = new FileWriter("tulokset", true)
    val kohdetiedosto_valikoitu = new FileWriter("tulokset_valikoitu") 
   
    try {
       kohdetiedosto.append(kirjoitettuNimi+";")
       kohdetiedosto.append(ui.min + ":" + ui.s + "." + ui.str +";")
       kohdetiedosto.append(ui.msEiNollata +";\n")
       valikoituTalletus()
    } finally {
       kohdetiedosto.close()
    }
    
    def valikoituTalletus() = {
      
      var loytyyko= false
      var tyypinTahanastinenEnnatys = 0
      for(i <- 0 until inputFromFile.size){
         if (inputFromFile(i).split(";")(0).equals(kirjoitettuNimi)) loytyyko = true
         tyypinTahanastinenEnnatys  = inputFromFile(i).split(";")(2).toInt
         if((loytyyko ) && (tyypinTahanastinenEnnatys > ui.msEiNollata ))   // huonompi tulos pois
          inputFromFile -= inputFromFile(i)
      }  
       
      
       if((!loytyyko ) || (tyypinTahanastinenEnnatys > ui.msEiNollata ))   {
          inputFromFile +=  kirjoitettuNimi+";"+ui.min + ":" + ui.s + "." + ui.str +";"+ui.msEiNollata +";"
       }   
       for(rivi <- this.inputFromFile){
           kohdetiedosto_valikoitu.write(rivi+"\n")
       }        
       kohdetiedosto_valikoitu.close()
       
         
         /*
          try {
             kohdetiedosto_valikoitu.append(kirjoitettuNimi+";")
             kohdetiedosto_valikoitu.append(ui.min + ":" + ui.s + "." + ui.str +";")
             kohdetiedosto_valikoitu.append(ui.msEiNollata +";\n")
          } finally {
             kohdetiedosto_valikoitu.close()
          } */
      
      
    }
  }
  
  
      
    var ajat = Buffer[Int]()
    var msRiviParit = Map[String,Buffer[String]]()
    var inputFromFile = Buffer[String]()
    
    
  def top10() = {
       
    val data = Source.fromFile("tulokset_valikoitu")  
    inputFromFile = Buffer[String]()
    ajat = Buffer[Int]()
    msRiviParit = Map[String,Buffer[String]]()  // saman tuloksen voi saada usea henkilö
    
    try {
        for (rivi <- data.getLines) {
           inputFromFile += rivi.trim
        }
     } finally {
        data.close()
     }
     
     for (i <- 0 until inputFromFile.size){
        val kolmeDataa = inputFromFile(i).split(";")
        var kokoRivi = Buffer[String]()
        kokoRivi += inputFromFile(i)
        if (!msRiviParit.contains(kolmeDataa(2)))  
           msRiviParit += kolmeDataa(2) -> kokoRivi  // msEiNollata -> rivi
        else  {
          kokoRivi = msRiviParit(kolmeDataa(2))
          if(!kokoRivi.contains(kolmeDataa(0)))
             kokoRivi += inputFromFile(i)
          msRiviParit += kolmeDataa(2) -> kokoRivi   // täällä usean ihmien tiedot
        }
       
        ajat += kolmeDataa(2).toInt
     }
     ui.repaint()
     
  }
  
}