package controllers;

import models.SensorReading;
import play.Logger;
import play.mvc.Controller;
import utilities.ThingSpeakService;

public class Dashboard extends Controller {

  public static void index() {
    Logger.info("Rendering IoT Dashboard");

    SensorReading reading = null;

    try {
      // Fetch the latest data from ThingSpeak
      reading = ThingSpeakService.getLatestReading();
    } catch (Exception e) {
      Logger.error("Error fetching data from ThingSpeak", e);
    }

    // Render the dashboard view with the reading
    render("dashboard.html", reading);
  }
}
