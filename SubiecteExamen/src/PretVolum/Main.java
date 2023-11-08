package PretVolum;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static List<PretVolum> getPreturi() {
        List<PretVolum> preturi = new ArrayList<>();
        try (var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("date/PretVolum.txt")))) {
            preturi = fisier.lines().map(PretVolum::new).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return preturi;
    }



    public static void main(String[] args) {
        List<PretVolum> preturi = getPreturi();
        System.out.println(preturi);

        // afisare bd fara date
        List<Titlu> titluri = new ArrayList<>();
        Titlu n1 = new Titlu("AACG", "A");
        Titlu n2 = new Titlu("AACI", "B");
        Titlu n3 = new Titlu("DSED", "C");
        Titlu n4 = new Titlu("HYTF", "D");
        Titlu n5 = new Titlu("ASED", "E");
        titluri.add(n1);
        titluri.add(n2);
        titluri.add(n3);
        titluri.add(n4);
        titluri.add(n5);
        System.out.println(titluri);
        //cerinta 1
        System.out.println("Simbol  Denumire");

            PretVolum valoareMin = preturi.stream().sorted(Comparator.comparing(PretVolum::getValoare)).findFirst().orElse(null);
            PretVolum valoareMax = preturi.stream().sorted(Comparator.comparing(PretVolum::getValoare).reversed()).findFirst().orElse(null);

            System.out.println(valoareMax.getSimbol()+" " +valoareMax.getValoare());
            System.out.println(valoareMin.getSimbol()+" " +valoareMin.getValoare());

        //cerinta 2 Aici nu mi a mers sa fac intr-un singur stream totul nu mi sorta nu intlg de ce asa ca am fct o lista in care am sortat si dupa stream pt afisare
        System.out.println("Simbol  Denumire  Volum");
        List<PretVolum> sortedPreturi = preturi.stream()
                .sorted(Comparator.comparing(PretVolum::getVolum).reversed())
                .collect(Collectors.toList());

        sortedPreturi.forEach(p -> {
            titluri.stream()
                    .filter(t -> t.getSimbol().equalsIgnoreCase(p.getSimbol()))
                    .forEach(t -> System.out.println(p.getSimbol() + " " + t.getDenumire() + " " + p.getVolum()));
        });

        //cerinta 3 Aici o sa punem in titlu diferenta si metoda o facem in pretvolum
        System.out.println("Simbol  Denumire  Diferenta(Max-Min > 1%)");

        titluri.stream().forEach(t->{
            for(PretVolum p: preturi){
                if(p.getDiferenta()>1 && p.getSimbol().equalsIgnoreCase(t.getSimbol())){
                    t.setDiferenta(p.getDiferenta());
                }
            }
        });
        //Cred ca e ok da s valorile aiurea
        titluri.stream().sorted(Comparator.comparing(Titlu::getDiferenta).reversed())
                .forEach(t-> System.out.println(t.getSimbol()+" "+t.getDenumire()+" "+t.getDiferenta()));

        new Thread(Main::ServerTCP).start();
        try(
                var socket = new Socket("localhost",8080);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ) {
            String simbol = "AACG";
            out.writeObject(simbol);
            String denumire = (String) in.readObject();
            Double pretInchidere = (Double) in.readObject();
            Long volumTranzactionat = (Long) in.readObject();
            System.out.println("Cerinta 3");
            System.out.println(denumire+ " "+pretInchidere+" "+volumTranzactionat);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void ServerTCP() {
        try(    var serversocket = new ServerSocket(8080);
                var socket = serversocket.accept();
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
        ){
            String simbol = (String) in.readObject();
            //tre sa pun astea din nou sa lucrez cu ele
            List<PretVolum> pretVolums = getPreturi();
            List<Titlu> titluri = new ArrayList<>();
            Titlu n1 = new Titlu("AACG", "A");
            Titlu n2 = new Titlu("AACI", "B");
            Titlu n3 = new Titlu("DSED", "C");
            Titlu n4 = new Titlu("HYTF", "D");
            Titlu n5 = new Titlu("ASED", "E");
            titluri.add(n1);
            titluri.add(n2);
            titluri.add(n3);
            titluri.add(n4);
            titluri.add(n5);
            //Normal arata mai frms
            Titlu titlu=titluri.stream().filter(t->t.getSimbol().equalsIgnoreCase(simbol)).findFirst().orElse(null);
            out.writeObject(titlu.getDenumire());
            PretVolum pretVolum = pretVolums.stream().filter(p->p.getSimbol().equalsIgnoreCase(simbol)).findFirst().orElse(null);
            out.writeObject(pretVolum.getInchidere());
            out.writeObject(pretVolum.getVolum());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}