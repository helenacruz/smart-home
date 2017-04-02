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
  out_message[0] = value;
  out_message[1] = prop_id;
  bitWrite(out_message[2], 0, 0);
  bitWrite(out_message[2], 1, 1);
  bitWrite(out_message[2], 2, 0);
  bitWrite(out_message[2], 3, 0); // not an answer
  bitWrite(out_message[2], 4, 0); // no error
  out_message[3] = led;
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

void make_flame_msg(int prop_id, int value)
{
  out_message[0] = value;
  out_message[1] = prop_id;
  bitWrite(out_message[2], 0, 0); // notify
  bitWrite(out_message[2], 1, 1); // ^
  bitWrite(out_message[2], 2, 0); // ^
  bitWrite(out_message[2], 3, 0); // not an answer
  bitWrite(out_message[2], 4, 0); // no error
  out_message[3] = flame_id;
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

void make_sensor_msg(int sensor, int prop_id, int value)
{
  out_message[0] = value;
  out_message[1] = prop_id;
  bitWrite(out_message[2], 0, 0); // notify
  bitWrite(out_message[2], 1, 1); // ^
  bitWrite(out_message[2], 2, 0); // ^
  bitWrite(out_message[2], 3, 0); // not an answer
  bitWrite(out_message[2], 4, 0); // no error
  out_message[3] = sensor;
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

void make_button_msg(int button, int prop_id, int value)
{
  out_message[0] = value;
  out_message[1] = prop_id;
  bitWrite(out_message[2], 0, 0); // notify
  bitWrite(out_message[2], 1, 1); // ^
  bitWrite(out_message[2], 2, 0); // ^
  bitWrite(out_message[2], 3, 0); // not an answer
  bitWrite(out_message[2], 4, 0); // no error
  out_message[3] = button; 
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

void send_answer()
{
  for (int i = 0; i < MSG_SIZE; i++) {
    out_message[i] = in_message[i];
  }
  bitWrite(out_message[2], 3, 0); // answer bit
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

void send_error()
{
  for (int i = 0; i < MSG_SIZE; i++) {
    out_message[i] = in_message[i];
  }
  bitWrite(out_message[2], 4, 0); // error bit
  if (DEBUG_COMM) {
    print_message(out_message);
    sending = false;
  }
}

// TODO: analysis of input messages

/* aux functions */

void print_message(byte *data) {
  if (DEBUG_COMM) { /* double check, better safe than sorry */
    Serial.print("ID: ");
    Serial.print(data[3]); // device id
    Serial.print(" E: ");
    Serial.print(bitRead(data[2], 4)); // error bit
    Serial.print(" A: ");
    Serial.print(bitRead(data[2], 3)); // answer bit
    if (bitRead(data[2], 0) == 0 && bitRead(data[2], 1) == 1 &&
        bitRead(data[2], 2) == 0) {
      Serial.print(" NOTIFY");      
    }
    else if (bitRead(data[2], 0) == 0 && bitRead(data[2], 1) == 0 &&
             bitRead(data[2], 2) == 0) {
      Serial.print(" GET");
    }
    else if (bitRead(data[2], 0) == 0 && bitRead(data[2], 1) == 0 &&
             bitRead(data[2], 2) == 1) {
      Serial.print(" SET");
    }
    Serial.print(" Prop: ");
    Serial.print(data[1]);
    Serial.print(" Value: ");
    Serial.println(data[0]);
  }
}
