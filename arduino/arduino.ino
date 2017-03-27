#include <EnableInterrupt.h>

#define DEBUG 1
#define READINGS 99

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

/* sensors/actuators IDs */

const int flame_id = 0;
const int white_button_id = 1;
const int blue_button_id = 2;
const int mode_button_id = 3;

#define NR_DEVICES 4

/* state variables */

volatile int device_state;
volatile bool fire;
bool buzzer_on;
unsigned long buzzer_millis;

/* to stabilize the readings */

int temp_readings;
float temp_avg;

int light_readings;
int light_avg;

int flame_readings;
int flame_avg;

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

  fire = false;
  buzzer_on = false;
  buzzer_millis = millis();

  temp_readings = 0;
  temp_avg = 0;

  light_readings = 0;
  temp_avg = 0;

  flame_readings = 0;
  flame_avg = 0;
}

void loop()
{
  read_temperature();
  read_light();
  read_flame();
  if (fire) {
    play_buzzer();
  }
}

void read_temperature()
{
  int val = analogRead(temp_pin);
  float mv = (val / 1024.0) * 5000; 
  float temperature = mv/10;

  if (temp_readings < READINGS) {
    temp_avg = (temp_avg * temp_readings + temperature) / ++temp_readings;
  }
  else {
    temp_readings = 0;
    if (DEBUG) {
      Serial.print("Temperature: ");
      Serial.print(temp_avg);
      Serial.println(" *C");
    }
  }
}

void read_light() 
{
  int light = analogRead(light_pin);

  if (light_readings < READINGS) {
    light_avg = (light_avg * light_readings + light) / ++light_readings;
  }
  else {
    light_readings = 0;
    if (DEBUG) {
      Serial.print("Light = ");
      Serial.println(light);
    }
  }
}

void read_flame()
{
  int flame = digitalRead(flame_pin);

  if (flame_readings < READINGS) {  
    if (flame == LOW) {
      flame_avg++;
    }
    else {
      flame_avg--;
    }
    flame_readings++;
  }
  else {
    if (flame_avg >= 60) {
      if (DEBUG) {
        Serial.println("FIRE");
      }
      fire = true;
    }
    else {
      if (DEBUG) {
        Serial.println("No fire");
      }
      fire = false;
      noTone(buzzer);
    }
    flame_readings = 0;
    flame_avg = 0;
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
    if (DEBUG) {
      Serial.println("Mode isr");
    }
  }
  
  last_interrupt_time = interrupt_time;
}

