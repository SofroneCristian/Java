package Autovehicule;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainAutovehiculeRevizii implements Serializable {
    public static List<Autovehicul> getAutovehicule() {
        List<Autovehicul> autovehicule = new ArrayList<>();
        try (var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/autovehicule.txt")))) {
            autovehicule = fisier.lines().map(linie -> new Autovehicul(
                    linie.split(",")[0],
                    linie.split(",")[1],
                    Integer.parseInt(linie.split(",")[2])
            )).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return autovehicule;
    }

    static int PORT = 1234;

    public static List<Revizie> getRevizii(){
        List<Revizie> revizies = new ArrayList<>();
        try(var conn = DriverManager.getConnection("jdbc:sqlite:Date/examen.db")){
            Statement statement = conn.createStatement();
            statement.execute("Select * from revizii");
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                revizies.add(new Revizie(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getDouble(5)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return revizies;
    }

    public static void main(String[] args) {
        //cerinta 1

        List<Autovehicul> autovehicule = getAutovehicule();
        autovehicule.stream().filter(x -> x.getNrKm() >= 100000)
                .forEach(x -> System.out.println("Numar Inmatriculare " + x.getNrInmatriculare() + " Km parcursi " + x.getNrKm()));
//        cerinta 2
        List<Revizie> revizii = getRevizii();
        List<Revizie> revizii2= new ArrayList<>();
        System.out.println(revizii);
        for(Revizie r: revizii){
            String nr_inmatriculare = r.getNrInmatriculare();
            for(Revizie r2: revizii){
            }

        }

        try (var fisier = new PrintWriter(new FileWriter("raport.txt"))) {
            for (Autovehicul a : autovehicule) {
                fisier.write(a.getNrInmatriculare() + "\n");
                for (Revizie r : revizii) {
                    if (a.getNrInmatriculare().equals(r.getNrInmatriculare()))
                        fisier.write(r.getData_revizie() + "," + r.getTip_revizie() + ","
                                + r.getKm() + "," + r.getCost_revizie() + "\n");
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        new Thread(MainAutovehiculeRevizii::ServerTCP).start();
        try(
                var socket = new Socket("localhost", PORT);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ) {
            String nrInmatriculare = "B47UYT";
            out.writeObject(nrInmatriculare);
            int nrRevizii = (int) in.readObject();
            System.out.println("Numarul de inmatriculare "+nrInmatriculare+ " a avut "+ nrRevizii+ " revizie");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void ServerTCP() {
        try(
                var serverSocket = new ServerSocket(PORT);
                var socket = serverSocket.accept();
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ){
            List<Revizie> revizies = getRevizii();
            List<Autovehicul> autovehiculs = getAutovehicule();
            String nrInmatriculare = (String) in.readObject();
            int nr=0;
            for(Revizie r : revizies){
                if(r.getNrInmatriculare().equalsIgnoreCase(nrInmatriculare)){
                    nr++;
                }

            }
            out.writeObject(nr);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

