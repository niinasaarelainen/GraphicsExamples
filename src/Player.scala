import javax.sound.midi.MidiSystem

class Player  {
  
    val synth = MidiSystem.getSynthesizer()
    val channels  =  synth.getChannels()
		val ch1 = channels(0)
		val hyvatSoundit = Array(96, 9, 10, 11, 12, 13, 14, 15, 46)   // 9 instrumenttia
       
    synth.open   
    
//    for (instr <- synth.getAvailableInstruments)
//        println(instr)
       
    def soita(korkeus: Int, korkeus2:Int = 0, patch:Int) = {   
        ch1.programChange(hyvatSoundit(patch)) 
        ch1.noteOn(korkeus, 100)  // 100= velocity   
        if(korkeus2 != 0) 
            ch1.noteOn(korkeus2, 100)  // 100= velocity  
        ch1.noteOff(korkeus)
        if(korkeus2 != 0) 
            ch1.noteOff(korkeus2)
    }
    
    def soitaSointu() = {
        ch1.programChange(hyvatSoundit(6))  
        ch1.noteOn(65, 119)
        ch1.noteOn(69, 119)
        ch1.noteOn(72, 119)
        ch1.noteOff(65); ch1.noteOff(69); ch1.noteOff(72);
    }
}