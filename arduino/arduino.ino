#define DEBUG 1
#define READINGS 99

const int temp_pin = A0;
const int light_pin = A4;
const int flame_pin = 12;

const int yellow_button = 2;
const int blue_button = 3;

const int blue_led = 7;
const int yellow_led = 5;

const int buzzer = 11;

bool fire;
bool buzzer_on;

unsigned long buzzer_millis;

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
  pinMode(yellow_button, INPUT);
  pinMode(blue_button, INPUT);
  pinMode(blue_led, OUTPUT);
  pinMode(yellow_led, OUTPUT);
  pinMode(buzzer, OUTPUT);

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

