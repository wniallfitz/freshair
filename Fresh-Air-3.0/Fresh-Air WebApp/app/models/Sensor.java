package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Sensor extends Model {
    public String name;          // Name of the sensor
    public String apiKey;        // ThingSpeak API key
    public String channelId;     // ThingSpeak channel ID
    public transient SensorReading latestReading; // Holds the latest reading temporarily

    public Sensor(String name, String apiKey, String channelId) {
        this.name = name;
        this.apiKey = apiKey;
        this.channelId = channelId;
    }
}
