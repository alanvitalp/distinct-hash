import java.util.Vector;

public class Tabela {
    private Vector<Pagina> paginas;
    private int qtd_paginas;
    public Tabela(){
        this.paginas = new Vector<Pagina>();
        this.qtd_paginas = 0;
    }
    public int insertRegistro(Registro r){
        int i;
        for (Pagina p : this.paginas){
            i = p.addRegistro(r);
            if (i != -1){
                return this.paginas.indexOf(p);
            }
        }
        Pagina pn = new Pagina();
        i = pn.addRegistro(r);
        this.paginas.add(pn);
        this.qtd_paginas = this.qtd_paginas + 1;
        return this.paginas.indexOf(pn);
    }
    public Vector<Pagina> getPaginas(){
        return this.paginas;
    }
    public int getQTD(){
        return this.qtd_paginas;
    }
}
