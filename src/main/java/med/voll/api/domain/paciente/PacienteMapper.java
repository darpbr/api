package med.voll.api.domain.paciente;

public record PacienteMapper(
        Long id,
        String nome,
        String ultimo_nome,
        String cpf,
        String telefone
) {

    public PacienteMapper(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getUltimo_nome(), paciente.getCpf(), paciente.getTelefone());
    }
}
