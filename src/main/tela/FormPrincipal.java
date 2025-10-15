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

	private int id;

	private JButton btnConsultar = new JButton();

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
		setTitle("Tela Inicial");
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
		btnIncluir.setMnemonic('I');
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
					int row = tabelaCliente.getSelectedRow();
					if(tabelaVazia()) {
						JOptionPane.showMessageDialog(null, "Erro! A tabela está vazia", "Erro", JOptionPane.ERROR_MESSAGE);
					} else {
						if(row == -1) {
							JOptionPane.showMessageDialog(null, "Aviso! Selecione uma linha para alterar", "Aviso", JOptionPane.WARNING_MESSAGE);
						} else {
							Pessoa p = getPessoaTabela(row);
							alterarCadastro(p);
							atualizaTabela();	
						}
					}
			}
				});
		btnAlterar.setMnemonic('A');
		btnAlterar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		containerHeader.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setMnemonic('E');
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePessoa();
			}
		});
		btnExcluir.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		containerHeader.add(btnExcluir);
		btnConsultar.setMnemonic('C');
		
		btnConsultar.setText("Consultar");
		btnConsultar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tabelaCliente.getSelectedRow();
				if(tabelaVazia()) {
					JOptionPane.showMessageDialog(null, "Erro! A tabela está vazia", "Erro", JOptionPane.ERROR_MESSAGE);

				} else {
					if(tabelaCliente.getSelectedRow() == -1) {
						JOptionPane.showMessageDialog(null, "Aviso! Selecione uma linha para consultar", "Aviso", JOptionPane.WARNING_MESSAGE);
					}else {
						Pessoa p = getPessoaTabela(row);
						consultaCadastro(p);
					}	
				}
			}
		});
		containerHeader.add(btnConsultar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setMnemonic('F');
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnFechar.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		containerHeader.add(btnFechar);
		
		contentPane.add(BorderLayout.NORTH, containerHeader);
		
		tabelaCliente = new JTable();
		tabelaCliente.setBounds(0, 0, 6, 6);
		String[] colunas = {"C\u00F3digo", "Nome", "Email"};
		modelPessoa = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		tabelaCliente.setModel(modelPessoa);
		tabelaCliente.setFont(new Font("Comic Sans MS", Font.PLAIN, 13));
		tabelaCliente.getColumnModel().getColumn(0).setPreferredWidth(25);
		tabelaCliente.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabelaCliente.getColumnModel().getColumn(2).setPreferredWidth(50);

		JScrollPane scrollPane = new JScrollPane(tabelaCliente);
		contentPane.add(BorderLayout.CENTER, scrollPane);		
				
		JButton btnRemoverSelecao = new JButton("Remover Seleção");
		btnRemoverSelecao.setVisible(false);
		btnRemoverSelecao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabelaCliente.clearSelection();
			}
		});
		contentPane.add(btnRemoverSelecao, BorderLayout.SOUTH);
		btnRemoverSelecao.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		tabelaCliente.getSelectionModel().addListSelectionListener(event -> {
			if(tabelaCliente.getSelectedRow() != -1) {
				btnRemoverSelecao.setVisible(true);
			} else {
				btnRemoverSelecao.setVisible(false);
			}
		});
	}
	
	public void abrirCadastro() {
		FormCadastro tela = new FormCadastro(0, null, this, rep);
		tela.setVisible(true);
		if(tela.getPessoaAdicionada() == 1) {
			atualizaTabela();		
		}
	}
	
	public void alterarCadastro(Pessoa p) {
		if(tabelaVazia()) {
			JOptionPane.showMessageDialog(null, "Erro! A tabela está vazia", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		else if(tabelaCliente.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Aviso! Selecione uma linha para alterar", "Aviso", JOptionPane.WARNING_MESSAGE);
		}else {
			FormCadastro tela = new FormCadastro(1, p, this, rep);
			tela.setVisible(true);
			if(tela.getPessoaAtualizada() == 1) {
				System.out.println("Chegou");
				atualizaTabela();
			}
		}
	}
	
	public void consultaCadastro(Pessoa p) {
			FormCadastro tela = new FormCadastro(2, p, this, rep);
			tela.setVisible(true);
			atualizaTabela();
	}
	
	
	public void atualizaTabela() {
		modelPessoa.setRowCount(0);
		for(Pessoa p : rep.getPessoas()) {
			Object[] pessoa = {
					p.getId(),
					p.getNome(),
					p.getEmail()
			};
			modelPessoa.addRow(pessoa);
		}
	}
	
	public Pessoa getPessoaTabela(int row) {
		int id = (int) tabelaCliente.getValueAt(row, 0);
		return rep.getPessoaPorId(id);
	}
	
	public void removePessoa() {
		if(tabelaVazia()) {
			JOptionPane.showMessageDialog(null, "Erro! A tabela está vazia", "Erro", JOptionPane.ERROR_MESSAGE);
		} 
		else if(tabelaCliente.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(null, "Selecione uma linha para excluir!", "Aviso!", JOptionPane.WARNING_MESSAGE);
		} else {
			Pessoa p1 = getPessoaTabela(tabelaCliente.getSelectedRow());
			int deseja = JOptionPane.showConfirmDialog(this, "Deseja excluir a pessoa de ID: " + p1.getId() + "?", "Confirma?", JOptionPane.YES_NO_OPTION);
			if(deseja == 0) {
				rep.removerPessoa(p1);
				atualizaTabela();
			}
		}
	}
	
	public boolean tabelaVazia() {
		if(tabelaCliente.getRowCount() == 0) {
			return true;
		} else {
			return false;
		}
	}
}