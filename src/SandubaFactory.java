public class SandubaFactory implements  IComidaFactory{
    @Override
    public Comida criarComida(boolean acrescimo) {
        return new Sanduiche(acrescimo);
    }
}
