package de.andreasbreer.gameoflife.view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import de.andreasbreer.gameoflife.model.World;
import de.andreasbreer.gameoflife.util.ApplicationProperties;
import de.andreasbreer.gameoflife.util.PropertyName;

public class WorldDimensionDialog extends JDialog implements ActionListener {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * A logger for all kinds of info-messages, warnings and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(WorldDimensionDialog.class);

    /**
     * The default value for the frame width.
     */
    private final static int DEFAULT_FRAME_WIDTH = 250;

    /**
     * The default value for the frame height.
     */
    private final static int DEFAULT_FRAME_HEIGHT = 110;
   
    /**
     * The okay button to apply the settings and close the frame.
     */
    private JButton okButton;

    /**
     * The input field for the width.
     */
    private JTextField widthField;

    /**
     * The input field for the height.
     */
    private JTextField heightField;

    /**
     * A label for error messages.
     */
    private JLabel messageLabel;

    /**
     * The world to work on
     */
    private World world;

    /**
     * The constructor of this class using the field owner and world.
     * @param owner
     *            the parent frame
     * @param world
     *            the world to work on
     */
    public WorldDimensionDialog(Frame owner, World world) {
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

        // Create the swing components
        widthField = new JTextField();
        heightField = new JTextField();
        okButton = new JButton("OK");
        messageLabel = new JLabel();

        // Initialize components
        widthField.setText(String.format("%d", world.getWidth()));
        heightField.setText(String.format("%d", world.getHeight()));
        okButton.addActionListener(this);
        messageLabel.setForeground(Color.RED);

        // Add components to the frame
        Box dimensionBox = Box.createHorizontalBox();
        dimensionBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        dimensionBox.add(new Label("Groesse der Welt:"));
        dimensionBox.add(Box.createHorizontalGlue());
        dimensionBox.add(widthField);
        dimensionBox.add(new JLabel("  x  "));
        dimensionBox.add(heightField);
        add(dimensionBox);

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonBox.add(messageLabel);
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(okButton);

        add(buttonBox);
    }

    /**
     * This method handles the button click event.
     * @param event
     *            this object holds all relevant data of the event
     */
    public void actionPerformed(ActionEvent event) {
        int width = 0;
        int height = 0;

        try {
            width = Integer.parseInt(widthField.getText());
            height = Integer.parseInt(heightField.getText());
            world.changeDimension(width, height);       
            SaveWorldDimension(width, height);            
            this.dispose();
        } catch (NumberFormatException ex) {
            messageLabel.setText("Bitte geben Sie einen gueltigen Wert ein!");
        }

    }
    
    /**
     * Save the new world dimension into the property file. 
     * 
     * @param width the new width of this world
     * @param height the new width of this world
     */
    private void SaveWorldDimension(int width, int height) {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        properties.setProperty(PropertyName.WORLD_WIDTH, String.valueOf(width));
        properties.setProperty(PropertyName.WORLD_HEIGHT, String.valueOf(height));
        try {
			properties.storeProperties();
		} catch (IOException e) {
        	LOGGER.info("Could not store properties!");
		} 	
    }

}
