import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.IOException;
import java.text.*;
import java.util.Date;
import java.util.Locale;

public class GUI extends Application {

   @FXML
   Button fetch, CF;

   @FXML
   Label zipcodeLabel, currentConditions, cityStateLabel, temperatureLabel, weatherConditionsLabel, cityState, temperature, weatherConditions;

   @FXML
   Label date1, date2, date3, date4, date5, errorlabel, errorkeyword;

   @FXML
   Label maxTemp1, maxTemp2, maxTemp3, maxTemp4, maxTemp5, minTemp1, minTemp2, minTemp3, minTemp4, minTemp5;

   @FXML
   ImageView weatherConditionImage, mapImage,
   imageOne, imageTwo, imageThree, imageFour, imageFive;

   @FXML
   TextField keywordEntry;

   private WeatherAPI w;

   private boolean tempIsInF = true;

   @Override
   public void start(Stage window) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
       window.setTitle("Weather API");
       window.setScene(new Scene(root, 600, 600));
       window.show();
   }

   public void fetchWeather(ActionEvent ae){
       String keyText = keywordEntry.getText();
       w = new WeatherAPI();
       w.fetch(keyText);

       if(w.getSuccess().equals("true"))
       {
           errorlabel.setText("");
           w.fetchMap(keyText);
           w.fetchForecast(keyText);

           weatherConditions.setText(w.getConditions());
           cityState.setText(w.getPlace() + ", " + w.getState());
           temperature.setText(w.getTemperature() + "°F");
           weatherConditionImage.setImage(w.getImage());
           mapImage.setImage(w.getMap());
           CF.setText("°C");

           long timestamp1 = w.getDates()[0];
           long timestamp2 = w.getDates()[1];
           long timestamp3 = w.getDates()[2];
           long timestamp4 = w.getDates()[3];
           long timestamp5 = w.getDates()[4];

           SimpleDateFormat sdf = new SimpleDateFormat("EEE", Locale.US);

           date1.setText(sdf.format(new Date(timestamp1 * 1000L)));
           date2.setText(sdf.format(new Date(timestamp2 * 1000L)));
           date3.setText(sdf.format(new Date(timestamp3 * 1000L)));
           date4.setText(sdf.format(new Date(timestamp4 * 1000L)));
           date5.setText(sdf.format(new Date(timestamp5 * 1000L)));

           maxTemp1.setText(w.getMaxTempF()[0] + "°F");
           maxTemp2.setText(w.getMaxTempF()[1] + "°F");
           maxTemp3.setText(w.getMaxTempF()[2] + "°F");
           maxTemp4.setText(w.getMaxTempF()[3] + "°F");
           maxTemp5.setText(w.getMaxTempF()[4] + "°F");

           minTemp1.setText(w.getMinTempF()[0] + "°F");
           minTemp2.setText(w.getMinTempF()[1] + "°F");
           minTemp3.setText(w.getMinTempF()[2] + "°F");
           minTemp4.setText(w.getMinTempF()[3] + "°F");
           minTemp5.setText(w.getMinTempF()[4] + "°F");

           imageOne.setImage(w.getImages()[0]);
           imageTwo.setImage(w.getImages()[1]);
           imageThree.setImage(w.getImages()[2]);
           imageFour.setImage(w.getImages()[3]);
           imageFive.setImage(w.getImages()[4]);

       }
       else if(w.getSuccess().equals("false"))
       {
           displayErrorMessage();
       }
   }

   public void displayErrorMessage ()
   {
       errorlabel.setText("Could not display weather for \"" + keywordEntry.getText() + "\"");
   }

   public void toggleTempUnit() {
       if(tempIsInF)
       {
           temperature.setText(w.getTempC() + "°C");
           maxTemp1.setText(w.getMaxTempC()[0] + "°C");
           maxTemp2.setText(w.getMaxTempC()[1] + "°C");
           maxTemp3.setText(w.getMaxTempC()[2] + "°C");
           maxTemp4.setText(w.getMaxTempC()[3] + "°C");
           maxTemp5.setText(w.getMaxTempC()[4] + "°C");
           minTemp1.setText(w.getMinTempC()[0] + "°C");
           minTemp2.setText(w.getMinTempC()[1] + "°C");
           minTemp3.setText(w.getMinTempC()[2] + "°C");
           minTemp4.setText(w.getMinTempC()[3] + "°C");
           minTemp5.setText(w.getMinTempC()[4] + "°C");
           tempIsInF = false;
           CF.setText("°F");
       }
       else
       {
           temperature.setText(w.getTemperature() + "°F");
           maxTemp1.setText(w.getMaxTempF()[0] + "°F");
           maxTemp2.setText(w.getMaxTempF()[1] + "°F");
           maxTemp3.setText(w.getMaxTempF()[2] + "°F");
           maxTemp4.setText(w.getMaxTempF()[3] + "°F");
           maxTemp5.setText(w.getMaxTempF()[4] + "°F");
           minTemp1.setText(w.getMinTempF()[0] + "°F");
           minTemp2.setText(w.getMinTempF()[1] + "°F");
           minTemp3.setText(w.getMinTempF()[2] + "°F");
           minTemp4.setText(w.getMinTempF()[3] + "°F");
           minTemp5.setText(w.getMinTempF()[4] + "°F");
           tempIsInF = true;
           CF.setText("°C");
       }
   }

}

