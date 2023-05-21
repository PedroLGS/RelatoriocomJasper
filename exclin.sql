CREATE DATABASE exclin
GO
USE exclin
GO
CREATE TABLE medico (
idmedico		INT				NOT NULL,
nome			VARCHAR(100)	NOT NULL
PRIMARY KEY(idmedico)
)
GO
CREATE TABLE paciente (
idpaciente		INT				NOT NULL,
nome			VARCHAR(100)	NOT NULL,
data_nasc		DATE			NOT NULL
PRIMARY KEY(idpaciente)
)
GO
CREATE TABLE medico_paciente (
medicoidmedico			INT				NOT NULL,
pacienteidpaciente		INT				NOT NULL,
datahora				DATETIME		NOT NULL,
cid						INT				NOT NULL,
tratamento				VARCHAR(50)		NOT NULL
PRIMARY KEY(medicoidmedico, pacienteidpaciente, datahora)
FOREIGN KEY(medicoidmedico) REFERENCES medico(idmedico),
FOREIGN KEY(pacienteidpaciente) REFERENCES paciente(idpaciente)
)
GO
CREATE TABLE especialidade (
idespecialidade			INT				NOT NULL,
especialidade			VARCHAR(50)		NOT NULL
PRIMARY KEY(idespecialidade)
)
GO
CREATE TABLE medico_especialidade (
medicoidmedico					INT			NOT NULL,
especialidadeidespecialidade	INT			NOT NULL
PRIMARY KEY(medicoidmedico, especialidadeidespecialidade)
FOREIGN KEY(medicoidmedico) REFERENCES medico(idmedico),
FOREIGN KEY(especialidadeidespecialidade) REFERENCES especialidade(idespecialidade)
)

SELECT p.nome AS Nome_Paciente, p.data_nasc AS Data_Nasc_Paciente, m.nome AS Nome_Medico, e.especialidade AS Especialidade_Medico, mp.datahora AS Data_hora_Consulta,
mp.cid AS CID, mp.tratamento AS Tratamento
FROM paciente p
INNER JOIN medico_paciente mp
ON p.idpaciente = mp.pacienteidpaciente
INNER JOIN medico m
ON mp.medicoidmedico = m.idmedico
INNER JOIN medico_especialidade me
ON m.idmedico = me.medicoidmedico
INNER JOIN especialidade e
ON e.idespecialidade = me.especialidadeidespecialidade
WHERE m.idmedico = 2 AND p.idpaciente = 2
GROUP BY p.nome, p.data_nasc, m.nome, e.especialidade, mp.datahora, mp.cid, mp.tratamento

CREATE PROCEDURE randespecialidade
AS
BEGIN
	DECLARE @especialidade INT,
	        @cont INT,
			@random INT,
			@gera INT
	SET @gera = (SELECT COUNT(e.idespecialidade) FROM especialidade e) - 1
	SET @cont = 1
		WHILE(@cont <= (SELECT COUNT(m.idmedico) FROM medico m))
	BEGIN
		SET @random = (RAND() * @gera + 1)
		INSERT INTO medico_especialidade VALUES
		(@cont, @random)
		SET @cont = @cont + 1
	END
END

EXEC randespecialidade

CREATE PROCEDURE insP @medico INT, @paciente INT, @cid INT
AS
BEGIN
DECLARE @idMedico INT,
        @idPaciente INT,
		@tratamento VARCHAR(100),
		@cid1 INT
		SET @idMedico = @medico
		SET @idPaciente = @paciente
		SET @cid1 = @cid
		SET @idMedico = (SELECT m.idmedico 
						  FROM medico m 
						  WHERE m.idmedico = @idMedico)
		SET @idPaciente = (SELECT p.idpaciente 
							FROM paciente p 
							WHERE p.idpaciente = @idPaciente)
		SET @tratamento = (SELECT e.especialidade 
							FROM medico m 
							INNER JOIN medico_especialidade me 
							ON me.medicoidmedico = m.idmedico 
							INNER JOIN especialidade e
							ON e.idespecialidade = me.especialidadeidespecialidade 
							WHERE m.idmedico = @idMedico)
		INSERT INTO medico_paciente VALUES (@idMedico, @idPaciente, CURRENT_TIMESTAMP, @cid1, @tratamento)
	END

EXEC insP 3, 4, 3

INSERT INTO medico VALUES
(1, 'José'), 
(2, 'Paulo'),
(3, 'Francisco'),
(4, 'Anderson'),
(5, 'Fernando')


INSERT INTO paciente VALUES
(1, 'Fernando', '11/02/2001'),
(2, 'Bruno', '07/06/1990'),
(3, 'Joseph', '27/01/1978'),
(4, 'Gabriel', '15/11/2003'),
(5, 'Lucas', '19/09/1997')

INSERT INTO especialidade VALUES
(1, 'Pediatra'),
(2, 'Cardiologista'),
(3, 'Dermatologista'),
(4, 'Psiquiatra')

INSERT INTO medico_paciente VALUES
(1, 1, '19/05/2023', 1, 'Exame Cardiovascular'),
(2, 2, '19/05/2023 10:40:20', 2, 'Exame Coração')

INSERT INTO medico_especialidade VALUES
(1, 1),
(2, 2)


SELECT * FROM medico
SELECT * FROM paciente
SELECT * FROM especialidade
SELECT * FROM medico_paciente
SELECT * FROM medico_especialidade
