# Motion Detection and Bluetooth Control Lights using Arduino
Contains Arduino Code and Android Application for Controlling Lights

## Requirements
* Windows 10 or Above
* Arduino IDE
* Android Studio
* Android Mobile (Android Version 12)

## Requirements for Arduino Setup
* Arduino Uno R3
* PIR Sensor
* HC-05 (Bluetooth Module)
* Relay Module
* Breadboard
* Jumper Wires

## Arduino Setup
<img src="Arduino%20IDE/Arduino%20Setup.png" width="600px">

Connect your Arduino Setup as per above image

## Steps for Arduino IDE Project
* Open Arduino IDE and paste the code from arduino.txt or open arduino.ino file.
* Then Verify and Import code to your Arduino.

(If code is not importing to your Arduino then remove RX and TX jumper wires and try again.)

* When PIR Sensor detects any motion then the light will turn on for 5 seconds.

(You can increase time from arduino code. Just change "delay(5000)" to any seconds you want.) [1s=1000]

## Sequence Diagram for Motion Detection
<img src="Arduino%20IDE/Motion.png" width="500px">

## Steps for Android Studio Project
* Download 'ArduinoLightsController' folder into your PC.
* Open 'ArduinoLightsController' project from Android Studio.
* Connect your Android Device to PC and run the project.
* You will have application installed into your device.

## Sequence Diagram for Android Application
<img src="Arduino%20IDE/app.png" width="800px">
