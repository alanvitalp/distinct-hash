public class Pagina {
    private Registro[] registros;
    public Pagina(){
        this.registros = new Registro[16];
    }
    public Registro[] getRegistros(){
        return this.registros;
    }
    public int addRegistro(Registro r){
        int i = 0;
        while (i < 16){
            if (this.getRegistros()[i] == null){
                this.getRegistros()[i] = new Registro(r.getID(),r.getNome(),r.getSobrenome(),r.getIdade());
                return i;
            }
            i = i + 1;
        }
        return -1;
    }
}
