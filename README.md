FreshAir - IoT Environment Monitoring System
Niall Fitzgerald - C09525611 - Computer Systems Assignment 2

Project Overview
This project, FreshAir, is an IoT-based system for monitoring environmental conditions using a Raspberry Pi Sense HAT. It collects temperature, humidity, pressure, and calculates the dew point to determine the risk of condensation. The data is sent to ThingSpeak for storage and processing, and a web application is used to display the data in real-time.

Folder Structure

Fresh-Air WebApp: Contains the Play Framework-based web application for visualizing the sensor data.

Raspberry Pi - Python Script: Contains the thingspeak-data-http.py Python script used on the Raspberry Pi to collect and send sensor data to ThingSpeak.


Features

1. Raspberry Pi:

Collects temperature, humidity, and pressure data using the Sense HAT.

Calculates dew point and determines condensation risk.

Sends all data to ThingSpeak every 20 seconds.



2. ThingSpeak:

Stores and provides an API for retrieving the collected data.

Configured fields:

Field 1: Temperature

Field 2: Humidity

Field 3: Pressure

Field 4: Dew Point

Field 5: Condensation Risk (1 = Yes, 0 = No).




3. Web Application:

Fetches data from ThingSpeak using its API.

Displays the data in a user-friendly dashboard:

Temperature (°C)

Humidity (%)

Pressure (hPa)

Dew Point (°C)

Condensation Risk (Yes/No)

Last Updated timestamp.





Setup Instructions

1. Raspberry Pi - Python Script
Requirements:



Raspberry Pi with Sense HAT.

Python installed.

Libraries: sense-hat, requests, math.


Steps:

Raspberry Pi:

1. Navigate to the Raspberry Pi - Python Script folder:
cd "Raspberry Pi - Python Script"


2. Install the required libraries:
pip install sense-hat requests


3. Run the script to start collecting and sending data:
python3 thingspeak-data-http.py




4. Web Application
Ensure Java 8 and Play Framework 1.6.0 are installed.

Navigate to the Fresh-Air WebApp folder and start the server:
play run

Open the app in your browser at:
http://localhost:9000

