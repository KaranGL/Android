package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar PB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        PB = findViewById(R.id.progress);
        PB.setVisibility(View.VISIBLE);
        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
//        ForecastQuery UV = new ForecastQuery();
//        UV.execute("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }

    public class ForecastQuery extends AsyncTask<String, Integer, String>  {

        TextView UV = findViewById(R.id.UV);
        TextView min = findViewById(R.id.minTemp);
        TextView max = findViewById(R.id.maxTemp);
        TextView current = findViewById(R.id.currentTemp);
        ImageView pic = findViewById(R.id.weatherImage);
        String temperatureText ;
        String minText, maxText, icon, filename ;
        float value;
        Bitmap image;

        @Override
        protected String doInBackground(String... args) {
            String retuurn = "DONE";
            try {
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);
                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

//                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            Log.i("temperature check", "temperature");
                            //If you get here, then you are pointing to a <Weather> start tag
                            temperatureText = xpp.getAttributeValue(null, "value");
                            minText = xpp.getAttributeValue(null, "min");
                            maxText = xpp.getAttributeValue(null, "max");
                            publishProgress(25, 50, 75);
                        } else if (xpp.getName().equals("weather")) {
                            icon = xpp.getAttributeValue(null, "icon");
                            filename = icon+".png";
                            //CHECKING IF FILE EXISTS AND THEN FETCHING THE IMAGES
                            if (fileExistance(filename)) {
                                Log.i("FILE CHECKING", filename);
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(filename);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                image= BitmapFactory.decodeStream(fis);
                            } else {
                                Log.i("DOWNLOADING IMAGES", filename);
                                String iconName = xpp.getAttributeValue(null, "icon");
                                String urlString = "http://openweathermap.org/img/w/" + iconName + ".png";

                                try {
                                    //DOWNLOADING THE IMAGE
                                    image = null;
                                    URL urlImage = new URL(urlString);
                                    HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
                                    connection.connect();
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {
                                        image = BitmapFactory.decodeStream(connection.getInputStream());
                                    }
                                    publishProgress(100);
                                    //SAVING THE IMAGES TO A LOCAL FILE
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    eventType = xpp.next();   //move to the next xml event and store it in a variable
                }//while
                String UVaddress = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
                    //create a URL object of what server to contact:
                    URL UVurl = new URL(UVaddress);
                    //open the connection
                    HttpURLConnection UVurlConnection = (HttpURLConnection) UVurl.openConnection();
                    //wait for data:
                    InputStream UVresponse = UVurlConnection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(UVresponse, "UTF-8"), 8);

                    StringBuilder sb = new StringBuilder();

                    String line = null;

                    while ((line = reader.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    String result = sb.toString();

                    JSONObject jObject = new JSONObject(result);
                    value = (float) jObject.getDouble("value");

            }catch (MalformedURLException e1){
                retuurn = "URL is malformed";
            }
            catch (JSONException | XmlPullParserException e)
            {
                e.printStackTrace();
            }
            catch(IOException e1){
                retuurn = "IO exception";
            }
            return retuurn;
        }

        public void onProgressUpdate(Integer ... args)
        {
            Log.i("HTTP", "update: " + args[0]);
            super.onProgressUpdate(args);
            PB.setVisibility(View.VISIBLE);
            PB.setProgress(args[0]);
        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            Log.i("HTTP", fromDoInBackground);
            current.setText("    "+temperatureText+"°C");
            min.setText("    "+minText+"°C");
            max.setText("    "+maxText+"°C");
            pic.setImageBitmap(image);
            UV.setText("    "+String.valueOf((int)value));
            PB.setVisibility(View.INVISIBLE );
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
    }
}