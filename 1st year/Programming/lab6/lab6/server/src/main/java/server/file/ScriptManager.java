package server.file;

import general.data.Coordinates;
import general.data.Person;
import general.data.RowStudyGroup;
import general.data.Semester;
import general.exeptions.EmptyIOException;
import general.exeptions.FileParsingException;
import server.Main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Operates the script file for opening/reading.
 */
public class ScriptManager {
    private Scanner scriptReader;

    /**
     * Constructor, open script file
     * @param path Path to script file
     */
    public ScriptManager(String path){
        try {
            scriptReader = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            Main.logger.error("Фаил не найден:" + path);
        }
    }

    /**
     * Read next line from script
     * @return Line in String
     */
    public String nextLine(){
        try {
            return scriptReader.nextLine().trim();
        } catch (NullPointerException e){
            return "exit";
        } catch (NoSuchElementException e){
            return "exit";
        }
    }

    /**
     * Method that ask group from file
     * @return Group
     */
    public RowStudyGroup askGroup()
    {

        try {

            String name = parseString();
            if (name == null)
                throw new FileParsingException();

            long x;

            x = parseLong();
            if (x > 752) {
                return null;
            }

            if (x == 0)
                throw new FileParsingException();

            int y = parseInteger();

            if (y == 0)
                throw new FileParsingException();
            Integer stdC;

            stdC = parseInteger();
            if (stdC <= 0) {
                return null;
            }

            Integer expSt;

            expSt = parseInteger();
            if (expSt <= 0) {
                return null;
            }

            Long evMark;


            evMark = parseLong();
            if (evMark <= 0) {
                return null;
            }

            Semester sem = parseSemester();

            if (sem == null)
                throw new FileParsingException();

            String admName = parseString();

            if (admName == null)
                throw new FileParsingException();

            java.time.LocalDateTime admDate = parseDate();

            if (admDate == null)
                throw new FileParsingException();

            Long admWeight;

            admWeight = parseLong();
            if (admWeight <= 0) {
                return null;
            }

            String admPassID = parseString();

            if (admPassID == null)
                throw new FileParsingException();

            return new RowStudyGroup(name, new Coordinates(x, y), stdC, expSt, evMark, sem, new Person(admName, admDate, admWeight, admPassID));
        }
        catch (NoSuchElementException | FileParsingException exception){
            Main.logger.error("Ошибка парсинга файла");
            return null;
        }
    }


    /**
     * Ask String from file
     * @return Result of parsing
     */
    public String parseString(){
        String str = null;

        try {
            String buf = scriptReader.nextLine().trim();
            if (buf.equals(""))
                throw new EmptyIOException();
            if (buf == null)
                throw new EmptyIOException();
            str = buf;
            return str;
        }
        catch (EmptyIOException e){
            Main.logger.error("Ввод не может быть пустым");
        }
        return null;
    }

    /**
     * Pars integer number from file
      * @return Result of parsing
     */
    public Integer parseInteger(){
        Integer out = null;

        try {
            String buf = scriptReader.nextLine().trim();
            if (buf.equals(""))
                throw new EmptyIOException();
            if (buf == null)
                throw new EmptyIOException();
            out = Integer.parseInt(buf);
            return out;
        }
        catch (EmptyIOException e){
            Main.logger.error("Ввод не может быть пустым");
        }
        catch (NumberFormatException e){
            Main.logger.error("Введите число");
        }
        return 0;
    }

    /**
     * Pars Long number from file
     * @return Result of parsing
     */
    public Long parseLong(){
        Long out = null;

        try {
            String buf = scriptReader.nextLine().trim();
            if (buf.equals(""))
                throw new EmptyIOException();
            if (buf == null)
                throw new EmptyIOException();
            out = Long.parseLong(buf);
            return out;
        }
        catch (EmptyIOException e){
            Main.logger.error("Ввод не может быть пустым");
        }
        catch (NumberFormatException e){
            Main.logger.error("Введите число");
        }
        return (long)0;
    }

    /**
     * Pars one of elements of enum Semester.
     * @return Result of parsing
     */
    public Semester parseSemester(){
        Semester out = null;

        try {
            String buf = scriptReader.nextLine().trim();
            if (buf.equals(""))
                throw new EmptyIOException();
            if (buf == null)
                throw new EmptyIOException();
            out = Semester.valueOf(buf);
            return out;
        }
        catch (EmptyIOException e){
            Main.logger.error("Ввод не может быть пустым");
        }
        catch (IllegalArgumentException e){
            Main.logger.error("Введите элемент из списка");
        }
        return null;
    }
    /**
     * Pars date and time from file
     * @return Result of parsing
     */
    public java.time.LocalDateTime parseDate(){
        java.time.LocalDateTime out = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            String buf = scriptReader.nextLine().trim();
            if (buf.equals(""))
                throw new EmptyIOException();
            if (buf == null)
                throw new EmptyIOException();
            out = java.time.LocalDateTime.parse(buf, formatter);
            return out;
        }
        catch (EmptyIOException e){
            Main.logger.error("Ввод не может быть пустым");
        }
        catch (java.time.format.DateTimeParseException e){
            Main.logger.error("Введите дату");
        }

        return null;
    }
}
