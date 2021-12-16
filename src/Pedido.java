import java.io.Serializable;
import java.util.Arrays;

/**
 * MIT License
 *
 * Copyright(c) 2021 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/** Classe pedido. Um pedido contém várias comidas (agregação) */
public class Pedido implements Serializable {
    
    private static final int MAXCOMIDAS;
    private static int ultPedido = 0;
    private int idPedido;
    private int quantComidas;
    private String sumario;
    private Comida[] comidas;
    private IPedidoStatus pedidoStatus;
    Data dataPedido;

    static{
        MAXCOMIDAS = 10;
    }

    /** Construtor, cria pedido vazio */
    public Pedido(){
        this.comidas = new Comida[MAXCOMIDAS];
        PedidoAberto p = new PedidoAberto();
        pedidoStatus = p;

    }

    /**
     * Get para a quantidade de comidas
     * @return Quantidade de comidas
     */
    public int getQuantComidas(){
        return this.quantComidas;
    }

    /**
     * Pedido aberto ou fechado
     * @return V/F para pedido fechado
     */
//    public boolean fechado(){
//        return this.fechado;
//    }

    /**
     * Adiciona uma comida, se ainda houver capacidade
     * @param c A comida a ser adicionada
     * @return V/F para o sucesso da operação de adicionar
     */
    public boolean addComida(Comida c){
        return pedidoStatus.addComida(c, this);
    }

    /**
     * Retorna o valor total do pedido (sem descontos)
     * @return Valor do pedido (double)
     */
    public double valorTotal(){
        double valor = Arrays.stream(this.comidas)
                .filter(c -> c != null)
                .mapToDouble(Comida::precoFinal)
                .sum();


        /*for(int i=0; i<this.quantComidas; i++){
            valor += comidas[i].precoFinal();

        }
        */
        return valor;
    }

    /**
     * Método interno para gerar ou retornar o sumário.
     * @return String com o sumário (detalhamento) do pedido
     */
    private String sumario() {
        return this.pedidoStatus.sumario(this);
    }

    /**
     * Fecha o pedido, se contiver ao menos uma comida. Gera o sumário no momento do fechamento.
     * @return V/F para o fechamento do pedido.
     */
    public boolean fecharPedido(){
        return this.pedidoStatus.fecharPedido(this);
    }


    /**
     * Sumário contendo a descrição de cada comida e o valor total.
     */
    @Override
    public String toString(){
        return this.sumario() + "Valor total = R$ "+this.valorTotal()+".";
    }

    public static int getUltPedido() {
        return ultPedido;
    }

    public static void setUltPedido(int ultPedido) {
        Pedido.ultPedido = ultPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public void setQuantComidas(int quantComidas) {
        this.quantComidas = quantComidas;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Comida[] getComidas() {
        return comidas;
    }

    public void setComidas(Comida[] comidas) {
        this.comidas = comidas;
    }

    public void setComidas(int index, Comida comida){
        comidas[index] = comida;
    }

    public Comida getComidas(int index){
        return this.comidas[index];
    }

    public IPedidoStatus getPedido() {
        return pedidoStatus;
    }

    public void setPedido(IPedidoStatus pedidoStatus) {
        this.pedidoStatus = pedidoStatus;
    }

    public Data getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Data dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getMAXCOMIDAS(){
        return this.MAXCOMIDAS;
    }

    public int getultPedido(){
        return ultPedido;
    }
}

