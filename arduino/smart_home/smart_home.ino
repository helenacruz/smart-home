#include <SimpleTimer.h>
#include <Bounce2.h>
#include <Arduino.h>
#include "smart_home.h"

/* extern variables */

extern const struct led blue_led_t;
extern const struct led white_led_t;
extern const struct flame flame_t;
extern const struct sensor temp_sensor_t;
extern const struct button blue_button_t;
extern const struct button white_button_t;
extern const struct button mode_button_t;
extern boolean new_data;
extern char out_message[];

/* button debounce */

Bounce blue_button_db;
Bounce white_button_db;
Bounce mode_button_db;

bool blue_button_pressed;
bool white_button_pressed;
bool mode_button_pressed;

/* state variables */

volatile byte device_state;
volatile bool fire;
volatile bool auto_mode;
volatile bool white_led_on;
volatile bool blue_led_on;
int blue_led_value;
int white_led_value;
bool buzzer_on;
unsigned long buzzer_millis;
int pot_value;
int temp_value;
int light_value; // 0 - 100%
bool pc_mode;

SimpleTimer temp_timer;
SimpleTimer light_timer;

void setup()
{
  if (DEBUG || COMM) {
    Serial.begin(9600);
    while (!Serial) {
      // wait for native port to connect
    }
  }
  
  pinMode(temp_pin, INPUT);
  pinMode(light_pin, INPUT);
  pinMode(flame_pin, INPUT);
  pinMode(white_button, INPUT);
  pinMode(blue_button, INPUT);
  pinMode(blue_led, OUTPUT);
  pinMode(white_led, OUTPUT);
  pinMode(buzzer, OUTPUT);

  for (int i = 0; i < NR_DEVICES; i++) {
    bitClear(device_state, i);
  }

  fire = false;
  auto_mode = false;
  buzzer_on = false;
  white_led_on = false;
  white_led_value = 0;
  blue_led_on = false;
  blue_led_value = 0;
  bool pc_mode;
  buzzer_millis = millis();

  pot_value = 0;
  temp_value = 0;
  light_value = 0;

  blue_button_pressed = false;
  white_button_pressed = false;
  mode_button_pressed = false;

  blue_button_db.attach(blue_button);
  blue_button_db.interval(20);

  white_button_db.attach(white_button);
  white_button_db.interval(20);

  mode_button_db.attach(mode_button);
  mode_button_db.interval(20);

  temp_timer.setInterval(5000, read_temperature);
  light_timer.setInterval(1000, read_light);
}

void loop()
{
  temp_timer.run();
  light_timer.run();
  read_flame();
  read_white_pot();
  if (fire) {
    play_buzzer();
  }
  check_status();
  check_buttons();
  receive_message();
 
  if (new_data) {
    show_new_message();
    parse_message();
    new_data = false;
  }
}

void read_temperature()
{
  int val = analogRead(temp_pin);
  float mv = (val / 1024.0) * 5000; 
  int temperature = mv/10;
  
  if (temp_value != temperature) {
    temp_value = temperature;
    bitSet(device_state, temp_id);
    if (DEBUG) {
      Serial.print("Temperature: ");
      Serial.print(temp_value);
      Serial.println(" *C");
    }
  }
}

void read_light() 
{
  int light = analogRead(light_pin) / 10.23;

  if (light < 5) {
    light = 0;
  }
  else if (light >= 5 && light < 15) {
    light = 10;
  }
  else if (light >= 15 && light < 25) {
    light = 20;
  }
  else if (light >= 25 && light < 35) {
    light = 30;
  }
  else if (light >= 35 && light < 45) {
    light = 40;
  }
  else if (light >= 45 && light < 55) {
    light = 50;
  }
  else if (light >= 55 && light < 65) {
    light = 60;
  }
  else if (light >= 65 && light < 75) {
    light = 70;
  }
  else if (light >= 75 && light < 85) {
    light = 80;
  }
  else if (light >= 85 && light < 95) {
    light = 90;
  }
  else if (light >= 95) {
    light = 100;
  }

  if (light != light_value) {
    light_value = light;
    if (DEBUG) {
      Serial.print("Light = ");
      Serial.println(light);
    }
  }

  int value = 1023 - (light_value * 10.23);

  if (auto_mode && white_led_on) {
    analogWrite(white_led, value);
    if (value != white_led_value) {
      bitSet(device_state, white_led_id);
      white_led_value = value;
    }
  }
}

