import java.awt.*;
import java.util.concurrent.CompletableFuture;

import static java.awt.GridBagConstraints.*;

import javax.swing.*;

public class DragonCurveGrid extends JPanel {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    private JLabel iterations;
    private JLabel pixels;
    private JSlider iterationSlider;
    private JSlider pixelSlider;
    private JLabel iterationValue;
    private JLabel pixelValue;
    private JButton startButton;
    private static JFrame dragonFrame;
    private static BufferedImagePanel dc;
    private JLabel imgLabel;


    public DragonCurveGrid() {

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        /*Iterations text*/
        iterations = new JLabel("Iterations");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 0, 0);  //left padding

        add(iterations, c);

        /*Iterations Slider*/
        iterationSlider = new JSlider();
        setSliderValues(iterationSlider, 5, 25, 5);
        c.gridx = 1;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 0, 20);  //left padding

        add(iterationSlider, c);

        /*Iterations valueChange label*/
        iterationValue = new JLabel("");
        iterationValue.setText(String.valueOf(iterationSlider.getValue()));

        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(0, 20, 0, 0);

        add(iterationValue, c);

        /*pixels text*/
        pixels = new JLabel("Forward Step (pixels)");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 0, 0);

        add(pixels, c);

        /*pixels Slider*/
        pixelSlider = new JSlider();
        setSliderValues(pixelSlider, 1, 10, 5);
        c.gridx = 1;
        c.weightx = 0.5;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 0, 20);

        add(pixelSlider, c);

        /*pixels valueChange label*/
        pixelValue = new JLabel("");
        pixelValue.setText(String.valueOf(pixelSlider.getValue()));
        c.gridx = 2;
        c.gridy = 1;
        c.insets = new Insets(10, 20, 0, 10);

        add(pixelValue, c);

        /*Start Button*/
        startButton = new JButton("Start");
        c.gridx = 1;
        c.fill = GridBagConstraints.REMAINDER;

        c.gridy = 2;
        c.insets = new Insets(20, 20, 0, 0);

        add(startButton, c);

        imgLabel = new JLabel();

        dc = new BufferedImagePanel(0, "");
        imgLabel.setIcon(dc.getIcon());
        c.insets = new Insets(10, 0, 0, 0);

        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 4;
        c.anchor = PAGE_END;
        c.weighty = 0.5;

        add(imgLabel, c);

        addPixelsListener();
        addIterationsListener();
        addButtonActionListener();
    }

    public void addButtonActionListener() {
        startButton.addActionListener(actionEvent -> {
            iterationSlider.setEnabled(false);
            pixelSlider.setEnabled(false);
            startButton.setEnabled(false);
            startButton.setText("Running...");
            calculateDragonCurve();
        });
    }

    public void addIterationsListener() {
        iterationSlider.addChangeListener(changeEvent -> {
            String iterationSliderValue1 = String.valueOf(iterationSlider.getValue());
            iterationValue.setText(iterationSliderValue1);
        });

    }

    public void addPixelsListener() {
        pixelSlider.addChangeListener(changeEvent -> {
            String pixelSliderValue1 = String.valueOf(pixelSlider.getValue());
            pixelValue.setText(pixelSliderValue1);
        });

    }

    public void calculateDragonCurve() {
        CompletableFuture.supplyAsync(() -> CalculateGenerations.applyRule(iterationSlider.getValue()))
                .thenAccept(this::drawImageFromCalculatedSequence)
                .thenRun(this::enableControls);
    }


    public void enableControls() {
        iterationSlider.setEnabled(true);
        pixelSlider.setEnabled(true);
        startButton.setEnabled(true);
        startButton.setText("Start");
    }

    public void drawImageFromCalculatedSequence(String result) {
        dc = new BufferedImagePanel(pixelSlider.getValue(), result);
        imgLabel.setIcon(dc.getIcon());
        revalidate();
        repaint();
    }

    public void setSliderValues(JSlider slider, int min, int max, int value) {
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(value);
    }

    public static void main(final String... args) {
        SwingUtilities.invokeLater(() -> {
            dragonFrame = new JFrame();
            dragonFrame.add(new DragonCurveGrid());
            dragonFrame.pack();
            dragonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dragonFrame.setSize(DragonCurveGrid.WIDTH, DragonCurveGrid.HEIGHT);
            dragonFrame.setResizable(false);
            dragonFrame.setLocationRelativeTo(null);
            dragonFrame.setVisible(true);
        });
    }
}