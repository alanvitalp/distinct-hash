public class Registro {
    private int id_func;
    private String nome;
    private String sobrenome;
    private int idade;
    public Registro(int id, String n, String s, int i){
        this.id_func = id;
        this.nome = n;
        this.sobrenome = s;
        this.idade = i;
    }
    public int getID(){
        return this.id_func;
    }
    public String getNome(){
        return this.nome;
    }
    public String getSobrenome(){
        return this.sobrenome;
    }
    public int getIdade(){
        return this.idade;
    }
}
