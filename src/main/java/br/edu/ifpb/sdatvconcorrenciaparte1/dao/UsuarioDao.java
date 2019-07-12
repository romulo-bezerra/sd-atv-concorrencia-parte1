package br.edu.ifpb.sdatvconcorrenciaparte1.dao;

import br.edu.ifpb.sdatvconcorrenciaparte1.confactory.ConFactoryPostgreSQL;
import br.edu.ifpb.sdatvconcorrenciaparte1.domain.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UsuarioDao {

    private final Logger log = Logger.getLogger(UsuarioDao.class.getName());
    private Connection con;

    public UsuarioDao() {
        try {
            this.con = ConFactoryPostgreSQL.getConnectionPostgres();
        } catch (SQLException e) {
            log.warning("Falha na conexão\n" + e.getMessage());
        }
    }

    public void insert(Usuario usuario){
        try {
            String sql = "INSERT INTO usuario (id, nome, atualizando, deletando) VALUES (?,?,?,?)";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setInt(1, usuario.getId());
            statement.setString(2,usuario.getNome());
            statement.setBoolean(3,false);
            statement.setBoolean(4,false);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Falha ao inserir um novo usuário\n");
        }
    }

//    public int getProxID(){
//        int lastId = 0;
//        try {
//            String sql = "SELECT COUNT(*) FROM usuario";
//            PreparedStatement statement = con.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//            lastId = rs.next() ? rs.getInt(1) : lastId;
//        } catch (SQLException e) {
//            log.warning("Falha ao ao executar a consulta\n");
//        }
//        return ++lastId;
//    }

    public void update(int userId){
        try {
            String sql = "UPDATE usuario SET atualizando=? WHERE id=?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Falha ao atualizar usuário\n");
        }
    }

    public void delete(int id){
        try {
            String sql = "UPDATE usuario SET deletando=? WHERE id=?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sql);
            statement.setBoolean(1,true);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning("Falha ao deletar usuário\n");
        }
    }

}
