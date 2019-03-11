import java.io.RandomAccessFile;
import java.util.Scanner;
class ArquivoProduto<T extends Entidade>{

    private RandomAccessFile arquivo;
    private String nomeArquivo;
    private Constructor<T> construtor;


    public ArquivoProduto(Constructor<T> construtor, String nomeArquivo) throws IOException{
        this.nomeArquivo = nomeArquivo;
        this.construtor = construtor;
        this.arquivo = new RandomAccessFile(this.nomeArquivo, "rw");
        if (arquivo.length() < 4){
            arquivo.writeInt(0);
            indice.apagar();
        }

        
    }

    public int incluir(T prod) throws IOException{
        int ultimoId;
        arquivo.seek(0);
        ultimoId = arquivo.readInt();
        arquivo.seek(0);
        arquivo.writeInt(ultimoId++);
        prod.setId(ultimoId);

        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();
        byte[] b;
        b = prod.toByteArray();
        arquivo.writeByte(' ');
        arquivo.writeShort(b.length);
        arquivo.write(b);
        indice.inserir(prod.getId(), endereco);
        return prod.getId();

    }
    //-------------------------------------------------------------------

    public static boolean isNumber(String s) {
        return s.matches("[+-]?([0-9]*[.])?[0-9]+");
    }

    public T lerProd(T prod) {
        String aux;
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
            System.out.println("Peso: ");
            aux = scan.nextLine().replace(',', '.');
        } while (!isNumber(aux));
        if (!aux.isEmpty()){
            p.setPeso(Float.parseFloat(aux));
        }
        return p;
    }

    public void alterar(int id){
        T novo;
        T prod = excluir(id);
        novo = lerProd(prod);

        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();
        byte[] b;
        b = novo.toByteArray();
        arquivo.writeByte(' ');
        arquivo.writeShort(b.length);
        arquivo.write(b);
        indice.atualizar(novo.getId(), endereco);

    }
    // -------------------------------------------------------------------

    public boolean excluir(int id) throws Exception{
        T prod = construtor.newInstance();
        short tamanho;
        byte[] b;
        byte lapide;
        long endereco;

        endereco = indice.buscar(id);
        arquivo.seek(endereco);
        lapide = arquivo.readByte();
        tamanho = arquivo.readShort();
        b = new byte[tamanho];
        arquivo.read(b);
        prod.fromByteArray(b);
        if (lapide == ' ' && prod.getId() == id) {
            arquivo.seek(endereco);
            arquivo.writeByte('*');
            indice.excluir(id);
            return true;
        }
        return false;
    }
    // -------------------------------------------------------------------

    public T buscar(int id) {
        T prod = construtor.newInstance();
        short tamanho;
        byte[] b;
        byte lapide;

        arquivo.seek(4);
        while (arquivo.getFilePointer() < arquivo.length()) {
            lapide = arquivo.readByte();
            tamanho = arquivo.readShort();
            b = new byte[tamanho];
            arquivo.read(b);
            prod.fromByteArray(b);
            if (lapide == ' ' && prod.getId() == id) {
                return prod;
            }
        }
        return null;
    }

    public void listar() throws Exception {
        T prod = construtor.newInstance();
        short tamanho;
        byte[] b;
        byte lapide;

        arquivo.seek(4);
        while (arquivo.getFilePointer() < arquivo.length()) {
            lapide = arquivo.readByte();
            tamanho = arquivo.readShort();
            b = new byte[tamanho];
            arquivo.read(b);
            prod.fromByteArray(b);
            if (lapide == ' ')
                System.out.println(prod);
        }
    }
}