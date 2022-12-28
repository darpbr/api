package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoPaciente(Long id, String nome, String ultimo_nome, String cpf, String telefone,
                                        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getUltimo_nome(), paciente.getCpf(),
                paciente.getTelefone(), paciente.getEndereco());
    }
}
