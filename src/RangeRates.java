import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RangeRates {
    private String codeCur;
    private Date startDate, endDate;

    //Constructor
    RangeRates(Date startDate, Date endDate, String codeCur) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.codeCur = codeCur;
    }

    public ArrayList<DateCurrency> getRates() throws Exception {

        String url = formatUrl(startDate, endDate);
        ArrayList<DateCurrency> sourceList = parseXML(url, startDate, endDate);

        return sourceList;
    }

    private String formatUrl(Date startDate, Date endDate){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        String codeCur = this.codeCur;
        String startStr = format.format(startDate);
        String endStr = format.format(endDate);
        String urlRange = String.format("http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", startStr, endStr, codeCur);

        System.out.println(urlRange);

        return urlRange;
    }

    private ArrayList<DateCurrency> parseXML(String xmlInput, Date startDate, Date endDate) {
        ArrayList<DateCurrency> curList = new ArrayList<DateCurrency>();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlInput);

            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    Date dateCur = format.parse(eElement.getAttribute("Date"));
                    String curStr = eElement.getElementsByTagName("Value").item(0).getTextContent();
                    Float curFloat = new Float(curStr.replaceAll(",","."));

                    DateCurrency curObj = new DateCurrency(dateCur, curFloat);
                    curList.add(curObj);
                }
            }
            //If values available
            curList = getRangeDate(curList, startDate, endDate);

            System.out.println("days records: " + nList.getLength());

            for(int i = 0;i <= curList.size() - 1; i++){ //Print target array
                DateCurrency el = curList.get(i);
                System.out.println("date:" + format.format(el.date) + "; cur:" + el.cur);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return curList;
    }

    private ArrayList<DateCurrency> getRangeDate(ArrayList<DateCurrency> curList, Date startDate, Date endDate){
        SimpleDateFormat formatXML = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat formatREST = new SimpleDateFormat("dd/MM/yyyy");
        //Если нет курсов на начальную и конечную дату - добавляем
        if (curList.size() == 0 || !curList.get(0).date.equals(startDate)) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                String dailyRest = String.format("http://www.cbr.ru/scripts/XML_daily.asp?date_req=%s", formatREST.format(startDate));
                System.out.println("Get daily cur... " + dailyRest);

                Document doc = dBuilder.parse(dailyRest);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("Valute");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    Element eElement = (Element) nNode;

                    //Парсим нужный codeCur
                    if (this.codeCur.equals(eElement.getAttribute("ID"))){
                        String curStr = eElement.getElementsByTagName("Value").item(0).getTextContent();
                        Float curFloat = new Float(curStr.replaceAll(",","."));

                        DateCurrency curObj = new DateCurrency(startDate, curFloat);
                        curList.add(0, curObj);
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Если последний элемент не равен конечной дате, добавим его с таким же курсом
        boolean lEl = curList.get(curList.size() - 1).date.equals(endDate);
        if (!lEl) {
            DateCurrency endCur = new DateCurrency(endDate, curList.get(curList.size() - 1).cur);
            curList.add(endCur);
        }

        long diff = curList.get(curList.size() - 1).date.getTime() - curList.get(0).date.getTime();
        long diffDays = TimeUnit.DAYS.convert(diff, MILLISECONDS);

        if(diffDays != curList.size() - 1){
            System.out.println("Records count not equal days");
            ListIterator<DateCurrency> iterList = curList.listIterator();
            DateCurrency next, last = iterList.next();
            while(iterList.hasNext()){
                next = iterList.next();
                if (1 < TimeUnit.DAYS.convert(next.date.getTime() - last.date.getTime(), MILLISECONDS)){
                    next = iterList.previous();
                    //+1 day
                    iterList.add(new DateCurrency(new Date(last.date.getTime() + 60 * 60 * 24 * 1000), last.cur));
                    next = iterList.previous();
                }
                last = next;
            }
        }

        return curList;
    }

}
