# sd-atv-concorrencia-parte1
Atvidade de Concorrência na Manipulação de Dados

### Solução

Para garantir a sincronia entre os métodos da aplicação local foi-se usado o Objeto ArrayBlockingQueue com as ações de lock e unlock, respectivamente put e __take__ (onde há a liberação do objeto). Cada proceso de thread (inserção atualização e remoção do usuário) está amarrado um a outro esperando o termino de um acabar para sua devida pŕopria execução.

O id de cada usuário está sendo representado como uma string composta pelo timestamp da inserção, nao apenas. As aplicações estão sendo executadas em tempos diferentes, com garantia, para que não haja chaves iguais.
