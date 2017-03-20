#define DEBUG 1

int tempPin = A5;
int lightPin = A3;

unsigned int minLight = 1023;
unsigned int maxLight = 0;

int photocellReading;

void setup()
{
  if (DEBUG) {
    Serial.begin(9600);
  }
}

void loop()
{
  readTemperature();
  delay(1000);
  readLight();
  delay(1000);
}

void readTemperature()
{
  int val = analogRead(tempPin);
  float mv = (val / 1024.0) * 5000; 
  float temperature = mv/10;

  if (DEBUG) {
    Serial.print("Temperature: ");
    Serial.print(temperature);
    Serial.println(" *C");
  }
}

void readLight() 
{
   photocellReading = analogRead(lightPin);

  if (DEBUG) {
    Serial.print("Light = ");
    Serial.println(photocellReading);
  }
}
