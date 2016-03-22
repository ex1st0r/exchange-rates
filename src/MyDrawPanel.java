package BankGUI;

import javafx.scene.text.*;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Arc2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class MyDrawPanel extends JPanel{
    int moveX,moveY,wheelRotation,newX,newY,siz=0;
    Font f;
    ArrayList<DateCurrency> arrList,asLis;
    String msg;
    int[] DateX,CurruncY;
    float[] CurrY;
    ArrayList<Point> YY;
    JTextField f1,g1;
    MyDrawPanel(String startD,String endD,String rang) throws Exception{
        addMouseMotionListener(new MyMouseMotion());
        addMouseWheelListener(new MyMouseWheel());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date startDate = format.parse(startD);//"01/02/2016"
        Date endDate = format.parse(endD);//"09/03/2016"
        RangeRates obj = new RangeRates(startDate, endDate, rang);
        arrList = obj.getRates();
        DateX=new int[arrList.size()];

        asLis=new ArrayList<DateCurrency>();
        for(int i=0;i<arrList.size();i++)
            asLis.add(arrList.get(i));
        for(int u=0;u<asLis.size();u++) {
            for (int i = 0; i < asLis.size(); i++) {
                float s = asLis.get(i).cur;
                for (int j = 0; j < asLis.size(); j++) {
                    if (j != i && s == asLis.get(j).cur) asLis.remove(j);
                }
            }
        }
        CurruncY=new int[asLis.size()];
        CurrY=new float[asLis.size()];
        for(int i=0;i<asLis.size();i++)
            CurrY[i]=asLis.get(i).cur;

        for(int i=0;i<CurrY.length;i++) {
            for(int j=0;j<CurrY.length-1;j++){
                    if(CurrY[j]>CurrY[j+1]){
                        float s=CurrY[j];
                        CurrY[j]=CurrY[j+1];
                        CurrY[j+1]=s;
                    }
            }
        }
        for(int i=0;i<CurrY.length;i++)
        System.out.println(CurrY[i]);

        }
    public void paint(Graphics g) {
        int delX, delY;
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawLine(5, this.getHeight() - 5, this.getWidth(), this.getHeight() - 5);
        g.drawLine(5, this.getHeight() - 5, 5, 0);

        int k = 0;
        int e=0;
        for (int i = (getWidth()-20) / arrList.size(); i <= (getWidth()-20); i = i + (getWidth()-20) / arrList.size()) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(5+i, getHeight(), 5+i, getHeight()-5);
            e++;
            if(e<=arrList.size()) {
                DateX[k] = 5+i;
                k++;
            }
        }
        int r = 0;
        int ds=0;
        for (int i = (getHeight()-20) / asLis.size(); i < (getHeight()-20); i = i + (getHeight()-20) / asLis.size()) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(0, getHeight() - 5 - i, 5, getHeight() - 5 - i);
            ds++;
            if(ds<=asLis.size()) {
                CurruncY[r] = getHeight() - 10 - i;
                r++;
            }
        }
        int ewq=0;
        YY=new ArrayList<>();
        for(int i=0;i<arrList.size();i++){
            float s=arrList.get(i).cur;
            for(int j=0;j<CurrY.length;j++){
                if(s==CurrY[j])ewq=j;
            }
            YY.add(new Point(DateX[i],CurruncY[ewq]));

        }
        if(YY.size()==1) {
            Color myColor1 = new Color(47, 0, 127);
            g.setColor(myColor1);
            Graphics2D g21 = (Graphics2D) g;
            g21.setStroke(new BasicStroke(3.0f));
            g.drawLine(5, YY.get(0).y+10, YY.get(0).x, YY.get(0).y+10);
        }
        if(YY.size()>1) {

            for (int i = 0; i < YY.size() - 1; i++) {
                Color myColor = new Color(47, 0, 127);
                g.setColor(myColor);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3.0f));
                g.drawLine(YY.get(i).x, YY.get(i).y+5, YY.get(i + 1).x, YY.get(i + 1).y+5);
            }
            for (int i = 0; i < YY.size(); i++) {
                g.fillOval(YY.get(i).x, YY.get(i).y, 0, 0);
            }
        }

    }
    class MyMouseMotion implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {
            newX=e.getX();
            newY=e.getY();

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            moveX=e.getX();
            moveY=e.getY();
            Locale local = new Locale("ru","RU");
            DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, local);
            setToolTipText(null);
            for(int i=0;i<YY.size();i++) {

                if (((moveX-YY.get(i).x)*(moveX-YY.get(i).x) + (moveY-YY.get(i).y)*(moveY-YY.get(i).y))<=100) {
                    msg = "Дата " + df.format(arrList.get(i).date) + " Значение " + arrList.get(i).cur;
                    //JOptionPane.showMessageDialog(null, "Дата "+df.format(arrList.get(i).date) + " Значение "+ arrList.get(i).cur, "" , JOptionPane.YES_NO_CANCEL_OPTION);
                    setToolTipText(msg);
                    continue;
                }

            }


        }
    }
    class MyMouseWheel implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            wheelRotation=e.getWheelRotation();
        }
    }
}