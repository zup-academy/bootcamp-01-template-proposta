package br.com.zup.proposta.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConverterArquivo {

    private List<byte[]> digitalEmBase64 = new ArrayList<>();
    private byte[] arrayFingerPrint;

    public List<byte[]> FileToBase64(List<MultipartFile> listaDigitais){

        Base64.Encoder encoder = Base64.getEncoder();

        listaDigitais.forEach(i -> { //1
            try { //2
                arrayFingerPrint = encoder.encode(i.getBytes());
                this.digitalEmBase64.add(arrayFingerPrint);
            } catch (IOException e) { //3
                e.printStackTrace();
            }
        });
        return digitalEmBase64;

    }


}
