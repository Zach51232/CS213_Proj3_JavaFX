package uintTesting;

/**
 * The Roster class will hold a list of students and let the user manipulate the list with methods
 *
 * @author Isaac Brukhman
 */
public class Roster {

    private Student[] roster;
    private int size;

    /**
     * Find will linearly search through the roster and return the index if found. If not -1 is returned.
     *
     * @param student takes in an Student to check if it's in our roster
     * @return index that's index of Student if found, -1 otherwise
     */
    private int find(Student student) {
        int index;
        for (index = 0; index < size; index++) {
            if (null == roster[index]) {
                break;
            }
            if (student.getProfileP().equals(roster[index].getProfileP())) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Grow will initiate the size by 4 and will increase roster's capacity by 4 after initialization.
     */
    private void grow() {
        size += 4;
        Student[] plus4 = new Student[size];

        for (int i = 0; i < size - 4; i++) {
            plus4[i] = roster[i];
        }

        roster = plus4;
    }


    /**
     * Add will look for an open spot and put the new Student in. If no spot is found, grow() and add the Student
     * to the array
     *
     * @param student that we're trying to add to the roster
     * @return boolean true if added, false if duplicate
     */
    public boolean add(Student student) {
        if (null == roster) {
            size = 4;
            roster = new Student[size];
        }

        //Return false if the Student
        // is a duplicate
        for (Student a : roster) {
            if (a == null) {
                break;
            }

            if (a.getProfileP().equals(student.getProfileP())) {
                return false;
            }
        }

        // If the array isn't full search for an open spot
        if (null == roster[size - 1]) {
            //Go through it backwards until find the place to put it
            for (int i = size - 1; i >= 0; i--) {

                if (!(null == roster[i])) {
                    roster[i + 1] = student;
                    return true;
                }
            }
            //Completely empty
            roster[0] = student;
            return true;
        } else { //If it's full, grow it and then add at the 1st new spot
            grow();
            roster[size - 4] = student;
            return true;
        }
    }

    /**
     * Remove will shift the array forward from the index of the Student that will be removed.
     *
     * @param student that we're trying to remove from the roster
     * @return boolean true if removes, false if can't find the Student
     */
    public boolean remove(Student student) {

        int i = find(student), len = size;

        //returns false if not there
        if (i == -1) {
            return false;
        }

        // shifts the whole array forward by one starting from the index of the Student
        for (int j = i; j < size - 1; j++) {
            roster[j] = roster[j + 1];
        }

        // set the last index to null since the whole thing was shifted
        roster[size - 1] = null;

        if (null == roster[0]) {   // for if the last item was removed
            roster = null;
            size = 0;
        }
        return true;
    }

    /**
     * gets the student that we are looking for
     *
     * @param newStudent a student with the same Major and name
     * @return Student the student in the roster
     */
    public Student findStudent(Student newStudent) {
        int indexOfStudent = find(newStudent);
        if (indexOfStudent == -1) {
            return null;
        } else {
            return roster[indexOfStudent];
        }
    }

    /**
     * Calculated tuition for the whole roster
     */
    public void calculateTuition() {
        for (int i = 0; i < size; i++) {
            if (null == roster[i]) {
                return;
            }
            roster[i].tuitionDue();
        }
    }

    /**
     * Tries to pay the students tuition and give a timestamp.
     *
     * @param student that we want to find
     * @param amount  that the student will pay
     * @param date    the date that payment was attempted
     * @return true if th payment was less than 10000
     */
    public boolean pay(Student student, double amount, Date date) {
        int i = find(student);
        return roster[i].pay(amount, date);
    }

    /**
     * sets the financial aid of the student to the amount
     *
     * @param student that need financial aid
     * @param amount  that will be aided
     * @return false if the student isn't eligible or the amount is wrong
     */
    public boolean setFinancialAid(Student student, double amount) {
        int i = find(student);
        return roster[i].setFinancialAid(amount);
    }

    /**
     * Set the abroad status to the inputed value if the student is international
     *
     * @param student     the international student we want to flip the
     * @param abroadState if the student is studying abroad
     * @return false if the student was not found or if the credits were more than 12
     */
    public boolean setStudyAbroad(Student student, boolean abroadState) {
        int i = find(student);
        if (i == -1) {
            return false;
        }
        //If found, check that the student is international
        if (roster[i] instanceof International) { // should return true
            return ((International) roster[i]).setStudyAbroad(abroadState);
        }
        return false;
    }

    /**
     * print, prints the whole roster sorted by index.
     * @return string that's printing
     */
    public String print() {
        String output = "";
        if (roster == null) {
            output += "Student roster is empty.\n";
            return output;
        }

        output += "* list of students in the roster **\n";

        for (int i = 0; i < size; i++) {

            if (null == roster[i]) {
                continue;
            } else {
                output += roster[i].toString() + "\n";
            }
        }
        output += "* end of roster **\n";
        return output;
    }

    /**
     * printByName will sort roster alphabetically and then print
     * @return string that's printing
     */
    public String printByName() {
        String output = "";
        if (roster == null) {
            output += "Student roster is empty.\n";
            return output;
        }
        output += "* list of students ordered by name **\n";

        int total = size;
        Student[] sort = new Student[size];

        for (int i = 0; i < total; i++) { // just sets sort = to roster
            if (null == roster[i]) {
                break;
            }
            sort[i] = roster[i];
        }

        boolean sorted = false;

        while (!sorted) {

            sorted = true;
            for (int i = 0; i < total - 1; i++) {
                if (null == sort[i + 1]) {
                    break;
                }
                if (sort[i].toString().compareTo(sort[i + 1].toString()) > 0) {
                    Student temp = sort[i];
                    sort[i] = sort[i + 1];
                    sort[i + 1] = temp;
                    sorted = false;
                }
            }
        }

        for (int i = 0; i < size; i++) {                  // prints the roster
            if (null == sort[i]) {
                continue;
            }
            output += sort[i].toString() + "\n";
        }
        output += "* end of roster **\n";
        return output;
    }

    /**
     * printByTuition will find all the students that paid and sort them by last payment date. Prints the resulting list
     * @return string that's printing
     */
    public String printByTuition() {
        String output = "";
        if (roster == null) {
            output = "Student roster is empty.\n";
            return output;
        }
        output = "* list of students made payments ordered by payment date **\n";

        Student[] sort = new Student[size];
        int sortIndex = 0;

        for (int i = 0; i < size; i++) { // just sets sort = to roster
            if (null == roster[i]) {
                break;
            }
            if (roster[i].hadPaid()) {
                sort[sortIndex] = roster[i];
                sortIndex++;
            }
        }

        boolean sorted = false;

        while (!sorted) {
            sorted = true;
            for (int i = 0; i < size - 1; i++) {
                if (null == sort[i + 1]) {
                    break;
                }
                if (sort[i].getLastPaid().compareTo(sort[i + 1].getLastPaid()) > 0) {
                    Student temp = sort[i];
                    sort[i] = sort[i + 1];
                    sort[i + 1] = temp;
                    sorted = false;
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {                  // prints the roster
            if (null == sort[i]) {
                continue;
            }
            output += sort[i].toString() + "\n";
        }
        output += "* end of roster **\n";
        return output;
    }
    /**
     * getRoster() gives us the roster instance variable that we'll use to check if our
     * roster is empty in MainController
     * @return roster that's our field
     */
    public Student[] getRoster() {
        return roster;
    }
}
