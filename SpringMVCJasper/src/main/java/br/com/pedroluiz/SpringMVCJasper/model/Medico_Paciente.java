package br.com.pedroluiz.SpringMVCJasper.model;

import java.time.LocalDate;

public class Medico_Paciente {
	
	private int idMedico;
	private int idPaciente;
	private String nomePaciente;
	private LocalDate datanascPaciente;
	private String nomeMedico;
	private LocalDate dataHora;
	private int cid;
	private String Tratamento;
	private int idEspecialidade;
	private String especialidadeMedico;
	public int getIdMedico() {
		return idMedico;
	}
	public void setIdMedico(int idMedico) {
		this.idMedico = idMedico;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	public LocalDate getDatanascPaciente() {
		return datanascPaciente;
	}
	public void setDatanascPaciente(LocalDate datanascPaciente) {
		this.datanascPaciente = datanascPaciente;
	}
	public String getNomeMedico() {
		return nomeMedico;
	}
	public void setNomeMedico(String nomeMedico) {
		this.nomeMedico = nomeMedico;
	}
	public LocalDate getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDate dataHora) {
		this.dataHora = dataHora;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getTratamento() {
		return Tratamento;
	}
	public void setTratamento(String tratamento) {
		Tratamento = tratamento;
	}
	public int getIdEspecialidade() {
		return idEspecialidade;
	}
	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}
	public String getEspecialidadeMedico() {
		return especialidadeMedico;
	}
	public void setEspecialidadeMedico(String especialidadeMedico) {
		this.especialidadeMedico = especialidadeMedico;
	}
	@Override
	public String toString() {
		return "Medico_Paciente [idMedico=" + idMedico + ", idPaciente=" + idPaciente + ", nomePaciente=" + nomePaciente
				+ ", datanascPaciente=" + datanascPaciente + ", nomeMedico=" + nomeMedico + ", dataHora=" + dataHora
				+ ", cid=" + cid + ", Tratamento=" + Tratamento + ", idEspecialidade=" + idEspecialidade
				+ ", especialidadeMedico=" + especialidadeMedico + "]";
	}
}