package main.dados;

/*O Modelo (Model): Uma classe simples (POJO - Plain Old Java 
Object) que representa a entidade a ser cadastrada. 
Por exemplo, uma classe Pessoa com atributos como id, nome e email.*/

public class Pessoa {
	private int id;
	private String nome;
	private String email;
	
	public Pessoa(){
		this.nome = null;
		this.email = null;
	}
	
	public Pessoa(int id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}
	
	public Pessoa(String nome, String email){
		this.nome = nome;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return id + nome + email;
	}
}
