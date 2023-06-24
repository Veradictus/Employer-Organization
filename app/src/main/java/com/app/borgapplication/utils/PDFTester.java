package com.app.borgapplication.utils;

public class PDFTester {

    private String e1 = "Cat Malfur";
    private String e2 = "David Palto";
    private String e3 = "Analog Ventr";
    private String e4 = "Jesherin Rondeau";


    private CalendarMonth testMonth;

    public PDFTester(int month, int year) {
//        this.testMonth = new CalendarMonth(24, returnMonth(month), year ,1);
//        loadMonth();

    }

    private String returnMonth(int month){
        switch (month) {
            case 0: return "January";
            case 1: return "February";
            case 2: return "March";
            case 3: return "April";
            case 4: return "May";
            case 5: return "June";
            case 6: return "July";
            case 7: return "August";
            case 8: return "September";
            case 9: return "October";
            case 10: return "November";
            case 11: return "December";
        }
        return "No";
    }


    public CalendarMonth getTestMonth() {return this.testMonth;}

    private String pickShift(){
        int random_int = (int)(Math.random() * (3 - 1 + 1) + 1);

        switch (random_int)
        {
            case 1:
                return "Morning";
            case 2:
                return "Evening";
            case 3:
                return "Both";
        }

        return "Error";

    }

//    private void loadMonth() {
//
//
//        testMonth.addDay();
//        testMonth.addShift(0, e1, pickShift());
//        testMonth.addShift(0, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(1, e3, pickShift());
//        testMonth.addShift(1, e4, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(2, e1, pickShift());
//        testMonth.addShift(2, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(3, e1, pickShift());
//        testMonth.addShift(3, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(4, e1, pickShift());
//        testMonth.addShift(4, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(5, e1, pickShift());
//        testMonth.addShift(5, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(6, e1, pickShift());
//        testMonth.addShift(6, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(7, e1, pickShift());
//        testMonth.addShift(7, e2, pickShift());
//
//        testMonth.addDay();
//        testMonth.addShift(8, e1, pickShift());
//        testMonth.addShift(8, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(9, e1, pickShift());
//        testMonth.addShift(9, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(9, e1, pickShift());
//        testMonth.addShift(9, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(9, e1, pickShift());
//        testMonth.addShift(9, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(10, e1, pickShift());
//        testMonth.addShift(10, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(11, e1, pickShift());
//        testMonth.addShift(11, e2, pickShift());
//
//
//        testMonth.addDay();
//        testMonth.addShift(12, e1, pickShift());
//        testMonth.addShift(12, e2, pickShift());
//
//
//
//        testMonth.addDay();
//        testMonth.addShift(13, e1, pickShift());
//        testMonth.addShift(13, e2, pickShift());
//
//
//
//        testMonth.addDay();
//        testMonth.addShift(14, e1, pickShift());
//        testMonth.addShift(14, e2, pickShift());
//
//    }
}
