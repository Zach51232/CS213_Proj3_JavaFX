package uintTesting;

/**
 * The Profile class holds name, major, and fulltime of a student we'll use the profile in
 *
 * @author Zachary Goldman, Isaac Brukhman
 */
public class Profile {

    private boolean fullTime;
    private String name;
    private Major major;

    /**
     * empty constructor
     */
    public Profile() {
    }

    /**
     * constructor for testing since fullTime is omitted
     *
     * @param name  of the student
     * @param major of the student
     */
    public Profile(String name, Major major) {
        this.name = name;
        this.major = major;
    }

    /**
     * constructor with all variables
     *
     * @param name     of the student
     * @param major    of the student
     * @param fullTime status of the student
     */
    public Profile(String name, Major major, boolean fullTime) {
        this.fullTime = fullTime;
        this.name = name;
        this.major = major;
    }

    /**
     * equals() method checks if passed in profile is an instance of profile and has the same name/major
     *
     * @param obj that we are checking if equal to our profile
     * @return boolean true if same name and major and also a profile type, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile newProfile = (Profile) obj;
            return newProfile.name.equals(this.name) && newProfile.major.equals(this.major);
        }
        return false;
    }

    /**
     * toString() method returns the name and major in the profile
     *
     * @return name+:major
     */
    @Override
    public String toString() {
        return name + ":" + major;
    }

    /**
     * getFullTime() returns if our profile is fullTime or not
     *
     * @return fullTime true if we're fulltime, false if not
     */
    public boolean getFullTime() {
        return fullTime;
    }

    /**
     * getName() returns the name from our profile
     *
     * @return name of our profile
     */
    public String getName() {
        return name;
    }

    /**
     * getMajor() returns the major from our profile
     *
     * @return major of our profile
     */
    public Major getMajor() {
        return major;
    }

}
