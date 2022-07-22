package com.soulcode.Servicos.Controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.soulcode.Servicos.Services.FuncionarioService;
import com.soulcode.Servicos.Util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.module.ResolutionException;
@CrossOrigin // comunicação entre 2 portas diferentes,
@RestController
@RequestMapping("servicos")
public class UploadFileController {

    @Autowired
    FuncionarioService funcionarioService;

    @PostMapping("/funcionarios/envioFoto/{idFuncionario}")
    public ResponseEntity<Void> enviarFoto(@PathVariable Integer idFuncionario,
                                           MultipartFile file,
                                           @RequestParam("nome") String nome) {
        String fileName = nome;
        String uploadDir = "/Users/naati/Downloads/FotosFunc";
        String nomeMaisCaminho = "/Users/naati/Downloads/FotosFunc/" + nome;

        try{
            UploadFile.saveFile(uploadDir, fileName, file);
            funcionarioService.salvarFoto(idFuncionario, nomeMaisCaminho);
        } catch (IOException e) {
            System.out.println("O arquivo não foi enviado: " + e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}