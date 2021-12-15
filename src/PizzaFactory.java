public class PizzaFactory implements IComidaFactory{
    @Override
    public Comida criarComida(boolean acrescimo) {
        return new Pizza(acrescimo);
    }
}
