package de.andreasbreer.gameoflife.view;

import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSlider;

import de.andreasbreer.gameoflife.model.World;

public class FillWorldDialog extends JDialog implements ActionListener {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * The default value for the frame width.
     */
    private final static int DEFAULT_FRAME_WIDTH = 500;

    /**
     * The default value for the frame height.
     */
    private final static int DEFAULT_FRAME_HEIGHT = 160;

    /**
     * Default value for the minimum of the probability slider.
     */
    static final int PROBABILITY_MIN = 0;

    /**
     * Default value for the maximum of the probability slider
     */
    static final int PROBABILITY_MAX = 50;

    /**
     * Default value for the initial value of the the probability slider.
     */
    static final int PROBABILITY_INIT = 25;

    /**
     * Slider for the probability for a living cell in percent.
     */
    private JSlider probabilitySlider;

    /**
     * the okay button to apply the settings and close the frame.
     */
    private JButton okButton;

    /**
     * The world to work on.
     */
    private World world;

    /**
     * The constructor of this class using the field owner and world.
     * @param owner
     *            the parent frame
     * @param world
     *            the world to work on
     */
    public FillWorldDialog(Frame owner, World world) {
        super(owner, true);
        this.world = world;
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setLocationRelativeTo(owner);

        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Initialize the swing components.
     */
    private void initComponents() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        probabilitySlider =
                new JSlider(JSlider.HORIZONTAL, PROBABILITY_MIN,
                        PROBABILITY_MAX, PROBABILITY_INIT);
        okButton = new JButton("Fuellen");

        // Turn on labels at major tick marks.
        probabilitySlider.setMajorTickSpacing(10);
        probabilitySlider.setMinorTickSpacing(1);
        probabilitySlider.setPaintLabels(true);
        probabilitySlider.setPaintTicks(true);

        okButton.addActionListener(this);

        Box labelBox = Box.createHorizontalBox();
        labelBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        labelBox.add(new Label(
                "Wahrscheinlichkeit für eine lebende Zelle in Prozent:"));
        add(labelBox);

        Box silderBox = Box.createHorizontalBox();
        silderBox.add(probabilitySlider);
        silderBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(silderBox);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(okButton);
        buttonBox.add(Box.createHorizontalGlue());
        add(buttonBox);
    }

    /**
     * This method handles the button click event.
     * @param event
     *            this object holds all relevant data of the event
     */
    public void actionPerformed(ActionEvent event) {
        int probability = (int) probabilitySlider.getValue();
        world.random(probability);
        this.dispose();
    }

}
