package org.example;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

public class Main extends JFrame {
    private JButton nextButton;
    private JLabel textLabel;
    private JLabel nextPageLabel;
    private JPanel nextPagePanel;
    private JButton closeButton;
    private int progress;
    final static int[] notAllowedFirst3 = {333, 444, 555, 666, 777, 888, 999};

    public Main() {
        JPanel container;

        // Set the dimensions of the window
        setSize(600, 400);
        setTitle("Windows 95 Keygen");
        setResizable(false);

        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResource("/bg1.png"));
            container = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            };
            nextPagePanel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, null);
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
            container = new JPanel();
            nextPagePanel = new JPanel();
        }

        nextButton = new JButton("Next");
        textLabel = new JLabel("<html><div style='text-align: center;'>Click Next to generate a Windows 95 Key!</div></html>");
        textLabel.setVerticalAlignment(JLabel.CENTER);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setForeground(Color.WHITE);

        // Create a container to hold the button and label
        container.setLayout(new BorderLayout());

        container.add(nextButton, BorderLayout.SOUTH);
        container.add(textLabel, BorderLayout.CENTER);

        // Add the container to the JFrame
        add(container);

        // Set the JFrame to exit on close and make it visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // Create a label and panel for the next page
        String key = generateKey();
        nextPageLabel = new JLabel("Generating Key...");
        nextPageLabel.setVerticalAlignment(JLabel.CENTER);
        nextPageLabel.setHorizontalAlignment(JLabel.CENTER);
        nextPageLabel.setForeground(Color.WHITE);
        closeButton = new JButton("Close");

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        nextPagePanel.setLayout(new BorderLayout());
        nextPagePanel.add(progressBar, BorderLayout.NORTH);
        nextPagePanel.add(closeButton, BorderLayout.SOUTH);
        nextPagePanel.add(nextPageLabel, BorderLayout.CENTER);

        // Add an action listener to the "Next" button
        nextButton.addActionListener(e -> {
            // Switch to the next page panel when the button is clicked
            setContentPane(nextPagePanel);
            revalidate();
            repaint();

            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Random r = new Random();
                    for (int i = 0; i <= 100; i++) {
                        progressBar.setValue(i);
                        Thread.sleep(r.nextInt(10,500)); // Sleep for 100 milliseconds (0.1 seconds)
                    }
                    return null;
                }
                @Override
                protected void done() {
                    // Hide the progress bar and show the label
                    progressBar.setVisible(false);
                    nextPageLabel.setText("Your Windows 95 Key: " + key);
                }
            };
            worker.execute();
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    static String generateKey() {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int first3 = 0;
        do {
            first3 = r.nextInt(100, 999);
        } while (Arrays.asList(notAllowedFirst3).contains(first3));

        int last7 = 0;
        int sum;
        do {
            sum = 0;
            last7 = r.nextInt(1000000, 9999999);

            for (char x : String.valueOf(last7).toCharArray()) {
                sum += Integer.parseInt(String.valueOf(x));
            }
        } while (sum % 7 != 0);

        sb.append(first3).append("-").append(last7);
        System.out.println(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        Main gui = new Main();

        try (InputStream inputStream = Main.class.getResourceAsStream("/audio.wav")) {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
}