package ui;

import model.*;
import service.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TelaORBEET extends JFrame {

    // Servicos
    private final SistemaAlertas    sistemaAlertas;
    private final RedeConectividade rede;
    private final RelatorioService  relatorioService;
    private final CalculadoraIRRP   calculadora;

    private Produtor          produtor;
    private List<ZonaCultivo> zonas = new ArrayList<>();

    // Aba 1 — Monitoramento
    private JTextField fProdNome, fProdEmail;
    private JTextField fCidade;
    private JTextField fTemp, fUmid, fSeca;
    private JCheckBox  chkOnda;
    private JTextArea  areaMonitor;

    // Aba 2 — Zonas de Cultivo
    private JTextField fZonaId, fZonaNome, fZonaArea, fZonaEndereco;
    private JComboBox<ZonaCultivo.TipoCultura> cbCultura;
    private JTextArea  areaZonas;

    // Aba 3 — Estacoes
    private JTextField fEstId, fEstNome, fEstEndereco, fEstDescricao;
    private JTextArea  areaRede;

    // Aba 4 — Relatorio
    private JTextArea areaRelatorio;

    // Paleta
    private static final Color C_FUNDO   = new Color(15, 18, 24);
    private static final Color C_PAINEL  = new Color(22, 27, 36);
    private static final Color C_BORDA   = new Color(44, 54, 72);
    private static final Color C_ACENTO  = new Color(230, 180, 60);   // âmbar discreto
    private static final Color C_TEXTO   = new Color(195, 210, 230);
    private static final Color C_TEXTO2  = new Color(120, 140, 165);
    private static final Color C_CAMPO   = new Color(26, 32, 44);
    private static final Color C_SAIDA   = new Color(10, 13, 19);
    private static final Color C_SAIDATX = new Color(180, 200, 220);
    private static final Color C_VERDE   = new Color(70, 185, 110);
    private static final Color C_PERIGO  = new Color(210, 75, 65);

    // Fontes
    private static final Font F_TITULO  = new Font("Monospaced", Font.BOLD, 22);
    private static final Font F_LABEL   = new Font("SansSerif",  Font.PLAIN, 12);
    private static final Font F_NEGRITO = new Font("SansSerif",  Font.BOLD,  12);
    private static final Font F_MONO    = new Font("Monospaced", Font.PLAIN, 12);
    private static final Font F_BTN     = new Font("SansSerif",  Font.BOLD,  12);

    // =========================================================
    //  CONSTRUTOR
    // =========================================================
    public TelaORBEET() {
        sistemaAlertas   = new SistemaAlertas();
        rede             = new RedeConectividade();
        relatorioService = new RelatorioService();
        calculadora      = new CalculadoraIRRP();

        popularDadosDemonstracao();
        configurarJanela();
        construirUI();
    }

    // =========================================================
    //  DADOS DE DEMONSTRACAO
    // =========================================================
    private void popularDadosDemonstracao() {
        EstacaoSensor e1 = new EstacaoSensor(
                "ES-01", "Estacao Norte",
                "Rodovia BR-060, Km 12, Zona Rural - Brasilia/DF",
                "Monitora a regiao norte das lavouras de soja");
        e1.monitorar(new DadosAmbientais(34.0, 45.0, 3.5));

        EstacaoSensor e2 = new EstacaoSensor(
                "ES-02", "Estacao Sul",
                "Estrada Municipal EM-221, Setor Agropecuario - Luziania/GO",
                "Monitora a regiao sul, proxima ao cerrado nativo");
        e2.monitorar(new DadosAmbientais(38.0, 28.0, 6.0, true));

        rede.adicionarEstacao(e1);
        rede.adicionarEstacao(e2);

        zonas.add(new ZonaCultivo("Z-01", "Zona Cerrado A", 120.0,
                ZonaCultivo.TipoCultura.SOJA,
                "Fazenda Boa Esperanca - BR-060 Km 15, Brasilia/DF"));
        zonas.add(new ZonaCultivo("Z-02", "Zona Cerrado B", 85.5,
                ZonaCultivo.TipoCultura.MILHO,
                "Sitio Sao Joao - Estrada EM-221, Luziania/GO"));
    }

    // =========================================================
    //  JANELA
    // =========================================================
    private void configurarJanela() {
        setTitle("ORBEET  —  SmartAgro Platform");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 740);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(C_FUNDO);
    }

    // =========================================================
    //  UI GERAL
    // =========================================================
    private void construirUI() {
        setLayout(new BorderLayout());
        add(criarCabecalho(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(C_PAINEL);
        abas.setForeground(C_ACENTO);
        abas.setFont(F_NEGRITO);

        abas.addTab("Monitoramento",   criarAbaMonitoramento());
        abas.addTab("Zonas de Cultivo",criarAbaZonas());
        abas.addTab("Estacoes",        criarAbaEstacoes());
        abas.addTab("Relatorio Geral", criarAbaRelatorio());

        add(abas,          BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
    }

    // =========================================================
    //  ABA 1 — MONITORAMENTO
    // =========================================================
    private JPanel criarAbaMonitoramento() {
        JPanel p = painelAba();
        p.setLayout(new GridLayout(1, 2, 12, 0));

        JPanel form = coluna();

        JPanel secProd = secao("Produtor");
        JPanel gp = grid(2);
        gp.add(rot("Nome"));   fProdNome  = campo(); gp.add(fProdNome);
        gp.add(rot("E-mail")); fProdEmail = campo(); gp.add(fProdEmail);
        secProd.add(gp, BorderLayout.CENTER);

        JPanel secReg = secao("Localizacao");
        JPanel gr = grid(1);
        gr.add(rot("Cidade")); fCidade = campo(); gr.add(fCidade);
        secReg.add(gr, BorderLayout.CENTER);

        JPanel secAmb = secao("Dados Ambientais");
        JPanel ga = grid(4);
        ga.add(rot("Temperatura (C)"));      fTemp = campo(); ga.add(fTemp);
        ga.add(rot("Umidade (%)"));          fUmid = campo(); ga.add(fUmid);
        ga.add(rot("Indice de Seca (0-10)")); fSeca = campo(); ga.add(fSeca);
        ga.add(rot("Onda de Calor?"));
        chkOnda = new JCheckBox("Sim");
        chkOnda.setBackground(C_PAINEL);
        chkOnda.setForeground(C_TEXTO);
        chkOnda.setFont(F_LABEL);
        ga.add(chkOnda);
        secAmb.add(ga, BorderLayout.CENTER);

        form.add(Box.createVerticalStrut(6));
        form.add(secProd);
        form.add(Box.createVerticalStrut(8));
        form.add(secReg);
        form.add(Box.createVerticalStrut(8));
        form.add(secAmb);
        form.add(Box.createVerticalStrut(12));
        form.add(fileiraBotoes(
                botao("Analisar Risco",  C_ACENTO, C_FUNDO,  e -> analisarRisco()),
                botao("Limpar",          C_BORDA,  C_TEXTO2, e -> limparMonitor())
        ));

        areaMonitor = areaTexto();
        areaMonitor.setText("Preencha os dados e clique em Analisar Risco.");

        p.add(form);
        p.add(scroll(areaMonitor));
        return p;
    }

    // =========================================================
    //  ABA 2 — ZONAS DE CULTIVO
    // =========================================================
    private JPanel criarAbaZonas() {
        JPanel p = painelAba();
        p.setLayout(new GridLayout(1, 2, 12, 0));

        JPanel form = coluna();

        JPanel sec = secao("Nova Zona de Cultivo");
        JPanel g = grid(5);
        g.add(rot("ID"));           fZonaId       = campo(); g.add(fZonaId);
        g.add(rot("Nome"));         fZonaNome     = campo(); g.add(fZonaNome);
        g.add(rot("Area (ha)"));    fZonaArea     = campo(); g.add(fZonaArea);
        g.add(rot("Endereco"));     fZonaEndereco = campo(); g.add(fZonaEndereco);
        g.add(rot("Cultura"));
        cbCultura = new JComboBox<>(ZonaCultivo.TipoCultura.values());
        cbCultura.setBackground(C_CAMPO);
        cbCultura.setForeground(C_TEXTO);
        cbCultura.setFont(F_LABEL);
        g.add(cbCultura);
        sec.add(g, BorderLayout.CENTER);

        form.add(Box.createVerticalStrut(6));
        form.add(sec);
        form.add(Box.createVerticalStrut(12));
        form.add(fileiraBotoes(
                botao("Adicionar Zona", C_VERDE,  C_FUNDO,  e -> adicionarZona()),
                botao("Listar Zonas",   C_ACENTO, C_FUNDO,  e -> listarZonas())
        ));

        areaZonas = areaTexto();
        listarZonas();

        p.add(form);
        p.add(scroll(areaZonas));
        return p;
    }

    // =========================================================
    //  ABA 3 — ESTACOES
    // =========================================================
    private JPanel criarAbaEstacoes() {
        JPanel p = painelAba();
        p.setLayout(new GridLayout(1, 2, 12, 0));

        JPanel form = coluna();

        JPanel sec = secao("Cadastrar Estacao de Monitoramento");
        JPanel g = grid(4);
        g.add(rot("ID"));         fEstId        = campo(); g.add(fEstId);
        g.add(rot("Nome"));       fEstNome      = campo(); g.add(fEstNome);
        g.add(rot("Endereco"));   fEstEndereco  = campo(); g.add(fEstEndereco);
        g.add(rot("Descricao"));  fEstDescricao = campo(); g.add(fEstDescricao);
        sec.add(g, BorderLayout.CENTER);

        form.add(Box.createVerticalStrut(6));
        form.add(sec);
        form.add(Box.createVerticalStrut(12));
        form.add(fileiraBotoes(
                botao("Cadastrar Estacao", C_VERDE,  C_FUNDO,  e -> adicionarEstacao()),
                botao("Ver Estacoes",      C_ACENTO, C_FUNDO,  e -> verRede()),
                botao("Consolidar Dados",  C_BORDA,  C_TEXTO2, e -> coletarDadosRede())
        ));

        areaRede = areaTexto();
        verRede();

        p.add(form);
        p.add(scroll(areaRede));
        return p;
    }

    // =========================================================
    //  ABA 4 — RELATORIO
    // =========================================================
    private JPanel criarAbaRelatorio() {
        JPanel p = painelAba();
        p.setLayout(new BorderLayout(0, 10));

        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8));
        topo.setBackground(C_FUNDO);
        topo.add(botao("Gerar Relatorio", C_ACENTO, C_FUNDO,  e -> gerarRelatorio()));
        topo.add(Box.createHorizontalStrut(8));
        topo.add(botao("Limpar",          C_BORDA,  C_TEXTO2, e -> areaRelatorio.setText("")));
        p.add(topo, BorderLayout.NORTH);

        areaRelatorio = areaTexto();
        p.add(scroll(areaRelatorio), BorderLayout.CENTER);
        return p;
    }

    // =========================================================
    //  LOGICA — ABA 1
    // =========================================================
    private void analisarRisco() {
        try {
            String nomeProd = fProdNome.getText().trim();
            String email    = fProdEmail.getText().trim();
            String cidade   = fCidade.getText().trim();

            if (nomeProd.isEmpty() || cidade.isEmpty()) {
                aviso("Preencha o nome do produtor e a cidade."); return;
            }

            double  temp = Double.parseDouble(fTemp.getText().trim());
            double  umid = Double.parseDouble(fUmid.getText().trim());
            double  seca = Double.parseDouble(fSeca.getText().trim());
            boolean onda = chkOnda.isSelected();

            if (seca < 0 || seca > 10) {
                aviso("Indice de Seca deve estar entre 0 e 10."); return;
            }

            Regiao reg       = new Regiao(cidade, "");
            produtor         = new Produtor(nomeProd, email, reg);
            DadosAmbientais dados = new DadosAmbientais(temp, umid, seca, onda);

            MonitoramentoAmbiental monitor = new MonitoramentoAmbiental(reg);
            monitor.analisar(dados);
            double irrp   = monitor.getUltimoIRRP();
            Alerta alerta = sistemaAlertas.gerarAlerta(irrp, reg, dados);
            sistemaAlertas.notificarProdutor(produtor, alerta);

            areaMonitor.setText(montarResultado(dados, irrp, alerta));

        } catch (NumberFormatException ex) {
            erro("Digite valores numericos validos para temperatura, umidade e indice de seca.");
        }
    }

    private String montarResultado(DadosAmbientais dados, double irrp, Alerta alerta) {
        String sep = "-".repeat(46);
        return sep + "\n"
                + "  ORBEET  --  ANALISE DE RISCO\n"
                + sep + "\n\n"
                + "Produtor : " + produtor.getNome() + "\n"
                + "Cidade   : " + produtor.getRegiao().getNome() + "\n\n"
                + sep + "\n"
                + "DADOS AMBIENTAIS\n"
                + sep + "\n"
                + dados + "\n\n"
                + sep + "\n"
                + "INDICE DE RISCO (IRRP)\n"
                + sep + "\n"
                + "Valor  : " + String.format("%.1f", irrp) + " / 100\n"
                + "Nivel  : " + alerta.getNivel() + "\n"
                + barra(irrp) + "\n\n"
                + sep + "\n"
                + "ALERTA\n"
                + sep + "\n"
                + alerta.getTitulo() + "\n"
                + alerta.getDescricao() + "\n\n"
                + sep + "\n"
                + "RECOMENDACAO\n"
                + sep + "\n"
                + alerta.getRecomendacao() + "\n\n"
                + sep + "\n"
                + "NOTIFICACOES\n"
                + sep + "\n"
                + String.join("\n", produtor.getHistoricoNotificacoes());
    }

    private void limparMonitor() {
        fProdNome.setText("");  fProdEmail.setText("");
        fCidade.setText("");
        fTemp.setText("");      fUmid.setText("");  fSeca.setText("");
        chkOnda.setSelected(false);
        areaMonitor.setText("Preencha os dados e clique em Analisar Risco.");
    }

    // =========================================================
    //  LOGICA — ABA 2
    // =========================================================
    private void adicionarZona() {
        try {
            String id       = fZonaId.getText().trim();
            String nome     = fZonaNome.getText().trim();
            String endereco = fZonaEndereco.getText().trim();
            double area     = Double.parseDouble(fZonaArea.getText().trim());
            ZonaCultivo.TipoCultura cultura =
                    (ZonaCultivo.TipoCultura) cbCultura.getSelectedItem();

            if (id.isEmpty() || nome.isEmpty()) {
                aviso("ID e Nome sao obrigatorios."); return;
            }

            boolean idExiste = zonas.stream().anyMatch(z -> z.getId().equalsIgnoreCase(id));
            if (idExiste) {
                aviso("Ja existe uma zona com o ID \"" + id + "\". Use um ID diferente."); return;
            }

            zonas.add(new ZonaCultivo(id, nome, area, cultura,
                    endereco.isEmpty() ? "Endereco nao informado" : endereco));
            listarZonas();
            fZonaId.setText(""); fZonaNome.setText("");
            fZonaArea.setText(""); fZonaEndereco.setText("");

        } catch (NumberFormatException ex) {
            erro("Area deve ser um numero valido.");
        }
    }

    private void listarZonas() {
        if (areaZonas == null) return;
        String sep = "-".repeat(50);
        StringBuilder sb = new StringBuilder();
        sb.append("ZONAS DE CULTIVO CADASTRADAS\n").append(sep).append("\n");
        if (zonas.isEmpty()) {
            sb.append("Nenhuma zona cadastrada.");
        } else {
            zonas.forEach(z -> sb.append(z).append("\n"));
        }
        areaZonas.setText(sb.toString());
    }

    // =========================================================
    //  LOGICA — ABA 3
    // =========================================================
    private void adicionarEstacao() {
        String id        = fEstId.getText().trim();
        String nome      = fEstNome.getText().trim();
        String endereco  = fEstEndereco.getText().trim();
        String descricao = fEstDescricao.getText().trim();

        if (id.isEmpty() || nome.isEmpty()) {
            aviso("ID e Nome sao obrigatorios."); return;
        }

        boolean idExiste = rede.getEstacoes().stream().anyMatch(e -> e.getId().equalsIgnoreCase(id));
        if (idExiste) {
            aviso("Ja existe uma estacao com o ID \"" + id + "\". Use um ID diferente."); return;
        }

        EstacaoSensor est = new EstacaoSensor(
                id, nome,
                endereco.isEmpty()  ? "Endereco nao informado" : endereco,
                descricao.isEmpty() ? "Sem descricao"          : descricao);
        rede.adicionarEstacao(est);
        verRede();
        fEstId.setText(""); fEstNome.setText("");
        fEstEndereco.setText(""); fEstDescricao.setText("");
    }

    private void verRede() {
        if (areaRede != null) areaRede.setText(rede.gerarRelatorioRede());
    }

    private void coletarDadosRede() {
        DadosAmbientais media = rede.coletarDadosRede();
        if (media == null) {
            areaRede.setText("Nenhuma estacao com dados coletados ainda.\n\n"
                    + rede.gerarRelatorioRede());
            return;
        }
        double irrp = calculadora.calcularIndice(media);
        String sep  = "-".repeat(50);
        areaRede.setText(
                "DADOS CONSOLIDADOS DA REDE\n" + sep + "\n"
                        + "Media consolidada : " + media + "\n"
                        + "IRRP calculado    : " + String.format("%.1f", irrp)
                        + "  --  " + calculadora.classificarRisco(irrp) + "\n\n"
                        + sep + "\n"
                        + rede.gerarRelatorioRede());
    }

    // =========================================================
    //  LOGICA — ABA 4
    // =========================================================
    private void gerarRelatorio() {
        if (produtor == null) {
            produtor = new Produtor("—", "—", new Regiao("—", "—"));
        }
        areaRelatorio.setText(
                relatorioService.gerarRelatorioCompleto(produtor, zonas, rede));
    }

    // =========================================================
    //  CABECALHO E RODAPE
    // =========================================================
    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(C_PAINEL);
        p.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, C_BORDA),
                new EmptyBorder(14, 22, 14, 22)));

        JLabel titulo = new JLabel("ORBEET  SmartAgro Platform");
        titulo.setFont(F_TITULO);
        titulo.setForeground(C_ACENTO);

        JLabel sub = new JLabel("Monitoramento  |  Zonas de Cultivo  |  Estacoes  |  ODS 9");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(C_TEXTO2);

        p.add(titulo, BorderLayout.WEST);
        p.add(sub,    BorderLayout.EAST);
        return p;
    }

    private JPanel criarRodape() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(C_PAINEL);
        p.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, C_BORDA),
                new EmptyBorder(5, 0, 5, 0)));
        JLabel l = new JLabel(
                "ORBEET  (c) 2026  |  ODS 9 — Industria, Inovacao e Infraestrutura");
        l.setFont(new Font("SansSerif", Font.PLAIN, 11));
        l.setForeground(C_TEXTO2);
        p.add(l);
        return p;
    }

    // =========================================================
    //  HELPERS VISUAIS
    // =========================================================
    private JPanel painelAba() {
        JPanel p = new JPanel();
        p.setBackground(C_FUNDO);
        p.setBorder(new EmptyBorder(14, 14, 14, 14));
        return p;
    }

    private JPanel coluna() {
        JPanel p = new JPanel();
        p.setBackground(C_FUNDO);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }

    private JPanel secao(String titulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(C_PAINEL);
        p.setBorder(BorderFactory.createLineBorder(C_BORDA));

        JLabel lbl = new JLabel("  " + titulo.toUpperCase());
        lbl.setFont(new Font("Monospaced", Font.BOLD, 11));
        lbl.setForeground(C_ACENTO);
        lbl.setBackground(new Color(18, 22, 32));
        lbl.setOpaque(true);
        lbl.setBorder(new EmptyBorder(7, 10, 7, 10));
        p.add(lbl, BorderLayout.NORTH);
        return p;
    }

    private JPanel grid(int linhas) {
        JPanel g = new JPanel(new GridLayout(linhas, 2, 8, 6));
        g.setBackground(C_PAINEL);
        g.setBorder(new EmptyBorder(10, 12, 12, 12));
        return g;
    }

    private JLabel rot(String texto) {
        JLabel l = new JLabel(texto + ":");
        l.setFont(F_LABEL);
        l.setForeground(C_TEXTO2);
        return l;
    }

    private JTextField campo() {
        JTextField tf = new JTextField();
        tf.setBackground(C_CAMPO);
        tf.setForeground(C_TEXTO);
        tf.setCaretColor(C_ACENTO);
        tf.setFont(F_LABEL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(C_BORDA),
                new EmptyBorder(4, 7, 4, 7)));
        return tf;
    }

    private JTextArea areaTexto() {
        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setFont(F_MONO);
        ta.setBackground(C_SAIDA);
        ta.setForeground(C_SAIDATX);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(new EmptyBorder(12, 14, 12, 14));
        return ta;
    }

    private JScrollPane scroll(JTextArea ta) {
        JScrollPane sp = new JScrollPane(ta);
        sp.setBorder(BorderFactory.createLineBorder(C_BORDA));
        sp.getViewport().setBackground(C_SAIDA);
        return sp;
    }

    private JButton botao(String texto, Color bg, Color fg, ActionListener al) {
        JButton b = new JButton(texto);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(F_BTN);
        b.setFocusPainted(false);
        b.setBorder(new EmptyBorder(9, 18, 9, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(al);
        b.addMouseListener(new MouseAdapter() {
            final Color orig = bg;
            public void mouseEntered(MouseEvent e) { b.setBackground(orig.brighter()); }
            public void mouseExited (MouseEvent e) { b.setBackground(orig); }
        });
        return b;
    }

    private JPanel fileiraBotoes(JButton... botoes) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        p.setBackground(C_FUNDO);
        for (JButton b : botoes) p.add(b);
        return p;
    }

    private String barra(double irrp) {
        int blocos = (int) (irrp / 5);
        return "[" + "#".repeat(blocos) + "." .repeat(20 - blocos) + "]  "
                + String.format("%.0f%%", irrp);
    }

    private void aviso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atencao", JOptionPane.WARNING_MESSAGE);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}