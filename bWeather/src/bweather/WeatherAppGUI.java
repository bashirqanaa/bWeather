
package bweather;

import org.json.simple.JSONObject;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class WeatherAppGUI extends JFrame {

    private JSONObject Data;

    
    WeatherAppGUI(){
        super("bWeather");
        // end the program process once the GUI has been closed.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // set the size of the GUI
        setSize(450,650);
        // center the GUI in the middle of the screen
        setLocationRelativeTo(null);
        // set the layout manger to null so we can position manually
        setLayout(null);
        // prevernt resizing the GUI
        setResizable(false);
        
        //add the GUI Components
        addGuiComponents();
    
    }
    
    private void addGuiComponents(){
        // Search Field
        JTextField searchField = new JTextField();
        searchField.setBounds(15,15,320,45);
        searchField.setFont(new Font("Dialog", Font.PLAIN,24));
        add(searchField);
        
        // Search Button
        JButton searchButton = new JButton("Search");
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(340,15,80,45);
        // give functionality to the button
        add(searchButton);

        //  Weather Label
        JLabel weatherConditionLabel = new JLabel();
        weatherConditionLabel.setBounds(0,100,450,217);
        weatherConditionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionLabel);

        //Tempreture text
        JLabel tempText = new JLabel();
        tempText.setBounds(0,320,450,54);
        tempText.setFont(new Font("Dialog",Font.BOLD,48));
        tempText.setHorizontalAlignment(SwingConstants.CENTER);
        add(tempText);

        //Weather description text
        JLabel weatherDisc = new JLabel();
        weatherDisc.setBounds(0,380,450,28);
        weatherDisc.setFont(new Font("Dialog",Font.PLAIN,28));
        weatherDisc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherDisc);



        //Humidity text
        JLabel humidityText = new JLabel();
        humidityText.setBounds(90,500,85,55);
        humidityText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(humidityText);

        //Humidity Image
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(15,500,70 ,70);
        add(humidityImage);

        // Windspeed Image
        JLabel WindSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        WindSpeedImage.setBounds(250,500,70,70);
        add(WindSpeedImage);

        //WindSpeed text
        JLabel WindSpeedText = new JLabel();
        WindSpeedText.setBounds(320,500,100,55);
        WindSpeedText.setFont(new Font("Dialog",Font.PLAIN,16));
        add(WindSpeedText);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchField.getText();
                // Check for errors
                if (userInput.replace("\\s","").length() <0){
                    return;
                }
                Data = WeatherApp.getWeatherData(userInput);
                String UpdateWeatherDesc = (String) Data.get("Desc");
                int UpdateTemp = (int) Data.get("Temp");
                String UpdateHum = (String) Data.get("Hum");
                String UpdateWind = (String) Data.get("Wind");
                // update the image
                switch (UpdateWeatherDesc){
                    case "Clear": weatherConditionLabel.setIcon(loadImage("src/assets/clear.png"));
                    break;
                    case "Mainly clear": weatherConditionLabel.setIcon(loadImage("src/assets/clear.png"));
                    break;
                    case  "Overcast":  weatherConditionLabel.setIcon(loadImage("src/assets/cloudy.png"));
                    break;
                    case "Partly cloudy": weatherConditionLabel.setIcon(loadImage("src/assets/cloudy.png"));
                    break;
                    case  "Slight Rain":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                    break;
                    case  "Moderate Rain":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                    break;
                    case  "Slight Rain Showers":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                    break;
                    case  "Heavy Rain":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case  "Moderate Rain Showers":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case  "Heavy Rain Showers":  weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                        break;
                }

                //update Wind speed data
                WindSpeedText.setText("<html><b>Wind Speed <b/> "+UpdateWind+" Km\\h<html/>");
                humidityText.setText("<html><b>Humidity <b/> "+UpdateHum+" %<html/>");
                tempText.setText(UpdateTemp+" C");
                weatherDisc.setText(UpdateWeatherDesc);
            }
        });

      
        
    }

    private Icon loadImage(String resourcePath) {
        try{
            BufferedImage image = ImageIO.read(new File(resourcePath));
            //Image newImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            return new ImageIcon(image);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    
}
