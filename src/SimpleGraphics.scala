import scala.swing._
import java.awt.Color

object SimpleGraphics extends SimpleSwingApplication {

  def top = new MainFrame {
    title    = "Pallo"
  //  contents = new Rect3DPaneeli
    contents = new PalloPaneeli
    size     = new Dimension(600, 600)
  }

  class PalloPaneeli extends Panel {

    override def paintComponent(g : Graphics2D) = {
      g.scale(-1.4, -0.8)  
      g.translate(-400,-400)
      g.setColor(Color.blue)
      g.fillOval(10, 10, 300, 200)

      g.setColor(Color.black)
      g.drawString("Ceci n'est pas un ovale.", 115,250)
    }
  }
  
   class RectPaneeli extends Panel {

    override def paintComponent(g : Graphics2D) = {
      g.setColor(Color.red)
      g.fillRect(90, 90, 200, 400)

      g.setColor(Color.black)
      g.drawString("Olen punainen suorakulmio. JEEE !", 115,250)
    }
  }
   
    class Rect3DPaneeli extends Panel {

    override def paintComponent(g : Graphics2D) = {
      g.setColor(Color.magenta)
      
      var tiheys = 3
      var koko = 260
      var xy = 0
      g.drawString("Olen 3D -pyramidi. JEEE !", 50, koko + 30)
      for (i <- 0 to koko/2/tiheys){
         g.draw3DRect(xy, xy, koko, koko, true)   // raised =  true
         xy += tiheys
         koko -= 2*tiheys
      }   
    }
  }

}