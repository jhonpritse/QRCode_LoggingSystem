package com.QR_Code.main;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class QR_MainBody implements ThreadFactory, Runnable {
         //region Declaration
    Webcam webcam= null;
    WebcamPanel webcamPanel;
    QR_Frame frame;
    int counter = 1;
    String QRCodeRaw;
    QR_SaveInfo saveInfo;

    private boolean isOnceTracker;
    String code1, code2;

//    private static final long serialVersionUID = 6441489157408381878L;
    private final Executor execute = Executors.newSingleThreadExecutor(this);

//endregion

    public static void main(String[] args) {
        new QR_MainBody();
    }

    public QR_MainBody()  {
        initComponent();
        savingInfoToFile();
    }
    void savingInfoToFile() {
        saveInfo = new QR_SaveInfo();
    }

        //region QR Reading Logic
    void  initComponent(){
        frame = new QR_Frame();

        frame.addingButtons();

        frame.startCameraButton.addActionListener(this::openCamera);
        frame.stopCameraButton.addActionListener(this::closeCamera);
        frame.switchCam.addActionListener(this::changeCamera);

        frame.revalidate();
        frame.repaint();
    }


    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement", "ConstantConditions"})
    @Override
    public void run() {
        try{
            do {

                try {
                    Thread.sleep(100);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Result result = null;
                BufferedImage image = null;

                if (this.webcam.isOpen()) {
                    if ((image = webcam.getImage()) == null) {
                        continue;
                    }
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    //notfound
                }

                if( result != null){
                    if(isOnceTracker){
                        code1 = result.getText();
                        if(!code1.equals(code2)){
                            code2 = code1;
                            QRCodeRaw = code2;
                            saveInfo.readQR(QRCodeRaw);
                            addNames();
                            isOnceTracker = false;
                        }
                        if (code2.equals(code1)){
                            isOnceTracker = true;
                        }

                    }

                    JScrollBar sb = frame.scrollPane.getVerticalScrollBar();
                    sb.setValue(sb.getMaximum());
                }

            } while (true);
        } catch (NullPointerException y){
            //  System.out.println("run made an error");
        }
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
    }
    //endregion

    void addNames(){
        //noinspection unchecked
        frame.mod.addElement(saveInfo.QRLogResult);
        frame.revalidate();
        frame.repaint();
    }
        //region Camera Buttons
int cameraIndex;
    public  void settingUpCamera(){
        Dimension size = WebcamResolution.QVGA.getSize();
        try{
            webcam = Webcam.getWebcams().get(cameraIndex); //0 is default webcam
        } catch (IndexOutOfBoundsException e){
            cameraIndex=0;
            webcam = Webcam.getWebcams().get(cameraIndex); //0 is default webcam
        }
        webcam.setViewSize(size);
        webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(size);
        webcamPanel.setFPSDisplayed(true);
        webcamPanel.setMirrored(true);

        frame.panelMain.add(webcamPanel);

        frame.revalidate();
        frame.repaint();
    }

    public void openCamera(ActionEvent e) {

        if (counter == 1){
            settingUpCamera();
            isOnceTracker = true;
            execute.execute(this);
        }
        counter = 0;
    }
    private void closeCamera(ActionEvent f) {
        if (counter == 0){
            counter = 1;
            webcam.close();
            frame.panelMain.remove(webcamPanel);
            frame.revalidate();
            frame.repaint();
        }
    }
    public void changeCamera(ActionEvent g){
try{
    cameraIndex++;
    webcam.close();
    frame.panelMain.remove(webcamPanel);
    frame.revalidate();
    frame.repaint();
    settingUpCamera();
    isOnceTracker = true;
    execute.execute(this);
    counter = 0;
} catch ( NullPointerException e){
    settingUpCamera();
    counter = 0;
}

    }
    //endregion


}
