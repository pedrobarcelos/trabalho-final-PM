public abstract class Fidelidade implements IFidelidade{
    protected IFidelidade iFidelidade;

    public Fidelidade(IFidelidade IFidelidade){
            this.iFidelidade = IFidelidade;
    }

    // public abstract double getTaxaDesconto();
    @Override
    public double getDesconto(){
        return iFidelidade.getDesconto();
    }

}
