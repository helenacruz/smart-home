package tecnico.ai.smarthome.ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tecnico.ai.smarthome.communication.Message;
import tecnico.ai.smarthome.communication.SerialPortReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JPanel;

public class SmartHome {

	public JFrame frame;
	private JToggleButton toogleCeiling;
	private JToggleButton toogleTable;
	private JToggleButton toogleMode;
	private JLabel labelTemperature;
	private JLabel labelFire;
	private JSlider slider;
	
	private ArrayList<String> messages;
	
	public SmartHome(ArrayList<String> messages) {
		this.messages = messages;
		initialize();
	}
	
	/**
	 * Updates the UI styles and controls from attributes of a
	 * message received. 
	 * @param deviceId Identification of the type of device.
	 * @param property Type of action to be performed on the device.
	 * @param value Value of the state of the device.
	 */
	public void update(int deviceId, int property, int value) {
		switch (deviceId) {
		// Ceiling light
		case 1:
			// Auto/Manual controll
			if (property == 1) {
				if (value == 1) {
					toogleMode.setText("Auto");
					toogleMode.setSelected(true);
				} else {
					toogleMode.setText("Manual");
					toogleMode.setSelected(false);
				}
			}
			// ON/OFF controll
			else if (property == 2) {
				if (value == 1) {
					toogleCeiling.setText("ON");
					toogleCeiling.setSelected(true);
				} else {
					toogleCeiling.setText("OFF");
					toogleCeiling.setSelected(false);
					slider.setValue(0);
				}
			}
			// INTENSITY
			else if (property == 3) {
				slider.setValue(value);
			}
			break;
		// Table lamp
		case 2:
			// ON/OFF control
			if (property == 2) {
				if (value == 1) {
					toogleTable.setText("ON");
					toogleTable.setSelected(true);
				} else {
					toogleTable.setText("OFF");
					toogleTable.setSelected(false);
				}
			}
			break;
		// Temperature sensor
		case 3:
			labelTemperature.setText(value + " ºC");
			break;
		// Fire sensor
		case 4:
			if (value == 0)
				labelFire.setText("OFF");
			else if (value == 1)
				labelFire.setText("On fire!");
			break;
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(253, 253, 253));
		frame.setBounds(100, 100, 728, 392);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblSmarthome = new JLabel("SmartHome");
		lblSmarthome.setBackground(Color.WHITE);
		lblSmarthome.setForeground(new Color(0, 153, 255));
		lblSmarthome.setFont(new Font("Arial", Font.BOLD, 28));
		lblSmarthome.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		/*****************************
		 *       Ceiling Lamp
		 *****************************/
		JLabel lblCeilingLamp = new JLabel("Ceiling Lamp");
		lblCeilingLamp.setFont(new Font("Arial", Font.BOLD, 13));
		
		toogleCeiling = new JToggleButton("OFF");
		toogleCeiling.setFont(new Font("Arial", Font.PLAIN, 13));
		
		// ON/OFF ceiling toggle
		toogleCeiling.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Message message = new Message(1, 2, 1); // ID Ceiling lamp, State, ON
					messages.add(message.toArduinoMessage());
					toogleCeiling.setText("ON");
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Message message = new Message(1, 2, 0); // ID Ceiling lamp, State, OFF
					messages.add(message.toArduinoMessage());
					toogleCeiling.setText("OFF");
				}
			}
		});
		
		// Manual/Auto mode control
		toogleMode = new JToggleButton("Manual");
		toogleMode.setFont(new Font("Arial", Font.PLAIN, 13));
		
		toogleMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Message message = new Message(1, 1, 1); // Turn on auto
					messages.add(message.toArduinoMessage());
					toogleMode.setText("Auto");
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Message message = new Message(1, 1, 0); // Turn on manual
					messages.add(message.toArduinoMessage());
					toogleMode.setText("Manual");
				}
			}
		});
		
		// Brightness slider controll
		slider = new JSlider();
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(10);
		
		slider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent event) {
		        int value = slider.getValue();
		        if (!slider.getValueIsAdjusting()) {
		        	int roundedValue = (int) Math.round(((double)value / 10) * 10);
		        	Message message = new Message(1, 3, roundedValue);
		        	messages.add(message.toArduinoMessage());
		        }
		      }
		    });
		
		/****************************************
		 *      Table lamp configurations 
		 ****************************************/
		JLabel lblTableLamp = new JLabel("Table Lamp");
		lblTableLamp.setFont(new Font("Arial", Font.BOLD, 13));
		
		toogleTable = new JToggleButton("OFF");
		toogleTable.setFont(new Font("Arial", Font.PLAIN, 13));
		
		// Check if toogle is clicked
		toogleTable.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				if (ev.getStateChange() == ItemEvent.SELECTED) {
					Message message = new Message(2, 2, 1); // Turn on lamp
					messages.add(message.toArduinoMessage());
					toogleTable.setText("ON");
				} else if (ev.getStateChange() == ItemEvent.DESELECTED) {
					Message message = new Message(2, 2, 0); // Turn off lamp
					messages.add(message.toArduinoMessage());
					toogleTable.setText("OFF");
				}
			}
		});
		
		/****************************************
		 *      Temperature configurations 
		 ****************************************/
		JLabel lblTemperature = new JLabel("Temperature");
		lblTemperature.setFont(new Font("Arial", Font.BOLD, 13));
		
		labelTemperature = new JLabel("ºC");
		labelTemperature.setFont(new Font("Arial", Font.PLAIN, 13));
		
		
		/****************************************
		 *      Fire alarm configurations 
		 ****************************************/
		JLabel lblFireAlarm = new JLabel("Fire Alarm");
		lblFireAlarm.setFont(new Font("Arial", Font.BOLD, 13));
		
		labelFire = new JLabel("OFF");
		labelFire.setFont(new Font("Arial", Font.PLAIN, 13));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(56)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(0, 0, Short.MAX_VALUE)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCeilingLamp)
										.addComponent(toogleCeiling))
									.addGap(101)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(toogleTable)
										.addComponent(lblTableLamp))
									.addGap(92)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblTemperature)
											.addGap(78))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(labelTemperature)
											.addGap(98)))
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblFireAlarm)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(12)
											.addComponent(labelFire))))
								.addComponent(toogleMode)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(17)
							.addComponent(slider, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)))
					.addGap(100))
				.addComponent(lblSmarthome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblSmarthome, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCeilingLamp)
						.addComponent(lblTableLamp)
						.addComponent(lblTemperature)
						.addComponent(lblFireAlarm))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(labelFire)
						.addComponent(labelTemperature)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(toogleCeiling)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(toogleMode))
						.addComponent(toogleTable))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(43))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
}
