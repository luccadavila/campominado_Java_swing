package br.com.Lu.cm.visao;

import javax.swing.JFrame;

import br.com.Lu.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {
	
	
	public TelaPrincipal() {
	
	Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
	PainelTabuliero painel = new PainelTabuliero(tabuleiro);
	
	add(painel);
	
	setSize(690, 438);
	setTitle("CAMPO MINADO");	
	setVisible(true);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
	
	}
	
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}

}
