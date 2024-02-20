package br.com.Lu.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;




public class Tabuleiro implements ObserverCampo{

	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo>campos = new ArrayList<Campo>();
    private final List<Consumer<ResultadoGanhou>> observadores = new ArrayList<>();
    
    
	
	public Tabuleiro(int linhas, int colunas,int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas=minas;
		
		gerarCampos();
		gerarVizinhos();
		sortearMinas();
	}
	
	public void paraCada(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
	
	public void abrir(int linha,int coluna) {
		
			campos.parallelStream()
			.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
			.findFirst()
			.ifPresent(c ->c.abrir());
		
	}
	
	
	
	public void marcar(int linha,int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c ->c.marcados());
	}
	
	
	
	private void gerarCampos() {
		for (int l = 0 ; l < linhas;l++) {
			for(int c=0; c < colunas;c++) {
				Campo camp = new Campo(l, c);
				camp.registrarObserver(this);
				campos.add(camp);
			}
		}
	}
	
	
	
	private void gerarVizinhos() {
	    for(Campo camp: campos) {
		    for(Campo camp2:campos) {
			    camp.adicionarVizim(camp2);
		    }
	    }	
	}
	
	
	
	public void gerarObservadores(Consumer<ResultadoGanhou> obser) {
		observadores.add(obser);
	}
	
	
	
	private void notifiObserver(Boolean result) {
	     observadores.stream()
		.forEach(o -> o.accept(new ResultadoGanhou(result)));
		
	}
	
	
	
	private void sortearMinas() {
		long minasArmadar = 0;
        Predicate<Campo> camp = c -> c.isMinado();
		do {
			int numeroAleatorio =(int) (Math.random() * campos.size());
			campos.get(numeroAleatorio).minar();
			minasArmadar = campos.stream().filter(camp).count();
		} while (minasArmadar < minas);
		
	}

	
	
	public boolean venceu() {
		return campos.stream().allMatch(a-> a.venceu());
	}

	
	
	public void reiniciar() {
		campos.stream().forEach( n -> n.reiniciar());
		sortearMinas();
		
	}
	
	
	
	
	public int getLinhas() {
		return linhas;
	}

	
	
	public int getColunas() {
		return colunas;
	}



	public int getMinas() {
		return minas;
	}



	@Override
	public void eventoCampo(CampoMinado_Evento evento, Campo campo) {
		if(evento == CampoMinado_Evento.EXPLODIR ) {
		    notifiObserver(false);
		    mostrarMinas();
		}
		else if (venceu()) {
			notifiObserver(true);
		}
		
		
	}
	
	
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c-> !c.isMarcado())
		.forEach(r -> r.setAberto(true));
	}
	
}
