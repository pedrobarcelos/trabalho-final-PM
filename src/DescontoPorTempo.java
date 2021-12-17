public class DescontoPorTempo extends Fidelidade{

    public DescontoPorTempo(IFidelidade iFidelidade){
        super(iFidelidade);
    }

    @Override
    public double getDesconto(){
        return super.getDesconto() + 0.05;
    }
    
}
