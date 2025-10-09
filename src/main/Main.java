package main;

import main.dados.Pessoa;
import main.dados.Repositorio;


public class Main {
	public static void main(String[] args) {
		Pessoa p1 = new Pessoa("Leo", "Leo@email.com");
		Pessoa p2 = new Pessoa("jao", "jao@email.com");
		
		Repositorio rep = new Repositorio();
		rep.adicionarPessoa(p1);
		rep.adicionarPessoa(p2);
		rep.mostrarPessoas();
		rep.removerPessoa(1);
		rep.mostrarPessoas();
	}
}
