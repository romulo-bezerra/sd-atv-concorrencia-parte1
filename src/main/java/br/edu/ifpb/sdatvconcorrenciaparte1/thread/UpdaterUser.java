package br.edu.ifpb.sdatvconcorrenciaparte1.thread;

import br.edu.ifpb.sdatvconcorrenciaparte1.dao.UsuarioDao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

public class UpdaterUser implements Runnable {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private UsuarioDao usuarioDao;
    private ArrayBlockingQueue<Integer> queueUpdate;
    private ArrayBlockingQueue<Integer> queueDelete;

    public UpdaterUser(ArrayBlockingQueue<Integer> queueUpdate, ArrayBlockingQueue<Integer> queueDelete) {
        this.queueUpdate = queueUpdate;
        this.queueDelete = queueDelete;
        this.usuarioDao = new UsuarioDao();
        new Thread(this, "UpdaterUser").start();
    }

    @Override
    public void run() {
        updateUser();
    }

    private void updateUser() {
        try {
            int userIdUpdateLiberado = queueUpdate.take();
            usuarioDao.update(userIdUpdateLiberado);
            queueDelete.put(userIdUpdateLiberado);
            log.info("Usuário atualizado");
        } catch (InterruptedException e) {
            log.warning(Thread.currentThread().getName() + "Thread interrompida");
        }
    }

}
