package org.pxcbpmisframework.core.util;

public class JdbcTest {
	private java.sql.Connection con = null;
	private final String url = "jdbc:sqlserver://";
	private final String serverName = "localhost";
	private final String portNumber = "1433";
	private final String databaseName = "bpmis";
	private final String userName = "sa";
	private final String password = "123456";
	// Informs the driver to use server a side-cursor,
	// which permits more than one active statement
	// on a connection.
	private final String selectMethod = "cursor";

	// Constructor
	public JdbcTest() {
	}

	private String getConnectionUrl() {
		return url + serverName + ":" + portNumber + ";databaseName="
				+ databaseName + ";selectMethod=" + selectMethod + ";";
	}

	private java.sql.Connection getConnection() {
		try {
			System.out.println(getConnectionUrl());
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			con = java.sql.DriverManager.getConnection(getConnectionUrl(),
					userName, password);
			if (con != null)
				System.out.println("Connection Successful!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Trace in getConnection() : "
					+ e.getMessage());
		}
		return con;
	}

	/*
	 * Display the driver properties, database details
	 */

	public void displayDbProperties() {
		java.sql.DatabaseMetaData dm = null;
		java.sql.ResultSet rs = null;
		try {
			con = this.getConnection();
			if (con != null) {
				dm = con.getMetaData();
				System.out.println("Driver Information");
				System.out.println("\tDriver Name: " + dm.getDriverName());
				System.out
						.println("\tDriver Version: " + dm.getDriverVersion());
				System.out.println("\nDatabase Information ");
				System.out.println("\tDatabase Name: "
						+ dm.getDatabaseProductName());
				System.out.println("\tDatabase Version: "
						+ dm.getDatabaseProductVersion());
				System.out.println("Avalilable Catalogs ");
				rs = dm.getCatalogs();
				while (rs.next()) {
					System.out.println("\tcatalog: " + rs.getString(1));
				}
				rs.close();
				rs = null;
				closeConnection();
			} else
				System.out.println("Error: No active Connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
		dm = null;
	}

	private void closeConnection() {
		try {
			if (con != null)
				con.close();
			con = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		JdbcTest myDbTest = new JdbcTest();
		myDbTest.displayDbProperties();
	}
}