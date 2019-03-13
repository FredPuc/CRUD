import java.io.IOException;
import java.util.Scanner;

public class ArquivoProduto extends ArquivoGenerico<Produto> {
    private static Scanner scan = new Scanner(System.in);

    public ArquivoProduto(String nomeArquivo, String nomeIdx) throws IOException {
        super(nomeArquivo, nomeIdx);
    }

    
    public Produto criaInstancia() {
        return new Produto();
    }

    
    public Produto lerEntidade() {
        String aux;
        Produto p = new Produto();

        System.out.print("Nome: ");
        aux = scan.nextLine();
        if (!aux.isEmpty()){
            p.setNome(aux);
        }

        System.out.print("Descrição: ");
        aux = scan.nextLine();
        if (!aux.isEmpty()){
            p.setDescricao(aux);
        }

        do {
            System.out.print("Quantidade: ");
            aux = scan.nextLine();
        } while (!isNumber(aux));
        if (!aux.isEmpty()){
            p.setQuantidade(Integer.parseInt(aux));
        }

        do {
            System.out.print("Preço: ");
            aux = scan.nextLine();
        } while (!isNumber(aux));
        if (!aux.isEmpty()){
            p.setPreco(Float.parseFloat(aux));
        }

        do {
            System.out.print("Peso: ");
            aux = scan.nextLine().replace(',', '.');
        } while (!isNumber(aux));
        if (!aux.isEmpty()){
            p.setPeso(Float.parseFloat(aux));
        }
        return p;
    }

    public boolean confirmaAlteracao(){
        String s = "";
        do {
            System.out.print("Deseja alterar o registro? (s/n)");
            s = scan.nextLine();
        } while (!(s.equals("s") || s.equals("n")));
        return "s".equals(s);
    }
    
    public static boolean isNumber(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }
}