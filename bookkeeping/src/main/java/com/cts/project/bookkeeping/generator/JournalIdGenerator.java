package com.cts.project.bookkeeping.generator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class JournalIdGenerator implements IdentifierGenerator{

	@Override
	 public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
	  String prefix = "J";
	  String suffix = "";
	  try (Connection connection = session.getJdbcConnectionAccess().obtainConnection();
			  Statement statement = connection.createStatement())
	  {
	   ResultSet resultSet = statement.executeQuery("SELECT journal_id FROM journal ORDER BY journal_id DESC LIMIT 1");
	   if(resultSet.next()) {
		   String s = resultSet.getString(1);
		   int i = Integer.parseInt(s.substring(1));
		   suffix = String. format("%04d" ,i+1 );
	   }else {
		   suffix=String. format("%04d" ,1);
	   }
	  } catch (SQLException e) {
	   e.printStackTrace();
	  }
	  return prefix + suffix;
	 }

	
}