void read_white_pot() 
{
  if (auto_mode || !white_led_on) {
    return;
  }

  int reading = analogRead(white_pot);
  int value = map(reading, 0, 1023, 0, 100);

  if (value < 5) {
    value = 0;
  }
  else if (value >= 5 && value < 15) {
    value = 10;
  }
  else if (value >= 15 && value < 25) {
    value = 20;
  }
  else if (value >= 25 && value < 35) {
    value = 30;
  }
  else if (value >= 35 && value < 45) {
    value = 40;
  }
  else if (value >= 45 && value < 55) {
    value = 50;
  }
  else if (value >= 55 && value < 65) {
    value = 60;
  }
  else if (value >= 65 && value < 75) {
    value = 70;
  }
  else if (value >= 75 && value < 85) {
    value = 80;
  }
  else if (value >= 85 && value < 95) {
    value = 90;
  }
  else if (value >= 95) {
    value = 100;
  }
    
  if (value != pot_value) {
    analogWrite(white_led, map(value, 0, 100, 0, 255));
    bitSet(device_state, white_led_id);
    pot_value = value;
    white_led_value = value;
  }
}

int read_flame()
{
  int flame = digitalRead(flame_pin);
  int status;

  if (fire == flame) {
    bitSet(device_state, flame_id);
  }

  if (flame == LOW) {
    if (DEBUG) {
      Serial.println("Fire");
    }
    fire = true;
    return ON_FIRE;
  }
  else {
    fire = false;
    noTone(buzzer);
    return NOT_ON_FIRE;
  }
}

void check_buttons()
{
  blue_button_db.update();
  if (blue_button_db.rose()) {
    do_blue_button();
  }
  white_button_db.update();
  if (white_button_db.rose()) {
    do_white_button();
  }
  mode_button_db.update();
  if (mode_button_db.rose()) {
    do_mode_button();
  }
}

void do_blue_button()
{
  bitSet(device_state, blue_button_id);
  digitalWrite(blue_led, !digitalRead(blue_led));
  blue_led_on = !blue_led_on;
  if (DEBUG) {
    Serial.println("white button");
  }
}

void do_white_button()
{
  bitSet(device_state, white_button_id);
  digitalWrite(white_led, !digitalRead(white_led));
  white_led_on = !white_led_on;
  if (DEBUG) {
    Serial.println("white button");
  }
}

void do_mode_button()
{
  auto_mode = !auto_mode;
  bitSet(device_state, mode_button_id);
  if (DEBUG) {
    Serial.println("Mode button");
  }
}

void play_buzzer()
{
  unsigned long current_millis = millis();

  if (current_millis - buzzer_millis >= 1000) {
    buzzer_millis = current_millis;
    if (buzzer_on == true) {
      noTone(buzzer);
      buzzer_on = false;
      if (DEBUG) {
        Serial.println("Buzzer off");
      }
    }
    else {
      tone(buzzer, 1000);
      buzzer_on = true;
      if (DEBUG) {
        Serial.println("Buzzer on");
      }
    }
  }
}

/*
 * IDs
 * flame sensor: 0
 * white led button: 1
 * blue led button: 2
 * mode button: 3
 * potentiometer: 4
 * temperature sensor: 5
 * light sensor: 6
 * blue led: 7
 * white led: 8
 */

