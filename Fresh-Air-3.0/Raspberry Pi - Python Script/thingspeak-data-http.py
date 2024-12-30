from sense_hat import SenseHat
 3 import requests
 4 import math
 5 import time
 6
 7 # Initialize the Sense HAT to read sensor data.
 8 sense = SenseHat()
 9
10 # ThingSpeak API details for sending data.
11 THINGSPEAK_WRITE_API_KEY = "YIXRO5RYXJQA9BA1"  # Replace with your API key.
12 THINGSPEAK_CHANNEL_URL = "https://api.thingspeak.com/update"  # URL to update data on ThingSpeak.
13
14 def calculate_dew_point(temp_celsius, humidity):
15     """
16     Calculate the dew point in Celsius based on temperature and humidity using the Magnus formula.
17     """
18     a = 17.27  # Constant 'a' for the Magnus formula.
19     b = 237.7  # Constant 'b' for the Magnus formula.
20     # Calculates the intermediate value 'alpha' for dew point computation.
21     alpha = ((a * temp_celsius) / (b + temp_celsius)) + math.log(humidity / 100.0)
22     # Computes the dew point using 'alpha' and returns it.
23     dew_point = (b * alpha) / (a - alpha)
24     return dew_point
25
26 def post_to_thingspeak(temp, humidity, pressure, dew_point, condensation_risk):
27     """
28     Send sensor data (temperature, humidity, pressure, dew point, and condensation risk) to ThingSpeak.
29     """
30     # Prepares the data payload with sensor readings and condensation risk.
31     payload = {
32         "api_key": THINGSPEAK_WRITE_API_KEY,   # API key for authentication.
33         "field1": temp,                       # Field 1: Temperature (°C).
34         "field2": humidity,                   # Field 2: Humidity (%).
35         "field3": pressure,                   # Field 3: Pressure (hPa).
36         "field4": dew_point,                  # Field 4: Dew point (°C).
37         "field5": int(condensation_risk)      # Field 5: Condensation risk (1 = Yes, 0 = No).
38     }
39
40     # Sends the data to the ThingSpeak channel using an HTTP POST request.
41     response = requests.post(THINGSPEAK_CHANNEL_URL, params=payload)
42     if response.status_code == 200:
43         # Prints success message if data was sent successfully.
44         print("Data sent to ThingSpeak successfully.")
45     else:
46         # Prints error message if the request failed, including the HTTP status code.
47         print(f"Failed to send data to ThingSpeak. Status code: {response.status_code}.")
48
49 # Main loop to continuously read and process sensor data.
50 while True:
51     # Reads the current temperature from the Sense HAT in Celsius.
52     temp = sense.get_temperature()
53     # Reads the current relative humidity from the Sense HAT in percentage.
54     humidity = sense.get_humidity()
55     # Reads the current atmospheric pressure from the Sense HAT in hectopascals (hPa).
56     pressure = sense.get_pressure()
57
58     # Calculates the dew point using the temperature and humidity values.
59     dew_point = calculate_dew_point(temp, humidity)
60
61     # Determines if there is a condensation risk (dew point is greater than or equal to the temperature).
62     condensation_risk = dew_point >= temp
63
64     # Displays a message on the Sense HAT LED matrix based on condensation risk.
65     if condensation_risk:
66         # Displays "Condensation Risk!" in red if there's a risk.
67         sense.show_message("Condensation Risk!", text_colour=[255, 0, 0])
68     else:
69         # Displays "No Risk" in green if there's no risk.
70         sense.show_message("No Risk", text_colour=[0, 255, 0])
71
72     # Optionally prints all sensor readings and calculations to the terminal for debugging or monitoring.
73     print(f"Temp: {temp:.2f}°C, Humidity: {humidity:.2f}%, Pressure: {pressure:.2f} hPa")
74     print(f"Dew Point: {dew_point:.2f}°C, Condensation Risk: {'Yes' if condensation_risk else 'No'}")
75
76     # Sends the collected data to ThingSpeak for remote monitoring and storage.
77     post_to_thingspeak(temp, humidity, pressure, dew_point, condensation_risk)
78
79     # Waits for 20  seconds before reading the sensors again.
80     time.sleep(20)
81