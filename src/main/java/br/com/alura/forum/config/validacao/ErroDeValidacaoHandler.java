package br.com.alura.forum.config.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;import net.bytebuddy.asm.Advice.Local;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	//ajuda a coletar msg de erro de acordo com os idiomas
	@Autowired
	private MessageSource messageSource;

	//Mostrar pro Spring quando e lancada a excecao e devolve o codigo 400 Bad Request
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception) {
		//Setando uma lista de ErroDeFormularioDto
		List<ErroDeFormularioDto> dto = new ArrayList<>();
		
		//coletando o retorno dos resultados de erro
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		//Guarda cada FildErro dentro da lista dto
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erro = new ErroDeFormularioDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
}
