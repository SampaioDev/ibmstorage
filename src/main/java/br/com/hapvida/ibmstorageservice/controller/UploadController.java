package br.com.hapvida.ibmstorageservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.hapvida.ibmstorageservice.entity.OperadorEntity;
import br.com.hapvida.ibmstorageservice.repository.OperadorRepository;
import br.com.hapvida.ibmstorageservice.service.interfaces.IUploadService;

@RestController
@RequestMapping ("/upload")
public class UploadController {

	@Autowired
	IUploadService uploadService;

	@Autowired
	OperadorRepository operadorRepository;
	
	@RequestMapping(value="/uploadibm", method = RequestMethod.POST, consumes = "multipart/form-data")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("cdOperador") Long cdOperador, @RequestParam("cpfCliente") Long cpfCliente  /*, @RequestParam("dtContato") Date dataContato*/) {
		try {
			OperadorEntity operador = operadorRepository.findOne(cdOperador);
			if(operador != null) {
				uploadService.upload(file, cdOperador, cpfCliente);				
			}else {
				throw new Exception("Operador inválido!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }

}
