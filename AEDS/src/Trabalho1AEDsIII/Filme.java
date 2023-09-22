package Trabalho1AEDsIII;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
public class Filme implements Serializable {
    private int id;
    private String Titulo;
    private String Sinopse;
    private float popularidade;
    private String datadelancamento;
    private float mediadevotos;
    private int quantidadevotos;
    private boolean lapide;
    public Filme (int ID, String Tit, String Sin, float pop, String dtlanc, float medvot, int qnt){
        this.id = ID;
        this.Titulo = Tit;
        this.Sinopse = Sin;
        this.popularidade = pop;
        this.datadelancamento = dtlanc;
        this.mediadevotos = medvot;
        this.quantidadevotos = qnt;
        this.lapide = false;
    }
    public Filme (){

    }
    public int GetID (){
        return id;
    }
    public void SetId(int id) {
        this.id = id;
    }
    public String GetTitulo() {
        return Titulo;
    }
    public void SetTitulo(String Titu) {
        this.Titulo = Titu;
    }
    public String GetSinopse() {
        return Sinopse;
    }
    public void SetSinopse(String Sin) {
        this.Sinopse = Sin;
    }
    public float GetPopularidade() {
        return popularidade;
    }
    public void SetPopularidade(float pop) {
        this.popularidade = pop;
    }
    public String GetDtLancamento () {
        return datadelancamento;
    }
    public void SetDtLancamento (String DTLanca) {
        this.datadelancamento = DTLanca ;
    }
    public float GetMedVot () {
        return mediadevotos;
    }
    public void SetMedVot (float MDVot) {
        this.mediadevotos = MDVot ;
    }
    public int GetQNTVotos () {
        return quantidadevotos;
    }
    public void SetQNTVotos (int QNTVotos) {
        this.quantidadevotos = QNTVotos ;
    }
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", Titulo='" + Titulo + '\'' +
                ", Sinopse='" + Sinopse + '\'' +
                ", Popularidade='" + popularidade + '\'' +
                ", Data de Lançamento ='" + datadelancamento + '\'' +
                ", Media de Votos='" + mediadevotos + '\'' +
                ", Quantidade de Votos='" + quantidadevotos + '\'' +
                '}';
    }
    public static long TamanhoArqCSV(String src) throws FileNotFoundException {
        Path arq = Paths.get(src);
        long i = 0;

        try {
            i = Files.lines(arq).count();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return i;
    }
    public static Filme fromByteArray(byte[] byteArray) throws IOException, ClassNotFoundException {
        //Esta linha cria um objeto ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
        //Aqui, um ObjectInputStream é criado ele é usado para ler objetos serializados a partir de fluxos de bytes
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        //Esta linha lê o objeto serializado do fluxo de bytes usando o ObjectInputStream o código realiza um downcast (conversão) do objeto para o tipo Filme e armazena-o na variável filme
        Filme filme = (Filme) objectInputStream.readObject();
        //Fechando o object
        objectInputStream.close();
        //Retorna a instância de Filme lida a partir dos bytes serializados.
        return filme;
    }
    //Esse trecho de código Java tem a função de armazenar objetos da classe Filme em um arquivo binário.
    public void ArmzArquivoDB(List<Filme> filmeList, String dbFilePath) throws IOException {
        //Aqui, um objeto RandomAccessFile é criado para o arquivo especificado por dbFilePath. O modo "rw" indica que o arquivo será aberto para leitura e escrita.
        RandomAccessFile arq = new RandomAccessFile(dbFilePath, "rw");
        //contador para saber se todos os registros foram armazenados
        int cont = 0;

        for (Filme filme : filmeList) {
            //Verifica se o objeto filme não é null
            if (filme != null) {
                // Se existe, serializa e salva no arquivo .db
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

                objectOutputStream.writeObject(filme);
                objectOutputStream.close();

                byte[] arrayBytes = byteArrayOutputStream.toByteArray();
                arq.writeInt(arrayBytes.length);
                arq.write(arrayBytes);
                cont++;
            }
        }

        System.out.println("Quantidade de Registros armazenados: " + cont);
        arq.close();
    }
    public static void MostraDadoDB(String dbFilePath, int num_reg) throws IOException, ParseException {
        //Cria uma instância vazia da classe Filme. Essa instância será usada para armazenar temporariamente os objetos lidos do arquivo.
        Filme filme = new Filme();
        //Declara uma variável tamanho que será usada para armazenar o tamanho dos dados de um objeto serializado.
        int tamanho;
        //Declara um array de bytes arrayBytes que será usado para armazenar os bytes lidos do arquivo e que representam o objeto serializado.
        byte[] arrayBytes;
        //Cria um objeto RandomAccessFile para abrir o arquivo binário especificado por dbFilePath no modo de leitura e escrita ("rw").
        RandomAccessFile arq = new RandomAccessFile(dbFilePath, "rw");
        //Lê um valor longo a partir do arquivo, que é usado para armazenar a posição atual no arquivo. Isso permite que você saiba onde os registros estão armazenados no arquivo.
        long filePointer = arq.readLong();
        //O código entra em um loop que vai de 0 até num_reg - 1, onde num_reg é o número de registros que devem ser lidos e exibidos.
        for (int i = 0; i < num_reg; i++) {
            try {
                //Armazena a posição atual no arquivo antes de ler o próximo registro.
                filePointer = arq.getFilePointer();
                //Lê o tamanho dos dados do próximo objeto serializado no arquivo.
                tamanho = arq.readInt();
                //Cria um array de bytes com o tamanho lido na etapa anterior. (array de tamanho variável)
                arrayBytes = new byte[tamanho];
                //Lê os bytes do objeto serializado do arquivo e armazena-os no array de bytes.
                arq.read(arrayBytes);
                // Agora, você precisa desserializar o objeto
                filme = Filme.fromByteArray(arrayBytes);
                //Exibe o objeto filme no console.
                System.out.println(filme);

            } catch (NullPointerException | ClassNotFoundException e) {
                System.out.println("NullPointerException thrown!");
            }
        }

        arq.seek(0);
        arq.writeLong(filePointer);
        arq.close();
    }
}
