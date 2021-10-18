import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherAPI { //COMMENT
   private String keyword;
   private JsonElement results, results2;
   public boolean tempIsInF;

   public WeatherAPI() {
           tempIsInF = true;
   }


   public void fetch(String k) {
       try {
           keyword = URLEncoder.encode(k, "utf-8").replace("+", "%20");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       String urlStringCurrent = "https://api.aerisapi.com/observations/" + keyword +
               "?client_id=0xIZteIZ1ZZ8jGleTvvaV&client_secret=mnXRnVr1XTIdlZngftxlQPYbhBojoaQ8eYoSRZ5V";

       try {
           // Make URL object from urlString
           URL url = new URL(urlStringCurrent);

           // Open stream readers for incoming data
           InputStream is = url.openStream();
           InputStreamReader isr = new InputStreamReader(is);
           BufferedReader br = new BufferedReader(isr);

           // Parse into JSON
           JsonParser parser = new JsonParser();
           results = parser.parse(br);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void fetchForecast(String k) {
       try {
           keyword = URLEncoder.encode(k, "utf-8").replace("+", "%20");
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
       }
       String urlStringForecast = "https://api.aerisapi.com/forecasts/" + keyword +
               "?client_id=0xIZteIZ1ZZ8jGleTvvaV&client_secret=mnXRnVr1XTIdlZngftxlQPYbhBojoaQ8eYoSRZ5V";

       try {
           URL url = new URL(urlStringForecast);

           InputStream is = url.openStream();
           InputStreamReader isr = new InputStreamReader(is);
           BufferedReader br = new BufferedReader(isr);

           JsonParser parser = new JsonParser();
           results2 = parser.parse(br);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void fetchMap(String k){
       keyword = URLEncoder.encode(k, StandardCharsets.UTF_8).replace("+", "%20");
       System.out.println(keyword);

       String urlStringMap = "https://maps.aerisapi.com/0xIZteIZ1ZZ8jGleTvvaV_mnXRnVr1XTIdlZngftxlQPYbhBojoaQ8eYoSRZ5V/flat," +
               "radar,counties,interstates,admin-cities/256x256/" + keyword + ",8/current.png";
       System.out.println(urlStringMap);
       try {
           URL url = new URL(urlStringMap);
           InputStream is = url.openStream();
           OutputStream os = new FileOutputStream("map.png");

           byte[] b = new byte[1024];
           int length;

           while ((length = is.read(b)) != -1) {
               os.write(b, 0, length);
           }

           is.close();
           os.close();
       } catch (MalformedURLException e) {
           e.printStackTrace();
       } catch (IOException e){
           e.printStackTrace();
       }
   }

   public Image getImage()
   {
       try (InputStream imageName = new BufferedInputStream(new FileInputStream("images/" + getIcon()))){
           Image image = new Image(imageName);
           return image;
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

   public String getSuccess()
   {
       String success = results.getAsJsonObject()
               .get("success").getAsString();
       return success;
   }

   public Image getMap(){
       try (InputStream imageName = new BufferedInputStream(new FileInputStream("map.png"))){
           Image image = new Image(imageName);
           return image;
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
   }

   public String getTemperature() {
       String temperature = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("ob").getAsJsonObject()
               .get("tempF").getAsString();

       return temperature;
   }

   public String getPlace(){
       String place = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("place").getAsJsonObject()
               .get("name").getAsString();

       return place;
   }

   public String getState(){
       String state = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("place").getAsJsonObject()
               .get("state").getAsString();

       return state;
   }

   public String getConditions(){
       String conditions = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("ob").getAsJsonObject()
               .get("weather").getAsString();

       return conditions;
   }

   public String getTempC(){
       String temperatureC = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("ob").getAsJsonObject()
               .get("tempC").getAsString();

       return temperatureC;
   }

   public String getIcon(){
       String icon = results.getAsJsonObject()
               .get("response").getAsJsonObject()
               .get("ob").getAsJsonObject()
               .get("icon").getAsString();

       return icon;
   }

   public long[] getDates()
   {
       long[] dates = new long[5];
       for(int i = 0; i < dates.length; i++)
       {
           dates[i] = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("timestamp").getAsLong();
       }

       return dates;
   }

   public String[] getMaxTempF()
   {
       String maxTempF[] = new String[5];
       for(int i = 0; i < maxTempF.length; i++)
       {
           maxTempF[i] = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("maxTempF").getAsString();
       }

       return maxTempF;
   }

   public String[] getMinTempF()
   {
       String minTempF[] = new String[5];
       for(int i = 0; i < minTempF.length; i++)
       {
           minTempF[i] = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("minTempF").getAsString();
       }

       return minTempF;
   }

   public String[] getMaxTempC()
   {
       String maxTempC[] = new String[5];
       for(int i = 0; i < maxTempC.length; i++)
       {
           maxTempC[i] = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("maxTempC").getAsString();
       }

       return maxTempC;
   }

   public String[] getMinTempC()
   {
       String minTempC[] = new String[5];
       for(int i = 0; i < minTempC.length; i++)
       {
           minTempC[i] = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("minTempC").getAsString();
       }

       return minTempC;
   }

   public Image[] getImages()
   {
       Image images[] = new Image[5];
       for(int i = 0; i < images.length; i++)
       {
           String name = results2.getAsJsonObject()
                   .get("response").getAsJsonArray()
                   .get(0).getAsJsonObject()
                   .get("periods").getAsJsonArray()
                   .get(i).getAsJsonObject()
                   .get("icon").getAsString();

           try (InputStream imageName = new BufferedInputStream(new FileInputStream("images/" + name))){
               images[i] = new Image(imageName);

           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return images;
   }
}

