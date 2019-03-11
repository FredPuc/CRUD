import java.util.*;
class Principal{
    public static Scanner scan = new Scanner(System.in);
    public static ArquivoProduto arquivoProduto = new ArquivoProduto();
    
    public static boolean isNumber(String s){
    
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
        
    }

    public static Produto lerProd(){
        Produto p = new Produto();
        String aux;
        System.out.println("Nome: ");
        aux = scan.nextLine();
        p.setNome(aux);

        System.out.println("Descrição: ");
        aux = scan.nextLine();
        p.setDescricao(aux);

        do {
            System.out.println("Quantidade: ");
            aux = scan.nextLine();   
        } while (!isNumber(aux));
        p.setQuantidade(Integer.parseInt(aux));

        do {
            System.out.println("Preço: ");
            aux = scan.nextLine();
        } while (!isNumber(aux));
        p.setPreco(Float.parseFloat(aux));

        do {
            System.out.println("Peso: ");
            aux = scan.nextLine().replace(',', '.');
        } while (!isNumber(aux));
        p.setPeso(Float.parseFloat(aux));
        return p;
    }

    public static void menu ( ){
        System.out.println("0-incluir\n1-alterar\n2-excluir\n3-buscar\n9-sair");
        String op = scan.nextLine();
        switch (op) {
            case "0":
                arquivoProduto.incluir(lerProd());
                break;
            case "1":
                arquivoProduto.alterar(0);
                break;
            case "2":
                arquivoProduto.excluir(0);
                break;
            case "3":
                arquivoProduto.buscar(0);
                break;
            default:
                
                break;
        }
        System.out.println(op);

        if (!op.equals("9")) {
            menu();
        }
        else{
            System.out.println("End");
            
        }
    }


    public static void main(String[] args) {
        menu();
    }
}