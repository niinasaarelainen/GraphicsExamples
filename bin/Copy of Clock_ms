
import scala.swing.Swing._
import scala.swing.{MainFrame, Panel, SimpleSwingApplication}
import scala.swing.event._

 
import java.awt.{Color, Dimension, Graphics, Graphics2D, Point, geom, RenderingHints}
import java.awt.event.{ActionListener, ActionEvent}
import javax.swing.{Timer => SwingTimer, AbstractAction} // for the Timer

object MovingText extends SimpleSwingApplication {
  
  val message = "Message moving?"
  var xCoordinate = 0
  var yCoordinate = 20

  
  lazy val ui = new Panel {
    background = Color.white
    preferredSize = (280, 100)
    
    focusable = true
    visible = true
    var ms = java.util.Calendar.getInstance().getTimeInMillis
        
    
    override def paintComponent(g: Graphics2D) = {
      super.paintComponent(g)
      
      var double = (java.util.Calendar.getInstance().getTimeInMillis - ms )/10.0
      var str=  ((Math.ceil(double)- 14.0).toInt % 100).toString()
      if (str.length()== 1)
        str= "0"+str
      
      //set antialiasing for shapes
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                         RenderingHints.VALUE_ANTIALIAS_ON)
      // set antialiasing for text                   
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
      
                                
      if (xCoordinate > this.size.width){
        xCoordinate = -20
      }                   
      xCoordinate += 3
      g.drawString(str, xCoordinate, yCoordinate)
      //g.drawString(message, xCoordinate, yCoordinate)
    }
    
    val timer = new SwingTimer(20, new ActionListener() {
      override def actionPerformed(e: ActionEvent) {
        repaint()
      } 
    })
    timer.start
  }
  
  def top = new MainFrame {
    title = "Animation Demo"
    contents = ui  
  }
  
}