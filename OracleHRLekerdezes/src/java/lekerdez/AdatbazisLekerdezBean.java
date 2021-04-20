package lekerdez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdatbazisLekerdezBean implements AdatbazisKapcsolat {

    private boolean loginOK;

    public AdatbazisLekerdezBean() {
        loginOK = false;
    }

    public boolean isLoginOK() {
        return loginOK;
    }

    public void setLoginOK(boolean loginOK) {
        this.loginOK = loginOK;
    }

    public String getNyithatoCsukhatoLista() {
        List<LekerdezAlapAdat> lista = new ArrayList<>();
        StringBuilder s = null; //new StringBuilder(LekerdezAlapAdat.HTML_START);
        try {
            Class.forName(DRIVER);
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(SQL_ALAPADAT);
            while (eredmeny.next()) {
                int reszlegAzonosito = eredmeny.getInt("DEPARTMENT_ID");
                String reszlegNev = eredmeny.getString("DEPARTMENT_NAME");
                String reszlegVezeto = eredmeny.getString("MANAGER_NAME");
                int reszlegLetszam = eredmeny.getInt("EMP_COUNT");
                double reszlegAtlagfizetes = eredmeny.getDouble("AVG_SALARY");
//        LekerdezAlapAdat laa=new LekerdezAlapAdat(reszlegAzonosito, reszlegNev,
//          reszlegVezeto, reszlegLetszam, reszlegAtlagfizetes);
//        s.append(laa.getHtmlTartalom());
                lista.add(new LekerdezAlapAdat(reszlegAzonosito, reszlegNev,
                        reszlegVezeto, reszlegLetszam, reszlegAtlagfizetes));
            }
            for (LekerdezAlapAdat lekerdezAlapAdat : lista) {
                PreparedStatement utasitas = kapcsolat.prepareStatement(SQL_RESZLEG_NEVSOR);
                utasitas.setInt(1, lekerdezAlapAdat.getReszlegAzonosito());
                eredmeny = utasitas.executeQuery();
                List<String> nevsor = new ArrayList<>();
                while (eredmeny.next()) {
                    String nev = eredmeny.getString("EMP_NAME");
                    nevsor.add(nev);
                }
                lekerdezAlapAdat.setReszlegNevsor(nevsor);
            }
            kapcsolat.close();
            s = new StringBuilder(LekerdezAlapAdat.HTML_START);
            for (int i = 0; i < lista.size(); i++) {
                LekerdezAlapAdat laa = lista.get(i);
                if (i == 0) {
                    laa.setKinyitott(true);
                }
                //s.append(lista.get(i).getHtmlTartalom()); //.append("\n");
                s.append(laa.getHtmlTartalom());
            }
            s.append(LekerdezAlapAdat.HTML_STOP);
        } catch (ClassNotFoundException | SQLException e) {
            s = new StringBuilder("Adatbázis hiba...");
            //s=new StringBuilder(e.getMessage());
        }
        return s.toString(); //"teszt, driver:"+ DRIVER;
    }

    public String jobTitle(String pass) {
        String sql = AdatbazisKapcsolat.SQL_JOB_TITLE;
        sql = sql.replace("##EMAIL##", pass);
        String sor = "";
        try {
            Class.forName(DRIVER);
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(sql);
            while (eredmeny.next()) {
                sor = eredmeny.getString("JOB_TITLE");
            }
            return sor;
        } catch (ClassNotFoundException | SQLException e) {
            return "Hiba";
        }
    }

    public String hierarhia() {
        StringBuilder lista = new StringBuilder("<ul id=\"myUL\">");
        String sql = AdatbazisKapcsolat.SQL_HIERARHIA;
        try {
            Class.forName(DRIVER);
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(sql);
            HashMap<String, ArrayList<String>> tomb = new HashMap<>();
            ArrayList<String> tom = new ArrayList<>();
            while (eredmeny.next()) {
                String fonok = eredmeny.getString("FONOK");
                String fMunkakor = eredmeny.getString("FONOKMUNKAKOR");
                String fFizetes = eredmeny.getString("FONOKFIZETES");
                String beosztott = eredmeny.getString("beosztott");
                if (tomb.containsKey(fonok + "(" + fMunkakor + ")" + fFizetes)) {
                    tom.add(eredmeny.getString("beosztott"));
                    fonok = fonok + "(" + fMunkakor + ")" + fFizetes;
                    tomb.put(fonok, tom);
                } else {
                    tom = new ArrayList<>();
                    tom.add(eredmeny.getString("beosztott"));
                    tomb.put(fonok, tom);
                }
            }
            kapcsolat.close();
            for (Map.Entry<String, ArrayList<String>> elem : tomb.entrySet()) {
                String key = elem.getKey();
                ArrayList<String> value = elem.getValue();
                lista.append("<li><span class=\"caret\">").append(key).append("</span></li>");
                for (String beoszt : value) {
                    lista.append("<ul class = \"nested\">").append(beoszt).append("</ul>");
                }
            }
            lista.append("</ul>");
            return lista.toString();
        } catch (ClassNotFoundException | SQLException e) {
            return "Hiba";
        }
    }

    public String megaMenu() {
        String lista = "<div class=\"navbar\">"
                + "<div class=\"dropdown\">\n"
                + "    <button class=\"dropbtn\">Departments \n"
                + "      <i class=\"fa fa-caret-down\"></i>\n"
                + "    </button>\n"
                + "    <div class=\"dropdown-content\">\n"
                + "      <div class=\"header\">\n"
                + "      </div>   \n"
                + "      <div class=\"row\">\n";
        String sql = AdatbazisKapcsolat.SQL_RESZLEG_MENU;
        try {
            Class.forName(DRIVER);
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(sql);
            String elozoReszleg = "";
            int reszlegSzam = 0;
            int i = 0;
            while (eredmeny.next()) {
                String aktReszleg = eredmeny.getString("DEPARTMENT_NAME");
                i++;
                if (aktReszleg.equals(elozoReszleg)) {
                    reszlegSzam += Integer.parseInt(eredmeny.getString("reszlegenkent"));
                    lista += "<a href=\"#\">" + eredmeny.getString("JOB_TITLE") + " (" + eredmeny.getString("reszlegenkent") + ")" + "</a>";

                } else {
                    lista = lista.replace("reszlegSzam", reszlegSzam + "");
                    reszlegSzam = Integer.parseInt(eredmeny.getString("reszlegenkent"));
                    if (i == 1) {
                        lista += "<div class=\"column\"><h3>" + aktReszleg + " (reszlegSzam)" + "</h3>";
                    } else {
                        lista += "</div>\n"
                                + "        <div class=\"column\"><h3>" + aktReszleg + " (reszlegSzam)" + "</h3>";
                    }
                    lista += "<a href=\"#\">" + eredmeny.getString("JOB_TITLE") + " (" + eredmeny.getString("reszlegenkent") + ")" + "</a>";

                }
                elozoReszleg = aktReszleg;
            }
            lista = lista.replace("reszlegSzam", reszlegSzam + "");
            lista += "</div>\n"
                    + "    </div>\n"
                    + "  </div> \n"
                    + "</div></div>";
            return lista;
        } catch (ClassNotFoundException | SQLException e) {
            return "Hiba";
        }
    }

    public int siker(String pass, String felhasznalo) {
        String sql = AdatbazisKapcsolat.sql_DB;
        sql = sql.replace("##JELSZO##", pass);
        sql = sql.replace("##FELHASZNALO##", felhasznalo);
        int sor = 0;
        try {
            Class.forName(DRIVER);
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(sql);
            while (eredmeny.next()) {
                sor = Integer.parseInt(eredmeny.getString("DB"));
            }
            return sor;
        } catch (ClassNotFoundException | SQLException e) {
            return -1;
        }
    }

    private String getNyithatoCsukhatoLista0() {
        StringBuilder s = new StringBuilder();
        try {
            Class.forName(DRIVER);
            //s.append("<ul>\n");
            Connection kapcsolat = DriverManager.getConnection(URL, USER, PASSWORD);
            ResultSet eredmeny = kapcsolat.createStatement().executeQuery(SQL_ALAPADAT);
            int i = 1;
            while (eredmeny.next()) {
                String reszleg = eredmeny.getString("DEPARTMENT_NAME");
                //s.append("      <li>").append(reszleg).append("</li>\n");
                s.append(
                        "    <div class=\"panel panel-default\">\n"
                        + "      <div class=\"panel-heading\">\n"
                        + "        <h4 class=\"panel-title\">\n"
                        + "          <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse" + i
                        + "\">" + reszleg + " (5 fő)</a>\n"
                        + "        </h4>\n"
                        + "      </div>\n"
                        + "      <div id=\"collapse" + i + "\" class=\"panel-collapse collapse in\">\n"
                        + "        <div class=\"panel-body\">Vezető: x y<br>Névsor: x y, z t, ...,<br>Átlagfizetés: W USD</div>\n"
                        + "      </div>\n"
                        + "    </div>\n");
                i++;
            }
            kapcsolat.close();
            //s.append("    </ul>");
        } catch (ClassNotFoundException | SQLException e) {
            //s=new StringBuilder("Adatbázis hiba...");
            s = new StringBuilder(e.getMessage());
        }
        return s.toString(); //"teszt, driver:"+ DRIVER;
    }

}
