package br.ufal.ic.p2.myfood.banco;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import br.ufal.ic.p2.myfood.exception.escritor.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Escritor {
    private static final String CAMINHO = "./src/br/ufal/ic/p2/myfood/dados";
    private static final String ARQUIVO = CAMINHO + File.separator + "dados.xml";

    public static void salvarDados(Dados dados) throws Exception {
        File directory = new File(CAMINHO);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        apagarDados();

        try (XMLEncoder e = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(ARQUIVO)))) {

            e.writeObject(dados);

        } catch (FileNotFoundException e) {
            throw new ErroAoSalvarArquivoException();
        }
    }

    public static Dados loadDados() throws Exception {
        File arquivo = new File(ARQUIVO);
        if (arquivo.exists()) {
            try (XMLDecoder d = new XMLDecoder(
                    new BufferedInputStream(
                            new FileInputStream(ARQUIVO)))) {
                return (Dados) d.readObject();
            } catch (FileNotFoundException e) {
                throw new ErroAoCarregarArquivoException();
            }
        }
        return null;
    }

    public static void apagarDados() throws Exception {
        File arquivo = new File(ARQUIVO);
        try {
            if (arquivo.exists()) {
                Files.delete(Path.of(ARQUIVO));
            }
        } catch (IOException e) {
            throw new ErroAoApagarArquivoException();
        }
    }
    
}
