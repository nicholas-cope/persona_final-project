import java.io.*;
import javax.sound.sampled.*;

public class Music {
  private Clip clip;
  
  public void playMusic(String filePath) {
    try {
      File file = new File(filePath);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (Exception e) {
      System.out.println("Error playing audio: " + e.getMessage());
    }
  }
  
  public void stopMusic() {
    if (clip != null && clip.isRunning()) {
      clip.stop();
      clip.close();
    }
  }
}