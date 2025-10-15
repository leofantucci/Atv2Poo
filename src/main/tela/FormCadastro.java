package main.tela;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.dados.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class FormCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldCodigo;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	
	private Repositorio rep;
	private int pessoaAdicionada = 0;
	private int pessoaAtualizada = 0;
	
	public FormCadastro(int key, Pessoa p, Frame parent, Repositorio rep) {
	    super(parent, "Cadastro de Pessoa", true);
	    setBackground(new Color(64, 64, 64));
	    getContentPane().setBackground(new Color(64, 64, 64));
	    this.rep = rep;
	    
	    
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(64, 64, 64));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblId = new JLabel("Código (ID)");
		lblId.setForeground(Color.white);
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("Dialog", Font.PLAIN, 22));
		lblId.setBounds(138, 16, 160, 32);
		contentPanel.add(lblId);
	
		textFieldCodigo = new JTextField();
		textFieldCodigo.setBackground(new Color(90, 90, 90));
		textFieldCodigo.setEditable(false);
		textFieldCodigo.setFocusable(false);
		textFieldCodigo.setBackground(new Color(192, 192, 192));
		textFieldCodigo.setBounds(107, 49, 222, 33);
		contentPanel.add(textFieldCodigo);
		textFieldCodigo.setText(String.valueOf(rep.getIdAtual()));
		textFieldCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setForeground(Color.white);
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Dialog", Font.PLAIN, 22));
		lblNome.setBounds(138, 85, 160, 22);
		contentPanel.add(lblNome);
		
		textFieldNome = new JTextField();
		textFieldNome.setBackground(new Color(90, 90, 90));
		textFieldNome.requestFocusInWindow();
		textFieldNome.setBackground(new Color(222, 222, 222));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(107, 111, 222, 33);
		contentPanel.add(textFieldNome);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBackground(new Color(90, 90, 90));
		textFieldEmail.setBackground(new Color(222, 222, 222));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(107, 171, 222, 33);
		contentPanel.add(textFieldEmail);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.white);
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("Dialog", Font.PLAIN, 22));
		lblEmail.setBounds(138, 147, 160, 22);
		contentPanel.add(lblEmail);
	
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(64, 64, 64));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBackground(new Color(144, 144, 144));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validaTextField(textFieldNome, "Nome") && validaTextField(textFieldEmail, "Email")) {
					switch(key) { //key = Seleção entre add(0) / Atualizar user (1) 
						case 0:
							salvarPessoa();
							break;
						case 1:
							atualizaPessoa(p);
							break;		
					}
				}	
			}
				});
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnCadastrar.setActionCommand("OK");
				buttonPane.add(btnCadastrar);
				getRootPane().setDefaultButton(btnCadastrar);
				
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setBackground(new Color(144, 144, 144));
				btnCancelar.setForeground(Color.WHITE);
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 16));
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
				
				switch(key) { // Alterar (1), Visualizar (2)
				case 1:
					setTitle("Edição de Pessoa");
					textFieldCodigo.setText(Integer.toString(p.getId())); 
					textFieldNome.setText(p.getNome());
					textFieldEmail.setText(p.getEmail());
					break;
				
				case 2:
					setTitle("Visualização de Pessoa");
					textFieldCodigo.setText(Integer.toString(p.getId()));
					textFieldCodigo.setEditable(false);
					textFieldNome.setText(p.getNome());
					textFieldNome.setEditable(false);
					textFieldEmail.setText(p.getEmail());
					textFieldEmail.setEditable(false);
					btnCadastrar.setVisible(false);
					break;
			}
		
	}
	
	public Repositorio criarRep() {
		Repositorio rep = new Repositorio();
		return rep;
	}
	
	public void salvarPessoa() {
		Pessoa p = new Pessoa();
	    p.setId(Integer.parseInt(textFieldCodigo.getText().trim()));
	    p.setNome(textFieldNome.getText().trim());
	    p.setEmail(textFieldEmail.getText().trim());
		System.out.println(pessoaAdicionada);

		if(validaTextField(textFieldNome, "nome") && validaTextField(textFieldNome, "email")) {
			rep.adicionarPessoa(p);
		}
		if(validaPessoaRep(p)) {
			pessoaAdicionada = 1;
			JOptionPane.showMessageDialog(null, "Pessoa adicionada com sucesso");
		} else{
			pessoaAdicionada = 0;
			JOptionPane.showMessageDialog(null, "Erro na hora de botar", "Erro", JOptionPane.WARNING_MESSAGE);
		}		
		dispose();
	}
	
	public boolean validaPessoaRep(Pessoa p) {
		if(rep.getPessoas().contains(p)) {
			 return true;
		} else {
			return false;
		}		
	}
	
	public boolean validaTextField(JTextField t, String campo) {
		if(t.getText().trim().isEmpty()) {
			t.requestFocusInWindow();
			JOptionPane.showMessageDialog(t, "Preencha corretamente o campo " + campo +"!", "Aviso", JOptionPane.WARNING_MESSAGE);
			return false;
		} else {
			return true;
		}
	}
	
	public int getPessoaAdicionada() {
		return pessoaAdicionada;
	}
	
	public int getPessoaAtualizada() {
		return pessoaAtualizada;
	}
	
	public void atualizaPessoa(Pessoa p) {
		if(validaPessoaRep(p)) {
			if(validaTextField(textFieldNome, "nome") && validaTextField(textFieldEmail, "email")) {
				p.setNome(textFieldNome.getText());
				p.setEmail(textFieldEmail.getText());
				pessoaAtualizada = 1;
				JOptionPane.showMessageDialog(null, "Pessoa atualizada com sucesso");
			} else {
				pessoaAtualizada = 0;
				JOptionPane.showMessageDialog(null, "Erro na hora de atualizar", "Erro", JOptionPane.WARNING_MESSAGE);
			}
		}
		dispose();
	}
}
