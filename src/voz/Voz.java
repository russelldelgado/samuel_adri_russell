/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voz;

/**
 *
 * @author adrian
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
  
/**
 *
 * @author Cmop
 */
import java.awt.Desktop;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.Voice;
 
public class Voz extends ResultAdapter
{

 
 static Recognizer recognizer;
 String gst;
 
 @Override
 public void resultAccepted(ResultEvent re)
 {
 try
 {
 Result res = (Result)(re.getSource());
 ResultToken tokens[] = res.getBestTokens();
 
 String args[]= new String[1];
 args[0]="";
 for (int i=0; i < tokens.length; i++)
 {
 gst = tokens[i].getSpokenText();
 args[0]+=gst+" ";
 System.out.print(gst + " ");
 }
 System.out.println();
 
 
 
 if(gst.equals("AbrirGoogle")){
    VoiceManager manager = VoiceManager.getInstance();
    Voice voz = manager.getVoice("kevin16");
    voz.allocate();
    voz.speak("Abriendo Google");
    voz.deallocate();
     
     
 Desktop enlace=Desktop.getDesktop();
        try {
                enlace.browse(new URI("www.google.com"));
        } catch (IOException | URISyntaxException e) {
            e.getMessage();
        }
 }
 if(gst.equals("Salir"))
 {
 recognizer.deallocate();
 args[0]="Adios";
 System.out.println(args[0]);
 System.exit(0);
 }
 else
 {
 recognizer.suspend();
 recognizer.resume();
 }
 }catch(Exception ex)
 {
 System.out.println("Ha ocurrido algo inesperado " + ex);
 }
 }
 
 public static void main(String args[])
 {
 try
 {
 recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
 recognizer.allocate();
 
 FileReader grammar1 =new FileReader("libreria/Diccionario.txt");
 
 RuleGrammar rg = recognizer.loadJSGF(grammar1);
 rg.setEnabled(true);
 
 recognizer.addResultListener(new Voz());
 
 System.out.println("Empieze Dictado");
 recognizer.commitChanges();
 
 recognizer.requestFocus();
 recognizer.resume();
 }catch (Exception e)
 {
 System.out.println("Exception en " + e.toString());
 e.printStackTrace();
 System.exit(0);
 }
 }
}
