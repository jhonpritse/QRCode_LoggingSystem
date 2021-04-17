package com.QR_Code.main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class QR_Frame extends JFrame implements ActionListenr {
    JButton startCameraButton = new JButton();
    JButton stopCameraButton = new JButton();

    JPanel panelMain;
    JPanel panelButtons;
    Dimension BoarderSIze;
    Color BoarderColor;


    public  JPanel  addingMainPanel(){
    panelMain = new JPanel();
    panelMain.setLayout(new BorderLayout());
    panelMain.setBackground(new Color(77, 80, 87));
    return panelMain;
    }

/**
 *
//    public JPanel panelCamera( JPanel added){
////region Button Camera Setup
//        panelCamera = new JPanel();
//        panelCamera.setLayout(new BorderLayout());
//        panelCamera.setBackground(Color.blue);
//        panelCamera.setSize(added.getSize());
//        panelCamera.add(added, BorderLayout.CENTER);
////endregion
//        return panelCamera;
//    }*/
JButton switchCam;

    public void addingButtons(){
//region Extra stuff

        BoarderSIze = new Dimension(50, 50);
        BoarderColor = new Color(78, 110, 93);

        panelButtons = new JPanel();

        JPanel addLEFT = new JPanel();
        JPanel addRIGHT = new JPanel();
        JPanel addTOP = new JPanel();

        panelButtons.setPreferredSize(new Dimension(BoarderSIze.width+20, BoarderSIze.height+20));
        addLEFT.setPreferredSize(BoarderSIze);
        addTOP.setPreferredSize(BoarderSIze);
        addRIGHT.setPreferredSize(BoarderSIze);

        addRIGHT.setBackground(BoarderColor);
        addTOP.setBackground(BoarderColor);
        addLEFT.setBackground(BoarderColor);
        panelButtons.setBackground(BoarderColor);

        panelMain.add(addRIGHT, BorderLayout.EAST);
        panelMain.add(addTOP, BorderLayout.NORTH);
        panelMain.add(addLEFT, BorderLayout.WEST);
        panelMain.add(panelButtons, BorderLayout.SOUTH);



        JPanel switchPanel = new JPanel();
        switchPanel.setLayout(new BorderLayout());
        switchPanel.setBackground(Color.red);
        switchPanel.setPreferredSize(new Dimension(150  ,40));

        switchCam = new JButton();
        switchCam.setText("Change Camera");
        switchCam.setFocusable(false);
        switchCam.setBackground(BoarderColor);

        switchCam.setPreferredSize(new Dimension(150,40));
        switchPanel.add(switchCam, BorderLayout.CENTER);

        addTOP.add(switchPanel, BorderLayout.WEST);


//endregion

//region Buttons Setup
        panelButtons.setLayout(new GridLayout(1,2, 0,0));

        Border borderGreen = BorderFactory.createLineBorder(new Color(47,150,105),3,true);
        Border borderRed = BorderFactory.createLineBorder(new Color(160,98,105),3,true);

        startCameraButton.setText("Start Camera");
        stopCameraButton.setText("Stop Camera");

        startCameraButton.setBackground(new Color(207, 207, 207));
        stopCameraButton.setBackground(new Color(207, 207, 207));

        startCameraButton.setBorder(borderGreen);
        stopCameraButton.setBorder(borderRed);

        startCameraButton.setFocusable(false);
        stopCameraButton.setFocusable(false);

        startCameraButton.setFont(new Font("Roboto", Font.BOLD, 18));
        stopCameraButton.setFont(new Font("Roboto", Font.BOLD, 18));

        //endregion
        panelButtons.add(startCameraButton);
        panelButtons.add(stopCameraButton);
    }

    public JPanel panel1(){
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(new Color(59, 193, 74));
        panel1.setOpaque(true);
        panel1.setPreferredSize(new Dimension(100,90));





        return panel1;
    }

    public DefaultListModel<String> mod = new DefaultListModel<>();
    public JPanel panel3 ;
   public JList<String> list ;
    JScrollPane scrollPane;

    public JPanel panel2(){
//      Student Name
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel2.setBackground(Color.blue);
        panel2.setOpaque(true);
        panel2.setPreferredSize(new Dimension(475,100));
//      HEADER
        JPanel panel4 = new JPanel();
        panel4.setBackground(new Color(59, 193, 74));
        panel4.setPreferredSize(new Dimension(100,90));

        JLabel label = new JLabel();

        label.setIcon(new ImageIcon(new ImageIcon("icc-header.jpg").getImage().getScaledInstance(380, 80, Image.SCALE_DEFAULT)));

        panel4.add(label);
        panel2.add(panel4, BorderLayout.NORTH);

//      LIST VIEW

        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setBackground(new Color(  77, 161, 103));

        //region boarder for panel 3
        JPanel panel3BL = new JPanel();
        JPanel panel3BR = new JPanel();

        panel3BL.setOpaque(false);
        panel3BR.setOpaque(false);

        panel3BL.setPreferredSize(new Dimension(30,100));
        panel3BR.setPreferredSize(new Dimension(30,100));

        panel3.add(panel3BL, BorderLayout.WEST);
        panel3.add(panel3BR, BorderLayout.EAST);
        //endregion
        panel3.setPreferredSize(new Dimension(10,300));
        panel2.add(panel3, BorderLayout.CENTER);

        Font font = new Font("Roboto", Font.BOLD,20);

        list = new JList<>(mod);
        list.setFont(font);
        list.setBackground(Color.lightGray);

        scrollPane = new JScrollPane(list , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollBar sbV = scrollPane.getVerticalScrollBar();
        sbV.setBackground(new Color(78, 110, 93));
        JScrollBar sbH = scrollPane.getHorizontalScrollBar();
        sbH.setBackground(new Color(78, 110, 93));
        scrollPane.setViewportView(list);
        panel3.add(scrollPane);

        return panel2;
    }


    QR_Frame(){
        ImageIcon image = new ImageIcon("icc-logo.jpg");
        this.setIconImage(image.getImage());
        this.setTitle("QR Attendance System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension startSize = new Dimension(1050, 550);

        this.setSize(startSize);
        this.setMinimumSize(startSize);

        this.add(panel1() , BorderLayout.SOUTH);
        this.add(panel2() , BorderLayout.EAST);
        this.add(addingMainPanel() , BorderLayout.CENTER);

        this.setVisible(true);
    }

}



