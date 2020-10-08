package goid.kotajambi.puskesmas_ngadu.adapter;

public class Mydate {

    public static String konversi_bulan(String month){
        if (month.equals("01")){
            month="Jan";
        }
        else if (month.equals("02")){
            month="Feb";
        }
        else if (month.equals("03")){
            month="Mar";
        }
        else if (month.equals("04")){
            month="Apr";
        }
        else if (month.equals("05")){
            month="Mei";
        }
        else if (month.equals("06")){
            month="Jni";
        }
        else if (month.equals("07")){
            month="Jli";
        }
        else if (month.equals("08")){
            month="Agu";
        }
        else if (month.equals("09")){
            month="Sep";
        }
        else if (month.equals("10")){
            month="Okt";
        }
        else if (month.equals("11")){
            month="Nov";
        }
        else if (month.equals("12")){
            month="Des";
        }
        return month;
    }
}
