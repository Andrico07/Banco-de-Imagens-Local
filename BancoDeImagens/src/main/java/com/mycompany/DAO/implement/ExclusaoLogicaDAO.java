package com.mycompany.DAO.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mycompany.factory.ConnectionSQLite;
import com.mycompany.model.ExclusaoLogica;

public class ExclusaoLogicaDAO {

	public static void createTableExclusao() throws Exception {
		try {
			String SQL = "CREATE TABLE IF NOT EXISTS Exclusao(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "id_Usuario INTEGER NOT NULL REFERENCES Usuario (id)," + "path VARCHAR NOT NULL UNIQUE," + "dt_Exclusao DATE NOT NULL" + ")";

			Connection conn = ConnectionSQLite.connect();
			Statement stmt = conn.createStatement();

			stmt.execute( SQL );

			stmt.close();
			conn.close();
		} catch ( SQLException e ) {
			throw new Exception( "Erro ao criar tabela usuario: " + e.getMessage() );
		}
	}

	public void insert( ExclusaoLogica excluir ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String SQL = " INSERT INTO Exclusao(id_Usuario, path, dt_Exclusao)" + "VALUES(?, ?,?)";
			conn = ConnectionSQLite.connect();
			ps = conn.prepareStatement( SQL );

			ps.setInt( 1, excluir.getIdUsuario() );
			ps.setString( 2, excluir.getPath() );
			ps.setDate( 3, Date.valueOf( excluir.getDataExclusao() ) );

			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao inserir" );
		} finally {
			ps.close();
			conn.close();

		}
	}

	public boolean find( String path ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String SQL = "SELECT * FROM Exclusao WHERE path = ?";

			conn = ConnectionSQLite.connect();
			ps = conn.prepareStatement( SQL );
			ps.setString( 1, path );
			ResultSet rs = ps.executeQuery();

			if( rs.next() ) {
				return true;
			}
			return false;
		} catch ( Exception e ) {
			throw new Exception( "Erro ao buscar" );
		} finally {
			ps.close();
			conn.close();

		}
	}

	public void delete( ExclusaoLogica excluir ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String SQL = "DELETE FROM Exclusao WHERE id= ?;";

			conn = ConnectionSQLite.connect();
			ps = conn.prepareStatement( SQL );

			ps.setLong( 1, excluir.getId() );
			ps.executeUpdate();

			ps.close();
			conn.close();
		} catch ( Exception e ) {
			throw new Exception( "Erro ao excluir" );
		} finally {
			ps.close();
			conn.close();

		}
	}
}
