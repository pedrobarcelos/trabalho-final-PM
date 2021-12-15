/**
 * MIT License
 * <p>
 * Copyright(c) 2021 João Caram <caram@pucminas.br>
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Exemplo - Restaurante, comidas, pedidos, clientes e fidelidade
 * Versão 0.3
 */

public class App {

    //#region Utilidades
    /**
     * "Limpa" a tela (códigos de terminal VT-100)
     */

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Menu para o restaurante
     *
     * @param teclado Scanner de leitura
     * @return Opção do usuário (int)
     */
    public static int menu(Scanner teclado) {
        limparTela();
        System.out.println("XULAMBS DELIVERY");
        System.out.println("==========================");
        System.out.println("1 - Novo pedido");
        System.out.println("2 - Incluir comida em pedido");
        System.out.println("3 - Detalhes do pedido");
        System.out.println("4 - Fechar pedido");
        System.out.println("5 - Contabilidade");
        System.out.println("6 - Cadastrar Cliente");
        System.out.println("7 - Selecionar Cliente");
        System.out.println("8 - Mostrar clientes");
        System.out.println("0 - Sair");

        int opcao = teclado.nextInt();
        teclado.nextLine();
        return opcao;
    }

    /**
     * Pausa para leitura de mensagens em console
     *
     * @param teclado Scanner de leitura
     */
    static void pausa(Scanner teclado) {
        System.out.println("Enter para continuar.");
        teclado.nextLine();
    }

    static void cadastrarCliente(Scanner teclado , List <Cliente> clientes) {
        String nomeCliente, cpf;
        System.out.println("Digite o nome do cliente: ");
        nomeCliente = teclado.nextLine();
        System.out.println("Digite o CPF do cliente: ");
        cpf = teclado.nextLine();
        if (!nomeCliente.isEmpty() && !cpf.isEmpty()) {
            Cliente c = new Cliente(nomeCliente, cpf);
            clientes.add(c);
            System.out.println(" SUCESSO! ");
        } else {
            System.out.println(" Cliente não cadastrado.");
        }
    }




    //#endregion

    //#region Métodos de controle

    /**
     * Cria uma comida de acordo com opções do menu (método "fábrica")
     *
     * @param teclado Scanner de leitura
     * @return Uma comida ou nulo
     */
    static Comida criarComida(Scanner teclado) {
        System.out.print("Incluir no pedido(1-Pizza 2-Sanduíche): ");
        int tipo = Integer.parseInt(teclado.nextLine());
        Comida nova;
        String aceito;
        switch (tipo) {
            case 1:
                System.out.println("Deseja borda? ");
                aceito = teclado.nextLine();
                nova = new PizzaFactory().criarComida(Objects.equals(aceito, "s"));
                break;
            case 2:
                System.out.println("Deseja mais uma carne? ");
                aceito = teclado.nextLine();
                nova = new SandubaFactory().criarComida(Objects.equals(aceito, "n"));
                break;
            default:
                nova = null;
                break;
        }
        if (nova != null) {
            System.out.print("Quantos adicionais: ");
            int quantos = Integer.parseInt(teclado.nextLine());
            for (int i = 0; i < quantos; i++)
                nova.addIngrediente("adicional " + (i + 1) + " ");
        }
        return nova;
    }

    private static Cliente selecionarCliente(Scanner teclado, List <Cliente> clientes) {
        Cliente c = null;
        String cpf;
        System.out.println("Digite o CPF do Cliente:");
        cpf = teclado.nextLine();
        if (!cpf.isEmpty()) {
            // todos os clientes da lista
            c = clientes.stream()
                    .filter(cliente -> cliente.getCPF().equals(cpf))
                    .findFirst()
                    .orElse(null);
        }
        return c;
    }

//    public static Cliente selecionarCliente(List<Cliente> clientes, String cpf) {
//        Cliente c = null;
//        c = clientes.stream()
//                .filter(cliente -> cliente.getCPF().equals(cpf))
//                .findFirst()
//                .orElse(null);
//        return c;
////    }

