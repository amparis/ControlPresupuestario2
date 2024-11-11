package com.springmvc.ControlPresupuestario.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springmvc.ControlPresupuestario.model.Income;
import com.springmvc.ControlPresupuestario.model.LoanReturnDTO;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer> {
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId ORDER BY i.fecha DESC")
	  public List<Income> findAllIncomesByProjectId(Long projectId);
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId and estado='V' ORDER BY i.fecha DESC")
	  public List<Income> findAllIncomesVigentesByProjectId(Long projectId);
	  
	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND (i.categoria = 'DESEMBOLSO' OR i.categoria = 'PRESTAMO') AND i.estado = 'V' ORDER BY i.fecha ASC")
	  public List<Income> findFirstAccountOriginByProjectId(Long projectId);
	  
	  @Query("SELECT i.account.id, i.account.nameBank, i.account.accountNumber FROM Income i WHERE i.proyecto.id = :projectId AND (i.categoria = 'DESEMBOLSO') AND i.estado = 'V' ORDER BY i.account.id")
	  public List<Object[]> findAccountsByProjectId(Long projectId);

	  @Query("SELECT i FROM Income i WHERE i.proyecto.id = :projectId AND i.account.id = :accountId AND i.estado = 'V'")
	  public  List<Income> findAllSaldosByProjectIdAndAccount(Long projectId, Long accountId);
	  
	  @Query("SELECT i.proyecto.id,i.proyecto.nombre, SUM(i.montoNoRecurrente) FROM Income i WHERE i.estado = 'V' GROUP BY i.proyecto.id, i.proyecto.nombre HAVING SUM(i.montoNoRecurrente) > 0")
	  public  List<Object[]> findAllSaldosByProjectId();
	  /*
	  @Query("SELECT i.proyecto.id, " +
		       "COALESCE(SUM(CASE WHEN i.categoria = 'PRESTAMO' THEN i.monto ELSE 0 END), 0) AS totalPrestamos, " +
		       "COALESCE(SUM(CASE WHEN i.categoria = 'DEVOLUCION' THEN i.monto ELSE 0 END), 0) AS totalDevoluciones, " +
		       "COALESCE(SUM(CASE WHEN i.categoria = 'PRESTAMO' THEN i.monto ELSE 0 END), 0) - " +
		       "COALESCE(SUM(CASE WHEN i.categoria = 'DEVOLUCION' THEN i.monto ELSE 0 END), 0) AS saldo " +
		       "FROM Income i " +
		       "WHERE i.proyecto.id = :projectId AND i.estado = 'V' " +
		       "GROUP BY i.proyecto.id " +
		       "HAVING SUM(CASE WHEN i.categoria = 'PRESTAMO' THEN i.monto ELSE 0 END) > 0")
		public List<Object[]> findLoanSummaryByProject(Long projectId);*/
	  /*@Query("SELECT ip.proyIdPrestamo AS prestamoId, " +
		       "SUM(CASE WHEN ip.categoria = 'PRESTAMO' THEN ip.monto ELSE 0 END) AS prestado, " +
		       "ip.proyecto.id AS proyectoId,ip.proyecto.nombre AS nombre,ip.account.id AS cuentaId, ip.account.nameBank AS nameBank," +
		       "(SELECT COALESCE(SUM(ide.monto), 0) " +
		       " FROM Income ide " +
		       " WHERE ide.categoria = 'DEVOLUCION' " +
		       "   AND ide.estado = 'V' " +
		       "   AND ide.proyecto.id = ip.proyIdPrestamo) AS devuelto, " +
		       "SUM(CASE WHEN ip.categoria = 'PRESTAMO' THEN ip.monto ELSE 0 END) - " +
		       "(SELECT COALESCE(SUM(ide.monto), 0) " +
		       " FROM Income ide " +
		       " WHERE ide.categoria = 'DEVOLUCION' " +
		       "   AND ide.estado = 'V' " +
		       "   AND ide.proyecto.id = ip.proyIdPrestamo) AS saldo " +
		       "FROM Income ip " +
		       "WHERE ip.categoria = 'PRESTAMO' " +
		       "  AND ip.estado = 'V' " +
		       "  AND ip.proyIdPrestamo = :prestamoId " +
		       "GROUP BY ip.proyIdPrestamo, ip.proyecto.id,ip.proyecto.nombre,ip.account.id,ip.account.nameBank  ")
		public List<Object[]> findLoanReturnSummaryByLoanId( Long prestamoId);*/
	/*  @Query("SELECT ip.proyIdPrestamo AS prestamoId, " +
	            "SUM(ip.monto) AS prestado, " +
	            "ip.proyecto.id AS proyectoId,ip.proyecto.nombre AS nombre,ip.account.id AS cuentaId, ip.account.nameBank AS nameBank," +
	            "COALESCE(SUM(devuelto.monto), 0) AS devuelto, " +
	            "SUM(ip.monto) - COALESCE(SUM(devuelto.monto), 0) AS saldo " +
	            "FROM Income ip " +
	            "LEFT JOIN ( " +
	                "SELECT ide.proyIdPrestamo AS pId, " +
	                "SUM(ide.monto) AS monto " +
	                "FROM Income ide " +
	                "WHERE ide.categoria = 'DEVOLUCION' " +
	                "AND ide.estado = 'V' " +
	                "AND ide.proyecto.id = :prestamoId " +
	                "GROUP BY ide.proyIdPrestamo " +
	            ") AS devuelto ON ip.proyecto.id = devuelto.pId " +
	            "WHERE ip.categoria = 'PRESTAMO' " +
	            "AND ip.estado = 'V' " +
	            "AND ip.proyIdPrestamo = :prestamoId " +
	            "GROUP BY ip.proyIdPrestamo, ip.proyecto.id, ip.proyecto.nombre, ip.account.id, ip.account.nameBank")
	  public List<Object[]> findLoanReturnSummaryByLoanId(long prestamoId);
*/
	// Consulta para obtener los montos prestados
	/*  @Query("SELECT ip.proyIdPrestamo AS prestamoId, " +
	              "SUM(ip.monto) AS prestado, " +
	              "ip.proyecto.id AS proyectoId, " +
	              "ip.proyecto.nombre AS nombre, " +
	              "ip.account.id AS cuentaId, " +
	              "ip.account.nameBank AS nameBank " +
	          "FROM Income ip " +
	          "WHERE ip.categoria = 'PRESTAMO' " +
	          "AND ip.estado = 'V' " +
	          "AND ip.proyIdPrestamo = :prestamoId " +
	          "GROUP BY ip.proyIdPrestamo, ip.proyecto.id, ip.proyecto.nombre, ip.account.id, ip.account.nameBank")
	  public List<Object[]> findLoanAmountByLoanId(Long prestamoId);

	  // Consulta para obtener los montos devueltos
	  @Query("SELECT ide.proyIdPrestamo AS prestamoId, SUM(ide.monto) AS devuelto " +
	          "FROM Income ide " +
	          "WHERE ide.categoria = 'DEVOLUCION' " +
	          "AND ide.estado = 'V' " +
	          "AND ide.proyecto.id = :prestamoId " +
	          "GROUP BY ide.proyIdPrestamo")
	  public  List<Object[]>   findReturnedAmountByLoanId(Long prestamoId);*/
	    @Query(value = "SELECT * FROM fn_obtener_saldo_prestamos(:id_prestamo)", nativeQuery = true)
	    public List<Object[]> findLoanAmountByLoanId(@Param("id_prestamo") Integer id_prestamo);
	    
	    //@Query(value = "SELECT * FROM fn_obtener_saldo_devueltos(:id_prestamo)", nativeQuery = true)
	    @Query(value = "SELECT * FROM fn_obtener_saldo_prestamos(:id_prestamo)", nativeQuery = true)
	    public List<Object[]> findLoanAmountByProyectoCreditorId(@Param("id_prestamo") Integer id_prestamo);
	  // @Query("SELECT i FROM Income i WHERE i.proyIdPrestamo = :projectId AND i.categoria = 'PRESTAMO' AND i.estado = 'V' ORDER BY i.fecha ASC")
	  //public List<Income> findLoansReceivedByProjectId(Long projectId);	  // loans received
	  
	  ///////
	  @Query("SELECT i.account.id, i.account.nameBank,i.account.accountNumber,  SUM(i.monto) ,  SUM(i.montoRecurrente) ,  SUM(i.montoNoRecurrente)   FROM Income i WHERE i.proyecto.id = :projectId AND i.estado = 'V' GROUP BY   i.account.id, i.account.nameBank,i.account.accountNumber  HAVING SUM(i.monto) > 0")
	  public  List<Object[]> findAllSaldosAndAccountByProjectId(Long projectId);
}


