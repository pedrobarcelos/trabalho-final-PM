public class PedidoAberto implements IPedidoStatus{
    @Override
    public boolean addComida(Comida c, Pedido p) {
        boolean resposta = false;

        if(p.getQuantComidas()<p.getMAXCOMIDAS()){
            p.setComidas(p.getQuantComidas(), c);
            p.setQuantComidas(p.getQuantComidas()+1);
            resposta = true;
        }
        return resposta;
    }

    @Override
    public String sumario(Pedido p) {
        StringBuilder relat = new StringBuilder("PEDIDO Nº "+p.getIdPedido()+"\n");

        for(int i =0; i<p.getQuantComidas(); i++){
            relat.append(p.getComidas(i).toString());
        }
        p.setSumario(relat.toString());
        return p.getSumario();
    }

    @Override
    public boolean fecharPedido(Pedido p) {
        boolean resposta = true;
        if(p.getQuantComidas() == 0)
            resposta = false;
        else{
            p.dataPedido = new Data(5,4,2021);
            p.setIdPedido(p.getultPedido());
            this.sumario(p);
            p.setPedido( new PedidoFechado());
        }
        return resposta;
    }
}