void check_status()
{
  if (DEBUG) {
    // Serial.println("Checking status");
  }

  if (bitRead(device_state, flame_id)) {
    if (DEBUG) {
      Serial.println("Flame bit on");
    }
    
    make_flame_msg(flame_t.on_fire, read_flame());
    //mySerial.println(out_message);
    bitClear(device_state, flame_id);
  }
  // white led has changed its status
  else if (bitRead(device_state, white_button_id)) {
    if (DEBUG) {
      Serial.println("White button bit on");
    }
    
    bitClear(device_state, white_button_id);
    if (white_led_on) {
      make_led_msg(white_led_id, white_led_t.status, ON);
      //mySerial.println(out_message);
    }
    else {
      make_led_msg(white_led_id, white_led_t.status, OFF);
      //mySerial.println(out_message);
    }
  }
  // blue led has changed its status
  else if (bitRead(device_state, blue_button_id)) {
    if (DEBUG) {
      Serial.println("Blue button bit on");
    }
    
    bitClear(device_state, blue_button_id);
    if (blue_led_on) {
      make_led_msg(blue_led_id, blue_led_t.status, ON);
      //mySerial.println(out_message);
    }
    else {
      make_led_msg(blue_led_id, blue_led_t.status, OFF);
      //mySerial.println(out_message);
    }
  }
  else if (bitRead(device_state, mode_button_id)) {
    if (DEBUG) {
      Serial.println("Mode button bit on");
    }
    
    bitClear(device_state, mode_button_id);
    if (auto_mode) {
      make_led_msg(white_led_id, white_led_t.mode, AUTO);
      //mySerial.println(out_message);
    }
    else {
      make_led_msg(white_led_id, white_led_t.mode, MANUAL);
      //mySerial.println(out_message);
    }
  }
  else if (bitRead(device_state, blue_led_id)) {
    if (DEBUG) {
      Serial.println("Blue led bit on");
    }
    
    bitClear(device_state, blue_led_id);
    make_led_msg(blue_led_id, blue_led_t.intensity, blue_led_value);
    //mySerial.println(out_message);
  }
  else if (bitRead(device_state, temp_id)) {
    if (DEBUG) {
      Serial.println("Temp bit on");
    }
    
    bitClear(device_state, temp_id);
    make_sensor_msg(temp_id, temp_sensor_t.value, temp_value);
    //mySerial.println(out_message);
  }
  else if (bitRead(device_state, blue_led_id)) {
    if (DEBUG) {
      Serial.println("Blue led bit on");
    }
    
    bitClear(device_state, blue_led_id);
    make_led_msg(blue_led_id, blue_led_t.intensity, blue_led_value);
    //mySerial.println(out_message);
  }
  else if (bitRead(device_state, white_led_id)) {
    if (DEBUG) {
      Serial.println("White led bit on");
    }
    
    bitClear(device_state, white_led_id);
    make_led_msg(white_led_id, white_led_t.intensity, white_led_value);
    //mySerial.println(out_message);
  }
}

void do_something(int device, int property, int value) 
{
  if (device == blue_led_id) {
    if (property == 1) { // state
      if (value == 1) {
        if (!blue_led_on) {
          do_blue_button();
        }
        else {
          // already on
        }
      }
      else if (value == 0) {
        if (blue_led_on) {
          do_blue_button();
        }
        else {
          // already off
        }
      }
    }
    // no more options for blue led
  }
  else if (device == white_led_id) {
    if (property == 1) { // state
      if (value == 1) {
        if (!white_led_on) {
          do_white_button();
        }
        else {
          // already on
        }
      }
      else if (value == 0) {
        if (white_led_on) {
          do_white_button();
        }
        else {
          // already off
        }
      }
    }
    else if (property == 2) { // intensity
      if (!auto_mode && white_led_on) {
        pc_mode = true;
        if (pot_value != value) {
          int intensity = map(value, 0, 100, 0, 255);
          white_led_value = intensity;
          analogWrite(white_led, intensity);
          if (value == 0) {
            digitalWrite(white_led, LOW);
          }
        }
      }
    }
    else if (property == 3) { // mode
      if (value == AUTO) {
        auto_mode = true;
      }
      else {
        auto_mode = false;
      }
    }
  }
  else {
    if (DEBUG) {
      Serial.print("Device: ");
      Serial.println(device);
    }
  }
}

