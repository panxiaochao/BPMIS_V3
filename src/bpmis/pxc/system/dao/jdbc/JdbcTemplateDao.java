package bpmis.pxc.system.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public interface JdbcTemplateDao {

	public JdbcTemplate getJdbcTemplate();
	//--------method
	public void execute(String sql);
	public int queryForInt(String sql);	
	public int queryForInt(String sql, Object[] args);
	public String queryForString(String sql);	
	public String queryForString(String sql, Object[] args);
	public <T> List<T> queryForList(String sql);
	public <T> List<T> queryForList(String sql, Class<T> entityClass);
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> entityClass);




}
