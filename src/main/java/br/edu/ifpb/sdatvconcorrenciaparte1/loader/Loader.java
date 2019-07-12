package br.edu.ifpb.sdatvconcorrenciaparte1.loader;

import br.edu.ifpb.sdatvconcorrenciaparte1.domain.Usuario;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.InserterUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.RemoverUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.UpdaterUser;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

public class Loader {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private ArrayBlockingQueue<Integer> queueInsert;
    private ArrayBlockingQueue<Integer> queueUpdate;
    private ArrayBlockingQueue<Integer> queueDelete;

    public Loader() {
        queueInsert = new ArrayBlockingQueue<Integer>(20);
        queueUpdate = new ArrayBlockingQueue<Integer>(20);
        queueDelete = new ArrayBlockingQueue<Integer>(20);
    }

    public static void main(String[] args) throws SQLException {
        new Loader().run();
    }

    public void run() {
        for (int i=1; i<=20; i++){
            try {
                Usuario usuario = new Usuario();
                usuario.setId(i);
                usuario.setNome("Bartolomeu");

                queueInsert.put(usuario.getId());
                InserterUser inserterUser = new InserterUser(usuario, queueInsert, queueUpdate);
                UpdaterUser updaterUser = new UpdaterUser(queueUpdate, queueDelete);
                RemoverUser removerUser = new RemoverUser(queueDelete);
            } catch (InterruptedException e) {
                log.warning(Thread.currentThread().getName() + "Thread interrompida");
            }
        }
    }

}
