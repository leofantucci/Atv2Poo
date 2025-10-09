package main.tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.dados.Pessoa;
import main.dados.Repositorio;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import javax.swing.JTable;

public class FormPrincipal extends JFrame {
	private Repositorio rep = new Repositorio();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaCliente;
	private DefaultTableModel modelPessoa;
	
	private JButton btnConsultar = new JButton();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormPrincipal frame = new FormPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public FormPrincipal() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JPanel containerHeader = new JPanel();
		containerHeader.setLayout(new FlowLayout());
			
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirCadastro();
			}
		});
		containerHeader.add(btnIncluir);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(tabelaCliente.getSelectedRow());
				
			}
		});
		btnAlterar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		containerHeader.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		containerHeader.add(btnExcluir);
		
		btnConsultar.setText("Consultar");
		btnConsultar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rep.getPessoas().size() > 0) {
					atualizaTabela();
					btnConsultar.setText("Consultar");
				} else {
					JOptionPane.showMessageDialog(FormPrincipal.this, "Ainda não existem dados cadastrados!", "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		containerHeader.add(btnConsultar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		containerHeader.add(btnFechar);
		
		contentPane.add(BorderLayout.NORTH, containerHeader);
		
		tabelaCliente = new JTable();
		tabelaCliente.setBounds(0, 0, 6, 6);
		String[] colunas = {"C\u00F3digo", "Nome", "Email"};
		modelPessoa = new DefaultTableModel(colunas, 0) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		     
		        return false;
		    }
		};
		tabelaCliente.setModel(modelPessoa);
		tabelaCliente.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
		
		tabelaCliente.getColumnModel().getColumn(0).setPreferredWidth(25);
		tabelaCliente.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabelaCliente.getColumnModel().getColumn(2).setPreferredWidth(50);

		JScrollPane scrollPane = new JScrollPane(tabelaCliente);
		contentPane.add(scrollPane);		
		
		

	}
	
	public void abrirCadastro() {
		FormCadastro tela = new FormCadastro(this, rep);
		
		tela.setVisible(true);
		if(tela.getPessoaAdicionada() == 1) {
			btnConsultar.setText("Consultar (*)");
		}
	}
	
	public void atualizaTabela() {
		modelPessoa.setRowCount(0);
		for(Pessoa p : rep.getPessoas()) {
			Object[] pessoa = {
					p.getId() - 1,
					p.getNome(),
					p.getEmail()
			};
			modelPessoa.addRow(pessoa);
		}
	}
}
