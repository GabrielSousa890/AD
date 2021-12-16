package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

public class SmartTermo extends JFrame{
    private JPanel jPanelMain;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JLabel jLabelDesejada;
    private JLabel jLabelDesejadaValue;
    private JTextArea logs;
    private JLabel jLabelHT;
    private JLabel jLabelHTValue;
    private JLabel jLabelAtual;
    private JLabel jLabelAtualValue;
    private JLabel jLabelHD;
    private JLabel jLabelHDValue;
    private JButton button5;
    private JLabel jLabelDate;
    private double cels,fars,currentTemp;
    private boolean celsius = true;
    private boolean heaterOn;
    private int hum;

    public SmartTermo(String title){
        super(title);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(jPanelMain);

        jLabelDesejada.setText("Temperatura Desejada");
        jLabelAtual.setText("Temperatura Atual");
        jLabelHT.setText("Humidade Atual");
        jLabelHD.setText("Humidade Desejada");
        jLabelDesejadaValue.setText("20.0");
        jLabelAtualValue.setText("20.0");
        jLabelHDValue.setText("50");
        jLabelHTValue.setText("70");
        Date date = new Date();
        SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        jLabelDate.setText(String.valueOf(data.format(date)));



        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTemp(false,celsius);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTemp(true,celsius);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeHumidade(false);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeHumidade(true);
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (celsius) {
                    changeTempType(true);
                    celsius = false;
                }else{
                    changeTempType(false);
                    celsius = true;
                }
            }
        });

        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {


                String celsiusDes = jLabelDesejadaValue.getText();
                String celsius = jLabelAtualValue.getText();
                if(Double.parseDouble(celsiusDes) > Double.parseDouble(celsius)){
                    heaterOn = false;
                }

                Random r = new Random();
                int v = r.nextInt(100);
                if(v % 2 == 0 && heaterOn){
                    currentTemp = Double.parseDouble(celsiusDes) + 0.5;
                }else {
                    // currentTemp = Double.parseDouble(celsiusDes) - 0.5;
                }
                jLabelDesejadaValue.setText(String.valueOf(currentTemp));
            }
        };
        java.util.Timer timer = new java.util.Timer("Timer");

        timer.schedule(repeatedTask,0,100);

        this.setSize(400,300);
    }

    public void scheduleHeaterTask(){

    }

    public void changeTemp(boolean verify,boolean cel){
        String celsius = jLabelAtualValue.getText();
        Date date = new Date();
        SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(cel){
            if(verify){
                cels = Double.valueOf(celsius) + 0.5;
                addLog(">   Aumentou a temperatura em 0.5ºC | "+String.valueOf(data.format(date))+"\n");
            }else{
                cels = Double.valueOf(celsius) - 0.5;
                addLog(">   Diminuiu a temperatura em 0.5ºC | "+String.valueOf(data.format(date))+"\n");
            }
        }else{
            if(verify){
                cels = Double.valueOf(celsius) + 1.8;
                addLog(">   Aumentou a temperatura em 0.5ºC | "+String.valueOf(data.format(date))+"\n");
            }else{
                cels = Double.valueOf(celsius) - 1.8;
                addLog(">   Diminuiu a temperatura em 0.5ºC | "+String.valueOf(data.format(date))+"\n");
            }
        }

        String celsiusDes = jLabelDesejada.getText();
        if(cels > Double.parseDouble(celsiusDes)){
            heaterOn = true;
        }else{
            heaterOn = false;
        }

        celsius = String.valueOf(cels);
        jLabelAtualValue.setText(celsius);
    }

    public void changeTempType(boolean verifyType){
        if(verifyType){
            String celsius = jLabelAtualValue.getText();
            fars = (Double.valueOf(celsius) * 1.8) + 32;
            celsius = String.valueOf(Math.round(fars));
            jLabelAtualValue.setText(celsius);

            celsius = jLabelDesejadaValue.getText();
            fars = (Double.valueOf(celsius) * 1.8) + 32;
            celsius = String.valueOf(Math.round(fars));
            jLabelDesejadaValue.setText(celsius);
        }else{
            String farnheneit = jLabelAtualValue.getText();
            cels = (Double.parseDouble(farnheneit) - 32) / 1.8;
            farnheneit = String.valueOf(Math.round(cels));
            jLabelAtualValue.setText(farnheneit);

            farnheneit = jLabelDesejadaValue.getText();
            cels = (Double.parseDouble(farnheneit) - 32) / 1.8;
            farnheneit = String.valueOf(Math.round(cels));
            jLabelDesejadaValue.setText(farnheneit);
        }
    }

    public void changeHumidade(boolean verify){
        String humidade = jLabelHDValue.getText();
        Date date = new Date();
        SimpleDateFormat data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if(verify){
            hum = Integer.valueOf(humidade) + 5;
            addLog(">   Aumentou a humidade em 5% | "+String.valueOf(data.format(date))+"\n");
        }else{
            hum = Integer.valueOf(humidade) - 5;
            addLog(">   Diminuiu a humidade em 5% | "+String.valueOf(data.format(date))+"\n");
        }

        humidade = String.valueOf(hum);
        jLabelHDValue.setText(humidade);
    }

    public void addLog(String message){
        logs.append(message);
    }

    public static void main(String[] args){
        JFrame frame = new SmartTermo("SmartTermo");
        frame.setVisible(true);
    }
}
