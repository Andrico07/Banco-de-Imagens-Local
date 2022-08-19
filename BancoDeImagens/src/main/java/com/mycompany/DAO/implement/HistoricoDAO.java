package com.mycompany.DAO.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.factory.ConnectionSQLite;
import com.mycompany.model.Historico;

public class HistoricoDAO {

	public static void createTableHistorico() throws Exception {
		try {
			String SQL = "CREATE TABLE IF NOT EXISTS Historico(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "id_Usuario INTEGER NOT NULL REFERENCES Usuario (id)," + "filtro VARCHAR NOT NULL," + "path VARCHAR NOT NULL," + "dt_Edicao DATE NOT NULL" + ")";

			Connection conn = ConnectionSQLite.connect();
			Statement stmt = conn.createStatement();

			stmt.execute( SQL );

			stmt.close();
			conn.close();

		} catch ( SQLException e ) {
			throw new Exception( "Erro ao criar tabela usuario: " + e.getMessage() );
		}
	}

	public void insert( Historico historico ) throws Exception {
		try {
			String SQL = "INSERT INTO Historico( id_Usuario, filtro, path, dt_Edicao " + "VALUES(?, ?, ?, ?)";

			Connection conn = ConnectionSQLite.connect();
			PreparedStatement ps = conn.prepareStatement( SQL );
			ps.setInt( 1, historico.getIdUsuario() );
			ps.setString( 2, historico.getFiltro() );
			ps.setString( 3, historico.getPath() );
			ps.setDate( 4, Date.valueOf( historico.getData() ) );

			ps.executeUpdate();

		} catch ( Exception e ) {

			throw new Exception( "Erro ao inserir" );
		}
	}

	public List<Historico> getByIdUsuario( int idUser ) throws Exception {
		try {
			String SQL = "SELECT * FROM Historico WHERE id_Usuario= ?;";

			Connection conn = ConnectionSQLite.connect();
			PreparedStatement ps = conn.prepareStatement( SQL );

			ps.setInt( 1, idUser );
			ResultSet rs = ps.executeQuery();

			List<Historico> historico = new ArrayList<>();

			while( rs.next() ) {
				var id = rs.getInt( "id" );
				var idUsuario = rs.getInt( "id_Usuario" );
				var path = rs.getString( "path" );
				var filtro = rs.getString( "filtro" );
				var data = rs.getDate( "dt_Edicao" ).toLocalDate();

				historico.add( new Historico( id, filtro, idUsuario, path, data ) );
			}
			ps.close();
			conn.close();
			rs.close();
			return historico;

		} catch ( Exception e ) {

			throw new Exception( "Erro ao buscar" );
		}
	}

	public List<Historico> getByPath( String pathImagem ) throws Exception {
		try {
			String SQL = "SELECT * FROM Historico WHERE path= ?;";

			Connection conn = ConnectionSQLite.connect();
			PreparedStatement ps = conn.prepareStatement( SQL );

			ps.setString( 1, pathImagem );
			ResultSet rs = ps.executeQuery();

			List<Historico> historico = new ArrayList<>();

			while( rs.next() ) {
				var id = rs.getInt( "id" );
				var idUsuario = rs.getInt( "id_Usuario" );
				var path = rs.getString( "path" );
				var filtro = rs.getString( "filtro" );
				var data = rs.getDate( "dt_Edicao" ).toLocalDate();

				historico.add( new Historico( id, filtro, idUsuario, path, data ) );
			}
			ps.close();
			conn.close();
			rs.close();
			return historico;

		} catch ( Exception e ) {

			throw new Exception( "Erro ao buscar" );
		}
	}
}
