import scala.swing._
import java.awt.{Font,Color}
import scala.swing.event._
import java.awt.image._
import java.io._
import javax.imageio._
import java.awt.{Color,Graphics2D,BasicStroke,Font}


 object ClockDigital_Panel extends SimpleSwingApplication{
  
  
   def top = new MainFrame {
      title    = "millisekuntikello"
      contents = new kuvaPaneeli
      size     = new Dimension(420, 160)
   }
}
 
 class kuvaPaneeli extends Panel {
   
    var double = (java.util.Calendar.getInstance().getTimeInMillis - ms )/10.0
    var str=  ((Math.ceil(double)- 14.0).toInt % 100).toString()
    var newTime = Time.current
   
   this.listenTo(keys)
     this.reactions += {
   
    case KeyTyped(_, c, _, _) =>  println("kasumo") //System.exit(0)
    focusable = true
    requestFocus
  }
  
  val MIDIPlayer = new Player
  var ms = java.util.Calendar.getInstance().getTimeInMillis
 
  preferredSize = new Dimension(420, 160)
  private var lastTime = Time.current
  private val clock = new Label(lastTime) {
     
    foreground = new Color(0, 0, 160)
    font = new Font("SansSerif", Font.PLAIN, 64)
  }
 
  Timer(10) { tick() }
  Timer(1000, false) { println("Clock has been running for 1 second!") }
  Timer(2000, false) { println("Clock has been running for 2 seconds!") }

  def tick() {
    
    double = (java.util.Calendar.getInstance().getTimeInMillis - ms )/10.0
 //   println((Math.ceil(double)- 14.0).toInt % 100)
    newTime = Time.current
  //  if (newTime != lastTime) {
     // clock.text = newTime
      str=  ((Math.ceil(double)- 14.0).toInt % 100).toString()
      if (str.length()== 1)
        str= "0"+str
      clock.text = newTime + ":" +str
    //  if(lastTime.toCharArray()(4) != newTime.toCharArray()(4))  // 13:33:04, minuutti vaihtuu
    //     MIDIPlayer.soitaSointu()
     // else   
        MIDIPlayer.soita((Math.random()*25 +66).toInt, 0, (Math.random()*9).toInt)  // random instrumentti  
      lastTime = newTime
     // this.background
      this.repaint
  //  }
  } 
  
   override def paintComponent(g: Graphics2D) = {  
      g.setBackground(Color.RED)
      g.setFont(new Font("Batang", Font.PLAIN, 62))
      g.setColor(Color.GREEN)
      g.drawString(str, 200, 111)
      g.drawString(newTime + ":", 40, 111)
   }
   
 }



object Time2 {
  private val form = new java.text.SimpleDateFormat("m:s")  //hh = hours
 //  private val form = new java.text.SimpleDateFormat   // päivämäärä ja vuosi
  def current = form.format(java.util.Calendar.getInstance().getTime) 
}

object Timer2 {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
  }
}

//class UI2 extends MainFrame {
  
  
//}

//object ClockTwo {
//  def main(args: Array[String]) {
//    val ui = new UI2
//    ui.visible = true
//  }
//}