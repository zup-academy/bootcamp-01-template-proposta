package br.com.zup.proposta.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ConverterArquivo {

    private byte[] digitalEmBase64;

    public byte[] FileToBase64(MultipartFile file){

        Base64.Encoder encoder = Base64.getEncoder();

        try{ //1
            this.digitalEmBase64 = encoder.encode(file.getBytes());
        } catch (IOException e) { //2
                e.printStackTrace();
        }
        return digitalEmBase64;

    }


}
