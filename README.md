# lunar-land

Esse projeto tem como objetivo explorar o uso de algoritmos neuro-evolutivos para solução do problema/jogo lunar land.

O objetivo do jogo é pousar um módulo lunar na superfice indicada no mapa, na posição correta e com uma velocidade (vetorial e angular) baixa o suficiente para não causar danos a estrutura.

O controlador (que no caso é o algoritmo) tem a sua disposição para direcionar o módulo lunar:

* Jatos laterais, que alteram a aceleração angular, e por consequencia a velocidade angular e angulo do módulo
* Jato principal, que altera a aceleração vetorial, e por consequência a velocidade vetorial e a posição (em conjunto com a gravidade)

## Algoritmo neuro-evolutivo

Essa classe de algoritmos combina pontos dos algoritmos evolutivos e das redes neurais.

Algoritmos evolutivos são ótimos para evoluir uma solução de forma heurística, usando o próprio resultado (fitness) como guia durante o processo. O problema dos algoritmos evolutivos é que não suportam muito bem uma mudança do problema. Se os parâmetros iniciais mudam, isso impacta de forma considerável os resultados, e possivelmente será preciso aguardar novamente que o processo evolutivo reaja a essa mudança.

Por outro lado, as redes neurais simples são boas em reagir a estímulos (entradas) externas, e depois de treinadas, podem se adaptar facilmente a novas entradas (desde que o treinamento tenha sido eficiente). O problema é que as redes neurais simples dependem de supervisão para serem treinadas, isso é, dependem de algum agente externo pra dizer se o resultado obtido foi satisfatório ou não.

A combinação dessas tecnologias resulta em um processo não supervisionado, mas que também consegue reagir ao ambiente. Na prática, temos o fitness do processo evolucionário guiando/selecionando/mutando redes neurais.

## Resultados

Os vídeos abaixo mostram alguns processos de treinamento/evolução.

![res1](./doc/res1.gif)

![res2](./doc/res2.gif)
