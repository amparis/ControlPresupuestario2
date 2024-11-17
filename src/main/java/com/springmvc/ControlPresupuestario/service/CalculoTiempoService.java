package com.springmvc.ControlPresupuestario.service;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CalculoTiempoService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

	public Integer calculateDaysBetweenDates(Date fechaInicio, Date fechaFin) {
	        String sql = "{? = call fn_obtener_dias_por_rango_fechas(?, ?)}";
	
	        return jdbcTemplate.execute(sql, (CallableStatementCallback<Integer>) cs -> {
	            cs.registerOutParameter(1, java.sql.Types.INTEGER);
	            cs.setDate(2, fechaInicio);
	            cs.setDate(3, fechaFin);
	            cs.execute();
	
	return cs.getInt(1);
	        });
	    }
}
