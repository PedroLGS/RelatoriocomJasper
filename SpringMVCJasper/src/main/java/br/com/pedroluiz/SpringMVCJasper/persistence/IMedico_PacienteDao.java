package br.com.pedroluiz.SpringMVCJasper.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.pedroluiz.SpringMVCJasper.model.Medico_Paciente;

public interface IMedico_PacienteDao {
	
	public void insereMedicoPaciente(Medico_Paciente mp) throws SQLException, ClassNotFoundException;
	public List<Medico_Paciente> consultaMedPac() throws SQLException, ClassNotFoundException;

}
