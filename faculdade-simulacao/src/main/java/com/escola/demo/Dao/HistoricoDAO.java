package com.escola.demo.Dao;


import com.escola.demo.Entity.Disciplina;
import com.escola.demo.Entity.Historico;
import com.escola.demo.Entity.Matricula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;

@Repository
@Transactional
public class HistoricoDAO extends AbstractDAO<Historico, Integer>{

    @Autowired
    private EntityManagerFactory emf;

    public Double buscarCoenficiente(Matricula matricula){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Double coeficiente = Double.valueOf(0);
        try{
            transaction.begin();
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("checkAlunoCoeficiente");
            storedProcedure.registerStoredProcedureParameter("matricula", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("@resultado", Double.class, ParameterMode.OUT);
            storedProcedure.setParameter("matricula", matricula.getMatricula());
            storedProcedure.execute();
            coeficiente = (Double) storedProcedure.getOutputParameterValue("@resultado");
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
        return coeficiente;
    }

    public Double mediaAlunosAbaixoDaMedia(Disciplina disciplina){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        Double media = Double.valueOf(0);
        try{
            transaction.begin();
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("buscarMedia");
            storedProcedure.registerStoredProcedureParameter("codigo", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("@resultado", Double.class, ParameterMode.OUT);
            storedProcedure.setParameter("codigo", disciplina.getCodigo());
            storedProcedure.execute();
            media = (Double) storedProcedure.getOutputParameterValue("@resultado");
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
        return media;
    }

    public void inserirNotaFinal(Double nota1, Double nota2, Matricula matricula, Disciplina disc){
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("finalizarSemestre");
            storedProcedure.registerStoredProcedureParameter("GQ1", double.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("GQ2", double.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("mat", String.class, ParameterMode.IN);
            storedProcedure.registerStoredProcedureParameter("codigo_disciplina", String.class, ParameterMode.IN);
            storedProcedure.setParameter("GQ1", nota1);
            storedProcedure.setParameter("GQ2", nota2);
            storedProcedure.setParameter("mat", matricula.getMatricula());
            storedProcedure.setParameter("codigo_disciplina", disc.getCodigo());
            storedProcedure.execute();
            transaction.commit();
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
    }

}
