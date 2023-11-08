package Spitale;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Main {
    static final int PORT=8080;
    public static List<Sectie> getSectii(){
        List<Sectie> secties = new ArrayList<>();
        try(var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/sectii.json")))){
            JSONArray array = new JSONArray(new JSONTokener(fisier));
            for(int i=0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                int cod = object.getInt("cod_sectie");
                String denumire = object.getString("denumire");
                int numar_locuri = object.getInt("numar_locuri");
                Sectie sectie = new Sectie(cod,denumire,numar_locuri);
                secties.add(sectie);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return secties;
    }
    public static List<Pacient> getPacienti(){
        List<Pacient> pacienti = new ArrayList<>();
        try(var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("Date/pacienti.txt")))){
            pacienti = fisier.lines().map(linie-> new Pacient(
                    Long.parseLong(linie.split(",")[0]),
                    linie.split(",")[1],
                    Integer.parseInt(linie.split(",")[2]),
                    Integer.parseInt(linie.split(",")[3])
            )).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pacienti;
    }
    public static void main(String[] args) {
        List<Sectie> sectii = getSectii();
        List<Pacient> pacienti = getPacienti();
        //cerinta 1
        sectii.stream().filter(x->x.getNumar_locuri()>10).forEach(System.out::println);
        List<Double> list = new ArrayList();
        Map<Integer,Double> map = new HashMap<>();
        //cerinta 2
        for(Sectie s:sectii){
            double varstaMedie = pacienti.stream().filter(p->s.getCod()==p.getCod()).collect(Collectors.averagingDouble(Pacient::getVarsta));
            s.setVarsta_medie(varstaMedie);
        }
        sectii.stream().sorted(Comparator.comparing(Sectie::getVarsta_medie).reversed()).forEach(s->{
            System.out.println("Cod "+s.getCod()+" denumirea "+s.getDenumire()+ " nr Locuri "+s.getNumar_locuri()+ " varsta medie "+s.getVarsta_medie());
        });

        //cerinta 3
        try(var fisier = new PrintWriter(new FileWriter("jurnal.txt"))){
            for(Sectie s : sectii){
                int nr=0;
                fisier.write(s.getCod() + "," +s.getDenumire());
                for(Pacient p : pacienti){
                    if(s.getCod()==p.getCod()){
                        nr++;
                    }
                }
                fisier.write(","+nr+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //cerinta 4
        new Thread(Main::serverTCP).start();
        //trimitere cerere catre server
        try(var socket = new Socket("localhost", PORT);

            var out = new ObjectOutputStream(socket.getOutputStream());
            var in = new ObjectInputStream(socket.getInputStream());

            ){

            int cod_sectie = 4;
            out.writeObject(cod_sectie);
            Long nr_locuri_libere = (Long) in.readObject();
            System.out.println("Numarul de locuri disponibile pentru sectia "+cod_sectie+" este de "+nr_locuri_libere);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void serverTCP() {
        try(var serverSocket = new ServerSocket(PORT);
            var socket = serverSocket.accept();
            var in = new ObjectInputStream(socket.getInputStream());
            var out = new ObjectOutputStream(socket.getOutputStream());){
            int cod_sectie = (Integer) in.readObject();
            List<Sectie> sectii = getSectii();
            List<Pacient> pacienti = getPacienti();
            Sectie sectie = sectii.stream().filter(s->s.getCod()==cod_sectie).findFirst().orElse(null);
            long nr_pacienti = pacienti.stream().filter(p->p.getCod()==cod_sectie).collect(Collectors.counting());
            out.writeObject(sectie.getNumar_locuri()-nr_pacienti);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
