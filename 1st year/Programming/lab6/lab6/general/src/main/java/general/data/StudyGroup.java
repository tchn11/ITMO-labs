package general.data;

import java.util.Date;

/**
 * Main collection element, study group
 */
public class StudyGroup {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле может быть null
    private Integer expelledStudents; //Значение поля должно быть больше 0, Поле может быть null
    private Long averageMark; //Значение поля должно быть больше 0, Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    /**
     * Constructor, just set class
     * @param Id ID
     * @param nm Name
     * @param coord Coordinates
     * @param crDt Creation date
     * @param studCo Students count
     * @param expSt Expelled students
     * @param avrMa Average mark
     * @param semEn Semester
     * @param gradm Group admin
     */
    public StudyGroup(Integer Id, String nm, Coordinates coord, Date crDt, Integer studCo, Integer expSt, Long avrMa, Semester semEn, Person gradm)
    {
        id = Id;
        name = nm;
        coordinates = coord;
        creationDate = crDt;
        studentsCount = studCo;
        expelledStudents = expSt;
        averageMark = avrMa;
        semesterEnum = semEn;
        groupAdmin = gradm;
    }

    public StudyGroup(Integer Id,RowStudyGroup sg){
        id = Id;
        name = sg.getName();
        coordinates = sg.getCoordinates();
        creationDate = new Date();
        studentsCount = sg.getStudentsCount();
        expelledStudents = sg.getExpelledStudents();
        averageMark = sg.getAverageMark();
        semesterEnum = sg.getSemesterEnum();
        groupAdmin = sg.getGroupAdmin();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
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

    public long getNum(){
        return (id + studentsCount + expelledStudents + averageMark);
    }

    @Override
    public String toString() {
        return "Учебная группа {" +
                "ID = " + id.toString() +
                ", имя = '" + name + '\'' +
                ", Координаты = " + coordinates.toString() +
                ", Дата создания = " + creationDate.toString() +
                ", Количество студентов = " + studentsCount.toString() +
                ", Количество отчисленных студентов = " + expelledStudents.toString() +
                ", Средняя оценка = " + averageMark.toString() +
                ", Семестр = " + semesterEnum.toString() +
                ", Админ = " + groupAdmin.toString() +
                '}';
    }
}
