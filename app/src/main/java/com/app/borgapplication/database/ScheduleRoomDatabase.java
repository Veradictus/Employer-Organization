package com.app.borgapplication.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.borgapplication.database.impl.AvailabilityDao;
import com.app.borgapplication.database.impl.AvailabilityTable;
import com.app.borgapplication.database.impl.Converters;
import com.app.borgapplication.database.impl.DateDao;
import com.app.borgapplication.database.impl.DateTable;
import com.app.borgapplication.database.impl.EmployeeDao;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.impl.ShiftDao;
import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.database.impl.TrainingDao;
import com.app.borgapplication.database.impl.TrainingTable;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = { EmployeeTable.class, AvailabilityTable.class, DateTable.class, TrainingTable.class, ShiftTable.class }, version = 9, exportSchema = true)
@TypeConverters({Converters.class})
public abstract class ScheduleRoomDatabase extends RoomDatabase {

    // This is how many random employees we're generating.
    private final static int EMPLOYEE_GENERATE_COUNT = 30;

    public abstract EmployeeDao employeeDao();
    public abstract AvailabilityDao availabilityDao();
    public abstract DateDao dateDao();
    public abstract TrainingDao trainingDao();
    public abstract  ShiftDao shiftDao();

    private static volatile ScheduleRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ScheduleRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScheduleRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScheduleRoomDatabase.class, "scheduling_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {

