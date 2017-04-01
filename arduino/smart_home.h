#ifndef SMART_HOME_H
#define SMART_HOME_H

#include "Arduino.h"

#define DEBUG 1
#define NR_DEVICES 6
#define ARDUINO_ID 1

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

const int flame_id = 0;
const int white_button_id = 1;
const int blue_button_id = 2;
const int mode_button_id = 3;
const int white_pot_id = 4;
const int temp_id = 5;
const int blue_led_id = 6;
const int white_led_id = 7;

/* prototypes */

void read_temperature();
void read_light();
void read_white_pot();
void read_flame();
void play_buzzer();
void flame_isr();
void blue_button_isr();
void white_button_isr();
void mode_button_isr();
void white_pot_isr();
void check_messages();

#endif
