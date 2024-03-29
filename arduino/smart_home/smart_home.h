#ifndef SMART_HOME_H
#define SMART_HOME_H

#include <Arduino.h>
#include <Bounce2.h>

#define DEBUG 0
#define COMM 1
#define NR_DEVICES 6

/* sensors/actuators pins */

const int temp_pin = A0;
const int light_pin = A4;
const int flame_pin = 12;
const int white_button = 3;
const int blue_button = 4;
const int mode_button = 2;
const int blue_led = 7;
const int white_led = 5;
const int buzzer = 11;
const int white_pot = A1;

/* sensors/actuators IDs */

const int flame_id = 4;
const int white_button_id = 6;
const int blue_button_id = 7;
const int mode_button_id = 0;
const int white_pot_id = 5;
const int temp_id = 3;
const int blue_led_id = 2;
const int white_led_id = 1;

/* property values */

#define ON 1
#define OFF 0
#define ON_FIRE 1
#define NOT_ON_FIRE 0
#define AUTO 1
#define MANUAL 0

/* structs for linking device <-> property */

struct led
{
  int id;
  int status; 
  int intensity;
  int mode;
};

struct flame
{
  int id;
  int on_fire;
};

struct sensor
{
  int id;
  int value;
};

struct button
{
  int id;
  int status;
};

/* device structs */

const struct led blue_led_t = { blue_led_id, 2, 3, 1 };
const struct led white_led_t = { white_led_id, 2, 3, 1 };
const struct flame flame_t = { flame_id, 1 };
const struct sensor temp_sensor_t = { temp_id, 1 };
const struct button blue_button_t = { blue_button_id, 1 };
const struct button white_button_t = { white_button_id, 1 };
const struct button mode_button_t = { mode_button_id, 1 };

/* prototypes */

void read_temperature();
void read_light();
void read_white_pot();
int read_flame();
void play_buzzer();

void make_led_msg(int led, int prop_id, int value);
void make_flame_msg(int prop_id, int value);
void make_sensor_msg(int sensor, int prop_id, int value);
void make_button_msg(int button, int prop_id, int value);
void send_answer();
void send_error();
void receive_message();
void show_new_message();
void parse_message();
void do_something(int device, int property, int value);

void print_message(byte *data);

#endif
