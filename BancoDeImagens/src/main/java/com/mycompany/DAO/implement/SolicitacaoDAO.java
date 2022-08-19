package com.mycompany.DAO.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.factory.ConnectionSQLite;
import com.mycompany.model.Permissao;
import com.mycompany.model.Solicitacao;
import com.mycompany.model.Usuario;

public class SolicitacaoDAO {

	public static void createTablePermissoes() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		var query = "CREATE TABLE IF NOT EXISTS Solicitacao (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "id_admin INTEGER NOT NULL REFERENCES Usuario(id), " + "id_permissao INTEGER NOT NULL REFERENCES Permissao(id), " + "visualizar INT DEFAULT 0, " + "compartilhar INT DEFAULT 0,"
				+ "excluir INT DEFAULT 0, " + ")";

		try {
			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			stmt.execute( query );

		} catch ( SQLException e ) {
			throw new RuntimeException( "Erro ao criar tabela permissões: " + e.getMessage() );
		} finally {
			stmt.close();
			conn.close();
		}
	}

	public void insert( Solicitacao solicitacao ) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "INSERT INTO Solicitacao(excluir, compartilhar, visualizar, id_permissao, id_admin) VALUES (?, ?, ?, ?, ?); ";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setBoolean( 1, solicitacao.isExcluir() );
			ps.setBoolean( 2, solicitacao.isCompartilhar() );
			ps.setBoolean( 3, solicitacao.isVisualizar() );
			ps.setInt( 4, solicitacao.getPermissao().getId() );
			ps.setInt( 5, solicitacao.getUsuario().getId() );
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

	public void delete( Solicitacao solicitacao ) throws Exception {
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "DELETE FROM Solicitacao WHERE id_permissao = ? AND id_admin = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, solicitacao.getPermissao().getId() );
			ps.setInt( 2, solicitacao.getUsuario().getId() );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao excluir" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}

	public List<Solicitacao> getAllByAdmin( Usuario usuario ) throws Exception {
		List<Solicitacao> solicitacoes = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;

		PreparedStatement ps = null;
		try {

			String SQL = "SELECT s.id, s.excluir, s.compartilhar, s.visualizar, s.id_permissao, u.id, u.login " + " FROM Solicitacao s INNER JOIN Permissao p ON p.id = s.id_permissao INNER JOIN Usuario u ON u.id = p.id_usuario" + " WHERE s.id_admin = ? ;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, usuario.getId() );
			ResultSet rs = ps.executeQuery();

			while( rs.next() ) {
				Solicitacao s = new Solicitacao();
				Permissao p = new Permissao();
				Usuario u = new Usuario();
				s.setId( rs.getInt( 1 ) );
				s.setExcluir( rs.getBoolean( 2 ) );
				s.setCompartilhar( rs.getBoolean( 3 ) );
				s.setVisualizar( rs.getBoolean( 4 ) );
				p.setId( rs.getInt( 5 ) );
				s.setUsuario( usuario );
				u.setId( rs.getInt( 6 ) );
				u.setLogin( rs.getString( 7 ) );
				p.setUsuario( u );
				s.setPermissao( p );
				solicitacoes.add( s );
			}

			stmt.close();
			conn.close();

			return solicitacoes;

		} catch ( Exception e ) {
			throw new Exception( "Erro ao buscar" );
		} finally {
			ps.close();
			conn.close();
			stmt.close();

		}
	}
}
