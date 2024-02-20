package br.com.Lu.cm.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.Lu.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class PainelTabuliero extends JPanel {
	
	public PainelTabuliero(Tabuleiro tabuleiro){
		
	setLayout( new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
	
		tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
		
			
			tabuleiro.gerarObservadores(e -> {
				SwingUtilities.invokeLater(()->{
				if (e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "ganhou");
				}else {
					JOptionPane.showMessageDialog(this, "perdeu");
				}
				tabuleiro.reiniciar();
			});
			
		
			
		});
		
		
		
	}

}
