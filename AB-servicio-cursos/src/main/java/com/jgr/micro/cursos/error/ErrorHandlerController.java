package com.jgr.micro.cursos.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


//no lo usamos porque no tenemos thymeleaf
//@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler(IdNoEncontradoException.class)
	public String usuarioNoEncontrado(IdNoEncontradoException ex,Model model) {
		model.addAttribute("error", "Error: usuario no encontrado!");
		model.addAttribute("message", ex.getMessage());
		model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("timestamp", new Date());
		return "error/usuario";
	}

}
