package com.escola.demo;

import com.escola.demo.Dao.CursoDAO;
import com.escola.demo.Dao.HistoricoDAO;
import com.escola.demo.Dao.MatriculaDAO;
import com.escola.demo.Entity.*;
import com.escola.demo.Repository.CursoRepository;
import com.escola.demo.Service.*;
import com.escola.demo.Util.Situacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

    @Autowired
    AlunoService alunoService;

    @Autowired
    CursoService cursoService;

    @Autowired
    DepartamentoService departamentoService;

    @Autowired
    HistoricoService historicoService;

    @Autowired
    DisciplinaService disciplinaService;
    @Autowired
    MatriculaService matriculaService;
    @Autowired
    MatriculaDAO matriculaDAO;
    @Autowired
    HistoricoDAO historicoDAO;

    @Autowired
    ProfessorService professorService;

    @Autowired
    TurmaService turmaService;
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }




    @Override
    public void run(String... args) throws Exception {
        Departamento d = new Departamento();
        d.setNome("departamento 1");
        d.setCodigo("dpto 01");
        departamentoService.salvar(d);

        Curso c = new Curso();
        c.setCodigo("inf0001");
        c.setCargaHoraria(40);
        c.setCreditos(20);
        c.setDepartamento(d);
        c.setNome("Curso 01");
        cursoService.salvar(c);

        Aluno a = new Aluno();
        a.setNome("bruno");
        Matricula m = new Matricula();
        m.setMatricula("2016107000");
        m.setCurso(c);
        m.setSituacao(Situacao.Ativo);
        m.setAluno(a);

        //a.addMatricula(m);
        alunoService.salvar(a);
        matriculaService.salvar(m);

        Disciplina disc1 = new Disciplina();
        disc1.setCodigo("disc001");
        disc1.setNome("banco de dados");
        disciplinaService.salvar(disc1);

        Disciplina disc2 = new Disciplina();
        disc2.setCodigo("td2");
        disc2.setNome("programacao");
        disciplinaService.salvar(disc2);

        Historico h = new Historico();
        h.setDisciplina(disc1);
        h.setMatricula(m);
        historicoService.salvar(h);

        Historico h1 = new Historico();
        h.setDisciplina(disc2);
        h.setMatricula(m);
        historicoService.salvar(h1);
        System.out.println("*******************************");
        historicoDAO.inserirNotaFinal(10.0, 7.0, m, disc1);
        historicoDAO.inserirNotaFinal(5.7, 5.0, m, disc2);
        System.out.println("Nota = " + matriculaService.buscarCoeficiente(m));

        Professor p = new Professor();
        p.setDepartamento(d);
        p.setMatricula("2016107509"); //matricula válida 
        p.setNome("professor1");
        professorService.salvar(p);


        //alunoService.deletar(a);
//        Matricula m = new Matricula();
//        m.setMatricula("2016107634");
//        Aluno aluno = new Aluno();
//       aluno.setMatricula(m);
//        aluno.setNome("Felipe");
//        alunoService.salvar(aluno);
//        List<Aluno> alunos= alunoService.listarTodos();
//        alunos.forEach(a-> System.out.println(a.getMatricula() + "-" + a.getNome()));
//        aluno.setNome("lucas");
//        alunoService.atualizar(aluno);
//        System.out.println(aluno.getNome());
//        Aluno a3 = new Aluno();
//        a3.setNome("lucas");
//        a3.setMatricula(m);
//        Aluno a2 = alunoService.buscar(aluno);
//        Aluno a4 = alunoService.buscar(a3);

    }
}
