import java.io.*;
import java.util.Scanner;
import java.util.Vector;
public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String entrada;
        String saida;
        System.out.println("Nome do arquivo de entrada:");
        entrada = scanner.nextLine();
        System.out.println("Nome do arquivo de saída:");
        saida = scanner.nextLine();
        int[] projected = new int[4];
        System.out.println("Deseja projetar a coluna id_func? Sim: 1 ; Não: 0");
        projected[0] = scanner.nextInt();
        System.out.println("Deseja projetar a coluna nome? Sim: 1 ; Não: 0");
        projected[1] = scanner.nextInt();
        System.out.println("Deseja projetar a coluna sobrenome? Sim: 1 ; Não: 0");
        projected[2] = scanner.nextInt();
        System.out.println("Deseja projetar a coluna idade? Sim: 1 ; Não: 0");
        projected[3] = scanner.nextInt();
        scanner.close();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(entrada));
            String line = reader.readLine();
            int l;
            Tabela ti = new Tabela();
            while(line!=null){
                String[] tupla = line.split(",", 4);
                int id = Integer.parseInt(tupla[0]);
                String n = tupla[1];
                String s = tupla[2];
                int idade = Integer.parseInt(tupla[3]);
                Registro r = new Registro(id, n, s, idade);
                l = ti.insertRegistro(r);
                line = reader.readLine();
            }
            reader.close();
            int sb1 = 30;
            Tabela[] particoes = new Tabela[sb1];
            for (int i = 0; i < sb1; i++){
                particoes[i] = new Tabela();
            }
            for (Pagina p : ti.getPaginas()){
                for (Registro re : p.getRegistros()){
                    int h1 = 0;
                    h1 = (h1 + projected[0] * re.getID()) % sb1;
                    h1 = (h1 + projected[1] * Math.abs((re.getNome().hashCode() & 0x7fffffff))) % sb1;
                    h1 = (h1 + projected[2] * Math.abs((re.getSobrenome().hashCode() & 0x7fffffff))) % sb1;
                    h1 = (h1 + projected[3] * re.getIdade()) % sb1;
                    int hash1 = h1;
                    particoes[hash1].insertRegistro(re);
                }
            }
            int sb2 = sb1 + 10;
            Tabela ts = new Tabela();
            for (Tabela particao : particoes){
                Vector<Vector<Registro>> hashtable = new Vector<Vector<Registro>>(sb2);
                for (int c = 0; c < sb2; c++){
                    hashtable.add(new Vector<Registro>());
                }
                for (Pagina pp : particao.getPaginas()){
                    for (Registro rp : pp.getRegistros()){
                        if (rp != null){
                            int h2 = 0;
                            h2 = (h2 + projected[0]*rp.getID()) % sb2;
                            h2 = (h2 + projected[1]* Math.abs((rp.getNome().hashCode() & 0x7fffffff))) % sb2;
                            h2 = (h2 + projected[2]* Math.abs((rp.getSobrenome().hashCode() & 0x7fffffff))) % sb2;
                            h2 = (h2 + projected[3]*rp.getIdade()) % sb2;
                            int hash2 = h2;
                            int flag = 0;
                            for (Registro rh : hashtable.get(hash2)){
                                int check1 = 0;
                                int check2 = projected[0] + projected[1] + projected[2] + projected[3];
                                if ((projected[0] == 1) && (rp.getID() == rh.getID())){
                                    check1 = check1 + 1;
                                }
                                if ((projected[1] == 1) && (rp.getNome().equals(rh.getNome()))){
                                    check1 = check1 + 1;
                                }
                                if ((projected[2] == 1) && (rp.getSobrenome().equals(rh.getSobrenome()))){
                                    check1 = check1 + 1;
                                }
                                if ((projected[3] == 1) && (rp.getIdade() == rh.getIdade())){
                                    check1 = check1 + 1;
                                }
                                if (check1 == check2){
                                    flag = 1;
                                }
                            }
                            if (flag == 0){
                                hashtable.get(hash2).add(rp);
                            }
                        }
                    }
                }
                for (Vector<Registro> bucket : hashtable){
                    for (Registro reg : bucket){
                        ts.insertRegistro(reg);
                    }
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saida));
            for (Pagina pag : ts.getPaginas()){
                for (Registro registro : pag.getRegistros()){
                    if (registro != null){
                        if (projected[0] == 1){
                            writer.append(registro.getID() + " ");
                        }
                        if (projected[1] == 1){
                            writer.append(registro.getNome() + " ");
                        }
                        if (projected[2] == 1){
                            writer.append(registro.getSobrenome() + " ");
                        }
                        if (projected[3] == 1){
                            writer.append(registro.getIdade() + " ");
                        }
                        writer.append("\n");
                    }
                }
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}