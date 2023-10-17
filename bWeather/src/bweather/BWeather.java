
package bweather;

import javax.swing.SwingUtilities;


public class BWeather {


    public static void main(String[] args) {
        // Thread safe updates to the Swing GUI
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new WeatherAppGUI().setVisible(true);
                //System.out.println(WeatherApp.getWeatherData("Milan"));
            }
        });
    }
    
}
