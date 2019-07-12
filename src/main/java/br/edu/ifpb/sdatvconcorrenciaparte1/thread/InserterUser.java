package br.edu.ifpb.sdatvconcorrenciaparte1.thread;

import br.edu.ifpb.sdatvconcorrenciaparte1.dao.UsuarioDao;
import br.edu.ifpb.sdatvconcorrenciaparte1.domain.Usuario;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

public class InserterUser implements Runnable {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private ArrayBlockingQueue<Integer> queueInsert;
    private ArrayBlockingQueue<Integer> queueUpdate;

    public InserterUser(Usuario usuario, ArrayBlockingQueue<Integer> queueInsert, ArrayBlockingQueue<Integer> queueUpdate) {
        this.usuario = usuario;
        this.queueInsert = queueInsert;
        this.queueUpdate = queueUpdate;
        this.usuarioDao = new UsuarioDao();
        new Thread(this, "InserterUser").start();
    }

    @Override
    public void run() {
        insertUser();
    }

    private void insertUser() {
        try {
            int userIdInsertLiberado = queueInsert.take();
            usuarioDao.insert(usuario);
            queueUpdate.put(userIdInsertLiberado);

            log.info("Usuário inserido");
        } catch (InterruptedException e) {
            log.warning(Thread.currentThread().getName() + "Thread interrompida");
        }
    }

}
