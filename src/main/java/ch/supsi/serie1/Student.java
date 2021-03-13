package ch.supsi.serie1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Student {
    private String firstName;
    private String lastName;
    private Calendar dateBorn;

    public Student(String firstName, String lastName,String dateInput) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateBorn = initDate(dateInput);
    }

    @Override
    public String toString() {
        return "" + firstName + " " + lastName + " " + "("+getAge()+")";
    }

    //Metodo che inizializza la date di nascita
    public Calendar initDate(String dateInput){
        int anno = Integer.parseInt(dateInput.substring(6,10));
        int mese = Integer.parseInt(dateInput.substring(3,5));
        int giorno = Integer.parseInt(dateInput.substring(0,2));
        return new GregorianCalendar(anno,mese,giorno);
    }

    //Metodo che restituisce gli anni dello studente
    public int getAge(){
        Calendar attuale = new GregorianCalendar();
        return attuale.get(Calendar.YEAR) - dateBorn.get(Calendar.YEAR);
    }
}
