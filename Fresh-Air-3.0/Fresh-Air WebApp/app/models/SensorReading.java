package models;

import javax.persistence.Entity;
import java.util.Date;
import play.db.jpa.Model;

@Entity
public class SensorReading extends Model {
  public double temperature;      // Temperature reading
  public double humidity;         // Humidity reading
  public double pressure;         // Pressure reading
  public double dewPoint;         // Dew Point reading
  public int condensationRisk;    // Condensation risk (0 = No, 1 = Yes)
  public Date timestamp;          // Timestamp of the reading

  // Constructor
  public SensorReading(double temperature, double humidity, double pressure, double dewPoint, int condensationRisk, Date timestamp) {
    this.temperature = temperature;
    this.humidity = humidity;
    this.pressure = pressure;
    this.dewPoint = dewPoint;
    this.condensationRisk = condensationRisk;
    this.timestamp = timestamp;
  }
}
