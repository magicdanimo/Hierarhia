package lekerdez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdatbazisLekerdezBean implements AdatbazisKapcsolat {
  private boolean loginOK;

  public AdatbazisLekerdezBean() {
    loginOK=false;
  }

  public boolean isLoginOK() {
    return loginOK;
  }

  public void setLoginOK(boolean loginOK) {
    this.loginOK = loginOK;
  }

  public String getNyithatoCsukhatoLista() {
    List<LekerdezAlapAdat> lista=new ArrayList<>();
    StringBuilder s=null; //new StringBuilder(LekerdezAlapAdat.HTML_START);
    try {
      Class.forName(DRIVER);
      Connection kapcsolat=DriverManager.getConnection(URL, USER, PASSWORD);
      ResultSet eredmeny=kapcsolat.createStatement().executeQuery(SQL_ALAPADAT);
      while(eredmeny.next()) {
        int reszlegAzonosito=eredmeny.getInt("DEPARTMENT_ID");
        String reszlegNev=eredmeny.getString("DEPARTMENT_NAME");
        String reszlegVezeto=eredmeny.getString("MANAGER_NAME");
        int reszlegLetszam=eredmeny.getInt("EMP_COUNT");
        double reszlegAtlagfizetes=eredmeny.getDouble("AVG_SALARY");
//        LekerdezAlapAdat laa=new LekerdezAlapAdat(reszlegAzonosito, reszlegNev,
//          reszlegVezeto, reszlegLetszam, reszlegAtlagfizetes);
//        s.append(laa.getHtmlTartalom());
        lista.add(new LekerdezAlapAdat(reszlegAzonosito, reszlegNev,
          reszlegVezeto, reszlegLetszam, reszlegAtlagfizetes));
      }
      for (LekerdezAlapAdat lekerdezAlapAdat : lista) {
        PreparedStatement utasitas=kapcsolat.prepareStatement(SQL_RESZLEG_NEVSOR);
        utasitas.setInt(1, lekerdezAlapAdat.getReszlegAzonosito());
        eredmeny=utasitas.executeQuery();
        List<String> nevsor=new ArrayList<>();
        while(eredmeny.next()) {
          String nev=eredmeny.getString("EMP_NAME");
          nevsor.add(nev);
        }
        lekerdezAlapAdat.setReszlegNevsor(nevsor);
      }      
      kapcsolat.close();
      s=new StringBuilder(LekerdezAlapAdat.HTML_START);     
      for (int i = 0; i < lista.size(); i++) {
        LekerdezAlapAdat laa=lista.get(i);
        if(i==0)
          laa.setKinyitott(true);
        //s.append(lista.get(i).getHtmlTartalom()); //.append("\n");
        s.append(laa.getHtmlTartalom());
      }
      s.append(LekerdezAlapAdat.HTML_STOP);
    }
    catch(ClassNotFoundException | SQLException e) {
      s=new StringBuilder("Adatbázis hiba...");
      //s=new StringBuilder(e.getMessage());
    }    
    return s.toString(); //"teszt, driver:"+ DRIVER;
  }  
  
public String jobTitle(String pass){
    String sql = AdatbazisKapcsolat.SQL_JOB_TITLE;
    sql=sql.replace("##EMAIL##", pass);
    String sor="";
    try {
      Class.forName(DRIVER);
      Connection kapcsolat=DriverManager.getConnection(URL, USER, PASSWORD);
      ResultSet eredmeny=kapcsolat.createStatement().executeQuery(sql);
      while(eredmeny.next()) {
        sor=eredmeny.getString("JOB_TITLE");
      }
      return sor;
    }catch(ClassNotFoundException | SQLException e) {
      return "Hiba";
    }   
}  
public String hierarhia(){
    String lista="<ul id=\"myUL\">";
    String sql = AdatbazisKapcsolat.SQL_HIERARHIA;
    try {
      Class.forName(DRIVER);
      Connection kapcsolat=DriverManager.getConnection(URL, USER, PASSWORD);
      ResultSet eredmeny=kapcsolat.createStatement().executeQuery(sql);
      while(eredmeny.next()) {
          lista+= "  <li><span class=\"caret\">"+ eredmeny.getString("FONOK")+" ("+eredmeny.getString("FONOKMUNKAKOR")+
                  ") "+eredmeny.getString("FONOKFIZETES")+"</span>"+"</li>";
      }
      lista+="</ul>";
      return lista;
    }catch(ClassNotFoundException | SQLException e) {
      return "Hiba";
    }   
}
  
public int siker(String pass, String felhasznalo){
String sql = AdatbazisKapcsolat.sql_DB;
sql=sql.replace("##JELSZO##", pass);
sql=sql.replace("##FELHASZNALO##", felhasznalo);
int sor = 0;
try {
      Class.forName(DRIVER);
      Connection kapcsolat=DriverManager.getConnection(URL, USER, PASSWORD);
      ResultSet eredmeny=kapcsolat.createStatement().executeQuery(sql);
      while(eredmeny.next()) {
        sor=Integer.parseInt(eredmeny.getString("DB"));
      }
      return sor;
    }catch(ClassNotFoundException | SQLException e) {
      return -1;
    }
}

  private String getNyithatoCsukhatoLista0() {
    StringBuilder s=new StringBuilder();
    try {
      Class.forName(DRIVER);
      //s.append("<ul>\n");
      Connection kapcsolat=DriverManager.getConnection(URL, USER, PASSWORD);
      ResultSet eredmeny=kapcsolat.createStatement().executeQuery(SQL_ALAPADAT);
      int i=1;
      while(eredmeny.next()) {
        String reszleg=eredmeny.getString("DEPARTMENT_NAME");
        //s.append("      <li>").append(reszleg).append("</li>\n");
        s.append(
          "    <div class=\"panel panel-default\">\n" +
          "      <div class=\"panel-heading\">\n" +
          "        <h4 class=\"panel-title\">\n" +
          "          <a data-toggle=\"collapse\" data-parent=\"#accordion\" href=\"#collapse"+i+
                       "\">"+reszleg+" (5 fő)</a>\n" +
          "        </h4>\n" +
          "      </div>\n" +
          "      <div id=\"collapse"+i+"\" class=\"panel-collapse collapse in\">\n" +
          "        <div class=\"panel-body\">Vezető: x y<br>Névsor: x y, z t, ...,<br>Átlagfizetés: W USD</div>\n" +
          "      </div>\n" +
          "    </div>\n");
        i++;
      }
      kapcsolat.close();
      //s.append("    </ul>");
    }
    catch(ClassNotFoundException | SQLException e) {
      //s=new StringBuilder("Adatbázis hiba...");
      s=new StringBuilder(e.getMessage());
    }    
    return s.toString(); //"teszt, driver:"+ DRIVER;
  }
  
}
