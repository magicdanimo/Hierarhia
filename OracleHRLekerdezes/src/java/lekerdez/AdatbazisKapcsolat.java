package lekerdez;

public interface AdatbazisKapcsolat { //public static final
  String URL="jdbc:oracle:thin:@localhost:1521:xe",
         USER="HR",
         PASSWORD="hr",
         DRIVER="oracle.jdbc.driver.OracleDriver";
  String SQL_ALAPADAT=
    "SELECT D1.DEPARTMENT_ID, DEPARTMENT_NAME, FIRST_NAME || ' ' || LAST_NAME AS MANAGER_NAME,\n" +
    "  (SELECT COUNT(EMPLOYEE_ID)\n" +
    "   FROM EMPLOYEES E JOIN DEPARTMENTS D2 ON D2.DEPARTMENT_ID=E.DEPARTMENT_ID\n" +
    "   WHERE E.DEPARTMENT_ID=D1.DEPARTMENT_ID) AS EMP_COUNT,\n" +
    "  (SELECT ROUND(AVG(SALARY), 2)\n" +
    "   FROM EMPLOYEES E JOIN DEPARTMENTS D2 ON D2.DEPARTMENT_ID=E.DEPARTMENT_ID\n" +
    "   WHERE E.DEPARTMENT_ID=D1.DEPARTMENT_ID) AS AVG_SALARY\n" +
    "FROM DEPARTMENTS D1, EMPLOYEES E\n" +
    "WHERE D1.MANAGER_ID=E.EMPLOYEE_ID\n" +
    "ORDER BY 2";
  String SQL_RESZLEG_NEVSOR=
    "SELECT DEPARTMENT_ID, FIRST_NAME || ' ' || LAST_NAME AS EMP_NAME\n" +
    "FROM EMPLOYEES\n" +
    "WHERE DEPARTMENT_ID=?\n" +
    "ORDER BY EMP_NAME";
String sql = "select count(*)as db from EMPLOYEES where LAST_NAME = ##JELSZO## and EMAIL = ##FELHASZNALO##";
}
