public interface IPedidoStatus {
    public boolean addComida(Comida c, Pedido p);
    public String sumario(Pedido p);
    public boolean fecharPedido(Pedido p);


}
