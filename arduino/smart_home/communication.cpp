#include "smart_home.h"

#define MSG_SIZE 4

bool sending = false;
bool receiving = false;

int in_message[MSG_SIZE];
int out_message[MSG_SIZE];

/* device structs */

struct led blue_led_t = { blue_led_id, 1, 2 };
struct led white_led_t = { white_led_id, 1, 1 };
struct flame flame_t = { flame_id, 1 };
struct sensor temp_sensor_t = { temp_id, 1 };
struct button blue_button_t = { blue_button_id, 1 };
struct button white_button_t = { white_button_id, 1 };

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
}

void send_answer()
{
  for (int i = 0; i < MSG_SIZE; i++) {
    out_message[i] = in_message[i];
  }
  bitWrite(out_message[2], 3, 0); // answer bit
}

void send_error()
{
  for (int i = 0; i < MSG_SIZE; i++) {
    out_message[i] = in_message[i];
  }
  bitWrite(out_message[2], 4, 0); // error bit
}

// TODO: analysis of input messages