    /**
     * Apaga o pedido p e cria um novo
     *
     * @param p O pedido a ser apagado
     * @return
     */
    static Pedido criarNovo(Pedido p) {
        if (p == null) {
            p = new Pedido();
            System.out.print("Novo pedido criado.");
        }
        return p;
    }

    private static void incluirComidaPedido(Pedido pedido, Scanner teclado) {

        if (pedido != null) {
            Comida aux = criarComida(teclado);
            if (aux != null) {
                if (pedido.addComida(aux))
                    System.out.println("Adicionado: " + aux);
                else
                    System.out.println("Não foi possível adicionar.");
            } else
                System.out.print("Inválido. Favor tentar novamente. ");
        } else
            System.out.print("Pedido ainda não foi aberto. ");
    }

    private static void detalhesPedido(Pedido pedido) {

        if (pedido != null) {
            System.out.println(pedido);
        } else
            System.out.print("Pedido ainda não foi aberto. ");
    }

    private static void fecharPedido(Pedido pedido,Cliente cliente) {

        if (pedido != null) {

            pedido.fecharPedido();
            double totPagar = pedido.valorTotal() * (1.0 - cliente.desconto());
            cliente.addPedido(pedido);
            System.out.println(pedido);
            System.out.println("Cliente " + cliente.nome + " Total R$ " + totPagar);
        } else
            System.out.print("Pedido ainda não foi aberto. ");
    }


    //#endregion

    public static void main(String[] args) throws Exception {

        Scanner teclado = new Scanner(System.in);
        List<Cliente> clientes = Arquivos.obterDados();
        Pedido pedido = null;
        Cliente cliente = null;
        int opcao = -1;
        do {
            opcao = menu(teclado);
            limparTela();
            switch (opcao) {
                case 1 -> {
                    pedido = criarNovo(pedido);
                    pausa(teclado);
                }
                case 2 -> {
                    incluirComidaPedido(pedido, teclado);
                    pausa(teclado);
                }
                case 3 -> {
                    detalhesPedido(pedido);
                    pausa(teclado);
                }
                case 4 -> {
                    if (cliente != null) {
                        fecharPedido(pedido, cliente);
                    } else {
                        System.out.println(" Para prosseguir, selecione um cliente ");
                    }
                    pausa(teclado);
                }
                case 5 -> {
                    if (cliente != null) {
                        contabilidadePedidos(cliente, clientes);
                    }
                    pausa(teclado);
                }
                case 6 -> {
                    cadastrarCliente(teclado, clientes);
                    pausa(teclado);
                }
                case 7 -> {
                    if (!clientes.isEmpty()) {
                        cliente = selecionarCliente(teclado, clientes);
                    } else {
                        System.out.println(" Nenhum cliente cadastrado ");
                    }
                    pausa(teclado);
                }
                case 8 -> {
                    listarClientes(clientes);
                    pausa(teclado);
                }
            }
        } while (opcao != 0);
        Arquivos.escreverDados(clientes);

        System.out.println("FIM");
        teclado.close();
    }

    private static void listarClientes(List <Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void contabilidadePedidos(Cliente c ,List <Cliente> clientes) {
        System.out.println(" Valor total de todos os pedidos:\n" + somarPedidos(clientes));
        System.out.println(" Cliente VIP - Maior valor de pedidos:\n" +  buscarClienteMaiorTotalPedidos(clientes));
        System.out.println(" Valor médio dos pedidos do cliente: \n" + valorMediaPedidoCliente(c));
    }

    public static double somarPedidos(List <Cliente> clientes) {

        return clientes.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Cliente::somarValorpedidos)
                .sum();
    }

    public static Cliente buscarClienteMaiorTotalPedidos(List <Cliente> clientes) {

        double maiorSoma = clientes.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Cliente::somarValorpedidos)
                .max()
                .orElse(0);

        return clientes.stream()
                .filter(c -> c != null && c.somarValorpedidos() == maiorSoma)
                .findFirst()
                .orElse(null);
    }

    public static double valorMediaPedidoCliente(Cliente c) {

        return c.calcularMediaValorPedidos();
    }
}
