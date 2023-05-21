package br.com.pedroluiz.SpringMVCJasper.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pedroluiz.SpringMVCJasper.model.Medico_Paciente;

@Repository
public class Medico_PacienteDao implements IMedico_PacienteDao {
	
	@Autowired
	GenericDao gDao;

	@Override
	public void insereMedicoPaciente(Medico_Paciente mp) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "CALL insP ?,?,?";
		CallableStatement cs = c.prepareCall(sql);
		cs.setInt(1, mp.getIdMedico());
		cs.setInt(2, mp.getIdPaciente());
		cs.setInt(3, mp.getCid());
		cs.execute();
		cs.close();
		c.close();
	}
	@Override
	public List<Medico_Paciente> consultaMedPac() throws SQLException, ClassNotFoundException {
		List<Medico_Paciente> mps = new ArrayList<>();
		Connection c = gDao.getConnection();
		String sql = "SELECT p.nome AS Nome_Paciente, p.data_nasc AS Data_Nasc_Paciente, m.nome AS Nome_Medico, e.especialidade AS Especialidade_Medico, mp.datahora AS Data_hora_Consulta,\r\n"
				+ "mp.cid AS CID, mp.tratamento AS Tratamento\r\n"
				+ "FROM paciente p\r\n"
				+ "INNER JOIN medico_paciente mp\r\n"
				+ "ON p.idpaciente = mp.pacienteidpaciente\r\n"
				+ "INNER JOIN medico m\r\n"
				+ "ON mp.medicoidmedico = m.idmedico\r\n"
				+ "INNER JOIN medico_especialidade me\r\n"
				+ "ON m.idmedico = me.medicoidmedico\r\n"
				+ "INNER JOIN especialidade e\r\n"
				+ "ON e.idespecialidade = me.especialidadeidespecialidade\r\n"
				+ "WHERE m.idmedico = ? AND p.idpaciente = ?\r\n"
				+ "GROUP BY p.nome, p.data_nasc, m.nome, e.especialidade, mp.datahora, mp.cid, mp.tratamento";
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			Medico_Paciente mp  = new Medico_Paciente();
			mp.setNomePaciente(rs.getString("p.nome"));
			mp.setDatanascPaciente(LocalDate.parse(rs.getString("p.data_nasc")));
			mp.setNomeMedico(rs.getString("m.nome"));
			mp.setEspecialidadeMedico(rs.getString("e.especialidade"));
			mp.setDataHora(LocalDate.parse(rs.getString("mp.datahora")));
			mp.setCid(rs.getInt("mp.cid"));
			mp.setTratamento(rs.getString("mp.tratamento"));
			mps.add(mp);
		}
		rs.close();
		ps.close();
		c.close();
		return mps;
	}
}
