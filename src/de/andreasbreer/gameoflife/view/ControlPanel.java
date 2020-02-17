package de.andreasbreer.gameoflife.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

/**
 * This class represents the control panel
 * @author Andreas Breer
 */
public class ControlPanel extends JPanel {
    /**
     * Default value for the minimum of the frames per seconds slider.
     */
    static final int FPS_MIN = 1;

    /**
     * Default value for the maximum of the frames per seconds slider.
     */
    static final int FPS_MAX = 31;

    /**
     * Default value for the initial value of the frames per seconds slider.
     */
    static final int FPS_INIT = 15;

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The start button.
     */
    private JButton startButton;

    /**
     * The frames per second slider.
     */
    private JSlider framesPerSecondSlider;

    /**
     * The constructor of this class.
     */
    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        startButton = new JButton("Start");

        framesPerSecondSlider =
                new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

        // Turn on labels at major tick marks.
        framesPerSecondSlider.setMajorTickSpacing(5);
        framesPerSecondSlider.setMinorTickSpacing(1);
        framesPerSecondSlider.setPaintTicks(true);

        add(framesPerSecondSlider);
        add(Box.createHorizontalGlue());
        add(startButton);
    }

    /**
     * Getter for the start button.
     * @return the start button
     */
    public JButton getStartButton() {
        return startButton;
    }

    /**
     * Getter for the frames per second slider.
     * @return the frames per second slider
     */
    public JSlider getFramesPerSecondSlider() {
        return framesPerSecondSlider;
    }

}
