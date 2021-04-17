package com.QR_Code.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class QR_SaveInfo {

    HashMap<String, ArrayList<String>> QRInputMap = new HashMap<>();

    String QRLogResult;

    String timeStamp;
    String name;

    DateFormat time = new SimpleDateFormat("hh:mm:ss aa MMM/dd/yyyy");
    Date date = new Date();
    SimpleDateFormat dateText = new SimpleDateFormat("dd_MMM_yyyy_hh");
    LocalDateTime now;
    String LogStatus;
    String filePath;

    public void readQR(String QRInput){
        now = LocalDateTime.now();
        if (!QRInputMap.containsKey(QRInput)){
            QRInputMap.put(QRInput, new ArrayList<>( ));
            QRInputMap.get(QRInput).add((time.format(new Date())));
            filePath = ("Log Result/" +dateText.format(date)+"_00_Logged_Records.csv");
                for (String i : QRInputMap.keySet()) {
                    timeStamp = QRInputMap.get(i).toString();
                    name = i;
                        if(name.equals(QRInput)){
                            QRLogResult = ( name + " has LOG IN at " + timeStamp);
                            LogStatus = "Logged In";
                            saveToCSV(name,timeStamp,LogStatus,filePath);
                }
            }
        }else {
            QRInputMap.get(QRInput).clear();
            QRInputMap.get(QRInput).add((time.format(new Date())));

            timeStamp = QRInputMap.get(QRInput).toString();
            name = QRInput;

            QRLogResult = ( name + " has LOG OUT at " + timeStamp );
            LogStatus = "Logged Out";

            saveToCSV(name,timeStamp,LogStatus,filePath);

            QRInputMap.remove(QRInput);
        }

    }

    public static void saveToCSV(String name, String timeStamp, String LogStatus, String filePath){
        FileWriter fileWriter ;
        try {
            File csvFile = new File(filePath);
            //noinspection ResultOfMethodCallIgnored
            csvFile.getParentFile().mkdir();
//            System.out.println( csvFile.getAbsolutePath());

            if(!csvFile.exists()){
                fileWriter   = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.println("Time Stamp"+","+"Students Name"+","+"Log Status");
                printWriter.println(timeStamp+","+name+","+LogStatus);
                printWriter.flush();
                printWriter.close();
            }else {
                fileWriter   = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.println(timeStamp+","+name+","+LogStatus);
                printWriter.flush();
                printWriter.close();
            }
        } catch (IOException e) {
          //  e.printStackTrace();
        }



    }

}
