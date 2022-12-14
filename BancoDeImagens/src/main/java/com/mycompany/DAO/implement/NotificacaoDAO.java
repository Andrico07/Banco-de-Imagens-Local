package com.mycompany.DAO.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.factory.ConnectionSQLite;
import com.mycompany.model.Notificacao;
import com.mycompany.model.Usuario;

public class NotificacaoDAO {

	public static void createTableNotificacao() throws SQLException {
		var query = "CREATE TABLE IF NOT EXISTS Notificacao(" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "id_usuario INTEGER NOT NULL REFERENCES Usuario (id)," + " " + "mensagem VARCHAR NOT NULL, " + ")";
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			stmt.execute( query );

		} catch ( SQLException e ) {
			throw new RuntimeException( "Erro ao criar tabela notificações: " + e.getMessage() );
		} finally {
			stmt.close();
			conn.close();

		}
	}

	public void insert( Notificacao notificacao ) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "INSERT INTO Notificacao(mensagem, id_usuario) VALUES (?, ?) ";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setString( 1, notificacao.getMensagem() );
			ps.setInt( 2, notificacao.getUsuario().getId() );
			ps.executeUpdate();

			stmt.close();
			conn.close();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao inserir" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}

	public void delete( Notificacao notificacao ) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "DELETE FROM Notificacao WHERE id = ? AND id_usuario = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, notificacao.getId() );
			ps.setInt( 1, notificacao.getUsuario().getId() );
			ps.executeUpdate();

			stmt.close();
			conn.close();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao excluir" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}

	public void deleteAllByIdUsuario( int idUsuario ) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "DELETE FROM Notificacao WHERE id_usuario = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, idUsuario );
			ps.executeUpdate();

			stmt.close();
			conn.close();
		} catch ( Exception e ) {
			throw new Exception( "Erro ao excluir" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}

	public List<Notificacao> getAllByIdUsuario( int idUsuario ) throws Exception {
		List<Notificacao> notificacoes = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "SELECT n.id, n.mensagem, n.id_usuario FROM Notificacao n " + " WHERE n.id_usuario = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, idUsuario );
			ResultSet rs = ps.executeQuery();

			while( rs.next() ) {
				Usuario u = new Usuario();
				Notificacao n = new Notificacao();
				n.setId( rs.getInt( 1 ) );
				n.setMensagem( rs.getString( 2 ) );
				n.setUsuario( u.comId( rs.getInt( 3 ) ) );
				notificacoes.add( n );
			}

			stmt.close();
			conn.close();

			return notificacoes;

		} catch ( Exception e ) {
			throw new Exception( "Erro ao buscar" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}
}
