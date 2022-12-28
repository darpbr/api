package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoMedico(Long id, String nome, String email, String crm, String telefone,
        Especialidade especialidade, Endereco endereco) {

//    Como estamos passando um Objeto do tipo Medico precisamos criar um construtor que recebe o Objeto passado
//    O construtor chama o próprio construtor para instanciar os parâmetros
    public DadosDetalhamentoMedico(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(),
                medico.getEspecialidade(), medico.getEndereco());
    }
}
