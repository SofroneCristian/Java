package FacSpecializari;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    static final int port = 8080;
    public static List<Specializare> getSpecializari(){
        List<Specializare> specializares= new ArrayList<>();
        try(var conn = DriverManager.getConnection("jdbc:sqlite:Date/facultate.db")){
            Statement statement = conn.createStatement();
            statement.execute("Select * from Specializari");
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                specializares.add(new Specializare(rs.getInt(1),rs.getString(2),rs.getInt(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return specializares;
    }

    public static List<Inscriere> getInscrieri(){
        List<Inscriere> inscrieri = new ArrayList<>();
        try(var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/inscrieri.txt")))){
            inscrieri = fisier.lines().map(linie-> new Inscriere(
                    Long.parseLong(linie.split(",")[0]),
                    linie.split(",")[1],
                    Double.parseDouble(linie.split(",")[2]),
                    Integer.parseInt(linie.split(",")[3]))
            ).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inscrieri;
    }
    public static void main(String[] args) {
        List<Specializare> specializari = getSpecializari();
        System.out.println(specializari);
        List<Inscriere> inscrieri = getInscrieri();
        System.out.println(inscrieri);
        //cerinta 1
        Integer nrLocuri=specializari.stream().collect(Collectors.summingInt(Specializare::getLocuri));
        System.out.println(nrLocuri);
        //cerinta 2
        for(Specializare s: specializari){
            int nrInscrieri=0;
            int locuriRamase=0;
            for(Inscriere i : inscrieri){
                if(s.getCod()==i.getCod_specializare_aleasa()){
                    nrInscrieri++;
                }
                locuriRamase = s.getLocuri()-nrInscrieri;
            }
            System.out.println("Codul specializarii " + s.getCod()+ " denumirea " +s.getDenumire()+ " Numarul de locuri ramase " +locuriRamase );
        }
       try(var fisier = new PrintWriter(new FileWriter("inscrieri_specializari.json"))){
           JSONArray array = new JSONArray();
           specializari.stream().forEach(s->{
               long nrInscrieri = inscrieri.stream().filter(i->i.getCod_specializare_aleasa()==s.getCod()).collect(Collectors.counting());
               double media = inscrieri.stream().filter(i->i.getCod_specializare_aleasa()==s.getCod())
                       .collect(Collectors.averagingDouble(Inscriere::getNota_bacalaureat));
               JSONObject object = new JSONObject();
               object.put("cod_specializare",s.getCod());
               object.put("denumire",s.getDenumire());
               object.put("nrInscrieri", nrInscrieri);
               object.put("media", media);
               array.put(object);
           });
           fisier.write(array.toString(1));
       } catch (IOException e) {
           e.printStackTrace();
       }

        //cerinta patru
        new Thread(Main::serverTCP).start();

        try(
                var socket = new Socket("localhost", port);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());



        ) {
            String denumire_specializare = "Cibernetica";
            out.writeObject(denumire_specializare);
            int nrLocuriDisponibile = (Integer) in.readObject();
            System.out.println("Numarul de locuri pentru specializarea " +denumire_specializare+" este "+nrLocuriDisponibile);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static void serverTCP() {
        try(
                var serverSocket = new ServerSocket(port);
                var socket = serverSocket.accept();


                var in = new ObjectInputStream(socket.getInputStream());
                var out = new ObjectOutputStream(socket.getOutputStream());
                )
                 {



            String denumire_specializare = (String) in.readObject();
            List<Specializare> specializari =getSpecializari();
            List<Inscriere> inscrieri = getInscrieri();




            System.out.println(denumire_specializare);
            Specializare specializare = specializari.stream().filter(s->s.getDenumire().equalsIgnoreCase(denumire_specializare)).findFirst().orElse(null);
            int nrLocuriOcupate=0;
            int nrLocuriTotale = specializare.getLocuri();
            for( Inscriere i: inscrieri){
                if(i.getCod_specializare_aleasa()==specializare.getCod()){
                    nrLocuriOcupate++;
                }
            }
            int locuriRamase = nrLocuriTotale-nrLocuriOcupate;
            out.writeObject(locuriRamase);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
