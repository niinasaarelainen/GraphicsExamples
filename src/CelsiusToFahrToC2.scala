
 import swing._
 import event._
  

object CelsiusToFahrToC2 extends SimpleSwingApplication {
  
      def top = new MainFrame {
        title = "Celsius/Fahrenheit Converter"
        object celsius extends TextField { columns = 5 }  // mahtuu 5 leveää kirjainta esim WWWWW, mutta esim 19 i:tä
        object fahrenheit extends TextField { columns = 5 }
        contents = new FlowPanel {
          contents += celsius
          contents += new Label(" Celsius  <==>  ")
          contents += fahrenheit
          contents += new Label(" Fahrenheit")
          border = Swing.EmptyBorder(15, 30, 15, 30)   // (harmaan osan y ennen tekstikenttää, laatikon x ennen tekstiä, 
                                                        // harmaa after tekstikenttä, harmaa after y)
        }
        
        listenTo(celsius, fahrenheit)
        reactions += {
          case EditDone(`fahrenheit`) =>
            val f = fahrenheit.text.toInt
            val c = (f - 32) * 5 / 9
            celsius.text = c.toString
          case EditDone(`celsius`) =>
            val c = celsius.text.toInt
            val f = c * 9 / 5 + 32
            fahrenheit.text = f.toString
        }     
      }
    }