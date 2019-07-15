# sd-atv-concorrencia-parte1
Atvidade de Concorrência na Manipulação de Dados

### Solução

Para garantir a sincronia entre os métodos da aplicação local foi-se usado o Objeto ArrayBlockingQueue com as ações de lock e unlock, respectivamente put e __take__ (onde há a liberação do objeto). Cada proceso de thread (inserção, atualização e remoção do usuário) está amarrado um a outro esperando o término de um para sua devida pŕopria execução.

O id de cada usuário está sendo representado como uma string composta pelo timestamp da inserção, nao apenas. As aplicações estão sendo executadas em tempos diferentes, com garantia, para que não haja chaves iguais. Há uma tabela TimeWaitApplication que dá a garantia de execução em tempos distintos das aplicações em até 4999 ms. Quando uma instancia tenta iniciar ela insere um registro de TimeWaitApplication com o tempo em milissegundos, esse tempo é randômico. A próxima aplicação funcionará da mensma forma, mas há uma comparação de tempo: se uma nova instância iniciar (depois de registrar o tempo que será inicializada), a instância faz uma uma checagem verificando se o tempo da última instancia foi igual, em caso verdadeiro tem-se um looping gerando novos tempos até que o tempo de inicialização da instancia em questão seja difrente entre a ultima executada. Dessa forma, o id será mutualmente exclusivo, garantindo a integridade de chave primária.

#### Passos para Execução
 > 1. Rode src/main/docker/docker-compose.yml com `docker-compose up -d` para criar e inicializar o banco PostgreSQL
 > 2. Banco inicializado e configurado, rode o arquivo `sh` na raiz do projeto com o comando `sh execute-instances.sh` para construir o projeto com maven assemby e executar duas instancias.
