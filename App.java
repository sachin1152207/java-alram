import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm"); 
        String time = dtf.format(LocalDateTime.now());
        System.out.println("Current time: "+time);
        System.out.println("Welcome Java Alram by Sachin");
        System.out.print("Enter time to set alram format = 20:10: ");
        String setTotime = scan.nextLine();
        System.out.print("Enter description: ");
        String desc = scan.nextLine();
        System.out.println(setTotime);
        setAlram(setTotime, desc);
        scan.close();

        
    }
    static void playsound(){
        File file = new File("alram_tone.wav");
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            // Wait for the sound to finish playing
            while (!clip.isRunning()) {
                Thread.sleep(10);
            }
            while (clip.isRunning()) {
                Thread.sleep(10);
            }
            clip.close();
            audioInputStream.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
     //  Sending notification
    static void sendNotification(String msg){
        try {
            SystemTray tray = SystemTray.getSystemTray();

        // Setting icon for notification
        Image image = Toolkit.getDefaultToolkit().createImage("alram.png");

        TrayIcon trayicon = new TrayIcon(image, "Alram Notification");

        // let auto resize image by system
        trayicon.setImageAutoSize(true);

        // Setting tooltip for text tray
        trayicon.setToolTip("Alram by java");
        tray.add(trayicon);

        // Displaying Notification
        trayicon.displayMessage("Alram", msg, MessageType.INFO);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    // Setting Alram
    static void setAlram(String alramTime, String alramMsg){
        while (true) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm"); 
            String time = dtf.format(LocalDateTime.now());
            if (time.equals(alramTime)) {
                sendNotification(alramMsg);
                playsound();
                break;
            }
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
