package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Anotação para indicar para o Spring que é uma classe que vai tratar os erros da api. Realiza o tratamento
// das exceptions geradas nas classes controller.
@RestControllerAdvice
public class TratadorDeErros {
//    Método que vai tratar os erros 404, poderia ser qualquer nome de método. Entre parênteses colocamos
//    a exception que será tratada pelo método. Qualquer controller que ocorrer esta exception será chamado
//    este método retornando o que ele retornar.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }


    // Criação do DTO nesta classe mesmo porque somente será utilizado por ela para tratar os erros
    private record DadosErroValidacao(String campo, String mensagem){
        // Devemos lembrar de criar o construtor do record para não ocorrer erro no map
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
