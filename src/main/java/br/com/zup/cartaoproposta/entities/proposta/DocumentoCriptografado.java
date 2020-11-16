package br.com.zup.cartaoproposta.entities.proposta;

import br.com.zup.cartaoproposta.util.AESEncryptionDecryption;

/**
 * Contagem de carga intrínseca da classe: 1
 */

public class DocumentoCriptografado {

    //1
    private AESEncryptionDecryption encryptionDecryption  = new AESEncryptionDecryption();
    private String documento;

    public DocumentoCriptografado(String documento, String chave) {
        this.documento = encryptionDecryption.encrypt(documento, chave);
    }

    public String getDocumentoCriptografado() {
        return documento;
    }

}
