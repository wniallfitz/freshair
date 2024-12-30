// Note: TS = ThingSpeak

// Declares that the class belongs to the "utilities" package.
package utilities;

// Imports the SensorReading model to store the data fetched.
import models.SensorReading;

// Below are the various JSON and Java library objects imported to aid the web app functionality.

// Imports JSONArray to handle arrays in JSON responses.
import org.json.JSONArray;
// Imports JSONObject to parse and manipulate JSON data.
import org.json.JSONObject;
// Imports Logger to log messages and errors for debugging.
import play.Logger;

// Imports BufferedReader to read the server response line by line.
import java.io.BufferedReader;
// Imports InputStreamReader to convert input streams to readers.
import java.io.InputStreamReader;
// Imports HttpURLConnection to make HTTP requests to ThingSpeak.
import java.net.HttpURLConnection;
// Imports URL to construct the ThingSpeak API endpoint.
import java.net.URL;
// Imports SimpleDateFormat to handle and parse date strings.
import java.text.SimpleDateFormat;
// Imports Date to store and manipulate timestamp information.
import java.util.Date;

// Declares the ThingSpeakService class, which contains methods to interact with the ThingSpeak API and retrieve sensor data.
public class ThingSpeakService {

  // Declares the relevant ThingSpeak channel ID to identify where the sensor data is stored.
  private static final String CHANNEL_ID = "2792943";

  // Declares the ThingSpeak read API key for accessing the TS data.
  private static final String API_KEY = "YIXRO5RYXJQA9BA1";

  /**
   * The getLatestReading retrieves the latest sensor reading from ThingSpeak.
   * It returns a SensorReading object containing the latest temperature, humidity, and timestamp.
   * For error handling, it throws an exception if an error occurs during data retrieval or data processing.
   * This method makes the code webapp adaptable to other TS channels and APIs.
   */
  public static SensorReading getLatestReading() throws Exception {
    // Constructs the API URL for fetching the latest data, specifying the channel ID, API key, and number of results.
    String apiUrl = String.format(
        "https://api.thingspeak.com/channels/%s/feeds.json?api_key=%s&results=1",
        CHANNEL_ID, API_KEY
    );

    // Step 1. for interacting with the APi. Opens a HTTP connection to the specified API URL i.e. to the TS server using the apiUrl constructed above
    HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();

    // Step 2. Sets the HTTP request method to GET for retrieving data.
    conn.setRequestMethod("GET");

    // Reads the response coming from the API. Creates a BufferedReader to read the response from the API, converting the JSON data into text.
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    // Sets a StringBuilder to collect and store all lines of response text from the API.
    StringBuilder response = new StringBuilder();
    // Declares the variable 'line' to hold each line of the response until all necessary data for the dashboard has been retrieved.
    String line;

    // While loop that reads the API response line by line and stores each line in the StringBuilder ('response' variable) until the response is null.
    // The Stringbuilder combines all lines of the response into a single string, ready for processing.
    while ((line = reader.readLine()) != null) {
      response.append(line);
    }
    // Closes the reader after finishing reading the response.
    reader.close();

    // Converts the complete response text into a JSON object.
    JSONObject json = new JSONObject(response.toString());
    //  Extracts the relevant 'feeds' array from TS API, which contains the sensor data points, i.e. Temperature etc. from the JSON object.
    JSONArray feeds = json.getJSONArray("feeds");

    // Checks if there is at least one data point in the 'feeds' array to avoid error.
    if (feeds.length() > 0) {
      // Gets the first data point from the 'feeds' array.
      JSONObject feed = feeds.getJSONObject(0);

      // Declares temperature variable to store its value. Extracts the temperature value from the 'field1' key in the JSON object.
      // Defaults to Double.NaN if the key is missing or contains invalid data.
      double temperature = feed.optDouble("field1", Double.NaN);

      // Declares humidity variable to store its value. Extracts the humidity value from the 'field2' key in the JSON object.
      // Defaults to Double.NaN if the key is missing or contains invalid data.
      double humidity = feed.optDouble("field2", Double.NaN);

      // Declares pressure variable to store its value. Extracts the pressure value from the 'field3' key in the JSON object.
      // Defaults to Double.NaN if the key is missing or contains invalid data.
      double pressure = feed.optDouble("field3", Double.NaN);

      // Declares dewPoint variable to store its value. Extracts the pressure value from the 'field4' key in the JSON object.
      // Defaults to Double.NaN if the key is missing or contains invalid data.
      double dewPoint = feed.optDouble("field4", Double.NaN);

      // Declares condensationRisk variable to store its value. Extracts the pressure value from the 'field5' key in the JSON object.
      double condensationRisk = feed.optDouble("field5", 0);

      // Declares Date object and timestamp variable. Extracts the timestamp of the reading from the 'created_at' key and converts it into a Date object.
      Date timestamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
          .parse(feed.getString("created_at"));

      // Logs a message to the terminal to indicate that the data was successfully fetched from TS.
      Logger.info("Fetched latest reading from ThingSpeak.");

      // Creates and returns a SensorReading object containing the extracted temperature, humidity, and timestamp data.
      return new SensorReading(temperature, humidity, pressure, dewPoint,(int) condensationRisk, timestamp);
    }

    // Ensures getLatestReading() method returns null to handle the case if no data points are available in the 'feeds' array.
    return null;
  }
}
