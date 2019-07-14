package br.edu.ifpb.sdatvconcorrenciaparte1.loader;

import br.edu.ifpb.sdatvconcorrenciaparte1.domain.Usuario;
import br.edu.ifpb.sdatvconcorrenciaparte1.factory.UserIdFactory;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.InserterUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.RemoverUser;
import br.edu.ifpb.sdatvconcorrenciaparte1.thread.UpdaterUser;

import java.net.UnknownHostException;
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

    public static void main(String[] args) throws UnknownHostException {
        //Analisa tempo com 10 threads
        long initialTimeWith10Threads = System.currentTimeMillis();
        new Loader().run(10);
        long finalTimeWith10Threads = System.currentTimeMillis();
        System.out.println("Tempo de duração em milissegundos com 10 Threads: " + (finalTimeWith10Threads - initialTimeWith10Threads));

        //Analisa tempo com 100 threads
//        long initialTimeWith100Threads = System.currentTimeMillis();
//        new Loader().run(100);
//        long finalTimeWith100Threads = System.currentTimeMillis();
//        System.out.println("Tempo de duração em milissegundos com 100 Threads: " + (finalTimeWith100Threads - initialTimeWith100Threads));

        //Analisa tempo com 1000 threads
//        long initialTimeWith1000Threads = System.currentTimeMillis();
//        new Loader().run(1000);
//        long finalTimeWith1000Threads = System.currentTimeMillis();
//        System.out.println("Tempo de duração em milissegundos com 1000 Threads: " + (finalTimeWith1000Threads - initialTimeWith1000Threads));
    }

    public void run(int qntThreads) {
        for (int i=1; i<=qntThreads; i++){
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
