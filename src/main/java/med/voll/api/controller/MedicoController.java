package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
//        repository Persiste desta forma save(medico) ou save( new Medico(id,atributo1, atributo2,...)
//        no nosso caso a chave id será gerada automaticamente no banco de dados, por isto null
        repository.save(new Medico(dados));
    }

//    Para usar paginação basta adicionar o parâmetro Pageable *Atenção para a classe importada, tem que
//    ser a org.springframework.data.domain.Pageable* e o método findAll recebe esta paginação
//    Coma a anotação @PageableDefault() podemos personalizar o retorno default da paginação como
//    primeira pagina, quantos elementos por pagina, ordenação. Caso não seja passado algum parâmetro
//    na requisição o default vai ser assumido conforme personalizamos. Para ordenação podemos passar
//    o campo a ser ordenado e a forma: crescente ou decrescente (asc ou desc)
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
//        return repository.findAll(paginacao).map(DadosListagemMedico::new);
        // Listar apenas os médicos com cadastro avito
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);

    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id){
//    Exclusão fisica, apaga do banco de dados
//        repository.deleteById(id);
//    Exclusão lógica, não apaga efetivamente do banco, apenas "desativa" sem apagar do banaco
        Medico medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
