#define ledPin 13                                   // LED pin
#define sensorPin 2                                 // Sensor pin

void setup() {
  Serial.begin(9600);                               // initialize serial communication at 9600 bits per second
  pinMode(ledPin, OUTPUT);                          // Declared LED as output
  pinMode(sensorPin, INPUT);                        // Declared sensor as input
}

void loop() {
  int sensor = digitalRead(sensorPin);
  if(sensor == 1){				   // Detect the motion
    digitalWrite(ledPin, HIGH);                    // If motion detected LED will turn ON
    delay(5000);                                   // LED will turn ON for 5 seconds
    digitalWrite(ledPin, LOW);                     // LED will turn OFF
  }
  else if(Serial.available() > 0){                 // msg received from app
    char received_msg = Serial.read();
    if(received_msg == '0'){
      digitalWrite(ledPin, LOW);
    }
    else if(received_msg == '1'){
      digitalWrite(ledPin, HIGH);
    }
    else if(received_msg == '2'){
      digitalWrite(ledPin, HIGH);
      delay(10000);
      digitalWrite(ledPin, LOW);
    }
    else if(received_msg == '3'){
      digitalWrite(ledPin, HIGH);
      delay(20000);
      digitalWrite(ledPin, LOW);
    }
    else if(received_msg == '4'){
      digitalWrite(ledPin, HIGH);
      delay(30000);
      digitalWrite(ledPin, LOW);
    }
  }
}