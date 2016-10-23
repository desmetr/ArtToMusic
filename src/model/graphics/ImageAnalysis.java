package model.graphics;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static javax.swing.UIManager.get;

/**
 * Created by rafael on 15.10.16.
 */
public class ImageAnalysis extends Application
{
    private Vector<PixelRGB> pixelRGBs = new Vector();
    private Vector<PixelHSV> pixelHSVs = new Vector();

    private void analysePixelsRGB(Vector<PixelRGB> pixelRGBs)
    {
        int greyCounter = 0;
        int redCounter = 0;
        int greenCounter = 0;
        int blueCounter = 0;
        int whiteCounter = 0;
        int blackCounter = 0;

        for (int i = 0; i < pixelRGBs.size(); i++)
        {
            int currentRed = pixelRGBs.get(i).getRed();
            int currentGreen = pixelRGBs.get(i).getGreen();
            int currentBlue = pixelRGBs.get(i).getBlue();

            // Found a grey pixel.
            if (currentRed == currentGreen && currentRed == currentBlue)
                greyCounter++;

            // Mostly red.
            else if (currentRed >= 230 && currentGreen <= 50 && currentBlue <= 50)
                redCounter++;

            // Mostly green.
            else if (currentRed <= 50 && currentGreen >= 230 && currentBlue <= 50)
                greenCounter++;

            // Mostly blue.
            else if (currentRed <= 50 && currentGreen <= 50 && currentBlue >= 230)
                blueCounter++;

            // Mostly white.
            else if (currentRed >= 240 && currentGreen >= 240 && currentBlue >= 240)
                whiteCounter++;

            // Mostly black.
            else if (currentRed <= 20 && currentGreen <= 20 && currentBlue <= 20)
                blackCounter++;
        }

        System.out.println("The number of black pixelRGBs in this image is:" + blackCounter);
        System.out.println("The number of white pixelRGBs in this image is:" + whiteCounter);
        System.out.println("The number of grey pixelRGBs in this image is:" + greyCounter);
        System.out.println("The number of red pixelRGBs in this image is:" + redCounter);
        System.out.println("The number of green pixelRGBs in this image is:" + greenCounter);
        System.out.println("The number of blue pixelRGBs in this image is:" + blueCounter);

        if ((greyCounter + blackCounter + whiteCounter + redCounter + greenCounter + blueCounter) != pixelRGBs.size())
            System.out.println("Woops, missed some pixelRGBs.");
    }

    private PixelHSV rgbToHsv(PixelRGB pixelRGB)
    {
        int currentRed = pixelRGB.getRed();
        int currentGreen = pixelRGB.getGreen();
        int currentBlue = pixelRGB.getBlue();

        float rNew = (float)currentRed / 255;
        float gNew = (float)currentGreen / 255;
        float bNew = (float)currentBlue / 255;
        float cMax = max(rNew, max(gNew, bNew));
        float cMin = min(rNew, min(gNew, bNew));
        float difference = cMax - cMin;

        PixelHSV pixel = new PixelHSV();

        // Hue calculation.
        if (difference == 0)
            pixel.setHue((float) 0.0);
        else if (cMax == rNew)
        {
            float temp = 60 * (((gNew - bNew) / difference) % 6);
            pixel.setHue(temp);
        }
        else if (cMax == gNew)
        {
            float temp = 60 * (((bNew - rNew) / difference) + 2);
            pixel.setHue(temp);
        }
        else if (cMax == bNew)
        {
            float temp = 60 * (((rNew - gNew) / difference) + 4);
            pixel.setHue(temp);
        }

        // Saturation calculation.
        if (cMax == 0)
            pixel.setSaturation((float) 0.0);
        else
            pixel.setSaturation(difference / cMax);

        // Value calculation.
        pixel.setValue(cMax);

        return pixel;
    }

    private void analysePixelsHSV(Vector<PixelRGB> pixelRGBs)
    {
        for (int i = 0; i < pixelRGBs.size(); i++)
            pixelHSVs.add(rgbToHsv(pixelRGBs.get(i)));
    }

    public BufferedImage imageProcessing(BufferedImage source)
    {
        return null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Image Analysis");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        BufferedImage bi = ImageIO.read(selectedFile);
        WritableImage imageBI = SwingFXUtils.toFXImage(bi, null);
        ImageView imageViewBI = new ImageView(imageBI);

        int[] pixel;

        for (int y = 0; y < bi.getHeight(); y++)
        {
            for (int x = 0; x < bi.getWidth(); x++)
            {
                pixel = bi.getRaster().getPixel(x, y, new int[3]);
                pixelRGBs.add(new PixelRGB(pixel[0], pixel[1], pixel[2]));
            }
        }

//        BufferedImage newBI = imageProcessing(bi);
//        System.out.println("d1");
//        WritableImage imageNewBI = SwingFXUtils.toFXImage(newBI, null);
//        System.out.println("d2");
//        ImageView imageViewNewBI = new ImageView(imageNewBI);
//        System.out.println("d3");

        HBox hbox = new HBox(50);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(imageViewBI);
        System.out.println("e");

        primaryStage.setScene(new Scene(hbox, 600, 300));
        primaryStage.show();
        System.out.println("f");

//        analysePixelsRGB(pixelRGBs);
//        analysePixelsHSV(pixelRGBs);

//        System.out.println("\n");
//        for (PixelHSV pixelHSV : pixelHSVs)
//            pixelHSV.print();
    }
}
