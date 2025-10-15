package main.tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
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
import java.awt.Color;

public class FormPrincipal extends JFrame {
	private Repositorio rep = new Repositorio();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tabelaCliente;
	private DefaultTableModel modelPessoa;
	
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

	public FormPrincipal() {
		setBackground(new Color(64, 64, 64));
		ImageIcon icone = new ImageIcon("icone.png");
		setIconImage(icone.getImage());
		setTitle("Tela Inicial");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(222, 222, 800, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(64, 64, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JPanel containerHeader = new JPanel();
		containerHeader.setBackground(new Color(64, 64, 64));
		containerHeader.setLayout(new FlowLayout());
		
		// função auxiliar para aplicar hover
		java.awt.event.MouseAdapter hoverEfeito = new java.awt.event.MouseAdapter() {
			Color normal = new Color(144, 144, 144);
			Color hover = new Color(190, 190, 190);
			
			public void mouseEntered(java.awt.event.MouseEvent e) {
				JButton btn = (JButton) e.getSource();
				btn.setBackground(hover);
				btn.setForeground(Color.BLACK);
			}
			public void mouseExited(java.awt.event.MouseEvent e) {
				JButton btn = (JButton) e.getSource();
				btn.setBackground(normal);
				btn.setForeground(Color.WHITE);
			}
		};
			
		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.setBackground(new Color(144, 144, 144));
		btnIncluir.setForeground(Color.WHITE);
		btnIncluir.setMnemonic('I');
		btnIncluir.setFont(new Font("Times New Roman", Font.PLAIN, 33));
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirCadastro();
			}
		});
		btnIncluir.addMouseListener(hoverEfeito);
		containerHeader.add(btnIncluir);
		
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setBackground(new Color(144, 144, 144));
		btnAlterar.setForeground(Color.WHITE);
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
		btnAlterar.setFont(new Font("Times New Roman", Font.PLAIN, 33));
		btnAlterar.addMouseListener(hoverEfeito);
		containerHeader.add(btnAlterar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(new Color(144, 144, 144));
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setMnemonic('E');
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePessoa();
			}
		});
		btnExcluir.setFont(new Font("Times New Roman", Font.PLAIN, 33));
		btnExcluir.addMouseListener(hoverEfeito);
		containerHeader.add(btnExcluir);
		
		
		btnConsultar.setMnemonic('C');
		btnConsultar.setBackground(new Color(144, 144, 144));
		btnConsultar.setForeground(Color.WHITE);
		btnConsultar.setText("Consultar");
		btnConsultar.setFont(new Font("Times New Roman", Font.PLAIN, 33));
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
		btnConsultar.addMouseListener(hoverEfeito);
		containerHeader.add(btnConsultar);
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setBackground(new Color(144, 144, 144));
		btnFechar.setForeground(Color.WHITE);
		btnFechar.setMnemonic('F');
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
		});
		btnFechar.setFont(new Font("Times New Roman", Font.PLAIN, 33));
		btnFechar.addMouseListener(hoverEfeito);
		containerHeader.add(btnFechar);
		
		contentPane.add(BorderLayout.NORTH, containerHeader);
		
		tabelaCliente = new JTable();
		tabelaCliente.setBackground(new Color(64, 64, 64));
		tabelaCliente.setForeground(Color.WHITE);
		tabelaCliente.setSelectionBackground(new Color(90, 90, 90));
		tabelaCliente.getTableHeader().setBackground(new Color(50, 100, 220));
		tabelaCliente.getTableHeader().setForeground(new Color(222, 222, 222));
		tabelaCliente.getTableHeader().setFont(tabelaCliente.getTableHeader().getFont().deriveFont(Font.BOLD, 22));
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
		tabelaCliente.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		tabelaCliente.setRowHeight(77);
		
		JScrollPane scrollPane = new JScrollPane(tabelaCliente);
		scrollPane.getViewport().setBackground(new Color(111, 111, 111));
		
		contentPane.add(BorderLayout.CENTER, scrollPane);				
		JButton btnRemoverSelecao = new JButton("Remover Seleção");
		btnRemoverSelecao.setVisible(false);
		btnRemoverSelecao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabelaCliente.clearSelection();
			}
		});
		contentPane.add(btnRemoverSelecao, BorderLayout.SOUTH);
		btnRemoverSelecao.setBackground(new Color(144, 144, 144));
		btnRemoverSelecao.setForeground(Color.WHITE);
		btnRemoverSelecao.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnRemoverSelecao.addMouseListener(hoverEfeito);
		
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
		return tabelaCliente.getRowCount() == 0;
	}
}
