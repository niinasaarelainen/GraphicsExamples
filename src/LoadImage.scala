import java.awt._;
import java.awt.event._
import java.awt.image._
import java.io._
import javax.imageio._
import javax.swing._
 
/**
 * This class demonstrates how to load an Image from an external file
 */
class LoadImageApp extends Component {
   
  // val pic = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB)
     var img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB)
 
   def paint(g: Graphics2D) = {
        g.drawImage(img, 0, 0, null);
    }
 
   def pic = {
     img = ImageIO.read(new File("treble_clef.png"))
     img
   } 
 
    override def getPreferredSize()  = {
        if (img == null) {
             new Dimension(100,100);
        } else {
           new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
   
}

object testi extends App {
  
     val kuva = new LoadImageApp
     kuva.pic
     
}