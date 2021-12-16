public abstract class Fidelidade implements IFidelidade{
    
    protected IFidelidade IFidelidade;
    // protected double taxaDesconto;
    protected double desconto;

    public Fidelidade(IFidelidade IFidelidade, double desconto){
            this.IFidelidade = IFidelidade;
            // this.taxaDesconto = taxaDesconto;
            this.desconto = desconto;
    }

    // public abstract double getTaxaDesconto();

    public abstract double desconto(Pedido[] pedido);

}