            databaseWriteExecutor.execute(() -> {

                EmployeeDao employeeDao = INSTANCE.employeeDao();
                AvailabilityDao availabilityDao = INSTANCE.availabilityDao();
                DateDao dateDao = INSTANCE.dateDao();
                TrainingDao trainingDao = INSTANCE.trainingDao();
                ShiftDao shiftDao = INSTANCE.shiftDao();

                // --- POPULATE THE DATE TABLE WITH INFORMATION
                // --- Start Date, Dec 1, 2020
                Calendar inDate = Calendar.getInstance();
                inDate.set(2020, 11, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2021, 11, 1);
                long dateEpoch = Converters.dateToTimestamp(inDate);
                int yearNum = inDate.get(Calendar.YEAR);
                int monthNum = inDate.get(Calendar.MONTH) + 1;
                int dayOfWeek = dayOfWeek = inDate.get(Calendar.DAY_OF_WEEK) - 1;
                int dateNum = inDate.get(Calendar.DAY_OF_MONTH);
                String dateStr = Integer.toString(yearNum) + "-" + Integer.toString(monthNum) + "-" + Integer.toString(dateNum);
                String dayStr = getDay(inDate.get(Calendar.DAY_OF_WEEK));
                String monthStr = getMonth(inDate.get(Calendar.MONTH));
                int i = 0;
                while (inDate.compareTo(endDate) <= 0) {
                    DateTable curDate = new DateTable(inDate, dateEpoch, dateStr, yearNum, monthNum, dateNum, dayOfWeek, monthStr, dayStr);
                    dateDao.insert(curDate);
                    inDate.add(Calendar.DATE, 1);
                    dateEpoch = Converters.dateToTimestamp(inDate);
                    yearNum = inDate.get(Calendar.YEAR);
                    monthNum = inDate.get(Calendar.MONTH) + 1;
                    dateNum = inDate.get(Calendar.DAY_OF_MONTH);
                    dateStr = Integer.toString(yearNum) + "-" + Integer.toString(monthNum ) + "-" + Integer.toString(dateNum);
                    dayStr = getDay(inDate.get(Calendar.DAY_OF_WEEK));
                    dayOfWeek = inDate.get(Calendar.DAY_OF_WEEK) - 1;
                    monthStr = getMonth(inDate.get(Calendar.MONTH));
                }

                for (i = 0; i < EMPLOYEE_GENERATE_COUNT; i++) {

                    String firstName = getFirstName(new Random());
                    String lastName = getLastName(new Random());
                    String email = getEmail(firstName.toLowerCase(), lastName.toLowerCase(), new Random());

                    EmployeeTable employee = new EmployeeTable(firstName, lastName, email);
                    employeeDao.insert(employee);
//
//                  // -------------- Create an entry in Availability for each employee

                    Random random = new Random();
                    String[] trainingStatus = { "Open", "Close", "Both", "None" };
                    int employee_id = employeeDao.getEmployeeId(email);
                    int randomStatus = random.nextInt(4);

                    for (int j = 0; j < 7; j++)
                        availabilityDao.insert(new AvailabilityTable(employee_id, j));

                    // ------------- Create an entry in Training for each employee
                    // TODO
                    trainingDao.insert(new TrainingTable(employee_id, trainingStatus[randomStatus]));
                }
            });

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            String backupDBPath = db.getPath();
            System.out.println(backupDBPath + "\n");
            super.onOpen(db);

        }


        public String getDay(int day_of_week) {
            String dayStr;
            switch (day_of_week) {
                case 1:
                    dayStr = "Sunday";
                    return dayStr;
                case 2:
                    dayStr = "Monday";
                    return dayStr;
                case 3:
                    dayStr = "Tuesday";
                    return dayStr;
                case 4:
                    dayStr = "Wednesday";
                    return dayStr;
                case 5:
                    dayStr = "Thursday";
                    return dayStr;
                case 6:
                    dayStr = "Friday";
                    return dayStr;
                case 7:
                    dayStr = "Saturday";
                    return dayStr;
            }
            return null;
        }

        public String getMonth(int month_number) {
            String monthStr;

            switch (month_number) {
                case 0:
                    monthStr = "January";
                    return monthStr;
                case 1:
                    monthStr = "February";
                    return monthStr;
                case 2:
                    monthStr = "March";
                    return monthStr;
                case 3:
                    monthStr = "April";
                    return monthStr;
                case 4:
                    monthStr = "May";
                    return monthStr;
                case 5:
                    monthStr = "June";
                    return monthStr;
                case 6:
                    monthStr = "July";
                    return monthStr;
                case 7:
                    monthStr = "August";
                    return monthStr;
                case 8:
                    monthStr = "September";
                    return monthStr;
                case 9:
                    monthStr = "October";
                    return monthStr;
                case 10:
                    monthStr = "November";
                    return monthStr;
                case 11:
                    monthStr = "December";
                    return monthStr;

            }
            return null;
        }

        final String[] firstNames = { "Jessie", "McCree", "John", "Rebecca", "Bob", "Becky", "Andrew", "Jonathan", "Justin", "Jenny",
            "Jared", "Amelia", "Jacob", "Annie", "McLovin", "Devon", "Joseph", "Ronnie", "Mercy", "Sombra", "Erin", "Jesherin",
            "Mercedes", "Garet", "Nicholas", "Winston", "Anne", "Anna", "Erick", "Ericka", "Scarlett", "Willy", "Patricia",
            "Torie", "Clotilde", "Reena", "Lisandra", "Rich", "Kristopher", "Bryce", "Russel", "Alexis", "Ursula", "Claud",
            "Stewart", "Steward", "Grover", "Gaston", "Timothy", "Adam", "Carmel", "Pamela", "Lily", "Zana", "Flo", "Rosaline" };

        final String[] lastNames = { "McLaughlin", "Enrique", "Iglesias", "Laughlin", "Rondeau", "Wu", "Poenaru", "Sabine", "Ha",
            "Doe", "Almarvez", "Nyugen", "Angel", "Smith", "Frisk", "Wood", "Steel", "Carter", "Tandy", "Vanhorn", "Hottinger",
            "Matsuo", "Ohler", "Rendon", "Ellerbe", "Swindoll", "Cassette", "Denker", "Wang", "Lundholm", "Cawthon", "Carbonell",
            "Buttner", "Ciampa", "Mcduffee", "Mitton", "Lykins", "Jenkins", "Kitchen", "Ching", "Amesquita", "Maurice", "Engels",
            "Shahan", "Roosa", "Musante" };

        final String[] emailProviders = { "@yahoo.com", "@mymacewan.ca", "@outlook.com", "@gmail.com" };

        private String getFirstName(Random random) {
            return firstNames[random.nextInt(firstNames.length)];
        }

        private String getLastName(Random random) {
            return lastNames[random.nextInt(lastNames.length)];
        }

        private String getEmail(String firstName, String lastName, Random random) {
            return firstName.concat(".").concat(lastName).concat(emailProviders[random.nextInt(emailProviders.length)]);
        }
    };
}
