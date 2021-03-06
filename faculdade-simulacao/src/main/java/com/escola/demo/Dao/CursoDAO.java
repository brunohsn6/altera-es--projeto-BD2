package com.escola.demo.Dao;

import com.escola.demo.Entity.Curso;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CursoDAO extends AbstractDAO<Curso, Integer>{


}
