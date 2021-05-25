package general.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Class like person, but can be sent and without any data witch will be generated
 */
public class RowStudyGroup  implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Integer expelledStudents; //Значение поля должно быть больше 0, Поле может быть null
    private Long averageMark; //Значение поля должно быть больше 0, Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    /**
     * Constructor, just set class
     * @param nm Name
     * @param coord Coordinates
     * @param studCo Students count
     * @param expSt Expelled students
     * @param avrMa Average mark
     * @param semEn Semester
     * @param gradm Group admin
     */
    public RowStudyGroup(String nm, Coordinates coord, Integer studCo, Integer expSt, Long avrMa, Semester semEn, Person gradm)
    {
        name = nm;
        coordinates = coord;
        studentsCount = studCo;
        expelledStudents = expSt;
        averageMark = avrMa;
        semesterEnum = semEn;
        groupAdmin = gradm;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public Integer getExpelledStudents() {
        return expelledStudents;
    }

    public Long getAverageMark() {
        return averageMark;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    @Override
    public String toString() {
        return "Учебная группа {" +
                ", имя = '" + name + '\'' +
                ", Координаты = " + coordinates.toString() +
                ", Количество студентов = " + studentsCount.toString() +
                ", Количество отчисленных студентов = " + expelledStudents.toString() +
                ", Средняя оценка = " + averageMark.toString() +
                ", Семестр = " + semesterEnum.toString() +
                ", Админ = " + groupAdmin.toString() +
                '}';
    }
}
