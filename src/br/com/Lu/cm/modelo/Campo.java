package br.com.Lu.cm.modelo;

import java.util.ArrayList;
import java.util.List;



public class Campo {

	
	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean marcado;
    private boolean minado; 
    
    private  List<Campo> vizinhos = new ArrayList<Campo>();
	private final List<ObserverCampo> cmpobserver = new ArrayList<>();
    
	Campo(int linha , int coluna){
		this.linha = linha;
		this.coluna=coluna;
		
	}
 
 
	boolean adicionarVizim(Campo a) {
		
		boolean linhaDifere = linha != a.linha;
		boolean colunaDifere = coluna != a.coluna;
		boolean Diagonal = linhaDifere && colunaDifere;
		
		int deltaLinha = Math.abs(linha - a.linha); 
		int deltaColuna = Math.abs(coluna - a.coluna);
        int deltaGeral = deltaColuna + deltaLinha;
       
        if(deltaGeral == 1 & !Diagonal) {
        	vizinhos.add(a);
        	return true;
        }
        else if(deltaGeral == 2 & Diagonal) {
        	vizinhos.add(a);
        	return true;
        }
        else return false;
        
	}
	
	public void registrarObserver(ObserverCampo observador) {
		cmpobserver.add(observador);
	}
	
	private void notifiObserver(CampoMinado_Evento e) {
		cmpobserver.stream()
		.forEach(o -> o.eventoCampo(e, this));
		
	}
	
	
	public void marcados() {
		if(!aberto) {
			marcado = !marcado;
			if(marcado) {
				notifiObserver(CampoMinado_Evento.MARCAR);
			}else {
				notifiObserver(CampoMinado_Evento.DESMARCAR);
			}
			
		}
	}
	
	void minar() {
		minado =true;
		
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	  
	public boolean isAberto() {
			return aberto;
	}
	
	
		
	 void setAberto(boolean aberto) {
		this.aberto = aberto;
		if(aberto ) {
			notifiObserver(CampoMinado_Evento.ABRIR);
		}
	}


	public int getLinha() {
			return linha;
	}

	public int getColuna() {
			return coluna;
	}
	
	
	public boolean abrir() {
		if (!aberto & !marcado) {
			aberto = true;
			 if(minado) {
				 notifiObserver(CampoMinado_Evento.EXPLODIR);
		        return true;
		     }
			 setAberto(aberto);
		
			 
		     if(abrirvizinhanca()) {
			   vizinhos.forEach(v->v.abrir());
			 } 
	         return true;
		}
		else
	    return false;
	 }
	  public boolean abrirvizinhanca() {
		return vizinhos.stream().noneMatch(n -> n.minado);
	}
	  
	
	  
	 boolean venceu() {
		 boolean desvendado = !minado && aberto;
		 boolean protegido = minado && marcado;
		 return protegido || desvendado;
		 
	 }
	 
	public int minasVinzinhas() {
	return (int) vizinhos.stream().filter(v -> v.minado).count();		
	}
	  
	  void reiniciar() {
		  aberto = false;
		  minado = false;
		  marcado = false;
		  notifiObserver(CampoMinado_Evento.REINICIAR);
	  }
	

}
