import java.io.RandomAccessFile;
import java.util.Scanner;
import java.io.IOException;

abstract class ArquivoGenerico<T extends Entidade> {

    private RandomAccessFile arquivo;
    private String nomeArquivo;
    private String nomeIndice;
    private Indice indice;

    public ArquivoGenerico(String nomeArquivo, String nomeIdx) throws IOException{
        this.nomeArquivo = nomeArquivo;
        this.nomeIndice = nomeIdx;
        indice = new Indice(20, this.nomeIndice);
        this.arquivo = new RandomAccessFile(this.nomeArquivo, "rw");
        if (arquivo.length() < 4){
            arquivo.writeInt(0);
            indice.apagar();
        }
    }

    public int incluir(T entidade) throws IOException {
        int ultimoId;
        arquivo.seek(0);
        ultimoId = arquivo.readInt();
        arquivo.seek(0);
        arquivo.writeInt(++ultimoId);
        entidade.setId(ultimoId);

        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();
        byte[] b;
        b = entidade.toByteArray();
        arquivo.writeByte(' ');
        arquivo.writeShort(b.length);
        arquivo.write(b);
        indice.inserir(entidade.getId(), endereco);
        return entidade.getId();
    }
    //-------------------------------------------------------------------

    public void alterar(int id) throws Exception {
        T novo;
        T entidade = this.buscar(id);
        System.out.println("Registro a ser alterado :");
        System.out.println(entidade);
        novo = this.lerEntidade();
        novo.setId(id);

        if (this.confirmaAlteracao()) {
            this.excluir(id);
            arquivo.seek(arquivo.length());
            long endereco = arquivo.getFilePointer();
            byte[] b;
            b = novo.toByteArray();
            arquivo.writeByte(' ');
            arquivo.writeShort(b.length);
            arquivo.write(b);
            indice.atualizar(novo.getId(), endereco);
        }
    }
    // -------------------------------------------------------------------

    public boolean excluir(int id) throws Exception{
        T entidade = this.buscar(id);
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
        entidade.fromByteArray(b);
        if (lapide == ' ' && entidade.getId() == id) {
            arquivo.seek(endereco);
            arquivo.writeByte('*');
            indice.excluir(id);
            return true;
        }
        return false;
    }
    // -------------------------------------------------------------------

    public T buscar(int id) throws Exception {
        T entidade = this.criaInstancia();
        short tamanho;
        byte[] b;
        byte lapide;

        arquivo.seek(4);
        while (arquivo.getFilePointer() < arquivo.length()) {
            lapide = arquivo.readByte();
            tamanho = arquivo.readShort();
            b = new byte[tamanho];
            arquivo.read(b);
            entidade.fromByteArray(b);
            if (lapide == ' ' && entidade.getId() == id) {
                return entidade;
            }
        }
        return null;
    }

    public void listar() throws Exception {
        T entidade = this.criaInstancia();
        short tamanho;
        byte[] b;
        byte lapide;

        arquivo.seek(4);
        while (arquivo.getFilePointer() < arquivo.length()) {
            lapide = arquivo.readByte();
            tamanho = arquivo.readShort();
            b = new byte[tamanho];
            arquivo.read(b);
            entidade.fromByteArray(b);
            if (lapide == ' ')
                System.out.println(entidade);
        }
    }

    abstract T lerEntidade();
    abstract T criaInstancia();
    abstract boolean confirmaAlteracao();
}