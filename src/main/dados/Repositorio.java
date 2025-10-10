package main.dados;

import java.util.ArrayList;

/*O Repositório de Dados (Data Layer): Uma classe que centralizará o acesso aos dados. 
Como não usaremos um banco de dados, esta classe terá uma ArrayList<Pessoa> estática.
O uso de static garante que a mesma lista de dados seja acessível por todas as janelas da aplicação*/

public class Repositorio {
	private ArrayList<Pessoa> pessoas = new ArrayList<>();
	private int id = 1;
	
	public Repositorio() {
		this.pessoas = new ArrayList<>();
	}
	
	public void adicionarPessoa(Pessoa p) {
		pessoas.add(p);
		p.setId(id);
		id++;
	}
	
	public void mostrarPessoas() {
		for(int i = 0; i < pessoas.size(); i++) {
			System.out.println(pessoas.get(i));
		}
	}
	
	public void removerPessoaPorId(int id) {
		int pos = id - 1;
		pessoas.remove(pos);
	}
	
	public void removerPessoa(Pessoa p) {
		pessoas.remove(p);
	}
	
	public int getIdAtual() {
		return id;
	}

	public ArrayList<Pessoa> getPessoas(){
		return pessoas;
	}
	
	public ArrayList<Integer> getIdPessoas(){
		ArrayList<Integer> ret = new ArrayList<>();
		for(Pessoa p : pessoas) {
			ret.add(p.getId());
		}
		return ret;
	}
	
	public String getNomePorId(int id) {
		return pessoas.get(id).getNome();
	}
	
	public String getEmailPorId(int id) {
		return pessoas.get(id).getEmail();
	}
	
	public Pessoa getPessoaPorId(int id) {
		for(Pessoa p : pessoas) {
			if(p.getId() == id) {
				return p;
			}
		}
		return null;
	}
	
}
