#include <Arduino.h>
#include <EnableInterrupt.h>
#include "smart_home.h"
#include "devices.h"

/* extern variables */

extern bool sending;
extern bool receiving;

/* state variables */

volatile int device_state;
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

void setup()
{
  if (DEBUG) {
    Serial.begin(9600);
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

  enableInterrupt(mode_button, mode_button_isr, RISING);
  enableInterrupt(white_button, white_button_isr, RISING);
  enableInterrupt(blue_button, blue_button_isr, RISING);
  enableInterrupt(flame_pin, flame_isr, RISING);

  fire = false;
  auto_mode = false;
  buzzer_on = false;
  white_led_on = false;
  white_led_value = 0;
  blue_led_on = false;
  blue_led_value = 0;
  buzzer_millis = millis();

  pot_value = 0;
  temp_value = 0;
  light_value = 0;
}

void loop()
{
  read_temperature();
  read_light();
  read_flame();
  read_white_pot();
  if (fire) {
    play_buzzer();
  }
  check_status();
  check_messages();
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
  if (blue_led_on) {
    analogWrite(blue_led, value);
    if (value != blue_led_value) {
      bitSet(device_state, blue_led_id);
      blue_led_value = value;
    }
  }
}

void read_white_pot() 
{
  if (!auto_mode && white_led_on) {
    int reading = analogRead(white_pot);
    if (reading != pot_value) {
      analogWrite(white_led, reading / 4);
      pot_value = reading;
    }
  }
}

void read_flame()
{
  int flame = digitalRead(flame_pin);

  if (!fire == flame) {
    bitSet(device_state, flame_id);
  }

  if (flame == LOW) {
    if (DEBUG) {
      Serial.println("Fire");
    }
    fire = true;
  }
  else {
    fire = false;
    noTone(buzzer);
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

/* interrupt routines */

void flame_isr()
{
  if (DEBUG) {
    Serial.println("Flame isr");
  }
  bitSet(device_state, flame_id);
}

void blue_button_isr()
{
  static unsigned long last_interrupt_time = 0;
  unsigned long interrupt_time = millis();
  
  if (interrupt_time - last_interrupt_time > 200) {
    bitSet(device_state, blue_button_id);
    digitalWrite(blue_led, !digitalRead(blue_led));
    blue_led_on = !blue_led_on;
    if (DEBUG) {
      Serial.println("Blue isr");
    }
  }
  
  last_interrupt_time = interrupt_time;
}

void white_button_isr()
{
  static unsigned long last_interrupt_time = 0;
  unsigned long interrupt_time = millis();
  
  if (interrupt_time - last_interrupt_time > 200) {
    bitSet(device_state, white_button_id);
    digitalWrite(white_led, !digitalRead(white_led));
    white_led_on = !white_led_on;
    if (DEBUG) {
      Serial.println("White isr");
    }
  }
  
  last_interrupt_time = interrupt_time;
}

void mode_button_isr()
{
  static unsigned long last_interrupt_time = 0;
  unsigned long interrupt_time = millis();
  
  if (interrupt_time - last_interrupt_time > 200) {
    bitSet(device_state, mode_button_id);
    auto_mode = !auto_mode;
    if (DEBUG) {
      Serial.println("Mode isr");
    }
  }
  
  last_interrupt_time = interrupt_time;
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
  if (sending) {
    return;
  }
  /*if (BitRead(device_state, flame_id)) {
    sending = true;
    make_flame_msg(
  }*/
}

void check_messages()
{
  
}

