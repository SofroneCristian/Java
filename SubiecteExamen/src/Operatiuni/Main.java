package Operatiuni;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static List<Depozit> getDepozite() {
        List<Depozit> depozits = new ArrayList<>();
        try (var fisier = new BufferedReader(new InputStreamReader(new FileInputStream("date/operatiuni.json")))) {

            var jsonDepozite = new JSONArray(new JSONTokener(fisier));

            for (int i = 0; i < jsonDepozite.length(); i++) {
                var jsonDepozit = jsonDepozite.getJSONObject(i);
                var jsonOperatiuni = jsonDepozit.getJSONArray("operatiuni");
                List<Operatiune> listaOperatiuni = new ArrayList<>();
                for (int j = 0; j < jsonOperatiuni.length(); j++) {
                    var jsonOperatiune = jsonOperatiuni.getJSONObject(j);
                    var operatiune = new Operatiune(
                            jsonOperatiune.getInt("cantitate"),
                            jsonOperatiune.getString("tip")
                    );
                    listaOperatiuni.add(operatiune);
                }
                var depozit = new Depozit(
                        listaOperatiuni,
                        jsonDepozit.getInt("cod")
                );
                depozits.add(depozit);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return depozits;
    }
    public static void main(String[] args) {
        List<Depozit> depozits = getDepozite();
        System.out.println(depozits);

        List<Articol> articole = new ArrayList<>();
        Articol n1 = new Articol(101, "Pantalon barbatesc 33/32");
        Articol n2 = new Articol(201, "Pantalon dama 28/30");
        Articol n3 = new Articol(302, "Palarie barbateasca vara 58");
        articole.add(n1);
        articole.add(n2);
        articole.add(n3);
        System.out.println(articole);
        //cerinta 1 pentru sortare o sa parcurgem depozit si o sa setam un parametru total intrari cu totalul intrarilor, iar dupa facem sortarea
        //soutu urmator e ca sa fie afisarea la fel
        System.out.println("Cod articol   Total intrari");
        depozits.forEach(d->{
            List<Operatiune> operatiuni = d.getOperatiuni();
            int sum= operatiuni.stream().filter(o -> o.getTip().equalsIgnoreCase("intrare")).collect(Collectors.summingInt(Operatiune::getCantitate));
            d.setTotalIntrari(sum);
        });
        depozits.stream().sorted(Comparator.comparing(Depozit::getTotalIntrari).reversed()).forEach(d-> System.out.println(d.getCod()+" "+d.getTotalIntrari()));

        //cerinta 2 adaugam parametru stoc net in depozit
        System.out.println("Cod Articol   Denumire   Stoc net");
        depozits.forEach(d->{
            List<Operatiune> operatiuni = d.getOperatiuni();
            int intrari= operatiuni.stream().filter(o -> o.getTip().equalsIgnoreCase("intrare")).collect(Collectors.summingInt(Operatiune::getCantitate));
            int iesiri = operatiuni.stream().filter(o -> o.getTip().equalsIgnoreCase("iesire")).collect(Collectors.summingInt(Operatiune::getCantitate));
            int stocNet = intrari-iesiri;
            d.setStocNet(stocNet);

        });
        for(Articol a: articole){
            depozits.stream().sorted(Comparator.comparing(Depozit::getStocNet)).filter(d->d.getCod()==a.getCod()).forEach(d-> System.out.println(d.getCod()+" "+a.getDenumire()+" "+d.getStocNet()));
        }
        new Thread(Main::ServerTcp).start();
        try(
                var socket = new Socket("localhost",8080);
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ){
            int codArticol = 101;
            out.writeObject(codArticol);
            List<Operatiune> operatiouni = (List<Operatiune>) in.readObject();
            System.out.println(operatiouni);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void ServerTcp() {
        try(
                var serverSocket = new ServerSocket(8080);
                var socket = serverSocket.accept();
                var out = new ObjectOutputStream(socket.getOutputStream());
                var in = new ObjectInputStream(socket.getInputStream());
                ){
            List<Depozit> depozits = getDepozite();
            int codArticol = (int) in.readObject();
            Depozit depozit=depozits.stream().filter(d->d.getCod()==codArticol).findFirst().orElse(null);
            List<Operatiune> operatiuni = depozit.getOperatiuni();
            out.writeObject(operatiuni);

        } catch (IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
