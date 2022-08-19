package com.mycompany.DAO.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.factory.ConnectionSQLite;
import com.mycompany.model.Imagem;
import com.mycompany.model.Permissao;
import com.mycompany.model.Usuario;

public class PermissaoDAO {

	public static void createTablePermissoes() throws SQLException {
		var query = "CREATE TABLE IF NOT EXISTS Permissao (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "id_usuario INTEGER NOT NULL REFERENCES Usuario (id), " + "visualizar INT DEFAULT 0, " + "path varchar not null," + "compartilhar INT DEFAULT 0," + "aplicar_Filtro INT DEFAULT 0,"
				+ "excluir INT DEFAULT 0 " + ")";
		Connection conn = null;
		Statement stmt = null;
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

	public Permissao getPermissao( Usuario usuario, Imagem imagem ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		try {

			String SQL = "SELECT p.id, p.excluir, p.compartilhar, p.visualizar, p.aplicar_Filtro FROM Permissao p " + " INNER JOIN Usuario u ON u.id = p.id_usuario " + "  WHERE u.id = ? AND p.path = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, usuario.getId() );
			ps.setString( 2, imagem.getPath() );
			ResultSet rs = ps.executeQuery();

			Permissao p = null;
			while( rs.next() ) {
				p = new Permissao();
				p.setId( rs.getInt( 1 ) );
				p.setExcluir( rs.getBoolean( 2 ) );
				p.setCompartilhar( rs.getBoolean( 3 ) );
				p.setVisualizar( rs.getBoolean( 4 ) );
				p.setAplicarFiltro( rs.getBoolean( 5 ) );
			}

			return p;

		} catch ( Exception e ) {

			throw new Exception( "Erro ao buscar" );
		} finally {
			stmt.close();

			closeConnection( conn, ps );
		}
	}

	public Permissao getPermissaoByIdsUsuarioImagem( Permissao permissao ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			String SQL = "SELECT id, excluir, compartilhar, visualizar, aplicar_Filtro, path FROM Permissao " + " WHERE id_usuario=? AND path=? ;";

			conn = ConnectionSQLite.connect();
			Statement stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, permissao.getUsuario().getId() );
			ps.setString( 2, permissao.getPath() );
			ResultSet rs = ps.executeQuery();

			Permissao p = null;
			while( rs.next() ) {
				p = new Permissao();
				p.setId( rs.getInt( 1 ) );
				p.setExcluir( rs.getBoolean( 2 ) );
				p.setCompartilhar( rs.getBoolean( 3 ) );
				p.setVisualizar( rs.getBoolean( 4 ) );
				p.setAplicarFiltro( rs.getBoolean( 5 ) );
				p.setPath( rs.getString( 6 ) );
			}

			stmt.close();

			return p;

		} catch ( Exception e ) {
			throw new Exception( "Erro ao buscar" );
		} finally {
			closeConnection( conn, ps );
		}
	}

	public Permissao getPermissaoById( int id ) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try {

			String SQL = "SELECT p.id, p.excluir, p.compartilhar, p.visualizar, u.login, p.path, u.id, p.aplicar_Filtro " + " FROM Permissao p JOIN Usuario u ON u.id = p.id_usuario," + " WHERE p.id=? ;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, id );
			ResultSet rs = ps.executeQuery();

			Permissao p = null;
			Usuario u = null;
			while( rs.next() ) {
				p = new Permissao();
				u = new Usuario();
				p.setId( rs.getInt( 1 ) );
				p.setExcluir( rs.getBoolean( 2 ) );
				p.setCompartilhar( rs.getBoolean( 3 ) );
				p.setVisualizar( rs.getBoolean( 4 ) );
				u.setLogin( rs.getString( 5 ) );
				p.setPath( rs.getString( 6 ) );
				u.setId( rs.getInt( 7 ) );
				p.setAplicarFiltro( rs.getBoolean( 8 ) );
				p.setUsuario( u );

			}

			return p;

		} catch ( Exception e ) {
			throw new Exception( "Erro ao buscar" );
		} finally {
			closeConnection( conn, ps );
			stmt.close();
		}
	}

	public void insert( Permissao permissao ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;

		try {

			String SQL = "INSERT INTO Permissao(path, id_usuario, excluir, compartilhar, visualizar, aplicar_Filtro)" + " VALUES (?, ?, ?, ?, ?, ?); ";

			var u = permissao.getUsuario().getId();

			conn = ConnectionSQLite.connect();
			ps = conn.prepareStatement( SQL );

			ps.setString( 1, permissao.getPath() );
			ps.setInt( 2, u );
			ps.setBoolean( 3, permissao.isExcluir() );
			ps.setBoolean( 4, permissao.isCompartilhar() );
			ps.setBoolean( 5, permissao.isVisualizar() );
			ps.setBoolean( 6, permissao.isAplicarFiltro() );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao inserir" );
		} finally {
			closeConnection( conn, ps );
		}
	}

	public void update( Permissao permissao ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		try {

			String SQL = "UPDATE Permissao SET compartilhar = ?, excluir = ?, visualizar = ?, aplicar_Filtro= ? WHERE id = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setBoolean( 1, permissao.isCompartilhar() );
			ps.setBoolean( 2, permissao.isExcluir() );
			ps.setBoolean( 3, permissao.isVisualizar() );
			ps.setBoolean( 4, permissao.isAplicarFiltro() );
			ps.setInt( 5, permissao.getId() );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao atualizar" );
		} finally {
			closeConnection( conn, ps );
			stmt.close();
		}
	}

	public void delete( int id ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String SQL = "DELETE FROM Permissao WHERE id= ?";

			conn = ConnectionSQLite.connect();
			ps = conn.prepareStatement( SQL );

			ps.setInt( 1, id );
			ps.executeUpdate();

		} catch ( Exception e ) {
			throw new Exception( "Erro ao excluir" );
		} finally {
			closeConnection( conn, ps );
		}
	}

	public List<Permissao> getByUsuario( Usuario usuario ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		Statement stmt = null;
		List<Permissao> permissoes = new ArrayList<>();
		try {

			String SQL = "SELECT p.id, p.excluir, p.compartilhar, p.visualizar, p.path, p.aplicar_Filtro FROM Permissao p " + " INNER JOIN Usuario u ON u.id = p.id_usuario" + "  WHERE u.id = ?;";

			conn = ConnectionSQLite.connect();
			stmt = conn.createStatement();

			ps = conn.prepareStatement( SQL );
			ps.setInt( 1, usuario.getId() );
			ResultSet rs = ps.executeQuery();

			Permissao p = null;
			while( rs.next() ) {
				p = new Permissao();
				p.setId( rs.getInt( 1 ) );
				p.setExcluir( rs.getBoolean( 2 ) );
				p.setCompartilhar( rs.getBoolean( 3 ) );
				p.setVisualizar( rs.getBoolean( 4 ) );
				p.setPath( rs.getString( 5 ) );
				p.setAplicarFiltro( rs.getBoolean( 6 ) );

			}

			return permissoes;

		} catch ( Exception e ) {
			throw new Exception( "Erro ao Buscar" );
		} finally {

			stmt.close();
			closeConnection( conn, ps );
		}
	}

	private void closeConnection( Connection conn, PreparedStatement ps ) throws SQLException {
		if( ps != null ) {
			ps.close();
		}

		if( conn != null ) {
			conn.close();
		}
	}

}
