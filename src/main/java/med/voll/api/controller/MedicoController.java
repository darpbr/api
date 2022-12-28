package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
//        repository Persiste desta forma save(medico) ou save( new Medico(id,atributo1, atributo2,...)
//        no nosso caso a chave id será gerada automaticamente no banco de dados, por isto null
        Medico medico = new Medico(dados);
        repository.save(medico);

//        Utilizamos um URIComponentsBuilder para cirar uma uri dinâmica. Isto vai facilitar a criação da uri
//        quando precisarmos fazer um deploy e subir a aplicação. Localmente ela utiliza o caminho http://localhost
//        Vai montar a uri com o path /medicos/{id} e passar como parâmetro o id gerado anteriormente
        URI uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

//        Para retorno de um método de cadastro existe o código HTTP 201, mas exige que seja passado também
//        uma uri (Location), um body contendo os dados do Objeto cadastrado.
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

//    Para usar paginação basta adicionar o parâmetro Pageable *Atenção para a classe importada, tem que
//    ser a org.springframework.data.domain.Pageable* e o método findAll recebe esta paginação
//    Coma a anotação @PageableDefault() podemos personalizar o retorno default da paginação como
//    primeira pagina, quantos elementos por pagina, ordenação. Caso não seja passado algum parâmetro
//    na requisição o default vai ser assumido conforme personalizamos. Para ordenação podemos passar
//    o campo a ser ordenado e a forma: crescente ou decrescente (asc ou desc)
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
//         Listar apenas os médicos com cadastro avito
        return ResponseEntity.ok(repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new));

    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

//        Não é recomendável passar um objeto JPA como retorno do nosso controller, por isto criamos um novo
//        DTO (Record)
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
//    Exclusão fisica, apaga do banco de dados
//        repository.deleteById(id);
//    Exclusão lógica, não apaga efetivamente do banco, apenas "desativa" sem apagar do banaco
        Medico medico = repository.getReferenceById(id);
        medico.excluir();

//        Retornar resposta 204 que é mais adequada ao efetuar operação de exclusão
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
//        Busca as informações do médico na base de dados
        Medico medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}
