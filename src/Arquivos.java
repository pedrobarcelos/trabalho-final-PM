import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Arquivos {
    // Padrão do Java == .ser
    private static final String nomeArquivo = "lagrimas.ser";

    public static void escreverDados(List<Cliente> clientes) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo));
            out.writeObject(clientes);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Cliente> obterDados() {
        List<Cliente> clientes = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo));
            clientes = (List<Cliente>) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return clientes;
    }

}

