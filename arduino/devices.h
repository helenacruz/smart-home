#ifndef DEVICES_H
#define DEVICES_H

#include "smart_home.h"

struct led
{
  int id;
  int status; 
  int intensity;
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

struct led blue_led_t = { blue_led_id, 1, 2 };
struct led white_led_t = { white_led_id, 1, 1 };
struct flame flame_t = { flame_id, 1 };
struct sensor temp_sensor_t = { temp_id, 1 };
struct button blue_button_t = { blue_button_id, 1 };
struct button white_button_t = { white_button_id, 1 };

#endif
