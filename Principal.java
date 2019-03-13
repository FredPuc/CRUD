import java.util.*;
class Principal{
    public static Scanner scan = new Scanner(System.in);
    public static ArquivoProduto arquivoProduto;
    
    public static boolean isNumber(String s){
    
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
        
    }
    public static int ler(){
        System.out.print("Entre com o Id: ");
        int s = scan.nextInt();
        return s;
    }
    public static void menu ( ) throws Exception {
        System.out.println("0-incluir\n1-alterar\n2-excluir\n3-buscar\n4-listar\n9-sair");
        String op = scan.nextLine();
        switch (op) {
            case "0":
                arquivoProduto.incluir(arquivoProduto.lerEntidade());
                break;
            case "1":
                
                arquivoProduto.alterar(ler());
                break;
            case "2":
                arquivoProduto.excluir(ler());
                break;
            case "3":
                Produto x = arquivoProduto.buscar(ler());
                System.out.println(x);
                break;
            case "4":
                arquivoProduto.listar();
                break;
            default:
                
                break;
        }

        if (!op.equals("9")) {
            menu();
        }
        else{
            System.out.println("End");
            
        }
    }


    public static void main(String[] args) {
        try {
            arquivoProduto = new ArquivoProduto("produtos.txt", "indice");
            menu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}