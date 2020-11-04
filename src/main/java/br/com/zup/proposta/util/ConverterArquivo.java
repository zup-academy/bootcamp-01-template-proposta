package br.com.zup.proposta.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConverterArquivo {

    private byte[] digitalEmBase64;

    public byte[] fileToBase64(String digital){

        try {
            return Base64.getDecoder().decode(digital);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException();
        }

    }

}
