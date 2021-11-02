package uintTesting;

import java.util.StringTokenizer;
import java.util.Calendar;

/**
 * The Date class holds the date in mm/dd/yyyy
 *
 * @author Zachary Goldman
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Takes mm/dd/yyyy and creates a albums.Date object
     *
     * @param date in mm/dd/yyyy
     */
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        String month = st.nextToken();
        this.month = Integer.parseInt(month);
        String day = st.nextToken();
        this.day = Integer.parseInt(day);
        String year = st.nextToken();
        this.year = Integer.parseInt(year);
    }

    /**
     * Create an object with the current date
     */
    public Date() {
        Calendar now = Calendar.getInstance();
        this.day = now.get(Calendar.DAY_OF_MONTH);
        this.month = (int) now.get(Calendar.MONTH) + 1;
        this.year = now.get(Calendar.YEAR);
    }

    /**
     * Checks to see if the date is a valid date falling after the start of 2021
     * up until (not including) today, given leap years
     *
     * @return valid, true if a valid date, false otherwise
     */
    public boolean isValid() {
        //Check the month is valid
        if (this.month > 12 || this.month < 1) {
            return false;
        }

        //Check the day is valid
        Boolean dayValid = false;
        if (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7 || this.month == 8 || this.month == 10 || this.month == 12) {
            if (this.day <= 31 && this.day >= 1) {
                dayValid = true;
            }
        } else if (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
            if (this.day <= 30 && this.day >= 1) {
                dayValid = true;
            }
        } else if (this.month == 2) {
            if (isLeapYear(this.year)) {
                if (this.day <= 29 && this.day >= 1) {
                    dayValid = true;
                }
            } else {
                if (this.day <= 28 && this.day >= 1) {
                    dayValid = true;
                }
            }
        }
        if (!dayValid) {
            return false;
        }
        //Check the year is valid

        //Not too early or late (before 2021 or after 2021)
        Date now = new Date();
        if (this.year < 2021 || this.year > now.year) {
            return false;
        }
        if (this.year == now.year) {
            if (this.month > now.month) {
                return false;
            }
            //If it's October 2021 but equal or after our day, invalid
            if (this.month == now.month) {
                if (this.day >= now.day) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Compares 2 dates, returns 1 if the earlier date is later than this date,
     * 0 if the same date, -1 if earlier
     *
     * @param date representing the date of the payment we're comparing to this date
     * @return int that is 1 if later, 0 if the same day, -1 if earlier.
     */
    @Override
    public int compareTo(Date date) {
        int theirDay = date.day;
        int theirMonth = date.month;
        int theirYear = date.year;
        if (theirYear >= this.year) {
            if (theirYear > this.year) {
                return 1;
            }
            //Same year
            else if (theirYear == this.year) {
                if (theirMonth > this.month) {
                    return 1;
                }
                //same month
                else if (theirMonth == this.month) {
                    if (theirDay > this.day) {
                        return 1;
                    }
                    //same day
                    else if (theirDay == this.day) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * Returns true if a leap year, false if not.
     *
     * @param year in question
     * @return true if leap year, false if not.
     */
    private boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0) {
            return true;
        }
        if (year % 400 == 0) {
            return true;
        }
        return false;
    }

    /**
     * Main method
     *
     * @param args from command line, not given in our class.
     */
    public static void main(String[] args) {
    }

    /**
     * Gets the day of the payment in a String
     *
     * @return this.day
     */
    private String getDay() {
        return "" + this.day;
    }

    /**
     * Gets the month of the payment in a String
     *
     * @return this.month
     */
    private String getMonth() {
        return "" + this.month;
    }

    /**
     * Gets the year of the payment in a String
     *
     * @return this.year
     */
    private String getYear() {
        return "" + this.year;
    }

    /**
     * Gets the month/day/year of the payment in a String, empty if hasn't been set yet
     *
     * @return dateInFormat of mm/dd/yyyy
     */
    public String getDate() {
        String dateInFormat = getMonth() + "/" + getDay() + "/" + getYear();

        if (month == 0 || day == 0 || year == 0) {
            dateInFormat = "--/--/--";
        }

        return dateInFormat;
    }
}