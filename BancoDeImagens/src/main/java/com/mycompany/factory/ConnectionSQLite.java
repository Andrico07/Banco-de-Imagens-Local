package com.mycompany.factory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLite {

	private static final String URL = "jdbc:sqlite:src/main/java/com/mycompany/dao/db/gerenciadorImagens.db";

	public static Connection connect() throws SQLException {

		return DriverManager.getConnection( URL );

	}

	public static void checkDiretorioDb() {
		File diretorio = new File( "db/" );

		if( !diretorio.exists() ) {
			diretorio.mkdirs();
		}
	}
}
