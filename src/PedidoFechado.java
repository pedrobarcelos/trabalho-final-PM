public class PedidoFechado implements IPedidoStatus{
    @Override
    public boolean addComida(Comida c, Pedido p) {
        return false;
    }

    @Override
    public String sumario(Pedido p) {
        return p.getSumario();
    }

    @Override
    public boolean fecharPedido(Pedido p) {
        return true;
    }
}
