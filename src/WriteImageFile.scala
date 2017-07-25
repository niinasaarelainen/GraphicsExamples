import javax.imageio.ImageIO
import java.io.File
import java.awt.image.BufferedImage
import java.awt.{Graphics2D, Color}

object Images extends App {

  val pic = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB)
  val g = pic.getGraphics.asInstanceOf[Graphics2D]

  g.setColor(Color.blue)
  g.fillOval(10,10,290,290)

  ImageIO.write(pic, "png", new File("artwork.png"))
}