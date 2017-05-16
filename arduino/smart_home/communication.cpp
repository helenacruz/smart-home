#include "smart_home.h"
#include "communication.h"

/* all arduino output messages are NOTIFY */

/*
 * Message format:
 * CDevOrig - device id
 * E - error bit
 * A - answer bit
 * OpCode - operation code (000 - get, 001 - set, 010 - notify)
 * PropID - property ID
 * Value - value changed
 */

void make_led_msg(int led, int prop_id, int value)
{
  String message = "<";
  int len = String(value).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  message += String(value);

  len = String(prop_id).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(prop_id);
  message += '0';
  message += '0';
  message += '1';
  message += '0';
  message += '0';

  len = String(led).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(led);
  message += '>';

  Serial.println(message);
}

void make_flame_msg(int prop_id, int value)
{
  String message = "<";
  
  int len = String(value).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(value);

  len = String(prop_id).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(prop_id);
  message += "0";
  message += '0';
  message += '1';
  message += '0';
  message += '0';

  len = String(flame_id).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(flame_id);
  message += '>';

  Serial.println(message);
}

void make_sensor_msg(int sensor, int prop_id, int value)
{
  String message = "<";

  int len = String(value).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(value);

  len = String(prop_id).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(prop_id);
  message += '0';
  message += '0';
  message += '1';
  message += '0';
  message += '0';

  len = String(sensor).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(sensor);
  message += '>';

  Serial.println(message);
}

void make_button_msg(int button, int prop_id, int value)
{  
  String message = "<";

  int len = String(value).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(value);

  len = String(prop_id).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(prop_id);  
  message += '0';
  message += '0';
  message += '1';
  message += '0';
  message += '0';

  len = String(button).length();
  if (len == 1) {
    message += '0';
    message += '0';
  }
  if (len == 2) {
    message += '0';
  }
  
  message += String(button);
  message += '>';

  Serial.println(message);
}

void send_answer()
{
  for (int i = 0; i < MSG_SIZE; i++) {
//    out_message[i] = in_message[i];
  }
//  bitWrite(out_message[3], 3, 0); // answer bit
  if (COMM) {
    //print_message(out_message);
  }
}

void send_error()
{
  for (int i = 0; i < MSG_SIZE; i++) {
//    out_message[i] = in_message[i];
  }
//  bitWrite(out_message[3], 4, 0); // error bit
  if (COMM) {
    //print_message(out_message);
  }
}

// TODO: analysis of input messages

boolean new_data = false;

void receive_message() {
  static String message = "";
  static boolean receiving = false;
  char start = '<';
  char end = '>';
  char rc;
 
  while (Serial.available() > 0 && new_data == false) {
    rc = Serial.read();

    if (receiving == true) {
      if (rc != end) {
        message += rc;  
      }
      else {
        receiving = false;
        new_data = true;
        message += '>';
        in_message = message;
        message = "";
      }
    }

    else if (rc == start) {
      receiving = true;
      message += rc;
    }
  }
}

void show_new_message() 
{
  Serial.println(in_message);
}

void parse_message() 
{
  int device;
  int property;
  int value;

  if (in_message.length() != 16) {
    if (DEBUG) {
      Serial.print("Wrong size: ");
      Serial.println(in_message);
    }
    return;
  }

  String device_text = in_message.substring(12, 15);
  device = device_text.toInt();
  if (DEBUG) {
    Serial.print("Device: ");
    Serial.print(device_text);
    Serial.print(" int: ");
    Serial.println(device);
  }

  String property_text = in_message.substring(4, 7);
  property = property_text.toInt();
  if (DEBUG) {
    Serial.print("Property: ");
    Serial.print(property_text);
    Serial.print(" int: ");
    Serial.println(property);
  }

  String value_text = in_message.substring(1, 4);
  value = value_text.toInt();
  if (DEBUG) {
    Serial.print("Value: ");
    Serial.print(value_text);
    Serial.print(" int: ");
    Serial.println(value);
  }

  do_something(device, property, value);
}

