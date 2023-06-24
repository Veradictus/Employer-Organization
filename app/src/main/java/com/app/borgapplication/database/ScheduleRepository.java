package com.app.borgapplication.database;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.app.borgapplication.database.impl.AvailabilityDao;
import com.app.borgapplication.database.impl.AvailabilityTable;
import com.app.borgapplication.database.impl.DateTable;
import com.app.borgapplication.database.impl.EmployeeDao;
import com.app.borgapplication.database.impl.EmployeeTable;
import com.app.borgapplication.database.impl.JoinDailyAvailable;
import com.app.borgapplication.database.impl.JoinEmployeeInformation;
import com.app.borgapplication.database.impl.JoinEmployeesLeftBehind;
import com.app.borgapplication.database.impl.JoinMonthlyShiftInfo;
import com.app.borgapplication.database.impl.JoinShiftDateInfo;
import com.app.borgapplication.database.impl.JoinShiftReport;
import com.app.borgapplication.database.impl.ShiftDao;
import com.app.borgapplication.database.impl.ShiftTable;
import com.app.borgapplication.database.impl.TrainingDao;
import com.app.borgapplication.database.impl.TrainingTable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleRepository {
        public EmployeeDao employeeDao;
        public AvailabilityDao availabilityDao;
        public TrainingDao trainingDao;
        public ShiftDao shiftDao;

        public LiveData<List<EmployeeTable>> allEmployees;
        public LiveData<List<AvailabilityTable>> allAvailabilities;
        public LiveData<List<JoinEmployeeInformation>> employeeAvailability;

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        // See the BasicSample in the android-architecture-components repository at
        // https://github.com/googlesamples
        public ScheduleRepository(Application application) {
            ScheduleRoomDatabase db = ScheduleRoomDatabase.getDatabase(application);
            employeeDao = db.employeeDao();
            availabilityDao = db.availabilityDao();
            trainingDao = db.trainingDao();
            shiftDao = db.shiftDao();

            allEmployees = employeeDao.getAlphabetizedLastNames();
            allAvailabilities = availabilityDao.getAllAvailabilities();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<EmployeeTable>> getAllEmployees() {
            return allEmployees;
        }

        public LiveData<List<AvailabilityTable>> getAllAvailabilities() {
            return allAvailabilities;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insertEmployee(EmployeeTable employee) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() -> {
                employeeDao.insert(employee);
            });
        }

        public void insertEmployeeInformation(EmployeeTable employee, ArrayList<AvailabilityTable> availability, TrainingTable training) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() -> {
                try {
                    employeeDao.insert(employee);
                    int id = getEmployeeByEmail(employee.getEmail());
                    training.setEmployee_id(id);
                    System.out.println(training.getEmployee_id() + training.getTraining());
                    trainingDao.insert(training);
                    for (int i = 0; i < availability.size(); i++) {
                        availability.get(i).setEmployee_id(id);
                        availabilityDao.insert(availability.get(i));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        public void insertShift(ShiftTable shift) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() -> {
                shiftDao.insert(shift);
            });
        }


        public LiveData<List<JoinMonthlyShiftInfo>> getMonthlySchedule(long start, long end) {
            return shiftDao.getMonthlySchedule(start, end);
        }

        public LiveData<List<JoinShiftReport>> getShiftReport(long start, long end, String shiftType) {
            return shiftDao.getShiftReport(start, end, shiftType);
        }

        public LiveData<List<JoinEmployeesLeftBehind>> getEmployeesLeftBehind(long startDate, long endDate) {
            return shiftDao.getEmployeesNotScheduled(startDate, endDate);
        }

        public LiveData<List<DateTable>> getShiftsNotScheduled(long startDate, long endDate) {
            return shiftDao.getDaysWithNoShifts(startDate, endDate);
        }

        public LiveData<List<JoinShiftDateInfo>> getShiftOnDate(Calendar date) {
            int year = date.get(date.YEAR);
            int month = date.get(date.MONTH) + 1;
            int day = date.get(date.DAY_OF_MONTH);
            return shiftDao.getShiftOnDate(year,month,day);
        }

        public void insertAvailability(AvailabilityTable employee_availability) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() -> {
                availabilityDao.insert(employee_availability);
            });
        }

        //Method to write updates to Employee table in the database
        public void update(EmployeeTable employee, TrainingTable training) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() ->{
                employeeDao.updateEmployee(employee);
                trainingDao.update(training);
            });
        }

        public void delete(EmployeeTable employee){
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() ->{
                employeeDao.deleteEmployee(employee);
            });
        }

        public void deleteShift(ShiftTable shift) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() -> {
                shiftDao.delete(shift);
            });
        }

        public void updateAvailability(AvailabilityTable availability) {
            ScheduleRoomDatabase.databaseWriteExecutor.execute(() ->{
               availabilityDao.updateAvailability(availability);
            });
        }

        public int getEmployeeByEmail(String email) {
            return employeeDao.getEmployeeId(email);
        }

        public LiveData<List<EmployeeTable>> getEmployeeIdByEmail(String email) {
            return employeeDao.getEmployeeByEmail(email);
        }

        public LiveData<List<EmployeeTable>> findEmployeeById(int primaryKey) {
            return employeeDao.findEmployeeById(primaryKey);
        }

        public LiveData<List<JoinDailyAvailable>> getDailyAvailability(int dayOfWeek, int dayNum, int monthNum, int yearNum) {
            return employeeDao.dailyAvailability(dayOfWeek, dayNum, monthNum, yearNum);
        }

        public LiveData<List<AvailabilityTable>> getEmployeeAvailability (int employee_id) {
            return availabilityDao.getAvailabilityById(employee_id);
        }

        public LiveData<List<JoinEmployeeInformation>> getEmployeeInformation () {
            return employeeDao.getEmployeeInformation();
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

}

