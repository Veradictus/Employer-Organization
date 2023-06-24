package com.app.borgapplication.returnCalander;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.app.borgapplication.database.impl.JoinMonthlyShiftInfo;
import com.app.borgapplication.utils.CalendarMonth;
import com.app.borgapplication.utils.DayInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class Schedule {

    public PdfDocument doc;
    private PageInfo docInfo;

    private PdfDocument.Page docPage;
    private Paint lines;
    private String text;
    private Integer year;
    private String monthName;
    private List<JoinMonthlyShiftInfo> month;
    private Integer firstOfMonth;
    private int lastDay;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Schedule(CalendarMonth month) {

        //Create pdf
        this.doc = new PdfDocument();

        this.docInfo = new PageInfo.Builder(1000,1200,1).create();
        this.docPage = doc.startPage(docInfo);
        this.lines = new Paint();
        this.month = month.monthsShifts;
        this.monthName = month.getMonth();
        this.year = month.getYear();
        this.firstOfMonth = month.getDayOfFirst();
        this.lastDay = month.getNumOfDay();


    }

    public void makeSchedule() throws IOException {

        StringBuilder fileName = new StringBuilder();

        fileName.append(this.monthName);
        fileName.append(", ");
        fileName.append(this.year);


        lines.setTextSize(40);
        docPage.getCanvas().drawText(fileName.toString(), 400, 100, lines);
        lines.setTextSize(11);

        drawChart(docPage.getCanvas());

        doc.finishPage(docPage);


        doc.writeTo(getOutputStream());

        doc.close();
    }

    private void drawChart(Canvas canvas) {

        int l = 0;

        int startX = 15;
        int endX = 150;

        int split = 245;
        int startY = 215;
        int endY = 400;

        int date = 1;

        int incEmpPrint = 10;

        int numOfSquares = 1;

        int incX = 0;
        int incY = 0;

        System.out.println("------------");
        System.out.println(month.size());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                lines.setColor(Color.BLACK);
                lines.setStrokeWidth(2);
                canvas.drawRect((startX + incX), (startY + incY), (endX + incX), (endY + incY), lines);

                lines.setColor(Color.WHITE);
                lines.setStrokeWidth(3);
                canvas.drawRect(((startX + incX) + 2), ((startY + incY) + 2), ((endX + incX) - 2), ((endY + incY) - 2), lines);

                lines.setColor(Color.BLACK);
                lines.setStrokeWidth(2);
                canvas.drawRect((startX + incX), (startY + incY), (endX + incX), (split + incY), lines);

                lines.setColor(Color.WHITE);
                lines.setStrokeWidth(3);
                canvas.drawRect(((startX + incX) + 2), ((startY + incY) + 2), ((endX + incX) - 2), ((split + incY) - 2), lines);

                if (numOfSquares >= firstOfMonth) {
                    lines.setColor(Color.BLACK);
                    canvas.drawText(String.valueOf(date), ((startX + incX) + 60), ((split + incY) - 10), lines);

                    if (month.size() > l) {

                        while (month.get(l).getDayNumber() == date) {

                            StringBuilder info = new StringBuilder();

                            info.append(month.get(l).getEmployeeName());
                            info.append(": ");
                            info.append(month.get(l).getShift_type());

                            canvas.drawText(info.toString(), ((startX + incX) + 10), ((split + incY + incEmpPrint)), lines);
                            incEmpPrint += 10;
                            l++;

                            if (month.size() <= l) {
                                break;
                            }
                        }
                    }
                    incEmpPrint = 10;
                    date ++;
                }

                incX += (endX - startX);
                numOfSquares ++;
                if (date > this.lastDay) {
                    return;
                }
            }

            incX = 0;
            incY += (endY - startY);

        }

    }


    private FileOutputStream getOutputStream() {

        String path = String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));

        StringBuilder fileName = new StringBuilder();

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        fileName.append("sched");
        fileName.append("_");
        fileName.append(this.monthName);
        fileName.append("_");
        fileName.append(calendar.get(Calendar.HOUR));
        fileName.append("_");
        fileName.append(calendar.get(Calendar.MINUTE));
        fileName.append("_");
        fileName.append(calendar.get(Calendar.SECOND));
        fileName.append(".pdf");

        System.out.println(fileName.toString());

        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();

        File file = new File(dir, fileName.toString());
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fOut;
    }
}
