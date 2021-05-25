package lab5.data;

/**
 * Enum for semester number
 */
public enum Semester {
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH;

    /**
     * Function get nameList
     * @return String list of all elements
     */
    public static String nameList() {
        String list = "";
        for (Semester sem : values()){
            list += sem.name() + ", ";
        }
        return list.substring(0, list.length() - 2);
    }
}
