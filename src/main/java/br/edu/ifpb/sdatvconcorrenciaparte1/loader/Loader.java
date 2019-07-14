package br.edu.ifpb.sdatvconcorrenciaparte1.loader;

import br.edu.ifpb.sdatvconcorrenciaparte1.domain.Usuario;
import br.edu.ifpb.sdatvconcorrenciaparte1.factory.UserIdFactory;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.InserterUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.RemoverUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.UpdaterUser;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;

public class Loader {

    private final Logger log = Logger.getLogger(this.getClass().getName());
    private final ArrayBlockingQueue<String> queueInsert;
    private final ArrayBlockingQueue<String> queueUpdate;
    private final ArrayBlockingQueue<String> queueDelete;
    private UserIdFactory userIdFactory;

    public Loader() throws UnknownHostException {
        this.queueInsert = new ArrayBlockingQueue<String>(20);
        this.queueUpdate = new ArrayBlockingQueue<String>(20);
        this.queueDelete = new ArrayBlockingQueue<String>(20);
        this.userIdFactory = new UserIdFactory();
    }

    public static void main(String[] args) throws SQLException, UnknownHostException {
        new Loader().run();
    }

    public void run() {
        for (int i=1; i<=20; i++){
            try {
                Usuario usuario = new Usuario();
                usuario.setId((String) userIdFactory.createId());
                usuario.setNome("Bartolomeu");

                queueInsert.put(usuario.getId()); //id para a fila
                new InserterUser(usuario, queueInsert, queueUpdate); //thread responsável por inserir
                new UpdaterUser(queueUpdate, queueDelete); //thread responsável por atualizar
                new RemoverUser(queueDelete); //thread responsável por deletar
            } catch (InterruptedException e) {
                log.warning(Thread.currentThread().getName() + "Thread interrompida");
            }
        }
    }

}
