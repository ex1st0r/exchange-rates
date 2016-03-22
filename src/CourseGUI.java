package BankGUI;

import sun.util.calendar.JulianCalendar;
import sun.util.resources.CalendarData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseGUI{

    MyDrawPanel myDrawPanel;
    JTextField jTextField1,jTextField2;
    String s1,s2,s3;
    public void go() throws Exception{
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent event) {

            }

            public void windowClosed(WindowEvent event) {

            }

            public void windowClosing(WindowEvent event) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Закрыть окно?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n == 0) {
                    event.getWindow().dispose();
                }
            }

            public void windowDeactivated(WindowEvent event) {

            }

            public void windowDeiconified(WindowEvent event) {

            }

            public void windowIconified(WindowEvent event) {

            }

            public void windowOpened(WindowEvent event) {

            }

        });

        jTextField1=new JTextField(10);
        jTextField2=new JTextField(10);
        JButton jButton=new JButton("Показать график");
        List list = new List();
        list.add("Австралийский доллар");
        list.add("Фунт стерлингов Соединенного королевства");
        list.add("Белорусский рубль");
        list.add("Датский крон");
        list.add("Доллар США");
        list.add("Евро");
        list.add("Исландский крон");
        list.add("Казахстанский тенге");
        list.add("Канадский доллар");
        list.add("Норвежский крон");
        list.add("СДР (специальные права заимствования)");
        list.add("Сингапурский доллар");
        list.add("Турецкий лир");
        list.add("Шведский крон");
        list.add("Швейцарский франк");
        list.add("Японский иен");

        List day1 = new List();
        List month1 = new List();
        List day2 = new List();
        List month2 = new List();
        List year1 = new List();
        List year2 = new List();

        for(int i=1;i<=31;i++){
            day1.add(""+i);
            day2.add(""+i);
        }

        for(int i=1;i<=12;i++){
            month1.add(""+i);
            month2.add(""+i);
        }

        for(int i=2000;i<=2016;i++){
            year1.add(""+i);
            year2.add(""+i);
        }
        JLabel start = new JLabel("Выберете начальную дату");
        JLabel end = new JLabel("Выберете конечную дату");
        JLabel cour = new JLabel("Выберете валюту");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(4,1,10,10));
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT,7,7));
        jPanel1.add(start);
        jPanel1.add(day1);
        jPanel1.add(month1);
        jPanel1.add(year1);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        jPanel2.add(end);
        jPanel2.add(day2);
        jPanel2.add(month2);
        jPanel2.add(year2);

        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        jPanel3.add(cour);
        jPanel3.add(list);
        jPanel3.add(jButton);

        jPanel.add(jPanel1);
        jPanel.add(jPanel2);
        jPanel.add(jPanel3);
        jFrame.getContentPane().add(jPanel,BorderLayout.NORTH);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
        jButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                s1 = jTextField1.getText();
                s2 = jTextField2.getText();
                int qwerty = list.getSelectedIndex();
                String u1 = day1.getSelectedItem();
                String u2 = day2.getSelectedItem();
                String u3 = month1.getSelectedItem();
                String u4 = month2.getSelectedItem();
                String u5 = year1.getSelectedItem();
                String u6 = year2.getSelectedItem();

                try {
                    if (u1 != null && u2 != null && u3 != null && u4 != null && u5 != null && u6 != null && qwerty != -1) {
                        if (qwerty == 0) {
                            s3 = "R01010";
                        }
                        if (qwerty == 1) {
                            s3 = "R01035";
                        }
                        if (qwerty == 2) {
                            s3 = "R01090";

                        }
                        if (qwerty == 3) {
                            s3 = "R01215";

                        }
                        if (qwerty == 4) {
                            s3 = "R01235";

                        }
                        if (qwerty == 5) {
                            s3 = "R01239";

                        }
                        if (qwerty == 6) {
                            s3 = "R01310";

                        }
                        if (qwerty == 7) {
                            s3 = "R01335";

                        }
                        if (qwerty == 8) {
                            s3 = "R01350";

                        }
                        if (qwerty == 9) {
                            s3 = "R01535";

                        }
                        if (qwerty == 10) {
                            s3 = "R01589";

                        }
                        if (qwerty == 11) {
                            s3 = "R01625";

                        }
                        if (qwerty == 12) {
                            s3 = "R01700";

                        }
                        if (qwerty == 13) {
                            s3 = "R01720";

                        }
                        if (qwerty == 14) {
                            s3 = "R01770";

                        }
                        if (qwerty == 15) {
                            s3 = "R01775";

                        }
                        if (qwerty == 15) {
                            s3 = "R01820";

                        }

                        if (Integer.parseInt(u6) > Integer.parseInt(u5) || (Integer.parseInt(u6) == Integer.parseInt(u5) && Integer.parseInt(u4) > Integer.parseInt(u3))
                                || (Integer.parseInt(u6) == Integer.parseInt(u5) && Integer.parseInt(u4) == Integer.parseInt(u3) && Integer.parseInt(u2) >= Integer.parseInt(u1))
                                ) {
                            myDrawPanel = new MyDrawPanel(u1 + "/" + u3 + "/" + u5, u2 + "/" + u4 + "/" + u6, s3);
                            JFrame jFrame2 = new JFrame();
                            jFrame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            jFrame2.getContentPane().add(myDrawPanel);
                            if(myDrawPanel.DateX.length>500)
                            jFrame2.setSize(myDrawPanel.DateX.length+150, myDrawPanel.CurruncY.length+150);
                            if(myDrawPanel.DateX.length<500)
                                jFrame2.setSize(600,600);
                            jFrame2.setVisible(true);
                            jFrame2.addWindowListener(new WindowListener() {

                                public void windowActivated(WindowEvent event) {

                                }

                                public void windowClosed(WindowEvent event) {

                                }

                                public void windowClosing(WindowEvent event) {
                                    Object[] options = {"Да", "Нет!"};
                                    int n = JOptionPane
                                            .showOptionDialog(event.getWindow(), "Закрыть окно?",
                                                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE, null, options,
                                                    options[0]);
                                    if (n == 0) {
                                        event.getWindow().setVisible(false);
                                        event.getWindow().dispose();
                                    }
                                }

                                public void windowDeactivated(WindowEvent event) {

                                }

                                public void windowDeiconified(WindowEvent event) {

                                }

                                public void windowIconified(WindowEvent event) {

                                }

                                public void windowOpened(WindowEvent event) {

                                }

                            });
                        } else {
                            JOptionPane.showMessageDialog(null, "Нарушена последовательность дат", "Error", JOptionPane.YES_NO_CANCEL_OPTION);
                        }
                    }

                    }catch(Exception es){
                        System.out.print(es);
                    }
                }

        });


    }

    public static void main(String[] args) throws Exception{
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    CourseGUI courseGUI = new CourseGUI();
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    courseGUI.go();
                }catch (Exception e2){

                }
            }
        });
    }

}
