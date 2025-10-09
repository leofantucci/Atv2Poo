package main.tela;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import java.awt.event.ActionEvent;

public class FormCadastro extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldCodigo;
	private JTextField textFieldNome;
	private JTextField textFieldEmail;
	
	private Repositorio rep;
	private int pessoaAdicionada = 0;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	
	public FormCadastro(Frame parent, Repositorio rep) {
	    super(parent, "Cadastro de Pessoa", true);
	    this.rep = rep;
		Pessoa p1 = new Pessoa();
		p1.setId(rep.getIdAtual());
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JLabel lblId = new JLabel("Código (ID)");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblId.setBounds(138, 30, 160, 22);
		contentPanel.add(lblId);
	
		textFieldCodigo = new JTextField();
		textFieldCodigo.setEditable(false);
		textFieldCodigo.setBackground(new Color(192, 192, 192));
		textFieldCodigo.setBounds(138, 53, 160, 22);
		contentPanel.add(textFieldCodigo);
		textFieldCodigo.setText(String.valueOf(rep.getIdAtual()));
		textFieldCodigo.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblNome.setBounds(138, 84, 160, 22);
		contentPanel.add(lblNome);
		textFieldNome = new JTextField();
		textFieldNome.requestFocus();
		textFieldNome.setBackground(new Color(222, 222, 222));
		textFieldNome.setColumns(10);
		textFieldNome.setBounds(138, 106, 160, 22);
		contentPanel.add(textFieldNome);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBackground(new Color(222, 222, 222));
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(138, 160, 160, 22);
		contentPanel.add(textFieldEmail);

		JLabel lblEmail = new JLabel("Email");
		lblId.setHorizontalAlignment(SwingConstants.CENTER);
		lblId.setFont(new Font("Dialog", Font.PLAIN, 17));
		lblId.setBounds(138, 138, 160, 22);
		contentPanel.add(lblId);
	
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(validaTextField(textFieldNome, "Nome") && validaTextField(textFieldEmail, "Email")) {
					salvarPessoa();
				}	
			}
				});
				btnCadastrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
				btnCadastrar.setActionCommand("OK");
				buttonPane.add(btnCadastrar);
				getRootPane().setDefaultButton(btnCadastrar);
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
	
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
		String id = textFieldCodigo.getText().trim();
		String nome = textFieldNome.getText().trim();
		String email = textFieldEmail.getText().trim();
		System.out.println(pessoaAdicionada);

		if(!textFieldNome.getText().trim().isEmpty() && !textFieldEmail.getText().trim().isEmpty()) {
			rep.adicionarPessoa(p);
		}
		
		if(rep.getPessoas().contains(p)) {
			pessoaAdicionada = 1;
			JOptionPane.showMessageDialog(this, "Pessoa adicionada com sucesso");
			System.out.println(id + " " + nome + " " + email);
			dispose();
		} else{
			pessoaAdicionada = 0;
			JOptionPane.showMessageDialog(this, "Erro na hora de botar", "Erro", JOptionPane.WARNING_MESSAGE);
			dispose();
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
}
