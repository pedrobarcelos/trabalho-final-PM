import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

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

/**
 * Classe cliente do restaurante. Contém pedidos (composição)
 * @author João Caram
 */
public class  Cliente implements Serializable, IFidelidade {

    /** Data de referência. */
    LocalDate data = LocalDate.now();
    /** Data de referência. */
    Data hoje = new Data(data.getDayOfMonth(),data.getMonthValue(), data.getYear());
    /** Nome do cliente (livre) */
    public String nome;
    /** CPF do cliente (sem validação) */
    private String CPF;
    /** Vetor de pedidos. A ser melhorado */
    private Pedido pedidos[];
    /** Quantidade de pedidos até o momento */
    private int qtPedidos;
    /** Categoria: injeção de dependência com interface. Composição em lugar de herança */
    private Optional <IFidelidade> categoriaFidelidade;
    private double desconto;


    /**
     * Construtor. Devolve um cliente com 0 pedidos e categoria de fidelidade
     * @param nome Nome do cliente (livre)
     * @param CPF CPF do cliente (sem validação)
     */
    public Cliente(String nome, String CPF){
        this.nome = nome;
        this.CPF = CPF;
        this.pedidos = new Pedido[1_000];
        this.qtPedidos=0;
        this.categoriaFidelidade = null;
    }

    /**
     * Adiciona um pedido
     * @param p O pedido já pronto
     * @return V/F se foi possível adicionar
     */
    public boolean addPedido(Pedido p){
        boolean resposta = true;
        if(this.qtPedidos < this.pedidos.length){
            this.pedidos[this.qtPedidos] = p;
            this.qtPedidos++;
            // this.mudarCategoria();
        }
        else
            resposta = false;

        return resposta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public Pedido[] getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedido[] pedidos) {
        this.pedidos = pedidos;
    }

    public int getQtPedidos() {
        return qtPedidos;
    }

    public void setQtPedidos(int qtPedidos) {
        this.qtPedidos = qtPedidos;
    }

    public Optional <IFidelidade> getCategoriaFidelidade() {
        return categoriaFidelidade;
    }

    public void setCategoriaFidelidade(IFidelidade categoriaFidelidade) {
        // Pegar um valor e atribuir para var
        this.categoriaFidelidade = Optional.of(categoriaFidelidade);
    }

    /**
     *
     */
    public double desconto(){
        if(this.categoriaFidelidade.isEmpty()) return 0;
        else return this.categoriaFidelidade.get().desconto(this.pedidos);
    }



    /**
     * Verifica mudança de categoria. Só verifica upgrades, não rebaixa o cliente.
     */
    // private void mudarCategoria(){

    //     // IFidelidade fidelidade = new Cliente10(CPF, CPF);
    //     // Fidelidade teste; //vou testar se ele pode subir de categoria
    //     // teste = new IFidelidade(null, qtPedidos, qtPedidos);

    //     if(this.categoriaFidelidade.isEmpty()){
    //         teste = new Cliente10(teste, qtPedidos, qtPedidos);
    //     }
    //     else{
    //         teste = new Cliente25(teste, qtPedidos, qtPedidos);
    //     }

    //     if(teste.desconto(this.pedidos) > 0 )
    //         this.categoriaFidelidade = Optional.of(teste);
    // }

    public double somarValorpedidos(){

        return  Arrays.stream(this.pedidos)
                .filter(p -> p != null)
                .mapToDouble(Pedido::valorTotal)
                .sum();

    }

    public double calcularMediaValorPedidos() {

        return  Arrays.stream(this.pedidos)
                .filter(p -> p != null)
                .mapToDouble(Pedido::valorTotal)
                .average()
                .orElse(0);

    }

    /**
     * Descrição do cliente: nome, CPF, total de pedidos
     */
    public String toString(){
        StringBuilder sb = new StringBuilder(this.nome);
        sb.append("  CPF: "+this.CPF+"\n");
        sb.append("Total de pedidos: "+this.qtPedidos+"\n");
        return sb.toString();
    }

    @Override
    public void setDesconto(double desconto) {
        this.desconto = desconto;    
    }

    @Override
    public double getDesconto() {
        return this.desconto;
    }

    @Override
    public double desconto(Pedido[] pedidos) {
            double desconto = 0.0;
            double valorPedidos=0.0;
            int totalPedidos = 0;
            for (Pedido pedido : pedidos) {
                if(pedido!=null){
                    Data auxMes = pedido.dataPedido.acrescentaDias(31);
                    Data auxAno = pedido.dataPedido.acrescentaDias(366);
                    if(!hoje.maisRecente(auxMes))
                        valorPedidos += pedido.valorTotal();
                    if(!hoje.maisRecente(auxAno))
                        totalPedidos++;
                }
            }
    
            if(valorPedidos>=200.00 || totalPedidos>=50){
                desconto = 0.25;
                // this.setDesconto(desconto);
            }else if(valorPedidos>=100.00 || totalPedidos>=25){
                desconto = 0.10;
                // this.setDesconto(desconto);
            }else desconto = 1;
            return desconto;
    }



}
