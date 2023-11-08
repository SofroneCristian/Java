package Aventuri;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final int PORT=8080;
    public static List<Aventura> getAventuri(){
        List<Aventura> aventuri = new ArrayList<>();
        try(var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/aventuri.json")))){
            JSONArray array = new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                int cod_aventura = object.getInt("cod_aventura");
                String denumire = object.getString("denumire");
                double tarif = object.getDouble("tarif");
                int locuri_disponibile = object.getInt("locuri_disponibile");
                Aventura a = new Aventura(cod_aventura,denumire,tarif,locuri_disponibile);
                aventuri.add(a);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aventuri;
    }

    public static List<Rezervare> getRezervari(){
        List<Rezervare> rezervari = new ArrayList<>();
        try(var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/rezervari.txt")))){
            rezervari = fisier.lines().map(linie-> new Rezervare(
                    linie.split(",")[0],
                    Integer.parseInt(linie.split(",")[1]),
                    Integer.parseInt(linie.split(",")[2])
            )).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rezervari;
    }
    public static void main(String[] args) {
        List<Aventura> aventuri = getAventuri();
        List<Rezervare> rezervari = getRezervari();
        //Cerinta 1
        aventuri.stream().filter(a->a.getLocuri_disponibile()>=20).forEach(System.out::println);
        //Cerinta 2
        aventuri.forEach(a->{
            int nrLocuriRezervate = rezervari.stream().filter(r->r.getCod_aventura()==a.getCod_aventura())
                    .collect(Collectors.summingInt(Rezervare::getNr_locuri_rezervate));
            int locuriDispoinibile = a.getLocuri_disponibile()- nrLocuriRezervate;
            System.out.println(a.getCod_aventura()+ " " +a.getDenumire()+" "+ locuriDispoinibile);

        });
        //Cerinta 3
        try(var fisier = new PrintWriter(new FileWriter("venituri.txt"))){
            aventuri.forEach(a->{
                int nrLocuriRezervate = rezervari.stream().filter(r->r.getCod_aventura()==a.getCod_aventura())
                        .collect(Collectors.summingInt(Rezervare::getNr_locuri_rezervate));
                double valoare = a.getTarif()*nrLocuriRezervate;
                fisier.write(a.getDenumire()+" "+ nrLocuriRezervate+" " +valoare+"\n");

        })
    ;} catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(Main::serverTCP).start();
        try(
                var socket = new Socket("localhost",PORT);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ) {
            String denumire_aventura = "CATARARE";
            System.out.println("Trimitere aventura catre server " + denumire_aventura);
            out.writeObject(denumire_aventura);
            int nrLocuri= (int) in.readObject();
            System.out.println("Numarul locurilor dispoinibile " + nrLocuri);

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
                var serverSocket = new ServerSocket(PORT);
                var socket = serverSocket.accept();
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ){
            String denumire_aventura = (String) in.readObject();
            List<Aventura> aventuri = getAventuri();
            List<Rezervare> rezervari = getRezervari();
            Aventura aventura = aventuri.stream().filter(a->a.getDenumire().equalsIgnoreCase(denumire_aventura)).findFirst().orElse(null);
            int nrLocuriOcupate = rezervari.stream().filter(r->r.getCod_aventura()== aventura.getCod_aventura())
                    .collect(Collectors.summingInt(Rezervare::getNr_locuri_rezervate));
            int locuriLibere = aventura.getLocuri_disponibile()- nrLocuriOcupate;
            out.writeObject(locuriLibere);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
