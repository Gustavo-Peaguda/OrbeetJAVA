# ORBEET — SmartAgro Platform

> Plataforma inteligente de monitoramento ambiental agrícola voltada para a preservação da atividade de polinização.

---

## Integrantes

| Nome | RM |
|---|---|
| Enzo Fernandes Ramos | 563705 |
| Felipe Henrique de Souza Cerazi | 562746 |
| Gustavo Peaguda de Castro | 562923 |
| Lorenzo Andolfatto Coque | 563385 |

---

## Descrição do Projeto

O **ORBEET** é uma plataforma desenvolvida em Java que permite aos funcionários da empresa monitorar condições climáticas em áreas agrícolas e apoiar produtores rurais na proteção da polinização de suas lavouras.

O sistema coleta e analisa indicadores ambientais — temperatura, umidade do ar, índice de seca e ocorrência de ondas de calor — e calcula o **IRRP (Índice de Risco de Redução da Polinização)**. Quando o índice ultrapassa níveis críticos, o produtor vinculado à região recebe notificações automáticas com alertas preventivos e recomendações de manejo.

O projeto está alinhado ao **ODS 9 — Indústria, Inovação e Infraestrutura**, aplicando conceitos de infraestrutura inteligente, automação de processos e serviços conectados em um contexto real de agricultura sustentável.

---

## Funcionalidades

- Análise de risco ambiental com cálculo automático do IRRP
- Cadastro de zonas de cultivo com validação de ID duplicado
- Cadastro e gerenciamento de estações de monitoramento em campo
- Consolidação de dados da rede de estações
- Geração de relatório completo do sistema
- Notificações automáticas ao produtor com recomendações específicas
- Interface gráfica intuitiva com Java Swing

---

## Instruções de Execução

### Pré-requisitos

- Java JDK 17 ou superior
- IDE de sua preferência (IntelliJ IDEA, Eclipse ou VS Code com extensão Java)

### Passos

**1. Clone o repositório**
```bash
git clone https://github.com/Gustavo-Peaguda/OrbeetJAVA.git
cd orbeet
```

**2. Importe o projeto na sua IDE**

- No IntelliJ: `File > Open` e selecione a pasta do projeto
- No Eclipse: `File > Import > Existing Projects into Workspace`

**3. Configure o source root**

Certifique-se de que a pasta `src` está marcada como **Sources Root** na sua IDE.

**4. Execute a aplicação**

Localize e execute a classe:
```
src/app/Main.java
```

A janela da aplicação abrirá automaticamente.

### Estrutura de Diretórios

```
ORBEET/
└── src/
    ├── app/
    │   └── Main.java
    ├── interfaces/
    │   ├── Monitoravel.java
    │   ├── Notificavel.java
    │   └── Calculavel.java
    ├── abstracts/
    │   ├── EstacaoBase.java
    │   ├── MonitoramentoBase.java
    │   └── ServicoInteligente.java
    ├── model/
    │   ├── DadosAmbientais.java
    │   ├── Regiao.java
    │   ├── Alerta.java
    │   ├── Produtor.java
    │   ├── ZonaCultivo.java
    │   └── EstacaoSensor.java
    ├── service/
    │   ├── CalculadoraIRRP.java
    │   ├── MonitoramentoAmbiental.java
    │   ├── SistemaAlertas.java
    │   ├── RedeConectividade.java
    │   └── RelatorioService.java
    └── ui/
        └── TelaORBEET.java
```

---

## Explicação das Classes Principais

### `DadosAmbientais` — model
Representa uma leitura ambiental composta por temperatura, umidade, índice de seca e ocorrência de onda de calor. Possui construtores sobrecarregados — um completo e um sem o parâmetro de onda de calor.

### `EstacaoSensor` — model
Estação de monitoramento instalada em campo. Herda de `EstacaoBase` e implementa `Monitoravel`. Armazena leituras ambientais coletadas em sua área de cobertura e gera relatórios individuais.

### `Produtor` — model
Representa o produtor agrícola vinculado a uma região monitorada. Implementa `Notificavel` e mantém um histórico de todas as notificações e alertas recebidos do sistema.

### `ZonaCultivo` — model
Representa uma área agrícola cadastrada no sistema, com informações de identificação, área em hectares, tipo de cultura, endereço e status de polinização baseado no IRRP calculado.

### `CalculadoraIRRP` — service
Núcleo analítico do sistema. Herda de `ServicoInteligente` e implementa `Calculavel`. Calcula o Índice de Risco de Redução da Polinização cruzando as variáveis ambientais e classifica o risco em quatro níveis: BAIXO, MODERADO, ALTO e CRÍTICO. Possui método `calcularIndice()` sobrecarregado.

### `MonitoramentoAmbiental` — service
Serviço central de análise. Herda de `MonitoramentoBase` e implementa `Monitoravel`. Recebe dados ambientais, aciona a calculadora e expõe o resultado para geração de alertas.

### `SistemaAlertas` — service
Responsável por gerar o objeto `Alerta` com base no IRRP calculado e notificar o produtor vinculado à região com alertas preventivos e recomendações de manejo.

### `RedeConectividade` — service
Gerencia a coleção de estações de sensor cadastradas. Consolida as leituras de todas as estações online, calcula médias regionais e gera um IRRP médio da rede.

### `RelatorioService` — service
Gera o relatório completo do sistema, consolidando informações do produtor, zonas de cultivo cadastradas e rede de estações em um único documento formatado.

### `TelaORBEET` — ui
Interface gráfica principal da aplicação, construída com Java Swing. Herda de `JFrame` e organiza todas as funcionalidades em quatro abas: Monitoramento, Zonas de Cultivo, Estações e Relatório Geral.

---

## Conceitos de POO Aplicados

| Conceito | Onde foi aplicado |
|---|---|
| Herança | `EstacaoSensor → EstacaoBase`, `MonitoramentoAmbiental → MonitoramentoBase`, `CalculadoraIRRP → ServicoInteligente` |
| Classes Abstratas | `EstacaoBase`, `MonitoramentoBase`, `ServicoInteligente` |
| Interfaces | `Monitoravel`, `Notificavel`, `Calculavel` |
| Sobrescrita | `gerarRelatorio()`, `analisar()`, `gerarRecomendacao()`, `monitorar()`, `enviarAlerta()` |
| Sobrecarga | `calcularIndice()` em `CalculadoraIRRP`, construtores de `DadosAmbientais` e `Regiao` |
| Encapsulamento | Atributos `private`/`protected` com getters e setters em todas as classes |
| Polimorfismo | `EstacaoSensor` e `MonitoramentoAmbiental` tratados via interface `Monitoravel` |

---


---

> ORBEET © 2026 — ODS 9 | Indústria, Inovação e Infraestrutura
