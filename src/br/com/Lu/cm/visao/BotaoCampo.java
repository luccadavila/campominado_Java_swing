package br.com.Lu.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.Lu.cm.modelo.Campo;
import br.com.Lu.cm.modelo.CampoMinado_Evento;
import br.com.Lu.cm.modelo.ObserverCampo;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements ObserverCampo , MouseListener{
	private final Color BG_PADRAO = new Color(184,184,184);
	private final Color BG_MARCADO = new Color(8,179,247); 
	private final Color BG_EXPLODIR = new Color(189,66,68); 
	private final Color TEXTO_VERDE = new Color(0,100,0); 
	
	private Campo campo;
	
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBorder(BorderFactory.createBevelBorder(0));
		setBackground(BG_PADRAO);
		setOpaque(true);
		addMouseListener(this);
	    campo.registrarObserver(this);
	}
	
@Override
	public void eventoCampo(CampoMinado_Evento evento, Campo campo) {
		switch(evento) {
		case ABRIR:
			estiloabrir();
			
			break;
		
		case MARCAR:
			estilomarcar();
			break;
			
		case EXPLODIR :
			estiloaexplodir();
			break;
			
			default:
				aplicatestilopadrao();
				
				
		}
		if(!campo.isMinado()&&campo.isAberto() ) {
					String valor = !campo.abrirvizinhanca() ? campo.minasVinzinhas() + "" : "";
					setText(valor);}		
		
		
	}
    private void estiloaexplodir() {
	   setBackground(BG_EXPLODIR);
	   setForeground(Color.WHITE);
	   setText("X");
     }

	private void aplicatestilopadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
		
	}



	private void estilomarcar() {
		setBackground(BG_MARCADO);
		setText("M");
		
	}

	private void estiloabrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
			
		}
		
		setBackground(BG_PADRAO);
		switch (campo.minasVinzinhas()) {
		case 1: 
			setForeground(TEXTO_VERDE);
		break;
		
		case 2: 
			setForeground(Color.BLUE);
		break;
		
		case 3: 
			setForeground(Color.YELLOW);
		break;
		
		case 4:
			setForeground(Color.RED);
			break;
		case 5:
			setForeground(Color.RED);
			break;
		case 6:	
			setForeground(Color.RED);
		break;
		 default:
			 setForeground(Color.PINK);
			 break;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
		
			campo.abrir();
		}else {
			campo.marcados();
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {}
	
	public void mouseEntered(MouseEvent e) {}
	
	public void mouseExited(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
	
}
