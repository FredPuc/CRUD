import java.io.IOException;
public class Produto implements Entidade {
    private int id;
    private String nome;
    private String descricao;
    private float preco;
    private int quantidade;
    private float peso;

    public Produto(){    
        this.id = -1;
        this.nome = "";
        this.descricao = "";
        this.preco = 0.0;
        this.quantidade = 0;
        this.peso = 0.0;
    }

    public Produto(int id, String nome, String descricao, float preco, int quantidade, float peso) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.peso = peso;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return this.preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPeso() {
        return this.peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", preco='" + getPreco() + "'" +
            ", quantidade='" + getQuantidade() + "'" +
            ", peso='" + getPeso() + "'" +
            "}";
    }
    
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream dados = new ByteArrayOutputStream();
        DataOutputStream saida = new DataOutputStream(dados);
        saida.writeInt(this.id);
        saida.writeUTF(this.nome);
        saida.writeUTF(this.descricao);
        saida.writeFloat(this.preco);
        saida.writeInt(this.quantidade);
        saida.writeFloat(this.peso);
        return dados.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException {
        ByteArrayInputStream dados = new ByteArrayInputStream(b);
        DataInputStream entrada = new DataInputStream(dados);
        this.id = entrada.readInt();
        this.nome = entrada.readUTF();
        this.descricao = entrada.readUTF();
        this.preco = entrada.readFloat();
        this.quantidade = entrada.readInt();
        this.peso = entrada.readFloat();
    }
    
}