
Diante da dificuldade de encontrar simuladores de redes em chip de alto nível de abstração fáceis de usar, de aprender e que tenham implementadas todas as métricas para aviaço apenas ao nivel de mapeamento de tarefas, foi proposta a implementação de um simulador.
 
O SiNoC, nome vindo da junção entre Simulador e Network on Chip, foi implementado na linguagem de programação Java e tem o objetivo de avaliar as aplicações aqui estudadas quanto às métricas que ele implementa. Esse simulador é de código aberto, ou seja, é disponibilizado para que qualquer pessoa possa usar, contribuir para acrescentar funcionalidades ou corrigir eventuais bugs que possa apresentar. O código fonte do SiNoC está disponível aqui no GitHub.
 
A ferramenta SiNoC se restringe, na versão atual, a fazer simulações apenas em MPSoCs com topologia mesh 2D de qualquer dimensão, conforme ela foi inicialmente idealizada, porém isso não proíbe de, em trabalhos futuros, poder ser inserida a opção de realizar simulações com topologias genéricas de NoCs. 
 
Métricas Calculadas
 
O simulador possui uma série de métricas que ele calcula e apresenta como resultado. Entre elas, as principais são:
 
- Latência da aplicação: essa métrica contabiliza a latência do pacote que mais demorou para ser entregue à sua tarefa destino, fornecendo, pois, a latência total de comunicação da aplicação. Pode ser obtida em unidades (pacotes) ou em flits.
	
- Latência em hops da aplicação: essa métrica avalia qual a maior distância, em hops, percorrida por um pacote da aplicação.
	
- Somatório das latências: corresponde ao somatório total das latências dos pacotes.
	
- Latência média dos pacotes: é a divisão do somatório total das latências dos pacotes pela quantidade de pacotes transferidos. Ela diz, em média, quanto os pacotes demoram para ser entregues.
	
- Quantidade de enlaces acessados: contabiliza quantos enlaces foram acessados.
	
- Reuso dos enlaces: contabiliza quantos enlaces foram reusados. Ou seja, quantos enlaces foram usados pelo menos duas vezes. Pode ser obtida tanto em unidades (pacotes) ou em flits.
	
- Taxa de reuso dos enlaces: contabiliza quantas vezes os enlaces foram reusados. Pode ser obtida tanto em unidades (pacotes) ou em flits.
	
- Total de flits da aplicação: contabilização do total de flits que a aplicação possui.
	
- Somatório dos acessos aos enlaces: contabiliza os acessos a todos os enlaces da NoC. Pode ser obtida em unidades (pacotes) ou em flits.
	
Configurações Padrão
 
Por simplicidade, no SiNoC, a largura de banda dos canais e os buffers dos roteadores são infinitos. O simulador já tem os mecanismos de comunicação pré-fixados, não sendo possível a alteração desses. As configurações dos mecanismos de comunicação são os seguintes: 
 
- Chaveamento: é feito por pacote, da forma store-and-forward, logo, os pacotes são subdivididos em flits. Quando um grafo de uma aplicação é dado como entrada para a ferramenta, cada comunicação entre suas tarefas é considerada como um pacote, e os flits são os pesos relacionados a cada uma dessas comunicações.
    
- Controle de fluxo: como os buffers dos roteadores são infinitos, não é implementado nenhum controle de fluxo específico. Entretanto, pode-se dizer que temos um controle de fluxo baseado em créditos onde a quantidade de créditos é infinita.
    
- Roteamento: como já visto, o simulador implementa dois tipos de roteamento, sendo um não adaptativo, XY, e o outro adaptativo, XY\_YX sendo subclassificado de semi adaptativo, por evitar que os caminhos percorridos pelos pacotes se distancie de sua tarefa de destino.
    
- Arbitragem: é feita de forma distribuída, onde  roteamento e a arbitragem são realizados separadamente. Assim, o algoritmo de roteamento apenas diz ao roteador qual direção o pacote deve seguir, e o árbitro de cada roteador é quem vai dizer qual melhor caminho imediato a seguir para que o pacote percorra para aquela determinada direção.
 
- Memorização: é feita de forma centralizada em cada roteador, onde os pacotes que chegam de qualquer direção são armazenados em um único buffer, o qual usa a política First-In First-Out (FIFO). Entretanto, caso em um dado instante da simulação, chegue mais de um pacote em um determinado roteador, eles são armazenados no buffer de forma crescente de acordo com seus tamanhos, em flits. Caso o tamanho seja igual, a alocação é feita de forma arbitrária.
